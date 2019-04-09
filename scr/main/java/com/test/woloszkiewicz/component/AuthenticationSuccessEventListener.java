package com.test.woloszkiewicz.component;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.test.woloszkiewicz.entity.Loginfail;
import com.test.woloszkiewicz.entity.User;
import com.test.woloszkiewicz.service.LoginFailRepository;
import com.test.woloszkiewicz.service.UserRepository;

@Component
public class AuthenticationSuccessEventListener implements ApplicationListener<AuthenticationSuccessEvent> {
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	LoginFailRepository loginFailRepository;
	
	@Override
	public void onApplicationEvent(AuthenticationSuccessEvent event) {
		UserDetails userDetails = (UserDetails) event.getAuthentication().getPrincipal();
		String userName=userDetails.getUsername();
		User findOneByUsername = userRepository.findOneByUsername(userName);
		if(findOneByUsername != null) {
			Date currentDate=new Date();
			Loginfail loginfail = findOneByUsername.getLoginfail();
			if(loginfail.getLockfail() && loginfail.getDate_of_lock_out().after(currentDate)) {
				event.getAuthentication().setAuthenticated(false);
			}else {
				System.out.println("jestem w ok !!!!");
					
					loginfail.setLockfail(false);
					loginfail.setNumberloginfail(0);
					findOneByUsername.setLoginfail(loginfail);
					
					loginFailRepository.saveAndFlush(loginfail);
					userRepository.flush();
				
					/*Loginfail log=new Loginfail(findOneByUsername.getUsername(), 0, false, findOneByUsername);
					findOneByUsername.setLoginfail(log);
					System.out.println("jestem w po ok !!!!");
					loginFailRepository.save(log);
					loginFailRepository.delete(loginfail);
					userRepository.flush();*/
				
				
				
				/*Optional<Loginfail> loginfailtest=loginFailRepository.findById(findOneByUsername.getLoginfail().getIdlogfail());
				if(loginfailtest.isPresent()) {
					System.out.println("jestem w 2 ok !!!!");
					Loginfail loginfail2=loginfailtest.get();
					loginfail2.setLockfail(false);
					loginfail2.setNumberloginfail(0);
					loginFailRepository.flush();
				}*/
				
			}
		}

	}

}
