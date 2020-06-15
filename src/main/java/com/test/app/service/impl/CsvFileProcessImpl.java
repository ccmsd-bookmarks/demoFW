package com.test.app.service.impl;

import java.io.File;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.test.app.service.FileProcess;

@Service("csvFileProcess")
public class CsvFileProcessImpl implements FileProcess {

	

	@Override
	public boolean rename(File fileName) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean process(File currentFile, File inboundToDir) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean extract(File currentFile, File inboundToDir) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

}
