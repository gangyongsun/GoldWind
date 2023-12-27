package com.mdip.web.framework.base.service;

import java.util.List;
import javax.servlet.http.HttpServletRequest;

import com.mdip.web.framework.base.entity.BaseEntity;
import com.mdip.web.framework.base.exception.ServiceException;

public interface IUploadFileService<T extends BaseEntity<T>> {
	public T saveFileOne(String paramString1, String paramString2, String paramString3, String paramString4, HttpServletRequest paramHttpServletRequest, String paramString5) throws ServiceException;

	public T updateFileOne(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, HttpServletRequest paramHttpServletRequest, String paramString6)
			throws ServiceException;

	public boolean updateFileBySaveAfter(String paramString1, String paramString2);

	public boolean updateFileByUpdateAfter(String paramString1, String paramString2);

	public List<T> upFile(String paramString1, HttpServletRequest paramHttpServletRequest, String paramString2) throws ServiceException;

	public boolean checkFile(HttpServletRequest paramHttpServletRequest);

	public boolean checkFileType(HttpServletRequest paramHttpServletRequest, String paramString);

	public boolean checkMul(HttpServletRequest paramHttpServletRequest);

	public boolean delFileById(String paramString) throws ServiceException;

	public boolean saveFileSuc(String paramString) throws ServiceException;

	public T getSysUpDownFileById(String paramString) throws ServiceException;

	public boolean delFileByTableId(String paramString) throws ServiceException;

	public List<T> queryFileListByTables(String paramString1, String paramString2);

	public List<T> queryFileListByTemp(String paramString1, String paramString2);
}
