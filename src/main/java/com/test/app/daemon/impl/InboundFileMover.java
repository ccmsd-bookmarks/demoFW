package com.test.app.daemon.impl;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.test.app.common.AppPropertyValue;
import com.test.app.common.CommonUtil;
import com.test.app.daemon.FileMover;
import com.test.app.service.FileProcess;

@Component
public class InboundFileMover implements FileMover {

	@Autowired
	AppPropertyValue prop;
	@Autowired
	ApplicationContext context;

	private static final Logger LOGGER = LoggerFactory.getLogger(InboundFileMover.class);

	@Scheduled(cron = "${filemover.inbound.watchInterval}")
	@Override
	public void moveFile() {

		if (!StringUtils.isNoneBlank(prop.getInboundDirectory())
				|| !StringUtils.isNoneBlank(prop.getInboundMoveDirectory())) {
			LOGGER.error("Invalid Directory :" + prop.getInboundDirectory() + " or " + prop.getInboundMoveDirectory());
			return;
		}
		File inboundDir = new File(prop.getInboundDirectory());
		File inboundToDir = new File(prop.getInboundMoveDirectory());
		LOGGER.info("Filewatcher Running");
		if (CommonUtil.isValidDirectory(inboundDir) && CommonUtil.isValidDirectory(inboundToDir)) {
			Iterator<File> files = FileUtils.iterateFiles(inboundDir, null, false);
			if (files.hasNext()) {
				LOGGER.info("Filewatcher identified new files");
			}
			while (files.hasNext()) {
				File currentFile = files.next();
				LOGGER.info("Processing file:" + currentFile.getName());
				if (currentFile != null && currentFile.isFile() && currentFile.canRead()) {
					try {
						FileProcess fileProcess = getFileProcessBean(currentFile);
						boolean processStatus = fileProcess.process(currentFile, inboundToDir);
						if (processStatus) {
							LOGGER.info("Process for file completed .." + currentFile);
						}
						LOGGER.info("File Moved from " + currentFile.getAbsolutePath() + " to " + inboundDir);
					} catch (IOException e) {
						LOGGER.info(e.getMessage());
						LOGGER.debug(Arrays.deepToString(e.getStackTrace()));
					}
				} else {
					LOGGER.info("File is in use.. skipped processing:" + currentFile.getName());
				}
			}
		}
	}

	private FileProcess getFileProcessBean(File currentFile) {
		String fileProcessBean = "";
		switch (FilenameUtils.getExtension(currentFile.getName())) {
		case "dat":
			fileProcessBean = "datFileProcess";
			break;
		case "gz":
			fileProcessBean = "gzipFileProcess";
			break;
		case "zip":
			fileProcessBean = "zipFileProcess";
			break;
		default:
			fileProcessBean = "csvFileProcess";
		}
		LOGGER.info("got Bean" + fileProcessBean);
		return this.context.getBean(fileProcessBean, FileProcess.class);
	}

	

}
