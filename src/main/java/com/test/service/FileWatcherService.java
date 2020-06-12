package com.test.service;

import java.io.File;
import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.test.demo.MyFileMonitor;

public abstract class FileWatcherService {

	public enum MonitorEvent {
		STARTUP, FCREATE, FDELETE, FCHANGE
	};
	private static final Logger LOGGER = LoggerFactory.getLogger(FileWatcherService.class);

	private IOFileFilter ioFileFilter = null;
	private FileAlterationObserver observer;
	private FileAlterationMonitor monitor;
	private static Set<String> changeFiles = new HashSet<String>();

	FileWatcherService(File dir, IOFileFilter files, int interval) {
		this.observer = new FileAlterationObserver(dir, files);
		this.monitor = new FileAlterationMonitor(interval, observer);
		observer.addListener(new FileAlterationListenerAdaptor() {

			@SuppressWarnings("resource")
			@Override
			public void onFileCreate(File file) {
				System.out.println("create trigger");

				if (processFile(file)) {
					onUpdated(MonitorEvent.FCREATE, file);
				}

//				System.out.println("Fcreate  triggred");
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
//				Date now = new Date();
//				String strDate = sdf.format(now);
//				if (file.canWrite()) {
//					long oldSize = 0L;
//					long newSize = 1L;
//					boolean fileIsOpen = true;
//
//					while ((newSize > oldSize) || fileIsOpen) {
//						oldSize = file.length();
//						try {
//							Thread.sleep(2000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//						newSize = file.length();
//						System.out.println(oldSize + "--" + newSize);
//
//						try {
//							new FileInputStream(file);
//							fileIsOpen = false;
//						} catch (Exception e) {
//							System.out.println("asank rror ");
//						}
//					}
//					System.out.println("FIlecreae event complated..." + strDate);
//					onUpdated(MonitorEvent.FCREATE, file);
//				}
			}

			@Override
			public void onFileChange(File file) {
				System.out.println("chnage trigger");
				if (processFile(file)) {
					onUpdated(MonitorEvent.FCREATE, file);
				}
//				if (file.canWrite()) {
//					long oldSize = 0L;
//					long newSize = 1L;
//					boolean fileIsOpen = true;
//
//					while ((newSize > oldSize) || fileIsOpen) {
//						oldSize = file.lastModified();
//						try {
//							Thread.sleep(2000);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//						newSize = file.lastModified();
//						System.out.println(oldSize + "--" + newSize);
//
//						try {
//							new FileInputStream(file);
//							fileIsOpen = false;
//						} catch (Exception e) {
//							System.out.println("Error on file received");
//						}
//					}
//				}
//				System.out.println("FIlechange event triggered...");
//
//				onUpdated(MonitorEvent.FCHANGE, file);
			}

		});
	}

	public IOFileFilter getFiles() {
		return this.ioFileFilter;
	}

	public void start() throws Exception {
		LOGGER.error("Started FW......");
		monitor.start();
	}

	public abstract void onUpdated(MonitorEvent event, File file);

	private static Boolean processFile(File file) {
		System.out.println("processing file");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		Date now = new Date();
		String strDate = sdf.format(now);
//		if (file.canWrite()) {
		long oldSize = 0L;
		long newSize = 1L;
		boolean fileIsOpen = true;
		synchronized (changeFiles) {
			if (changeFiles.contains(file.getAbsolutePath())) {
				System.out.println("skipped this event");
				return false;
			}
			changeFiles.add(file.getAbsolutePath());
		}
		while ((newSize > oldSize)) {
			oldSize = file.lastModified();
			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			newSize = file.lastModified();
			System.out.println(oldSize + "--" + newSize);

//				try {
//					new FileInputStream(file);
//					fileIsOpen = false;
//				} catch (Exception e) {
//					System.out.println("asank rror ");
//				}
		}
		synchronized (changeFiles) {
			changeFiles.remove(file.getAbsolutePath());
		}
		System.out.println("process event complated..." + strDate);
//		onUpdated(MonitorEvent.FCREATE, file);
		return true;
//		}

	}

}
