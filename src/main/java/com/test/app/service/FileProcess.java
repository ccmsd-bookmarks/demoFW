package com.test.app.service;

import java.io.File;
import java.io.IOException;

public interface FileProcess {

	public boolean extract(File currentFile, File inboundToDir) throws IOException;

	public boolean process(File currentFile, File inboundToDir) throws IOException;

	boolean rename(File fileName);
	
}
