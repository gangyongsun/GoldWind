package com.mdip.web.framework.sysbase.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdip.common.util.DateUtil;
import com.mdip.common.util.FormatHandler;
import com.mdip.web.framework.base.dao.IDao;
import com.mdip.web.framework.base.entity.PageEntity;
import com.mdip.web.framework.base.exception.ServiceException;
import com.mdip.web.framework.base.service.BaseService;
import com.mdip.web.framework.base.service.IPageService;
import com.mdip.web.framework.base.util.Encodes;
import com.mdip.web.framework.security.Digests;
import com.mdip.web.framework.sysbase.entity.SysRole;
import com.mdip.web.framework.sysbase.entity.SysUser;
import com.mdip.web.framework.sysbase.entity.SysUserSysMenu;
import com.mdip.web.framework.sysbase.entity.SysUserSysRole;
import com.mdip.web.framework.sysbase.service.ISysUserService;
import com.mdip.web.framework.util.UserUtil;

@Service
public class SysUserServiceImpl extends BaseService implements ISysUserService{
	@Autowired
	protected IPageService pageService;

	@Autowired
	protected IDao sysUserDao;

	@Autowired
	protected IDao sysRoleDao;
	@Override
	public Boolean deleteById(Serializable id,String update_by)throws ServiceException {

		//物理删除
		//this.sysDictDao.delete(id);		
		//物理删除结束
		//逻辑删除
		/*  String tid =(String) id;
			  SysUser entity =(SysUser) this.sysUserDao.load(tid);
			  entity.setDel_flag(entity.DEL_FLAG_DELETE);//0正常 1 逻辑删除，2，审核
			   entity.setUpdate_by(update_by);
			   entity.setUpdate_date(new Date());
			  this.sysDictDao.update(entity);*/
		return this.deleteByIds("'"+id+"'", update_by);
		//逻辑删除结束


	}

	@Override
	@Transactional//加事务
	public Boolean deleteByIds(String ids,String update_by) throws ServiceException {
		//说明 string 格式要求"'id','id','id'"
		//TODO
		//格式校验 	
		ids=FormatHandler.isFormatIds(ids);
		if(ids==null){
			throw new ServiceException("SysUserServiceImpl deleteByIds  ids= " + ids + "格式不对");			

		}
		EntityManager em=this.sysUserDao.getEm();
		StringBuffer hql =new StringBuffer("delete  from ").append("SysUserSysMenu").append(" where ").append("sys_user_id in (").append(ids).append(")");
		em.createQuery(hql.toString()).executeUpdate();	//简单一些 直接物理删除掉旧的 ，再把新的插入即可；

		//物理删除 （提高效果使用 HQL执行 ，而不使用实体	）		
		//StringBuffer sql =new StringBuffer("delete  from ").append("sys_user").append(" where ").append("id in (").append(ids).append(")");
		// StringBuffer hql =new StringBuffer("delete  from ").append("SysUser").append(" where ").append("id in(").append(ids).append(")");

		//物理删除结束			
		//逻辑删除
		//StringBuffer sql =new StringBuffer("update ").append("sys_user set del_flag=1  , update_by='").append(update_by).append("' , update_date='").append(net.lantrack.common.util.DateUtil.getDateTime()).append("' where ").append("id in (").append(ids).append(")");
		hql =new StringBuffer("update  from ").append("SysUser set del_flag=1  , update_by='").append(update_by).append("' , update_date='").append(DateUtil.getDateTime()).append("' where ").append("id in (").append(ids).append(")");
		//逻辑删除结束
		em.createQuery(hql.toString()).executeUpdate();	
		//this.sysUserDao.sqlDelete(sql.toString());//执行sql 
		//this.sysUserDao.hqlDelete(hql.toString())
		return true;//执行HQL的方法

	}

	@Override
	public List<SysUser> queryByWhere(SysUser entity) throws ServiceException{
		// TODO 自己加条件	
		StringBuffer hql=new StringBuffer("from SysUser o  where");
		hql.append(" o.del_flag='").append(entity.DEL_FLAG_NORMAL).append("'");

		return this.sysUserDao.query(hql.toString());


	}

	@Override
	public SysUser queryById(String id)throws ServiceException {
		if(id==null){
			throw new ServiceException("SysUserServiceImpl queryById  id= " + id + "格式不对");			
		}
		return (SysUser)this.sysUserDao.load(id);
	}



	@Override
	public void getPage(SysUser entity, PageEntity pageentity) throws ServiceException{

		this.pageService.getPage(pageentity.getPageSize(),
				pageentity.getCurrentPage());
		//TODO 自动生成的代码，包括等值查询，LIKE查询，非查询，右左连接待开发完善中


		StringBuffer hql=new StringBuffer();
		hql.append("o.id!='admin' and o.id!='visitor' and  o.del_flag=").append(entity.DEL_FLAG_NORMAL);
		
		if(entity.getLogin_name()!=null && !entity.getLogin_name().equals("")){ //查询字段:姓名；搜索方式为：like

			hql.append(" and o.login_name like '%").append(entity.getLogin_name()).append("%'"); //like查询
		}

		if(entity.getName()!=null && !entity.getName().equals("")){ //查询字段:姓名；搜索方式为：like

			hql.append(" and o.name like '%").append(entity.getName()).append("%'"); //like查询
		}
		
		if(StringUtils.isNotBlank(entity.getSys_office_id())){
			hql.append(" and o.sys_office_id like '%").append(entity.getSys_office_id()).append("%'");
		}
		//条件结束
		
		if(!UserUtil.getCurrentUser().equals("admin")){
			SysUser u=this.getByLoginName(UserUtil.getCurrentUser());
			if(u==null){
				throw new ServiceException("找不到当前登录用户");
			}
			StringBuffer hqla=new StringBuffer();
			hqla.append("SELECT o1.id FROM SysOffice o1 where o1.del_flag=0 ");
			String[] office_arry=u.getSys_office_id().split(",");
			for(int i=0;i<office_arry.length;i++){
				if(i==0){
					hqla.append(" and (o1.parent_ids like '%").append(office_arry[i]).append("%' or o1.id = '").append(office_arry[i]).append("'");
				}else{
					hqla.append(" or o1.parent_ids like '%").append(office_arry[i]).append("%' or o1.id = '").append(office_arry[i]).append("'");
				}				
			}
			hqla.append(")");
			EntityManager em=this.sysUserDao.getEm();
			List<String> officeList=em.createQuery(hqla.toString()).getResultList();
			em.close();
			for(int j=0;j<officeList.size();j++){
				if(j==0){
					hql.append(" and (o.sys_office_id like '%").append(officeList.get(j)).append("%'");
				}else{
					hql.append(" or o.sys_office_id like '%").append(officeList.get(j)).append("%'");
				}
			}
			
			/*if(u.getSys_office_type().equals("area")){
				hql.append(" or  o1.parent_ids like '%community%' or o1.id='community'");
			}*/
			hql.append(")");
		}

		PageEntity init= this.pageService.doListbywhere("o", "SysUser o", hql.toString(), pageentity.getOrderBy());
		this.setpage(pageentity, init);

	}

	@Override
	public Boolean update(SysUser entity) throws ServiceException{
		
			return this.sysUserDao.update(entity);


	}

	@Override
	public Boolean save(SysUser entity)throws ServiceException {
		
		return	this.sysUserDao.insert(entity);
		

	}


	@Override
	public Boolean deleteByIdsRe(String ids,String update_by)throws ServiceException  {
		//说明 string 格式要求"'id','id','id'"
		//TODO
		//格式校验 	
		ids=FormatHandler.isFormatIds(ids);

		if(ids==null){
			throw new ServiceException("SysUserService deleteByIdsRe  ids= " + ids + "格式不对");			

		}
		//物理删除 （提高效果使用 HQL执行 ，而不使用实体	）		
		//StringBuffer sql =new StringBuffer("delete  from ").append("sys_user").append(" where ").append("id in (").append(ids).append(")");
		// StringBuffer hql =new StringBuffer("delete  from ").append("SysUser").append(" where ").append("id in(").append(ids).append(")");

		//物理删除结束			
		//逻辑删除
		//StringBuffer sql =new StringBuffer("update ").append("sys_user set del_flag=0  , update_by='").append(update_by).append("' , update_date='").append(net.lantrack.common.util.DateUtil.getDateTime()).append("' where ").append("id in (").append(ids).append(")");
		StringBuffer hql =new StringBuffer("update  from ").append("SysUser set del_flag=0  , update_by='").append(update_by).append("' , update_date='").append(DateUtil.getDateTime()).append("' where ").append("id in (").append(ids).append(")");
		//逻辑删除结束

		//this.sysUserDao.sqlDelete(sql.toString());//执行sql 
		return this.sysUserDao.hqlDelete(hql.toString());//执行HQL的方法

	}

	@Override
	public void getPageRe(SysUser entity, PageEntity pageentity)throws ServiceException {

		this.pageService.getPage(pageentity.getPageSize(),
				pageentity.getCurrentPage());
		//TODO 自动生成的代码，包括等值查询，LIKE查询，非查询，右左连接待开发完善中


		StringBuffer hql=new StringBuffer();
		hql.append(" o.del_flag=").append(entity.DEL_FLAG_DELETE);

		if(entity.getName()!=null && !entity.getName().equals("")){ //查询字段:姓名；搜索方式为：like

			hql.append(" and o.name like '%").append(entity.getName()).append("%'"); //like查询
		}//条件结束
		PageEntity init = pageService.doListbywhere("o", "SysUser o", hql.toString(), pageentity.getOrderBy());
		this.setpage(pageentity, init);

	}
	@Override
	public Map getAll() throws ServiceException{			

		List<SysUser> list = this.getAll(new SysUser());		
		Map map = new HashMap();		
		for(int id=0;id<list.size();id++){
			SysUser entity  = list.get(id);
			map.put(entity.getId(), entity.getName());//所有表都得有个名字，默认都有。如果，自己处理吧；					
		}
		return map;
	}
	
	@Override
	public Map getAllUserMap() throws ServiceException{			
		
		List<SysUser> list = this.getAll(new SysUser());		
		Map map = new HashMap();		
		for(int id=0;id<list.size();id++){
			SysUser entity  = list.get(id);
			map.put(entity.getLogin_name(), entity.getName());//key为登录名，value为用户姓名					
		}
		return map;
	}
	
	@Override
	public Map getUserMap() {			
		//查询所有用户包含未删除的
		List<SysUser> list=this.sysUserDao.query("select o from SysUser o");		
		Map map = new HashMap();		
		for(int id=0;id<list.size();id++){
			SysUser entity  = list.get(id);
			map.put(entity.getLogin_name(), entity);
		}
		return map;
	}

	@Override
	public List<SysUser> getAll(SysUser entity)throws ServiceException {

		StringBuffer hql=new StringBuffer();
		hql.append("select o from SysUser o where o.del_flag=").append(entity.DEL_FLAG_NORMAL);

		if(entity.getName()!=null && !entity.getName().equals("")){ //查询字段:姓名；搜索方式为：like

			hql.append(" and o.name like '%").append(entity.getName()).append("%'"); //like查询
		}//条件结束

		return this.sysUserDao.query(hql.toString());

	}

	//配置相关代码 根据表名自动生成	约定设计 表名_id模式	
	public String getSysRole(String sysUserId) throws ServiceException{
		StringBuffer hql = new StringBuffer("select o.sys_role_id from SysUserSysRole o where  o.del_flag=0 and o.sys_user_id='").append(sysUserId).append("'");

		List list =	this.sysUserDao.query(hql.toString());
		hql.delete(0, hql.length());
		for(int id=0;id<list.size();id++){
			if(id==list.size()-1){
				hql.append(list.get(id).toString());
			}else{
				hql.append(list.get(id).toString()).append(",");
			}
		}		
		return hql.toString();

	}

	@Override
	@Transactional//加事务
	public boolean configSysRole(String id, String ids,String update_by )throws ServiceException{

		if(ids==null){
			//throw new ServiceException("configSysRole   ids= " + ids + "格式不对");	
			ids="";
		}

		EntityManager em=this.sysUserDao.getEm();//开启事实，得到事实
		//StringBuffer hql =new StringBuffer("update  from ").append("SysUserSysRole set del_flag=1  , update_by='").append(update_by).append("' , update_date='").append(net.lantrack.common.util.DateUtil.getDateTime()).append("' where ").append("sys_user_id ='").append(id).append("'");

		StringBuffer hql =new StringBuffer("delete  from ").append("SysUserSysRole").append(" where ").append("sys_user_id ='").append(id).append("'");
		em.createQuery(hql.toString()).executeUpdate();	//简单一些 直接物理删除掉旧的 ，再把新的插入即可；				
		if(ids.endsWith(",")){
			ids = ids.substring(0,ids.length()-1);
		}
		if(!"".equals(ids)){
			String[] temp= ids.split(",");
			for(int tem=0;tem<temp.length;tem++){
				SysUserSysRole entity = new SysUserSysRole();
				entity.setSys_user_id(id);
				entity.setSys_role_id(temp[tem].toString());
				entity.setCreate_by(update_by);
				entity.setUpdate_by(update_by);
				em.persist(entity);				
			}
		}	
		return true;
	}
	
	@Transactional//加事务
	public boolean configSysMenu(String id, String role_ids,String menu_ids,String update_by )throws ServiceException{
		EntityManager em=this.sysUserDao.getEm();//开启事实，得到事实
		StringBuffer hql =new StringBuffer("delete  from ").append("SysUserSysMenu").append(" where ").append("sys_user_id ='").append(id).append("'");
		em.createQuery(hql.toString()).executeUpdate();	//简单一些 直接物理删除掉旧的 ，再把新的插入即可
		/*if(role_ids==null){
			//throw new ServiceException("configSysRole   ids= " + id + "格式不对");	
			role_ids="";
		}

		EntityManager em=this.sysUserDao.getEm();//开启事实，得到事实
		
		//将sys_role_sys_menu中的数据加入表sys_user_sys_menu中
		StringBuffer hql =new StringBuffer("delete  from ").append("SysUserSysMenu").append(" where ").append("sys_user_id ='").append(id).append("'");
		em.createQuery(hql.toString()).executeUpdate();	//简单一些 直接物理删除掉旧的 ，再把新的插入即可；
		if(!"".equals(role_ids)){
			Query query = em.createQuery("select o.sys_menu_id from SysRoleSysMenu o where o.del_flag=0 and o.sys_role_id in ("+FormatHandler.isFormatIds(role_ids)+")");
			List result = query.getResultList();
			for(Object menu_id:result){
				SysUserSysMenu m_entity=new SysUserSysMenu();
				m_entity.setSys_user_id(id);
				m_entity.setSys_menu_id(menu_id.toString());
				m_entity.setCreate_by(update_by);
				m_entity.setUpdate_by(update_by);
				em.persist(m_entity);	
			}
		}*/
		//再将界面勾选的权限加入表sys_user_sys_menu中
		if(menu_ids.endsWith(",")){
			menu_ids = menu_ids.substring(0,menu_ids.length()-1);
		}
		if(!"".equals(menu_ids)){
			String[] temp= menu_ids.split(",");
			for(int tem=0;tem<temp.length;tem++){
				SysUserSysMenu entity = new SysUserSysMenu();
				entity.setSys_user_id(id);
				entity.setSys_menu_id(temp[tem].toString());
				entity.setCreate_by(update_by);
				entity.setUpdate_by(update_by);
				em.persist(entity);				
			}
		}
		return true;
	}
	
	public String getSysMenuByUserId(String sysUser_Id) throws ServiceException{
		StringBuffer hql = new StringBuffer("select o.sys_menu_id from SysUserSysMenu o where  o.del_flag=0 and o.sys_user_id='").append(sysUser_Id).append("'");
		List list =	this.sysUserDao.query(hql.toString());
		hql.delete(0, hql.length());
		for(int id=0;id<list.size();id++){
			if(id==list.size()-1){
			hql.append(list.get(id).toString());
			}else{
			hql.append(list.get(id).toString()).append(",");
			}
		}		
		return hql.toString();
	}
	
	/*public String getSysMenuByOfficeId(String sysOffice_Id) throws ServiceException{
		StringBuffer hql = new StringBuffer("select o.sys_menu_id from SysRoleSysMenu o where  o.del_flag=0 and o.sys_role_id in (select o1.id from SysRole o1 where o1.del_flag=0 and o1.if_admin in(").append(FormatHandler.isFormatIds(sysOffice_Id)).append("))");
		List list =	this.sysUserDao.query(hql.toString());
		hql.delete(0, hql.length());
		for(int id=0;id<list.size();id++){
			if(id==list.size()-1){
			hql.append(list.get(id).toString());
			}else{
			hql.append(list.get(id).toString()).append(",");
			}
		}		
		return hql.toString();
	}*/

	/*	public SysUser Login(String name,String md5pwd,String ip){//简单一些，先用起来，后加

			try {			
			List<SysUser> list =this.sysUserDao.query("select o from SysUser o where o.del_flag='0' and o.login_name='"+name+"' and o.password='"+md5pwd+"'");
				if(list.size()==1){
					SysUser entity = (SysUser)list.get(0);	
					if(!"0".equals(entity.getLogin_flag())){
					entity.setLogin_ip(ip);
					entity.setLogin_date(net.lantrack.common.util.DateUtil.getDateTime());
					this.update(entity);
					}
					return entity;
				}else{
					return null;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
				return null;
			}
		}*/

	@Override
	public void updateUserLoginInfo(SysUser user) throws ServiceException{
		try {
			this.update(user);
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());	

		}

	}

	@Override
	public SysUser getByLoginName(String loginName)throws ServiceException {
		List<SysUser> list=null;
		try {
			list =this.sysUserDao.query("select o from SysUser o where o.del_flag=0 and o.login_name='"+loginName+"'");
			
		} catch (Exception e) {
			throw new ServiceException("getByLoginName   loginName= " + loginName + "格式不对");	
		}
		if(list==null){
			return null;
		}
		if(list.size()==1){
			return (SysUser)list.get(0);
		}else{
			return null;
		}

	}

	@Override
	public List<SysRole> getSysRoleList(String sysUserId)throws ServiceException {

		String ids = this.getSysRole(sysUserId);
		return sysRoleDao.query("select o from SysRole o where o.id in ('"+ids+"')");

	}



	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}

	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 * 	
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}
	
	@Override
	public void getPage(String hql, PageEntity paramPageEntity) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getPage(String tableName, String where, String orderBy, PageEntity paramPageEntity) {
		// TODO Auto-generated method stub
		
	}	

}
