package com.mdip.web.framework.sysbase.service.impl;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mdip.common.util.DateUtil;
import com.mdip.common.util.FormatHandler;
import com.mdip.web.framework.base.dao.IDao;
import com.mdip.web.framework.base.entity.PageEntity;
import com.mdip.web.framework.base.exception.ServiceException;
import com.mdip.web.framework.base.service.BaseService;
import com.mdip.web.framework.base.service.IPageService;
import com.mdip.web.framework.config.Config;
import com.mdip.web.framework.sysbase.entity.SysMenu;
import com.mdip.web.framework.sysbase.entity.SysMenuSys;
import com.mdip.web.framework.sysbase.service.ISysMenuService;

@Service
public class SysMenuServiceImpl extends BaseService implements ISysMenuService{
	@Autowired
	protected IPageService pageService;

	@Autowired
	protected IDao sysMenuDao;
	
		@Override
	public Boolean deleteById(Serializable id,String update_by) throws ServiceException {
	
			//物理删除
			//this.sysDictDao.delete(id);		
			//物理删除结束
			//逻辑删除
			/*  String tid =(String) id;
			  SysMenu entity =(SysMenu) this.sysMenuDao.load(tid);
			  entity.setDel_flag(entity.DEL_FLAG_DELETE);//0正常 1 逻辑删除，2，审核
			   entity.setUpdate_by(update_by);
			   entity.setUpdate_date(new Date());
			  this.sysDictDao.update(entity);*/
		return	 this.deleteByIds("'"+id+"'", update_by);
			//逻辑删除结束
			
		
	}
	
		@Override
		@Transactional
	public Boolean deleteByIds(String ids,String update_by) throws ServiceException {
		//说明 string 格式要求"'id','id','id'"
		//TODO
		//格式校验 	
		
		if(ids==null){
			throw new ServiceException("SysMenuService deleteByIds  ids= " + ids + "格式不对");			
		
		}
	
			
			EntityManager em=this.sysMenuDao.getEm();//开启事实，得到事实
			//删除前做parent_id 与parent_ids置换更新操作；//开启事实
			String temp[] = ids.split(",");
			StringBuffer up =new StringBuffer();
			for(int id=0;id<temp.length;id++){
				SysMenu treePlacetype = 	this.queryById(temp[id]);		//	1、找到自己	
				em.createQuery(up.append("update from SysMenu set parent_id='").append(treePlacetype.getParent_id()).append("'")
						.append(", parent_ids='").append(treePlacetype.getParent_ids()).append("'")
						.append(", full_name='").append(treePlacetype.getFull_name()).append("'")
						.append(", parent_name='").append(treePlacetype.getParent_name()).append("'")
						.append(" where parent_id='").append(treePlacetype.getId()).append("'")
						.toString()).executeUpdate();//2、将自己的儿子让爷级接管
				up.delete(0, up.length());
				StringBuffer hql =new StringBuffer("update  from ").append("SysMenu set del_flag=1  , update_by='").append(update_by).append("' , update_date='").append(DateUtil.getDateTime()).append("' where ").append("id in ('").append(temp[id]).append("')");
					em.createQuery(hql.toString()).executeUpdate();//3、再自杀
			
		
			//物理删除 （提高效果使用 HQL执行 ，而不使用实体	）		
			//StringBuffer sql =new StringBuffer("delete  from ").append("sys_menu").append(" where ").append("id in (").append(ids).append(")");
			// StringBuffer hql =new StringBuffer("delete  from ").append("SysMenu").append(" where ").append("id in(").append(ids).append(")");
			
			//物理删除结束			
			//逻辑删除
			//StringBuffer sql =new StringBuffer("update ").append("sys_menu set del_flag=1  , update_by='").append(update_by).append("' , update_date='").append(net.lantrack.common.util.DateUtil.getDateTime()).append("' where ").append("id in (").append(ids).append(")");
			//StringBuffer hql =new StringBuffer("update  from ").append("SysMenu set del_flag=1  , update_by='").append(update_by).append("' , update_date='").append(net.lantrack.common.util.DateUtil.getDateTime()).append("' where ").append("id in (").append(ids).append(")");
			//逻辑删除结束
			
			//this.sysMenuDao.sqlDelete(sql.toString());//执行sql 
			//this.sysMenuDao.hqlDelete(hql.toString());//执行HQL的方法
			}
			return true;
	}
	
		@Override
	public List<SysMenu> queryByWhere(SysMenu entity) throws ServiceException {
		// TODO 自己加条件	
		StringBuffer hql=new StringBuffer("from SysMenu o  where");
		hql.append(" o.del_flag='").append(entity.DEL_FLAG_NORMAL).append("'");
		
			return this.sysMenuDao.query(hql.toString());
		

	}
	
		@Override
	public SysMenu queryById(String id) throws ServiceException {
		
			return (SysMenu)this.sysMenuDao.load(id);
		

	}
	
	

	@Override
	public void getPage(SysMenu entity, PageEntity pageentity) throws ServiceException {
		
			this.pageService.getPage(pageentity.getPageSize(),
					pageentity.getCurrentPage());
			//TODO 自动生成的代码，包括等值查询，LIKE查询，非查询，右左连接待开发完善中
			
			
			StringBuffer hql=new StringBuffer();
			hql.append(" o.del_flag=").append(entity.DEL_FLAG_NORMAL);
			
			if(entity.getId()!=null && !entity.getId().equals("")){ //查询字段:编号；搜索方式为：=
			       					
			hql.append(" and o.id = '").append(entity.getId()).append("'");
						//AND a.id = #{id}
			}//条件结束
			
			
			if(entity.getName()!=null && !entity.getName().equals("")){ //查询字段:名称；搜索方式为：like
			       					
			hql.append(" and o.name like '%").append(entity.getName()).append("%'"); //like查询
			}//条件结束
			
			//根据parent找自己及自己的儿子
			if(entity.getParent_id()!=null && !entity.getParent_id().equals("")){ //查询字段:名称；搜索方式为：like
				hql.append(" and o.parent_id = '").append(entity.getParent_id()).append("'"); //like查询 把自己儿子都找出来
				hql.append(" or o.id='").append(entity.getParent_id()).append("'");//把自己搜索出来
			}//条件结束
	
	
			
			PageEntity init = this.pageService.doListbywhere("o", "SysMenu o", hql.toString(), pageentity.getOrderBy());
			this.setpage(pageentity, init);

	}
	
	@Override
	@Transactional //开启事实，数据得支持事实才行 mysql 表类型为MyISAM不支持事实，InnoDB支持，视情况用吧
	public Boolean update(SysMenu entity) throws ServiceException {
					
			SysMenu temp =	this.queryById(entity.getId());//得到自己
			
			//if(temp.getParent_id().equals(entity.getParent_id()))//判断自己是否换了节点
			//{
			//	this.sysMenuDao.update(entity);
			//}else{//换了节点，更新自己子孙们的full_name 与parent_pids
				EntityManager em = this.sysMenuDao.getEm(); //得到事实
				
				StringBuffer hql =new StringBuffer("update  from ")
				.append("SysMenu set update_by='").append(entity.getUpdate_by()).append("' , ")//更新者
				.append("update_date='").append(entity.getUpdate_date()).append("' , ")//更新时间
				.append("full_name=REPLACE(").append("full_name").append(",'").append(temp.getFull_name()).append("','").append(entity.getFull_name()).append("')").append(" , ")//换全称
				.append("parent_ids=REPLACE(").append("parent_ids").append(",'").append(temp.getParent_ids()).append("','").append(entity.getParent_ids()).append("') , ")//换ids
				.append("parent_name=REPLACE(").append("parent_name").append(",'").append(temp.getName()).append("','").append(entity.getName()).append("')")//换所有儿子的名称
				
				.append(" where ").append("parent_ids like ('%").append(entity.getId()).append("%')");//找满足条件的所有子孙
				
				em.createQuery(hql.toString()).executeUpdate();//更新自己所有子孙信息				
				em.merge(entity);//更新自己
				
				return true;
		
	}
	@Override
	public Boolean save(SysMenu entity) throws ServiceException {
		
			return this.sysMenuDao.insert(entity);
		
	}
	
	
	@Override
	public Boolean deleteByIdsRe(String ids,String update_by) throws ServiceException {
		//说明 string 格式要求"'id','id','id'"
		//TODO
		//格式校验 	
		ids=FormatHandler.isFormatIds(ids);
		if(ids==null){
			throw new ServiceException("SysMenuService deleteByIds  ids= " + ids + "格式不对");			
		
		}
	
			//物理删除 （提高效果使用 HQL执行 ，而不使用实体	）		
			//StringBuffer sql =new StringBuffer("delete  from ").append("sys_menu").append(" where ").append("id in (").append(ids).append(")");
			// StringBuffer hql =new StringBuffer("delete  from ").append("SysMenu").append(" where ").append("id in(").append(ids).append(")");
			
			//物理删除结束			
			//逻辑删除
			//StringBuffer sql =new StringBuffer("update ").append("sys_menu set del_flag=0  , update_by='").append(update_by).append("' , update_date='").append(DateUtil.getDateTime()).append("' where ").append("id in (").append(ids).append(")");
			StringBuffer hql =new StringBuffer("update  from ").append("SysMenu set del_flag=0  , update_by='").append(update_by).append("' , update_date='").append(DateUtil.getDateTime()).append("' where ").append("id in (").append(ids).append(")");
			//逻辑删除结束
			
			//this.sysMenuDao.sqlDelete(sql.toString());//执行sql 
			return this.sysMenuDao.hqlDelete(hql.toString());//执行HQL的方法
		
	}
	
		@Override
	public void getPageRe(SysMenu entity, PageEntity pageentity) throws ServiceException {
	
			this.pageService.getPage(pageentity.getPageSize(),
					pageentity.getCurrentPage());
			//TODO 自动生成的代码，包括等值查询，LIKE查询，非查询，右左连接待开发完善中
			
			
			StringBuffer hql=new StringBuffer();
			hql.append("  o.id!='root' and o.del_flag=").append(entity.DEL_FLAG_DELETE);
			
			
			if(entity.getName()!=null && !entity.getName().equals("")){ //查询字段:名称；搜索方式为：like
			       					
			hql.append(" and o.name like '%").append(entity.getName()).append("%'"); //like查询
			}//条件结束
			
	
	
	
			
			PageEntity init = this.pageService.doListbywhere("o", "SysMenu o", hql.toString(), pageentity.getOrderBy());
			this.setpage(pageentity, init);

	}
	
		@Override
		public List<SysMenu> getAll(SysMenu entity) throws ServiceException{
			
				StringBuffer hql=new StringBuffer();
				
			if(entity.getName()!=null && !entity.getName().equals("")){ //查询字段:名称；搜索方式为：like
			       					
			hql.append(" and o.name like '%").append(entity.getName()).append("%'"); //like查询
			}//条件结束
			
				
					StringBuffer in=new StringBuffer();
			
			if(hql.toString()!=null && !hql.toString().equals("")){ //判断是否有查询条件 ，有查询条件则拼in条件
			StringBuffer wh=new StringBuffer();
			wh.append("select o.parent_ids from SysMenu o where  o.id!='root' and o.del_flag=").append(entity.DEL_FLAG_NORMAL).append(hql);			
			List<String> list = this.sysMenuDao.query(wh.toString());//根据条件查询 
				
				for(int id=0;id<list.size();id++){									
					in.append(FormatHandler.isFormatIds(list.get(id).toString())).append(",");//格式校验  拼in条件	
				}
			}	
			//
			StringBuffer hqla=new StringBuffer();
			hqla.append("select o from SysMenu o where  o.del_flag=").append(entity.DEL_FLAG_NORMAL);
			if(hql.toString()!=null && !hql.toString().equals("")){ //判断是否有查询条件 ，有查询条件则拼in条件
				hqla.append(hql);//把自己加进去
			}
			if(in.toString()!=null && !in.toString().equals("")){ //是否有in条件，查询
				hqla.append(" or o.id in (").append(in.substring(0, in.length()-1)).append(")");	//把父节点全搜索出来加进去 //处尾“，”
			}
			hqla.append(" order by o.sort ");
				return this.sysMenuDao.query(hqla.toString());	
	
		}
		
		public List<SysMenu> getMenuByUser(String office_id,String login_user_id) throws ServiceException{
			
			StringBuffer hqla=new StringBuffer();
			hqla.append("select o from SysMenu o where  o.del_flag=0");
			
			hqla.append(" and o.id in (select o1.sys_menu_id from SysOfficeSysMenu o1 where  o1.del_flag=0 and o1.sys_office_id in(").append(FormatHandler.isFormatIds(office_id)).append(")");
			
			if(!login_user_id.equals("admin")){
				hqla.append(" and o1.sys_menu_id in(select o3.sys_menu_id from SysUserSysMenu o3 where o3.del_flag=0 and o3.sys_user_id ='").append(login_user_id).append("')");
			}
			hqla.append(")");
			hqla.append(" order by o.sort ");
			return this.sysMenuDao.query(hqla.toString());	

		}
		
		public List<SysMenuSys> getMenuByUserNo(String user_id,String parentId,String is_show) throws ServiceException{
			
			if("admin".equals(user_id)){
				List<SysMenuSys> list = this.sysMenuDao.query("from SysMenuSys where parent_id='"+parentId+"' and is_show='"+is_show+"' and del_flag='0'  order by sort ");
				return list;
			}else{
				//String role ="select o.sys_role_id from SysUserSysRole o where o.sys_user_id='"+user_id+"'";//找用户的角色
				//List list = this.sysMenuDao.query(role);
				
				//String menuid = "select u.sys_menu_id from  SysRoleSysMenu u where u.sys_role_id in("+role+")";//根据角色找功能ID
				String menuid = "select u.sys_menu_id from SysUserSysMenu u where u.sys_user_id='"+user_id+"'";
				List<SysMenuSys> list = this.sysMenuDao.query("from SysMenuSys where parent_id='"+parentId+"' and is_show='"+is_show+"' and del_flag='0' and id in("+menuid+") order by sort ");
				//重新加载children集合
				if(list==null){
					return null;
				}
				for(SysMenuSys menu:list){
					menuid = "select u.sys_menu_id from SysUserSysMenu u where u.sys_user_id='"+user_id+"'";
					List<SysMenu> list_child = this.sysMenuDao.query("from SysMenuSys where parent_id='"+menu.getId()+"' and is_show='"+is_show+"' and del_flag='0' and id in("+menuid+") order by sort ");
					menu.setChildren(list_child);
				}
				return list;
			}
		}
		
		public List<SysMenuSys> getOfficeMenu(String office_id,String user_id,String parentId,String is_show) throws ServiceException{
			
			StringBuffer hqla=new StringBuffer();
			hqla.append("select o from SysMenuSys o where o.parent_id='"+parentId+"' and o.is_show='"+is_show+"' and o.del_flag=0");
			
			hqla.append(" and o.id in (select o1.sys_menu_id from SysOfficeSysMenu o1 where  o1.del_flag=0 and o1.sys_office_id in(").append(FormatHandler.isFormatIds(office_id)).append(")");
			
			if(!user_id.equals("admin")){
				hqla.append(" and o1.sys_menu_id in(select o3.sys_menu_id from SysUserSysMenu o3 where o3.del_flag=0 and o3.sys_user_id ='").append(user_id).append("')");
			}
			hqla.append(")");
			hqla.append(" order by o.sort ");
			return this.sysMenuDao.query(hqla.toString());
		}

		@Override
		public PageEntity getPageTree(SysMenu entity, PageEntity pageentity) throws ServiceException{
				this.pageService.getPage(pageentity.getPageSize(),
						pageentity.getCurrentPage());
				//TODO 自动生成的代码，包括等值查询，LIKE查询，非查询，右左连接待开发完善中
				
				
				StringBuffer hql=new StringBuffer();
				hql.append(" o.id!='root' and o.del_flag=").append(entity.DEL_FLAG_NORMAL);
				
				if(entity.getName()!=null && !entity.getName().equals("")){ //查询字段:名称；搜索方式为：like
				       					
				hql.append(" and o.name like '%").append(entity.getName()).append("%'"); //like查询
				}//条件结束
				
				//根据parent找自己及自己的儿子
				if(entity.getParent_id()!=null && !entity.getParent_id().equals("")){ //查询字段:名称；搜索方式为：like
					hql.append(" and o.parent_ids like '%").append(entity.getParent_id()).append("%'"); //like查询 把自己儿子都找出来
					hql.append(" or o.id='").append(entity.getParent_id()).append("'");//把自己搜索出来
				}//条件结束
				
				
				return this.pageService.doListbywhere("o", "SysMenu o", hql.toString(), pageentity.getOrderBy());
		}

		
		
		@Override
		public String getMenuFullNameByUrl(String url,String permissions) throws ServiceException{
				if(url.indexOf("login")>0){
					return "登录";
				}if(url.indexOf("logout")>0){
					return "退出";
				}
				//处理url /hw/lcStu/getPage.do
				if(url.indexOf(Config.appName)>-1){//如果包含项目名，去掉项目名。	
					url =	url.replace(Config.appName+"/", "");
					//url = url.substring(1, url.length());
				}
				Query query = this.sysMenuDao.getEm().createQuery("select o from SysMenu o where  o.id!='root' and o.del_flag=0 and o.href like '"+url+"%'");	
				query.setHint("org.hibernate.cacheable", true);				
			List list =	 query.getResultList();			
				if(list.size()>0){
					SysMenu entity  = (SysMenu) list.get(0);
				return entity.getFull_name();
				}
				else{
					return "";
				}

		}
		/**
		 * 	
		 * @param 得到自己的所有，供父亲调用
		 * @return checkbox 下拉等使用map方式
		 */
		 @Override
		public Map getAll() throws ServiceException{
		List<SysMenu> list = this.getAll(new SysMenu());		
			Map map = new HashMap();		
			for(int id=0;id<list.size();id++){
				SysMenu entity  = list.get(id);
				map.put(entity.getId(), entity.getName());//所有表都得有个名字，默认都有。如果，自己处理吧；					
			}
			return map;
		}

		@Override
		public String getMenuFullNameById(String menuid) throws ServiceException {
			Query query = this.sysMenuDao.getEm().createQuery("select o from SysMenu o where  o.id!='root' and o.del_flag=0 and o.id=?1");	
			query.setHint("org.hibernate.cacheable", true);
			query.setParameter(1, menuid);	
			SysMenu entity = (SysMenu) query.getSingleResult()	;
			if(entity!=null){
			return entity.getFull_name();
			}
			else{
				return "";
			}

		}
		public List<SysMenu> getMenuByUserId(String user_id) throws ServiceException{

			if("admin".equals(user_id)){
				List<SysMenu> list = this.sysMenuDao.query("from SysMenu where permission!='' and del_flag='0'  order by sort ");
				return list;
			}else{
			//String role ="select o.sys_role_id from SysUserSysRole o where o.sys_user_id='"+user_id+"'";//找用户的角色
			//List list = this.sysMenuDao.query(role);
			
			//String menuid = "select u.sys_menu_id from  SysRoleSysMenu u where u.sys_role_id in("+role+")";//根据角色找功能ID
			String menuid = "select u.sys_menu_id from SysUserSysMenu u where u.sys_user_id='"+user_id+"'";
			List<SysMenu> list = this.sysMenuDao.query("from SysMenu where  permission!='' and del_flag='0' and id in("+menuid+") order by sort ");
			return list;
			}

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