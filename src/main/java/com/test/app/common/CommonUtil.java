package com.test.app.common;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonUtil {

	private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtil.class);

	public boolean isStringBlank(String str) {
		if (StringUtils.isNoneBlank(str)) {
			return false;
		}
		LOGGER.error("String is Empty :" + str);
		return true;
	}

	public static boolean isValidDirectory(File dir) {
		if (dir != null && dir.isDirectory() && dir.canRead()) {
			return true;
		}
		LOGGER.error("Invalid Directory :" + dir.getAbsolutePath());
		return false;
	}
}
