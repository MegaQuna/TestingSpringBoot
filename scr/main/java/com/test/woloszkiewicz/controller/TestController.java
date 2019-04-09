package com.test.woloszkiewicz.controller;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.test.woloszkiewicz.entity.NewUser;
import com.test.woloszkiewicz.entity.ReCaptchaResponse;
import com.test.woloszkiewicz.entity.User;
import com.test.woloszkiewicz.service.NewUserRepository;
import com.test.woloszkiewicz.service.UserRepository;


@Controller
@RequestMapping("app")
public class TestController {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	NewUserRepository newUserRepository;
	
	@Autowired
	RestTemplate restTemplate;
	
	@GetMapping("index")
	public ModelAndView index(HttpServletRequest request) {
		    ModelAndView mav = new ModelAndView();
		    String user=request.getRemoteUser();
		    System.out.println("login "+user);
		    if(user!=null && !user.isEmpty()) {
		    		User user_from_repository=userRepository.findOneByUsername(user);
				    String userloin=user_from_repository.getName()+" "+user_from_repository.getSurname();
				    System.out.println("login "+user);
				    mav.addObject("login", userloin);
		    }
		    
		    mav.setViewName("all/index");
		    return mav;
    }	
	
	@GetMapping("info")
	public ModelAndView info(HttpServletRequest request) {
		    ModelAndView mav = new ModelAndView();
		    String user=request.getRemoteUser();
		    System.out.println("login "+user);
		    if(user!=null && !user.isEmpty()) {
		    		User user_from_repository=userRepository.findOneByUsername(user);
				    String userloin=user_from_repository.getName()+" "+user_from_repository.getSurname();
				    System.out.println("login "+user);
				    mav.addObject("login", userloin);
		    }
		    //mav.addObject("userArticles", );
		    mav.setViewName("all/info");
		    return mav;
    }
	
	@GetMapping("test")
	public ModelAndView test(HttpServletRequest request) {
		    ModelAndView mav = new ModelAndView();
		    String user=request.getRemoteUser();
		    if(user!=null && !user.isEmpty()) {
		    		User user_from_repository=userRepository.findOneByUsername(user);
				    String userloin=user_from_repository.getName()+" "+user_from_repository.getSurname();
				    System.out.println("login "+user);
				    
				    mav.addObject("tests", user_from_repository.getTestsList());
				    mav.addObject("login", userloin);
		    }
		    //mav.addObject("userArticles", );
		    mav.setViewName("all/test");
		    return mav;
    }
	
	@GetMapping("login")
	public ModelAndView login(HttpServletRequest request) {
		    ModelAndView mav = new ModelAndView();
		    String user=request.getRemoteUser();
		    System.out.println("login "+user);
		    if(user!=null && !user.isEmpty()) {
		    		User user_from_repository=userRepository.findOneByUsername(user);
				    String userloin=user_from_repository.getName()+" "+user_from_repository.getSurname();
				    System.out.println("login "+user);
				    mav.addObject("login", userloin);
		    }
		    //mav.addObject("userArticles", );
		    mav.setViewName("all/login");
		    return mav;
    }
	
	@GetMapping("error")
	public ModelAndView error() {
		    ModelAndView mav = new ModelAndView();
		    String errorMessage= "You are not authorized for the requested data.";
		    mav.addObject("errorMsg", errorMessage);
		    mav.setViewName("all/403");
		    return mav;
    }		
	
	
	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public String handleException() {
	       return "Wystąpił błąd";
	}
	
	@GetMapping("newAccountTemplate")
	public ModelAndView newAccountTemplate(@RequestParam("name") Optional<String> name, @RequestParam("surname") Optional<String> surname,
			@RequestParam("username") Optional<String> username, @RequestParam("email") Optional<String> email, @RequestParam("email2") Optional<String> email2,
			@RequestParam("description") Optional<String> description,
			@RequestParam("rev_name") Optional<String> rev_name, @RequestParam("rev_surname") Optional<String> rev_surname, @RequestParam("rev_username") Optional<String> rev_username,
			@RequestParam("rev_email") Optional<String> rev_email, @RequestParam("rev_password") Optional<String> rev_password,
			@RequestParam("rev_description") Optional<String> rev_description) {
		    ModelAndView mav = new ModelAndView();
		    if(name.isPresent()) {
		    	mav.addObject("name",name.get());
		    }
		    if(surname.isPresent()) {
		    	mav.addObject("surname",surname.get());
		    }
		    if(username.isPresent()) {
		    	mav.addObject("username",username.get());
		    }
		    if(email.isPresent()) {
		    	mav.addObject("email",email.get());
		    }
		    if(email2.isPresent()) {
		    	mav.addObject("email2",email2.get());
		    }
		    if(description.isPresent()) {
		    	mav.addObject("description",description.get());
		    }
		    if(rev_name.isPresent()) {
		    	mav.addObject("rev_name",rev_name.get());
		    }
		    if(rev_surname.isPresent()) {
		    	mav.addObject("rev_surname",rev_surname.get());
		    }
		    if(rev_username.isPresent()) {
		    	mav.addObject("rev_username",rev_username.get());
		    }
		    if(rev_email.isPresent()) {
		    	mav.addObject("rev_email",rev_email.get());
		    }
		    if(rev_password.isPresent()) {
		    	mav.addObject("rev_password",rev_password.get());
		    }
		    if(rev_description.isPresent()) {
		    	mav.addObject("rev_description",rev_password.get());
		    }   
		    
		    mav.setViewName("all/NewUser");
		    return mav;
    }
	
	@GetMapping("newAccount")
	public RedirectView newAccount(RedirectAttributes attributes, @RequestParam("name") String name, @RequestParam("surname") String surname, @RequestParam("username") String username,
			@RequestParam("email") String email, @RequestParam("email2") String email2, @RequestParam("password") String password, @RequestParam("password2") String password2,
			@RequestParam("description") String description, @RequestParam("g-recaptcha-response") String captchaResponse) {
		
			String url="https://www.google.com/recaptcha/api/siteverify";
			String param="?secret=6LeUM4kUAAAAAFBLujSgsgdr5x3ACpQO4kKd9o0w&response="+captchaResponse;
			
			ReCaptchaResponse reCaptchaResponse=restTemplate.exchange(url+param	,HttpMethod.POST,null, ReCaptchaResponse.class).getBody();
			if(reCaptchaResponse.isSuccess()) {
				if(name.length()< 3 || name.length()>25) {
					attributes.addAttribute("rev_name", "Od 3 do 25 znaków");
					attributes.addAttribute("surname", surname);
					attributes.addAttribute("username", username);
					attributes.addAttribute("email", email);
					attributes.addAttribute("email2", email2);
					attributes.addAttribute("description", description);
					return new RedirectView("newAccountTemplate");
				}
				
				if(surname.length()< 3 || surname.length()>30) {
					attributes.addAttribute("rev_surname", "Od 3 do 30 znaków");
					attributes.addAttribute("name", name);
					attributes.addAttribute("username", username);
					attributes.addAttribute("email", email);
					attributes.addAttribute("email2", email2);
					attributes.addAttribute("description", description);
					return new RedirectView("newAccountTemplate");
				}
				if(username.length()< 3 || username.length()>30) {
					attributes.addAttribute("rev_username", "Od 3 do 30 znaków");
					attributes.addAttribute("name", name);
					attributes.addAttribute("surname", surname);
					attributes.addAttribute("email", email);
					attributes.addAttribute("email2", email2);
					attributes.addAttribute("description", description);
					return new RedirectView("newAccountTemplate");
				}
				if(email.length()< 4 || email.length()>35) {
					attributes.addAttribute("rev_email", "Od 4 do 35 znaków");
					attributes.addAttribute("name", name);
					attributes.addAttribute("surname", surname);
					attributes.addAttribute("username", username);
					attributes.addAttribute("description", description);
					return new RedirectView("newAccountTemplate");
				}			
				if(email2.length()< 4 || email2.length()>35) {
					attributes.addAttribute("rev_email2", "Od 4 do 35 znaków");
					attributes.addAttribute("name", name);
					attributes.addAttribute("surname", surname);
					attributes.addAttribute("username", username);
					attributes.addAttribute("description", description);
					return new RedirectView("newAccountTemplate");
				}
				if(password.length()< 5 || password.length()>35) {
					attributes.addAttribute("rev_password", "Od 4 do 35 znaków");
					attributes.addAttribute("name", name);
					attributes.addAttribute("surname", surname);
					attributes.addAttribute("username", username);
					attributes.addAttribute("email", email);
					attributes.addAttribute("email2", email);
					attributes.addAttribute("description", description);
					return new RedirectView("newAccountTemplate");
				}
				if(password2.length()< 5 || password2.length()>35) {
					attributes.addAttribute("rev_password2", "Od 5 do 35 znaków");
					attributes.addAttribute("name", name);
					attributes.addAttribute("surname", surname);
					attributes.addAttribute("username", username);
					attributes.addAttribute("email", email);
					attributes.addAttribute("email2", email);
					attributes.addAttribute("description", description);
					return new RedirectView("newAccountTemplate");
				}
				if(description.length()>200) {
					attributes.addAttribute("rev_description", "Maksymalnie 200 znaków");
					attributes.addAttribute("name", name);
					attributes.addAttribute("surname", surname);
					attributes.addAttribute("username", username);
					attributes.addAttribute("email", email);
					attributes.addAttribute("email2", email2);
					return new RedirectView("newAccountTemplate");
				}
				if(!email.contentEquals(email2)) {
					attributes.addAttribute("rev_email", "Pola email nie są takie same");
					attributes.addAttribute("name", name);
					attributes.addAttribute("surname", surname);
					attributes.addAttribute("username", username);
					attributes.addAttribute("description", description);
					return new RedirectView("newAccountTemplate");
				}
				if(!password.contentEquals(password2)) {
					attributes.addAttribute("rev_password", "Pola hasło nie są takie same");
					attributes.addAttribute("name", name);
					attributes.addAttribute("surname", surname);
					attributes.addAttribute("username", username);
					attributes.addAttribute("email", email);
					attributes.addAttribute("email2", email);
					attributes.addAttribute("description", description);
					return new RedirectView("newAccountTemplate");
				}
				
				Integer existUsername = userRepository.existUsername(username);
				Integer existEmail = userRepository.existEmail(email);
				
				if(existUsername>0) {
					attributes.addAttribute("rev_username", "Nazwa jest już zajęta");
					attributes.addAttribute("name", name);
					attributes.addAttribute("surname", surname);
					attributes.addAttribute("email", email);
					attributes.addAttribute("email2", email2);
					attributes.addAttribute("description", description);
					return new RedirectView("newAccountTemplate");
				}
				if(existEmail>0) {
					attributes.addAttribute("rev_email", "Email jest już zajęty");
					attributes.addAttribute("name", name);
					attributes.addAttribute("surname", surname);
					attributes.addAttribute("username", username);
					attributes.addAttribute("description", description);
					return new RedirectView("newAccountTemplate");
				} 
				NewUser newuser= new NewUser();
				newuser.setName(name);
				newuser.setSurname(surname);
				newuser.setUsername(username);
				newuser.setEmail(email);
				BCryptPasswordEncoder passwordencoder = new BCryptPasswordEncoder();
				String hashpassword = passwordencoder.encode(password);
				newuser.setPassword(hashpassword);
				newuser.setDescription(description);
				newUserRepository.save(newuser);
				
				
			
			    //return new RedirectView("newAccountTemplate");
				return new RedirectView("index");
			}else {
				return new RedirectView("newAccountTemplate");
			}
		    	
    }
	
	@GetMapping("checkusername")
	@ResponseBody
	public String checkusername(@RequestParam("username") String username) {
		System.out.println(username);
	    if(userRepository.existUsername(username)<1) {
	    	 return "<span style=\"color: green;\">Nazwa Dostępna</span>";
	    } else {
	    	return "<span style=\"color: red;\">Nazwa niedostępna</span>";
	    }
	}
	
	@GetMapping("checkemail")
	@ResponseBody
	public String checkemail(@RequestParam("email") String email) {
		System.out.println(email);
	    if(userRepository.existEmail(email)<1) {
	    	 return "<span style=\"color: green;\">Email Dostępny</span>";
	    } else {
	    	return "<span style=\"color: red;\">Email Niedostępny</span>";
	    }
	}
	
} 