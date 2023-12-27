package com.mdip.web.framework.base.service.impl;


import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.google.common.collect.Lists;
import com.mdip.common.util.DateUtil;
import com.mdip.common.util.FormatHandler;
import com.mdip.web.framework.base.dao.IDao;
import com.mdip.web.framework.base.exception.ServiceException;
import com.mdip.web.framework.base.service.IUploadFileService;
import com.mdip.web.framework.base.util.IdGen;
import com.mdip.web.framework.config.Config;
import com.mdip.web.framework.sysbase.entity.SysUpDownFile;
import com.mdip.web.framework.util.UserUtil;

/**
 * @author
 *
 */
@Service
public class UploadFileServiceImpl implements IUploadFileService {
	@Autowired
	public IDao sysUpDownFileDao;

	@Override
	public boolean delFileById(String id) {
		try {
			SysUpDownFile entity = new SysUpDownFile();
			entity = (SysUpDownFile) this.sysUpDownFileDao.load(id);
			if (entity != null) {
				File file1 = new File(entity.getUploadpath());
				this.sysUpDownFileDao.delete(id);
				if (file1.exists()) {
					file1.delete();
				}
			}
			return true;
		} catch (Exception e) {
			// e.printStackTrace();
			return false;

		}
	}

	@Override
	public boolean saveFileSuc(String id) {
		id = FormatHandler.isFormatIds(id);
		try {
			if (!"".equals(id) && id != null) {
				String hql = "update from SysUpDownFile set yn=1 where id in("
						+ id + ")";
				this.sysUpDownFileDao.hqlUpdate(hql);
			}
			return true;
		} catch (Exception e) {
			//e.printStackTrace();
			return false;
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.lantrack.framework.common.service.UpfileService#Upfile(java.lang.
	 * String, javax.servlet.http.HttpServletRequest, java.lang.String,
	 * java.lang.Boolean)
	 */
	@Override
	public List<SysUpDownFile> upFile(String sid, HttpServletRequest request, String type) throws ServiceException {
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
//		System.out.println(request.getSession().getServletContext().getRealPath(""));
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(	request.getSession().getServletContext());
		// 检查form中是否有enctype="multipart/form-data"	
		if (!multipartResolver.isMultipart(request)) {
			throw new ServiceException("form表单不是multipart/form-data类型!");
		}
		if(!this.checkFile(request)){
			throw new ServiceException("请选择要上传的文件!");
		}
		if(!this.checkFileType(request, type)){
			throw new ServiceException("文件类别不支持!");
		}
		List<SysUpDownFile> list =Lists.newArrayList();
		// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();

			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if(!file.isEmpty()){//类别不再判断	
					
					SysUpDownFile entity =	new SysUpDownFile();
					entity.setTablename(request.getParameter("tableName"));	
					entity.setTableid(request.getParameter("tableId"));	
					entity.setCont(file.getName());
					this.getSysUpDownFile(entity,file, sid, request);
					String hz=entity.getFilety();
					 /** 系统上传文件存放位置*/
					try {
						file.transferTo(new File(entity.getUploadpath()));
					} catch (IllegalStateException e1) {
						
						ServiceException se =  new ServiceException("非法文件"+e1.getMessage());
						se.initCause(e1);
						throw se;
					} catch (IOException e1) {
						ServiceException se =  new ServiceException("路径不对或磁盘空已满"+e1.getMessage());
						se.initCause(e1);
						throw se;
					}//不做事实	
					try{
					//String pathTemp=sysFileSource+ File.separator+entity.getSid()+ File.separator+entity.getNname();
//					String pathTemp=entity.getUploadpath();
//					if(!this.getFileType(entity, pathTemp)){
//						throw new ServiceException("入库失败！");
//					}
					this.sysUpDownFileDao.insert(entity);
				}catch (Exception e) {
					
					throw new ServiceException("入库失败！");
				}
					list.add(entity);
				}
			}
		return list;
	}
	public boolean getFileType(SysUpDownFile entity,String pathTemp){
		try {
//			String fileHouziTemp=FileType.getFileType(pathTemp);//路径，读取文件头信息，获得头信息code
//			int i = filename.lastIndexOf(".");
//            String houzui = filename.substring(i + 1);//
//			System.out.println(fileHouziTemp); 
//			System.out.println(entity.getFilety()); 
//			if(fileHouziTemp!=null){
//				return true;
//			}
			return true;
		} catch (Exception e) {
		}
		return false;
		
	}
	private  void getSysUpDownFile(SysUpDownFile entity,MultipartFile file,String sid,HttpServletRequest request){
	//	SysUpDownFile entity =	new SysUpDownFile();
		String nname = IdGen.uuid()+file.getOriginalFilename();	
    //	String tempPath=request.getSession().getServletContext().getRealPath("").substring(0,request.getSession().getServletContext().getRealPath("").length()-3);
    	String tempPath=request.getSession().getServletContext().getRealPath("");
		String dir =tempPath+Config.redirectPath+File.separator+sid+File.separator;
//		String dir =Config.redirectPath+File.separator+sid+File.separator;
		File fnn = new File(dir);//路径不存在新建路径
		if(!fnn.exists()) {
			fnn.mkdirs();
		}		
		String path =dir + nname;
		entity.setOname(file.getOriginalFilename());
		entity.setNname(nname);
		entity.setSid(sid);
		String filename =file.getOriginalFilename(); 
				int i = filename.lastIndexOf(".");
				String houzui = filename.substring(i + 1);//
		entity.setFilety(houzui);
		entity.setFilesize(file.getSize());
		entity.setUploadpath(path);
		//entity.setCont(file.getName());
		//return entity;
	}
	private boolean CheckFileType(String otype ,String type){
		if(null ==type||"".equals(type)||"*".equals(type)||"*.*".equals(type)){
			return true;
		}
		if(type.indexOf(otype)==-1){
			return false;
		}else{
			return true;			
		}
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.lantrack.framework.common.service.UpfileService#CheckFile(javax.servlet
	 * .http.HttpServletRequest)
	 */
	@Override
	public boolean checkFile(HttpServletRequest request) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		boolean ch = true;
		if (this.CheckMul(request, multipartResolver)) {// 得是正常的
			// 将request变成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
			int conut =0;
			// 获取multiRequest 中所有的文件名
			Iterator iter = multiRequest.getFileNames();
			
			while (iter.hasNext()) {
				// 一次遍历所有文件
				MultipartFile file = multiRequest.getFile(iter.next().toString());
				if(!file.isEmpty()){
					conut++;
				}
				if(conut>0){
					break; //跳出
				}
			}
			if(conut==0){
				ch =false; 
			}

		} else {
			ch = false;
		}
		return ch;

	}
	@Override
	public boolean checkFileType(HttpServletRequest request,String type){
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		boolean ch = true;
	
			// 将request变成多部分request
						MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
						int conut =0;
						// 获取multiRequest 中所有的文件名
						Iterator iter = multiRequest.getFileNames();
						
						while (iter.hasNext()) {
							// 一次遍历所有文件
							MultipartFile file = multiRequest.getFile(iter.next().toString());
							String filename =file.getOriginalFilename(); 
							int i = filename.lastIndexOf(".");
							String houzui = filename.substring(i + 1);//
							if(!file.isEmpty()&&!this.CheckFileType(houzui, type)){//不为空，并类别不在指定格式里面
								conut++;
							}
							if(conut>0){
								ch =false;
								break; //跳出
							}
						}
						if(conut>0){
							ch =false;							
						}
			return ch;
	}
	@Override
	public boolean checkMul(HttpServletRequest request) {
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		return this.CheckMul(request, multipartResolver);
	}

	private boolean CheckMul(HttpServletRequest request,
			CommonsMultipartResolver multipartResolver) {
		if (multipartResolver.isMultipart(request)) {
			return true;
		} else {
			return false;
		}
	}

	/* (non-Javadoc)
	 * @see net.lantrack.framework.common.service.UpfileService#getSysUpDownFileById(java.lang.String)
	 */
	@Override
	public SysUpDownFile getSysUpDownFileById(String id) throws ServiceException {
		return (SysUpDownFile) this.sysUpDownFileDao.load(id);
		
	}
	/**
	 * 指定上传某一个附件；如果没有返回 
	 * @param sid 存放的位置
	 * @param tableName 表名
	 * @param request  当前的请求
	 * @param typeclass 类别
	 * @param cont默认不传，使用file的name，如果传将指定字段上传  约定不file_cont 为当前默认附件值；
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public SysUpDownFile saveFileOne(String sid,String tableId ,String tableName,String cont,HttpServletRequest request, String typeclass)
			throws ServiceException {
		SysUpDownFile entity =	new SysUpDownFile();
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
				CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(	request.getSession().getServletContext());
				// 检查form中是否有enctype="multipart/form-data"	
				if (!multipartResolver.isMultipart(request)) {
				//	throw new ServiceException("form表单不是multipart/form-data类型!");
					return entity;
				}
				
				if(!this.checkFile(request)){
					//throw new ServiceException("请选择要上传的文件!");
					return entity;
				}
				if(!this.checkFileType(request, typeclass)){
					//throw new ServiceException("文件类别不支持!");
					return entity;
				}
				
				
				// 将request变成多部分request
					MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

					// 获取multiRequest 中所有的文件名
					Iterator iter = multiRequest.getFileNames();

					while (iter.hasNext()) {
						// 一次遍历所有文件
						MultipartFile file = multiRequest.getFile(iter.next().toString());
						if(!file.isEmpty()){//类别不再判断	
							String file_cont = "file_"+cont;//约定用法，防止类别转换失败
							if(file_cont.equals(file.getName())){							
								entity.setTablename(tableName);
								entity.setTableid(tableId);
								entity.setCont(cont);
								this.getSysUpDownFile(entity,file, sid, request);
								try {
									file.transferTo(new File(entity.getUploadpath()));
								} catch (IllegalStateException e1) {
									
									ServiceException se =  new ServiceException("非法文件"+e1.getMessage());
									se.initCause(e1);
									throw se;
								} catch (IOException e1) {
									ServiceException se =  new ServiceException("路径不对或磁盘空已满"+e1.getMessage());
									se.initCause(e1);
									throw se;
								}
								//不做事实	
								try{
									this.sysUpDownFileDao.insert(entity);
									}catch (Exception e) {
										
										throw new ServiceException("入库失败！");
									}
								break;
							}
							
						
						}
					}
				return entity;
	}
	/**
	 * 指定上传某一个附件；如果没有返回 
	 * @param sid 存放的位置
	 * @param tableName 表名
	 * @param request  当前的请求
	 * @param typeclass 类别
	 * @param cont默认不传，使用file的name，如果传将指定字段上传  约定不file_cont 为当前默认附件值；
	 * @return
	 * @throws ServiceException
	 */
	@Override
	public SysUpDownFile updateFileOne(String sid,String fileId ,String tableId,String tableName,String cont,HttpServletRequest request, String typeclass)
			throws ServiceException {
		SysUpDownFile entity =	new SysUpDownFile();
		// 将当前上下文初始化给 CommonsMutipartResolver （多部分解析器）
				CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(	request.getSession().getServletContext());
				// 检查form中是否有enctype="multipart/form-data"	
				if (!multipartResolver.isMultipart(request)) {
				//	throw new ServiceException("form表单不是multipart/form-data类型!");
					return entity;
				}
				
				if(!this.checkFile(request)){
					//throw new ServiceException("请选择要上传的文件!");
					return entity;
				}
				if(!this.checkFileType(request, typeclass)){
					//throw new ServiceException("文件类别不支持!");
					return entity;
				}
				
				
				// 将request变成多部分request
					MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;

					// 获取multiRequest 中所有的文件名
					Iterator iter = multiRequest.getFileNames();

					while (iter.hasNext()) {
						// 一次遍历所有文件
						MultipartFile file = multiRequest.getFile(iter.next().toString());
						if(!file.isEmpty()){//类别不再判断	
							String file_cont = "file_"+cont;//约定用法，防止类别转换失败
							if(file_cont.equals(file.getName())){	
								if(StringUtils.isNotBlank(fileId)){
									this.delFileById(fileId);//删除历史的附件
								}
								entity.setTablename(tableName);
								entity.setTableid(tableId);
								
								entity.setCont(cont);
								this.getSysUpDownFile(entity,file, sid, request);
								try {
									file.transferTo(new File(entity.getUploadpath()));
								} catch (IllegalStateException e1) {
									
									ServiceException se =  new ServiceException("非法文件"+e1.getMessage());
									se.initCause(e1);
									throw se;
								} catch (IOException e1) {
									ServiceException se =  new ServiceException("路径不对或磁盘空已满"+e1.getMessage());
									se.initCause(e1);
									throw se;
								}
								//不做事实	
								try{
									this.sysUpDownFileDao.insert(entity);
									}catch (Exception e) {
										
										throw new ServiceException("入库失败！");
									}
								break;
							}
							
						
						}
					}
				return entity;
	}
	/**
	 * 
	 */
	private void delFileByTableTemp(){
		
	}
	@Override
	public boolean updateFileBySaveAfter(String tableName, String tableId) {
		// TODO Auto-generated method stub
		StringBuffer hql =new StringBuffer("update  from ").append("SysUpDownFile set yn=1  , tableid='").append(tableId)
				.append("' where tableid='' and").append("tablename ='").append(tableName).append("' and create_by='").append(UserUtil.getCurrentUser()).append("'");
		return this.sysUpDownFileDao.hqlUpdate(hql.toString());
	}
	@Override
	public boolean updateFileByUpdateAfter(String tableName, String tableId) {
		// TODO Auto-generated method stub
		StringBuffer hql =new StringBuffer("update  from ").append("SysUpDownFile set yn=1 ")
				.append(" where tableid='").append(tableId).append("' and tablename ='").append(tableName).append("' and create_by='").append(UserUtil.getCurrentUser()).append("'");
		return this.sysUpDownFileDao.hqlUpdate(hql.toString());
	}

	@Override
	public List<SysUpDownFile> queryFileListByTables(String tableid, String coName) {
		StringBuffer hql=new StringBuffer();
		hql.append("select o from SysUpDownFile o where o.del_flag=0 and o.yn=1");
		hql.append(" and o.tableid='").append(tableid).append("'").append(" and o.cont='").append(coName).append("'");
		List<SysUpDownFile> list = this.sysUpDownFileDao.query(hql.toString());
		if(list==null){
			list= Lists.newArrayList();
		}
		return list ;
	}

	@Override
	public List<SysUpDownFile> queryFileListByTemp(String tableName, String coName) {
		StringBuffer hql=new StringBuffer();
		hql.append("select o from SysUpDownFile o where o.del_flag=0 and o.yn=0");
		hql.append(" and o.tablename='").append(tableName).append("'").append(" and o.cont='").append(coName).append("'")
		.append(" and create_by='").append(UserUtil.getCurrentUser()).append("'");
		List<SysUpDownFile> list = sysUpDownFileDao.query(hql.toString());
		if(list==null){
			list= Lists.newArrayList();
		}
		return list ;
	}

	@Override
	public boolean delFileByTableId(String tableId) throws ServiceException {
		StringBuffer hql =new StringBuffer("update  from ").append("SysUpDownFile set del_flag=1  , update_by='").append(UserUtil.getCurrentUser()).append("' , update_date='").append(DateUtil.getDateTime()).append("' where ").append("tableid ='").append(tableId).append("')");
		return this.sysUpDownFileDao.hqlUpdate(hql.toString());
	
	}

}
