package com.test.demo;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FileMover {

//	@Autowired
//	private MyFileMonitor fileMonitor;

	@Scheduled(cron = "*/5 * * * * *")
	public void cleanup() throws InterruptedException, IOException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date now = new Date();
		String strDate = sdf.format(now);

		synchronized (this) {

//			System.out.println("File mover will runn...." + strDate);
			String srcFilePath = "C:\\Users\\Naveen\\Desktop\\test\\trg";
			String destFilePath = "C:\\Users\\Naveen\\Desktop\\test";
			;
			/* Apache commons io StringUtils class provide isNoneBlank() method. */
			if (StringUtils.isNoneBlank(srcFilePath) && StringUtils.isNoneBlank(destFilePath)) {
				/* Create source instance. */
				File srcDir = new File(srcFilePath);
				File destDir = new File(destFilePath);
				Iterator<File> files = FileUtils.iterateFiles(srcDir, null, false);
				while (files.hasNext()) {
					File f = files.next();
					if (f != null && f.exists() && f.isFile() && f.canRead() && f.canWrite() )
						FileUtils.moveFileToDirectory(f, destDir, true);
					System.out.println("Use Apache commons io to move " + destFilePath + " to target directory "
							+ destFilePath + " successful. ");
				}
				/* Create target instance. */
//				File destFile = new File(destFilePath);

//				/* Move to target. */
//				FileUtils.moveFile(srcFile, destFile);
//
//				System.out.println("Use Apache commons io to move success from " + srcFilePath + " to " + destFilePath);

				/* Move target to another directory. */

				/* The third parameter is true means create target directory if not exist. */

			}

		}
//		Thread.sleep(10000);
//		System.out.println("File mover completed...." + strDate);

	}
}
