package com.test.app.daemon;

import org.springframework.validation.annotation.Validated;

@Validated
public interface FileMover {

	void moveFile();
}
