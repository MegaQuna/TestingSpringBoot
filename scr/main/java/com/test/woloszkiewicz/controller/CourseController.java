package com.test.woloszkiewicz.controller;

import java.util.ArrayList;
import java.util.HashSet;
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

import com.test.woloszkiewicz.entity.Answere;
import com.test.woloszkiewicz.entity.Course;
import com.test.woloszkiewicz.entity.Group;
import com.test.woloszkiewicz.entity.Question;
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
@RequestMapping("course")
public class CourseController {

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
	
	@GetMapping("templateNewCourse")
	public ModelAndView templateNewCourse(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}

		mav.setViewName("adminpanel/course/templateNewCourse");
		return mav;
	}
	
	@PostMapping("addNewCourse")
	public RedirectView addNewCourse (HttpServletRequest request, @Size(max=60) @RequestParam("name") String name, @RequestParam("description") String description  ) {
		
		Course course=new Course();
		course.setCoursename(name);
		course.setDescription(description);
		course.setArchive(false);
		
		
		courseRepository.save(course);
		
		return new RedirectView("../admin/courseManagement");
	}
	
	@GetMapping("templateChangeNameCourse{idcourse}")
	public ModelAndView templateChangeNameCourse(HttpServletRequest request, @PathVariable("idcourse") Integer idcourse) {
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		Course course=courseRepository.findOneByid(idcourse);
		
		mav.addObject("course", course);
		mav.setViewName("adminpanel/course/templateChangeNameCourse");
		return mav;
	}
	
	@PostMapping("changeNameCourse{idcourse}")
	public RedirectView changeNameCourse(HttpServletRequest request, @PathVariable("idcourse") Integer idcourse, @RequestParam("newNameCourse") String newNameCourse ) {
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		Course course=courseRepository.findOneByid(idcourse);
		course.setCoursename(newNameCourse);
		courseRepository.flush();
		
		return new RedirectView("../admin/coursedetail"+idcourse);
	}
	
	@GetMapping("templateChangeDescriptionCourse{idcourse}")
	public ModelAndView templateChangeDescriptionCourse(HttpServletRequest request, @PathVariable("idcourse") Integer idcourse) {
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		Course course=courseRepository.findOneByid(idcourse);
		
		mav.addObject("course", course);
		mav.setViewName("adminpanel/course/templateChangeDescriptionCourse");
		return mav;
	}
	
	@PostMapping("changeDescriptionCourse{idcourse}")
	public RedirectView changeDescriptionCourse(HttpServletRequest request, @PathVariable("idcourse") Integer idcourse, @RequestParam("newDescriptionCourse") String newDescriptionCourse ) {
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		Course course=courseRepository.findOneByid(idcourse);
		course.setDescription(newDescriptionCourse);
		courseRepository.flush();
		
		return new RedirectView("../admin/coursedetail"+idcourse);
	}
	
	@GetMapping("templateCourseAddGroup{idcourse}")
	public ModelAndView templateCourseAddGroup(HttpServletRequest request, @PathVariable("idcourse") Integer idcourse) {
		
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		Course course=courseRepository.findOneByid(idcourse);
		
		List<Group> allgrouplist=new ArrayList<Group>(groupRepository.findAll());
		List<Group> grouplist=new ArrayList<Group>();
		for (Group group : allgrouplist) {
			if(!group.getCourses().contains(course)) {
				grouplist.add(group);
			}
		}
		
		mav.addObject("course", course);
		mav.addObject("grouplist", grouplist);	
		mav.setViewName("adminpanel/course/templateCourseAddGroup");
		return mav;
	}
	
	@PostMapping("courseAddGroup{idcourse}")
	public RedirectView courseAddGroup(HttpServletRequest request, @PathVariable("idcourse") Integer idcourse, @RequestParam("ids") Optional<Integer[]> ids ) {
		
		Course course=courseRepository.findOneByid(idcourse);
		
		if(ids.isPresent()) {
			Integer[] sendIds=ids.get();
			for (Integer integer : sendIds) {
				
				Group groupsend=groupRepository.findOneByid(integer);
				Set<Course> courseGroups=groupsend.getCourses();
				courseGroups.add(course);
				groupsend.setCourses(courseGroups);
				Set<Group> groupInCourse = course.getGroups();
				groupInCourse.add(groupsend);
				course.setGroups(groupInCourse);
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
		
		return new RedirectView("../admin/coursedetail"+idcourse);
	}
	
	@GetMapping("courseRemoveGroup{idcourse}/{idgroup}")
	public RedirectView courseRemoveGroup(HttpServletRequest request, @PathVariable("idcourse") Integer idcourse, @PathVariable("idgroup") Integer idgroup ) {
		
		Course course=courseRepository.findOneByid(idcourse);
		Group group=groupRepository.findOneByid(idgroup);
		
		//User usersend=userRepository.findOneByid(idStudent);

		Set<Group> courseGroups=course.getGroups();
		courseGroups.remove(group);
		course.setGroups(courseGroups);
		
		Set<Course> courseInGroup=group.getCourses();
		courseInGroup.remove(course);
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
		userRepository.flush();
		groupRepository.flush();
		
		return new RedirectView("../../admin/coursedetail"+idcourse);
	}

	@GetMapping("moveToArchive{idcourse}")
	public RedirectView moveToArchive(HttpServletRequest request, @PathVariable("idcourse") Integer idcourse) {
		
		Course course=courseRepository.findOneByid(idcourse);
		course.setArchive(true);
		Set<Group> groupCourse=course.getGroups();
		
		for (Group group : groupCourse) {
			Set<Course> courseDetailGroup=group.getCourses();
				courseDetailGroup.remove(course);
				group.setCourses(courseDetailGroup);
				groupRepository.flush();
		}
		
		Set<Group> clean = new HashSet<Group>();
		course.setGroups(clean);

		courseRepository.flush();
		
		return new RedirectView("../admin/coursedetail"+idcourse);
	}
	
	@GetMapping("moveFromArchive{idcourse}")
	public RedirectView moveFromArchive(HttpServletRequest request, @PathVariable("idcourse") Integer idcourse) {
		
		Course course=courseRepository.findOneByid(idcourse);
		course.setArchive(false);
		courseRepository.flush();
		
		return new RedirectView("../admin/coursedetail"+idcourse);
	}
	
	@GetMapping("removeCourse{idcourse}")
	public RedirectView removeCourse(HttpServletRequest request, @PathVariable("idcourse") Integer idcourse) {
		
		Course course=courseRepository.findOneByid(idcourse);
		if(course.canWeremove()) {
			
			Set<Group> groupCourse=course.getGroups();
			
			for (Group group : groupCourse) {
				Set<Course> courseDetailGroup=group.getCourses();
					courseDetailGroup.remove(course);
					group.setCourses(courseDetailGroup);
					groupRepository.flush();
			}
			
			Set<Question> questionCourse=course.getQuestions();
			
			for (Question question : questionCourse) {
				question.setCourse(null);
				questionRepository.flush();
			}
			
			courseRepository.delete(course);
			courseRepository.flush();
		}
		
		return new RedirectView("../admin/courseManagement");
	}

	@GetMapping("addQuestionToCourse{idcourse}")
	public RedirectView templateAddQuestionToCourse(HttpServletRequest request, @PathVariable("idcourse") Integer idcourse) {
		
		Course course=courseRepository.findOneByid(idcourse);
		Set<Question> questionsCourse = course.getQuestions();
		
		Question question=new Question();
		question.setArchive(false);
		question.setContent_question("Nowe Pytanie");
		question.setDificulty(1);
		question.setSingle(true);
		question.setCourse(course);
		
		
		Answere answere1= new Answere();
		answere1.setAnswere("Odpowiedz");
		answere1.setCorrect(false);
		answere1.setQuestion(question);
		
		Answere answere2= new Answere();
		answere2.setAnswere("Odpowiedz");
		answere2.setCorrect(false);
		answere2.setQuestion(question);
		
		Set<Answere> answereQuestion= new HashSet<Answere>();
		answereQuestion.add(answere1);
		answereQuestion.add(answere2);
		
		question.setAnsweres(answereQuestion);
		questionsCourse.add(question);
		course.setQuestions(questionsCourse);
		courseRepository.flush();
		int questionId=question.getIdquestion();
		
		return new RedirectView("/question/editQuestion"+questionId);
	}
	
	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public String handleException() {
	       return "Wystąpił błąd";
	}
	
}