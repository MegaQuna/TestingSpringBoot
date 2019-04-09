package com.test.woloszkiewicz.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Size;

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

import com.test.woloszkiewicz.entity.Course;
import com.test.woloszkiewicz.entity.Group;
import com.test.woloszkiewicz.entity.User;
import com.test.woloszkiewicz.service.AnswereRepository;
import com.test.woloszkiewicz.service.CourseRepository;
import com.test.woloszkiewicz.service.GroupRepository;
import com.test.woloszkiewicz.service.ImgFileAnswereRepository;
import com.test.woloszkiewicz.service.QuestionRepository;
import com.test.woloszkiewicz.service.TestSettingsRepository;
import com.test.woloszkiewicz.service.ImgFileQuestionRepository;
import com.test.woloszkiewicz.service.UserRepository;


@Controller
@RequestMapping("group")
public class GroupController {

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
	
	@GetMapping("templateNewGroup")
	public ModelAndView templateNewGroup(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}

		mav.setViewName("adminpanel/group/templateNewGroup");
		return mav;
	}
	
	@GetMapping("templateChangeNameGroup{idgroup}")
	public ModelAndView templateChangeNameGroup(HttpServletRequest request, @PathVariable("idgroup") Integer idgroup) {
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		Group group=groupRepository.findOneByid(idgroup);
		
		mav.addObject("group", group);
		mav.setViewName("adminpanel/group/templateChangeNameGroup");
		return mav;
	}
	
	@GetMapping("templateChangeDescriptionGroup{idgroup}")
	public ModelAndView templateChangeDescriptionGroup(HttpServletRequest request, @PathVariable("idgroup") Integer idgroup) {
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		Group group=groupRepository.findOneByid(idgroup);
		
		mav.addObject("group", group);
		mav.setViewName("adminpanel/group/templateChangeDescriptionGroup");
		return mav;
	}
	
	@GetMapping("templateGroupAddStudent{idgroup}")
	public ModelAndView templateGroupAddStudent(HttpServletRequest request, @PathVariable("idgroup") Integer idgroup) {
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		Group group=groupRepository.findOneByid(idgroup);
		List<User> alluserlist=new ArrayList<User>(userRepository.findAll());
		List<User> userlist=new ArrayList<User>();
		for (User user2 : alluserlist) {
			if(!user2.getGroups().contains(group) && user2.getAuthorityName().contentEquals("ROLE_USER") && user2.getEnabled()) {
				userlist.add(user2);
			}
		}
		mav.addObject("group", group);
		mav.addObject("userlist", userlist);	
		mav.setViewName("adminpanel/group/templateGroupAddStudent");
		return mav;
	}
	
	@GetMapping("templateGroupAddCourse{idgroup}")
	public ModelAndView templateGroupAddCourse(HttpServletRequest request, @PathVariable("idgroup") Integer idgroup) {
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		Group group=groupRepository.findOneByid(idgroup);
		List<Course> allcourselist=new ArrayList<Course>(courseRepository.findAllByArchive(false));
		List<Course> courselist=new ArrayList<Course>();
		for (Course course : allcourselist) {
			if(!course.getGroups().contains(group)) {
				courselist.add(course);
			}
		}
		mav.addObject("group", group);
		mav.addObject("courselist", courselist);	
		mav.setViewName("adminpanel/group/templateGroupAddCourse");
		return mav;
	}
	
	@GetMapping("groupRemoveStudent{idgroup}/{idStudent}")
	public RedirectView groupRemoveStudent(HttpServletRequest request, @PathVariable("idgroup") Integer idgroup, @PathVariable("idStudent") Integer idStudent ) {
		
		Group group=groupRepository.findOneByid(idgroup);
		User usersend=userRepository.findOneByid(idStudent);

		Set<Group> userGroups=usersend.getGroups();
		userGroups.remove(group);
		usersend.setGroups(userGroups);
		Set<User> userInGroup=group.getUsers();
		userInGroup.remove(usersend);
		group.setUsers(userInGroup);
		
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		userRepository.flush();
		groupRepository.flush();
		
		return new RedirectView("../../admin/groupdetail"+idgroup);
	}
	
	@GetMapping("groupRemoveCourse{idgroup}/{idCourse}")
	public RedirectView groupRemoveCourse(HttpServletRequest request, @PathVariable("idgroup") Integer idgroup, @PathVariable("idCourse") Integer idCourse ) {
		
		Group group=groupRepository.findOneByid(idgroup);
		Course coursesend=courseRepository.findOneByid(idCourse);

		Set<Group> courseGroups=coursesend.getGroups();
		courseGroups.remove(group);
		coursesend.setGroups(courseGroups);
		Set<Course> courseInGroup=group.getCourses();
		courseInGroup.remove(coursesend);
		group.setCourses(courseInGroup);
		
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		courseRepository.flush();
		groupRepository.flush();
		
		return new RedirectView("../../admin/groupdetail"+idgroup);
	}
	
	@GetMapping("removeGroup{idgroup}")
	public RedirectView removeGroup(HttpServletRequest request, @PathVariable("idgroup") Integer idgroup) {
		
		Group group=groupRepository.findOneByid(idgroup);
		Set<Course> courseGroup=group.getCourses();
		Set<User> userGroup=group.getUsers();
		
		for (User user : userGroup) {
			Set<Group> userDetailGroup=user.getGroups();
				userDetailGroup.remove(group);
				user.setGroups(userDetailGroup);
				userRepository.flush();
		}
		
		for (Course course : courseGroup) {
			Set<Group> courseDetailGroup=course.getGroups();
				courseDetailGroup.remove(group);
				course.setGroups(courseDetailGroup);
				courseRepository.flush();
		}
		
		groupRepository.delete(group);
		groupRepository.flush();

		return new RedirectView("../admin/groupManagement");
	}
	
	@PostMapping("groupAddStudent{idgroup}")
	public RedirectView groupAddStudent(HttpServletRequest request, @PathVariable("idgroup") Integer idgroup, @RequestParam("ids") Optional<Integer[]> ids ) {
		
		Group group=groupRepository.findOneByid(idgroup);
		
		if(ids.isPresent()) {
			Integer[] sendIds=ids.get();
			for (Integer integer : sendIds) {
				User usersend=userRepository.findOneByid(integer);
				Set<Group> userGroups=usersend.getGroups();
				userGroups.add(group);
				usersend.setGroups(userGroups);
				Set<User> userInGroup=group.getUsers();
				userInGroup.add(usersend);
				group.setUsers(userInGroup);
			}
		}
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		userRepository.flush();
		groupRepository.flush();
		
		return new RedirectView("../admin/groupdetail"+idgroup);
	}
	
	@PostMapping("groupAddCourse{idgroup}")
	public RedirectView groupAddCourse(HttpServletRequest request, @PathVariable("idgroup") Integer idgroup, @RequestParam("ids") Optional<Integer[]> ids ) {
		
		Group group=groupRepository.findOneByid(idgroup);
		
		
		if(ids.isPresent()) {
			Integer[] sendIds=ids.get();
			for (Integer integer : sendIds) {
				Course coursesend=courseRepository.findOneByid(integer);
				Set<Group> courseGroups=coursesend.getGroups();
				courseGroups.add(group);
				coursesend.setGroups(courseGroups);
				Set<Course> courseInGroup=group.getCourses();
				courseInGroup.add(coursesend);
				group.setCourses(courseInGroup);;
			}
		}
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		courseRepository.flush();
		groupRepository.flush();
		
		return new RedirectView("../admin/groupdetail"+idgroup);
	}
	
	@PostMapping("changeNameGroup{idgroup}")
	public RedirectView changeNameGroup(HttpServletRequest request, @PathVariable("idgroup") Integer idgroup, @RequestParam("newNameGroup") String newNameGroup ) {
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		Group group=groupRepository.findOneByid(idgroup);
		group.setGroupname(newNameGroup);
		groupRepository.flush();
		
		return new RedirectView("../admin/groupdetail"+idgroup);
	}
	
	@PostMapping("changeDescriptionGroup{idgroup}")
	public RedirectView changeDescriptionGroup(HttpServletRequest request, @PathVariable("idgroup") Integer idgroup, @RequestParam("newDescriptionGroup") String newDescriptionGroup ) {
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		Group group=groupRepository.findOneByid(idgroup);
		group.setDescription(newDescriptionGroup);;
		groupRepository.flush();
		
		return new RedirectView("../admin/groupdetail"+idgroup);
	}
	
	@PostMapping("addNewGroup")
	public RedirectView addNewGroup (HttpServletRequest request, @Size(max=60) @RequestParam("name") String name, @RequestParam("description") String description  ) {
		
		Group group=new Group();
		group.setGroupname(name);
		group.setDescription(description);
		group.setCreate_date(new Date());
		
		groupRepository.save(group);
		
		return new RedirectView("../admin/groupManagement");
	}
	
	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public String handleException() {
	       return "Wystąpił błąd";
	}
}