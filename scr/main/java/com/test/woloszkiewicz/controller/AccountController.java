package com.test.woloszkiewicz.controller;


import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.test.woloszkiewicz.entity.Group;
import com.test.woloszkiewicz.entity.Loginfail;
import com.test.woloszkiewicz.entity.Test;
import com.test.woloszkiewicz.entity.Testsetting;
import com.test.woloszkiewicz.entity.User;
import com.test.woloszkiewicz.service.AnswereRepository;
import com.test.woloszkiewicz.service.CourseRepository;
import com.test.woloszkiewicz.service.GroupRepository;
import com.test.woloszkiewicz.service.ImgFileAnswereRepository;
import com.test.woloszkiewicz.service.ImgFileQuestionRepository;
import com.test.woloszkiewicz.service.QuestionRepository;
import com.test.woloszkiewicz.service.TestSettingsRepository;
import com.test.woloszkiewicz.service.UserRepository;

@Controller
@RequestMapping("account")
public class AccountController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	TestSettingsRepository testSettingsRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	AnswereRepository answereRepository;

	@Autowired
	ImgFileQuestionRepository imgFileQuestionRepository;

	@Autowired
	ImgFileAnswereRepository imgFileAnswereRepository;

	@GetMapping("deactivationAccount{iduser}")
	public RedirectView deactivationAccount(HttpServletRequest request, @PathVariable("iduser") Integer iduser) {
			
			User user=userRepository.findOneByid(iduser);
			user.setEnabled(false);
			userRepository.flush();
			
		return new RedirectView("../admin/accountdetails"+iduser);
	}
	
	@GetMapping("activationAccount{iduser}")
	public RedirectView activationAccount(HttpServletRequest request, @PathVariable("iduser") Integer iduser) {
			
			User user=userRepository.findOneByid(iduser);
			user.setEnabled(true);
			userRepository.flush();
			
		return new RedirectView("../admin/accountdetails"+iduser);
	}
	
	@GetMapping("unlockAccount{iduser}")
	public RedirectView unlockAccount(HttpServletRequest request, @PathVariable("iduser") Integer iduser) {
			
			User user=userRepository.findOneByid(iduser);
			Loginfail loginfail=user.getLoginfail();
			loginfail.setNumberloginfail(0);
			loginfail.setLockfail(false);
			user.setLoginfail(loginfail);
			userRepository.flush();
			
		return new RedirectView("../admin/accountdetails"+iduser);
	}
	
	@GetMapping("removeGroup{idgroup}/{iduser}")
	public RedirectView removeGroup(HttpServletRequest request, @PathVariable("idgroup") Integer idgroup, @PathVariable("iduser") Integer iduser) {
			
			User user=userRepository.findOneByid(iduser);
			Group remove=groupRepository.findOneByid(idgroup);
			Set<Group> usergroup=user.getGroups();
			usergroup.remove(remove);
			user.setGroups(usergroup);
			Set<User> removeUser=remove.getUsers();
			removeUser.remove(user);
			remove.setUsers(removeUser);
			groupRepository.flush();
			userRepository.flush();
			
		return new RedirectView("../../admin/accountdetails"+iduser);
	}
	
	@GetMapping("TemplateAddGroup{iduser}")
	public ModelAndView TemplateAddGroup(HttpServletRequest request, @PathVariable("iduser") Integer iduser) {
		
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		
		User usertemplate=userRepository.findOneByid(iduser);
		Set<Group> userGrouplist=usertemplate.getGroups();
		
		List<Group> grouplist=groupRepository.findAll();
		
		for (Group group : userGrouplist) {
			
			grouplist.remove(group);
			
		}
		
		mav.addObject("user", usertemplate);
		mav.addObject("grouplist", grouplist);

		mav.setViewName("adminpanel/account/TemplateAddGroup");
		return mav;
	}

	@PostMapping("userAddGroup{idusername}")
	public RedirectView addNewCourse (HttpServletRequest request,  @PathVariable("idusername") Integer idusername,
			@RequestParam("ids") Optional<Integer[]> ids ) {
		
		User user=userRepository.findOneByid(idusername);
		Set<Group> grouplist=user.getGroups();
		
		if(ids.isPresent()) {
			Integer[] sendIds=ids.get();
			for (Integer integer : sendIds) {
				Group group=groupRepository.findOneByid(integer);
				Set<User> userlist=group.getUsers();
				userlist.add(user);
				group.setUsers(userlist);
				grouplist.add(group);
				groupRepository.flush();
				
			}
			user.setGroups(grouplist);
			userRepository.flush();
		}
		
		
		
		
		
		return new RedirectView("../admin/accountdetails"+idusername);
	}
	
	@GetMapping("templateSetTestToUser{iduser}")
	public ModelAndView templateSetTestToUser(HttpServletRequest request, @PathVariable("iduser") Integer iduser) {
		
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		
		User usertemplate=userRepository.findOneByid(iduser);
		
		List<Testsetting> allTestsettings=testSettingsRepository.findAll();
		Set<Test> userTestlist=usertemplate.getTests();
		
		for (Test test : userTestlist) {
			allTestsettings.remove(test.getTestsetting());
		}
		
		mav.addObject("user", usertemplate);
		mav.addObject("testsettings", allTestsettings);

		mav.setViewName("adminpanel/account/templateSetTestToUser");
		return mav;
	}

	@PostMapping("setTestToUser{idusername}")
	public RedirectView setTestToUser (HttpServletRequest request,  @PathVariable("idusername") Integer idusername, @RequestParam("ids") Optional<Integer[]> ids ) {
		
		User user=userRepository.findOneByid(idusername);
		Set<Test> userTests = user.getTests();
		
		if(ids.isPresent()) {
			Integer[] sendIds=ids.get();
			for (Integer integer : sendIds) {
				Testsetting testSetting=testSettingsRepository.findOneById(integer);
				
				Test newTest= new Test();
				newTest.setFirst_init(false);
				newTest.setAvailable(false);
				newTest.setTestsetting(testSetting);
				newTest.setUser(user);
				
				Set<Test> curentTest = testSetting.getTests();
				curentTest.add(newTest);
				testSetting.setTests(curentTest);
				
				
				userTests.add(newTest);
				testSettingsRepository.flush();
				
			}
			user.setTests(userTests);
			userRepository.flush();
		}
		
		
		
		
		
		return new RedirectView("../admin/accountdetails"+idusername);
	}
	
	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public String handleException() {
	       return "Wystąpił błąd";
	}
}