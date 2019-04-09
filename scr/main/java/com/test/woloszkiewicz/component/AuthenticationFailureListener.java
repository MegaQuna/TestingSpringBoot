package com.test.woloszkiewicz.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.stereotype.Component;

import com.test.woloszkiewicz.entity.Loginfail;
import com.test.woloszkiewicz.entity.User;
import com.test.woloszkiewicz.service.LoginFailRepository;
import com.test.woloszkiewicz.service.UserRepository;

@Component
public class AuthenticationFailureListener implements ApplicationListener<AuthenticationFailureBadCredentialsEvent> {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LoginFailRepository loginFailRepository;

	@Override
	public void onApplicationEvent(AuthenticationFailureBadCredentialsEvent event) {
		
		/*WebAuthenticationDetails auth = (WebAuthenticationDetails) event.getAuthentication().getDetails();
		String ip = auth.getRemoteAddress();*/
		
		String userName = event.getAuthentication().getPrincipal().toString();
		User findOneByUsername = userRepository.findOneByUsername(userName);
		if(findOneByUsername != null) {
			System.out.println("jest");
			Loginfail loginfail = findOneByUsername.getLoginfail();
			loginfail.addFailLogin();
			findOneByUsername.setLoginfail(loginfail);
			loginFailRepository.saveAndFlush(loginfail);
			userRepository.flush();
		}	
		//String password = event.getAuthentication().getCredentials().toString();
		//System.out.println("Failed login using USERNAME " + userName);
	}

}
