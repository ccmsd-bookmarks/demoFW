package com.test.demo;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import com.test.service.MyFileUtils;
import com.test.service.TrgFileWatcher;

@Component
public class MyFileMonitor {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyFileMonitor.class);
	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
	private TrgFileWatcher trgFileWatcher;

	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	@PostConstruct
	public void setupFileMonitor() {
		LOGGER.info("post construct executed.......................................");
		String dirPath = "C:\\Users\\Naveen\\Desktop\\test";
		File folder = new File(dirPath);
		if (!folder.exists()) {
			throw new RuntimeException("Directory not found: " + dirPath);
		}
		try {
			this.trgFileWatcher = new TrgFileWatcher(folder, MyFileUtils.getAllFilesWithExtention("*.trg"), 20);
			this.trgFileWatcher.start();

		} catch (

		Exception e) {
			LOGGER.error("ERROR.: ", e);
		}
	}

	public TrgFileWatcher getTrgFileWatcher() {
		return trgFileWatcher;
	}

}
