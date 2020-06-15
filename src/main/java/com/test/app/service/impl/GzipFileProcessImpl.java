package com.test.app.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.test.app.service.FileProcess;
import org.slf4j.Logger;

@Service("gzipFileProcess")
public class GzipFileProcessImpl implements FileProcess {

	byte[] buffer = new byte[2048];
	private static final Logger LOGGER = LoggerFactory.getLogger(GzipFileProcessImpl.class);

	@Override
	public boolean process(File fileName, File inboundToDir) throws IOException {
		boolean isExtraced = extract(fileName, inboundToDir);
		if (isExtraced) {
			return FileUtils.deleteQuietly(fileName);
		}
		return false;
	}

	@Override
	public boolean extract(File fileName, File inboundToDir) throws IOException {
		GZIPInputStream gzis = null;
		FileOutputStream out = null;
		boolean isExtractCompleted = false;
		if (!fileName.canRead()) {
			LOGGER.info("File not ready." + fileName.getName());
			return false;
		}
		checkFileProcess(fileName);
		try {
			gzis = new GZIPInputStream(new FileInputStream(fileName));
			out = new FileOutputStream(inboundToDir + File.separator + FilenameUtils.getBaseName(fileName.getName()));
			int len;
			while ((len = gzis.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
			isExtractCompleted = true;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			gzis.close();
			out.close();
		}
		return isExtractCompleted;
	}

	private void checkFileProcess(File fileName) {
		try {
			long tmp = 0L;
			long lastModified = fileName.lastModified();
			while (lastModified >= tmp) {
				tmp = lastModified;
				Thread.sleep(1L);
			}
		} catch (InterruptedException e) {
			LOGGER.error("Unable to read file " + fileName + " Error:" + e.getMessage());
		}
	}

	@Override
	public boolean rename(File fileName) {
		// TODO Auto-generated method stub
		return false;
	}
}
