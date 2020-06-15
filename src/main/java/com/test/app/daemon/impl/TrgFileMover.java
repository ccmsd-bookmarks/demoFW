package com.test.app.daemon.impl;

import java.io.File;
import java.util.Arrays;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.test.app.common.AppPropertyValue;
import com.test.app.common.CommonUtil;
import com.test.app.daemon.FileMover;

@Component
public class TrgFileMover implements FileMover {

	@Autowired
	AppPropertyValue prop;

	private static final Logger LOGGER = LoggerFactory.getLogger(TrgFileMover.class);

	@Scheduled(cron = "${filemover.trg.watchInterval}")
	@Override
	public void moveFile() {

		if (!StringUtils.isNoneBlank(prop.getTrgDirectory()) || !StringUtils.isNoneBlank(prop.getTrgMoveDirectory())) {
			LOGGER.error("Invalid Directory :" + prop.getTrgDirectory() + " or " + prop.getTrgMoveDirectory());
			return;
		}

		File trgDir = new File(prop.getTrgDirectory());
		File trgMoveDirectory = new File(prop.getTrgMoveDirectory());
		if (CommonUtil.isValidDirectory(trgDir) && CommonUtil.isValidDirectory(trgMoveDirectory)) {
			Iterator<File> files = FileUtils.iterateFiles(trgDir, null, false);
			if (files.hasNext()) {
				LOGGER.info("Filewatcher identified new files");
			}
			while (files.hasNext()) {
				File currentFile = files.next();
				LOGGER.info("Processing file:" + currentFile.getName());
				if (currentFile != null && currentFile.isFile() && currentFile.canRead()) {
					String trgName = FilenameUtils.getBaseName(currentFile.getName());
					String[] appData = trgName.split("\\.");
					System.out.println(Arrays.deepToString(appData));
					if (appData.length == 3) {
						String appId = appData[0];
						String processId = appData[1];
						String executionId = appData[2];
						System.out.println("appid" + appId);
						if (prop.getTrgProcessConfig().contains(appId)) {
							System.out.println(prop.getValueByKey(appId + "." + "reprocess"));
						}
					}

				}
			}
		}
	}

}
