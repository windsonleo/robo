package com.tecsoluction.robo.entidade;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Autenticador extends Authenticator {

	
	public String username = null;
	public String password = null;


	
	
	
	public Autenticador(String user, String pwd) {
		username = user;
		password = pwd;
	}
	
	


	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication (username,password);
	}
	
}