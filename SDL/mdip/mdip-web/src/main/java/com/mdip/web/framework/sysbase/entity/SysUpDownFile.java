package com.mdip.web.framework.sysbase.entity;

import java.io.File;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PreRemove;
import javax.persistence.Table;

@Entity
@Table(name = "sys_updownfile")
public class SysUpDownFile extends SysBaseEntity<SysUpDownFile> {
	// start 基础字段统一使用

	public SysUpDownFile(String id) {
		this.id = id;
	}

	private static final long serialVersionUID = 1L;

	protected String uploadpath;// 保存路径 "\项目\文件夹名\文件名" 如当前的\

	protected String loginfo;// 日志
	protected Boolean yn = false;// 成功或是失败，失败可去查看日志信息
	protected String oname;// 源文件名
	protected String nname;// 新文件名
	protected String filety;// 文件类型
	protected Long filesize;// 文件大小
	protected String cont;// 文件说明
	protected String sid;// 子系统字典名称 或ID
	protected String tablename;// 子系统字典名称 或ID

	protected String tableid;// 统一使用存其他表的ID

	public String getTableid() {
		return tableid;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	@PreRemove
	protected void preRemove() {
		File file1 = new File(this.getUploadpath());// 删除前把自己的附件干掉
		// System.out.println(this.getUploadpath());
		try {
			if (file1.exists()) {
				file1.delete();
			}

		} catch (Exception e) {

		} finally {

		}
	}

	@Column(name = "sid", length = 50)
	public String getSid() {
		return sid;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	@Column(name = "oname", length = 50)
	public String getOname() {
		return oname;
	}

	public void setOname(String oname) {
		this.oname = oname;
	}

	@Column(name = "nname", length = 300)
	public String getNname() {
		return nname;
	}

	public void setNname(String nname) {
		this.nname = nname;
	}

	@Column(name = "loginfo", length = 50)
	public String getLoginfo() {
		return loginfo;
	}

	public void setLoginfo(String loginfo) {
		this.loginfo = loginfo;
	}

	@Column(name = "yn")
	public Boolean getYn() {
		return yn;
	}

	public void setYn(Boolean yn) {
		this.yn = yn;
	}

	@Column(name = "uploadpath", length = 500)
	public String getUploadpath() {
		return uploadpath;
	}

	public void setUploadpath(String uploadpath) {
		this.uploadpath = uploadpath;
	}

	@Column(name = "filety", length = 20)
	public String getFilety() {
		return filety;
	}

	public void setFilety(String filety) {
		this.filety = filety;
	}

	@Column(name = "filesize")
	public Long getFilesize() {
		return filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	@Column(name = "cont", length = 100)
	public String getCont() {
		return cont;
	}

	public void setCont(String cont) {
		this.cont = cont;
	}

	public SysUpDownFile() {

	}

}