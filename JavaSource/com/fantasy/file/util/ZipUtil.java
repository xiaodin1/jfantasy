package com.fantasy.file.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.fantasy.file.FileItem;
import com.fantasy.file.FileItemSelector;
import com.fantasy.framework.util.common.StreamUtil;

public class ZipUtil {

	private static final Log logger = LogFactory.getLog(ZipUtil.class);

	/**
	 * zip压缩
	 * 
	 * @功能描述
	 * @param outputStream 要压缩到的流
	 * @param fileItem 文件对象
	 * @param comment 说明信息
	 */
	public void compress(OutputStream outputStream, FileItem fileItem, String comment) {
		compress(outputStream, fileItem, new FileItemSelector() {

			public boolean traverseDescendents(FileItem fileItem) {
				return true;
			}

			public boolean includeFile(FileItem fileItem) {
				return true;
			}

		}, "gbk", comment);
	}

	/**
	 * 
	 * @功能描述 
	 * @param outputStream
	 * @param fileItem
	 * @param encoding 压缩时采用的编码
	 * @param comment
	 */
	public void compress(OutputStream outputStream, FileItem fileItem, String encoding, String comment) {
		compress(outputStream, fileItem, new FileItemSelector() {

			public boolean traverseDescendents(FileItem fileItem) {
				return true;
			}

			public boolean includeFile(FileItem fileItem) {
				return true;
			}

		}, encoding, comment);
	}

	/**
	 * 
	 * @功能描述 
	 * @param outputStream
	 * @param fileItem
	 * @param selector
	 * @param encoding
	 * @param comment
	 */
	public void compress(OutputStream outputStream, FileItem fileItem, FileItemSelector selector, String encoding, String comment) {
		ZipOutputStream out = new ZipOutputStream(outputStream);
		List<FileItem> fileItems = fileItem.listFileItems(selector);
		for (FileItem item : fileItems) {
			ZipEntry entry = new ZipEntry(item.getAbsolutePath().replaceFirst(fileItem.getAbsolutePath(), ""));
			try {
				out.putNextEntry(entry);
				InputStream in = fileItem.getInputStream();
				StreamUtil.copy(in, out);
				StreamUtil.closeQuietly(in);
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			}
		}
		out.setEncoding(encoding);
		out.setComment(comment);
		StreamUtil.closeQuietly(out);
	}

}
