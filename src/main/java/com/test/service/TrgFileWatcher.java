package com.test.service;

import java.io.File;

import org.apache.commons.io.filefilter.IOFileFilter;

public class TrgFileWatcher extends FileWatcherService {

	public TrgFileWatcher(File dir, IOFileFilter files, int interval) {
		super(dir, files, interval);
	}

	@Override
	public void onUpdated(MonitorEvent event, File file) {
		System.out.println("File Found :............" + file.getName());

	}

}
