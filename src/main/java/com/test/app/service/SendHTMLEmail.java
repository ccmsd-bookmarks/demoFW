package com.test.app.service;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SendHTMLEmail {
	private static final String SENDMAIL = "sendmail -R hdrs -N never -t -v < ";
	private static final Logger LOGGER = LoggerFactory.getLogger(SendHTMLEmail.class);

	private String fileName;

	public SendHTMLEmail(String fileName) {
		this.fileName = fileName;
	}

	public void sendMail() {
		String command = SENDMAIL + fileName;
		try {
			Runtime r = Runtime.getRuntime();
			LOGGER.info("Envoi de la commande: " + command);
			Process p = r.exec(new String[] { "/usr/bin/ksh", "-c", command });
			p.waitFor();
			LOGGER.info("Résultat de l'envoi de l'e-mail : " + p.getOutputStream());
		} catch (InterruptedException ex) {
			LOGGER.info(ex.getMessage());
		} catch (IOException ex) {
			LOGGER.info(ex.getMessage());
		}

	}
}