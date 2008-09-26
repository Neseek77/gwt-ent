package com.gwtent.acegi.sample.server.service;

import org.springframework.security.Authentication;
import org.springframework.security.event.authentication.AuthenticationSuccessEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

public class LoginSuccessListener implements ApplicationListener {

	public void onApplicationEvent(ApplicationEvent event) {
		if (event instanceof AuthenticationSuccessEvent) {
			AuthenticationSuccessEvent authEvent = (AuthenticationSuccessEvent)event;
			Authentication auth = authEvent.getAuthentication();
			String userName = auth.getName();
			System.out.println("User["+userName+"]login successed...");
		}
	}
}
