package com.test.app.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
@Configuration
@PropertySource(value = "classpath:application.properties", ignoreResourceNotFound = false)
public class AppPropertyValue {

	@Autowired
	private Environment environment;

	public String getValueByKey(String key) {
		return environment.resolvePlaceholders("${" + key + "}");
	}

	@Value("${filemover.inbound.directory}")
	private String inboundDirectory;

	@Value("${filemover.inbound.watchInterval}")
	private String inboundWatchInterval;

	@Value("${filemover.inbound.moveToDir}")
	private String inboundMoveDirectory;

	@Value("${filemover.trg.directory}")
	private String trgDirectory;

	@Value("${filemover.trg.watchInterval}")
	private String trgWatchInterval;

	@Value("${filemover.trg.moveToDir}")
	private String trgMoveDirectory;

	@Value("#{'${filemover.trg.processConfig}'.split(',')}")
	private List<String> trgProcessConfig;

	public String getInboundDirectory() {
		return inboundDirectory;
	}

	public String getInboundWatchInterval() {
		return inboundWatchInterval;
	}

	public String getInboundMoveDirectory() {
		return inboundMoveDirectory;
	}

	public String getTrgDirectory() {
		return trgDirectory;
	}

	public String getTrgWatchInterval() {
		return trgWatchInterval;
	}

	public String getTrgMoveDirectory() {
		return trgMoveDirectory;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public List<String> getTrgProcessConfig() {
		return trgProcessConfig;
	}
	
}
