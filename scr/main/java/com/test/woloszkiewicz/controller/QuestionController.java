package com.test.woloszkiewicz.controller;

import java.util.List;
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

import com.test.woloszkiewicz.entity.Answere;
import com.test.woloszkiewicz.entity.Course;
import com.test.woloszkiewicz.entity.Question;
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
@RequestMapping("question")
public class QuestionController {

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

	@GetMapping("singleQuestion{idquestion}")
	public ModelAndView singleQuestion(HttpServletRequest request, @PathVariable("idquestion") Integer idquestion) {

		Question question = questionRepository.findOneById(idquestion);
		Course course=question.getCourse();

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}

		mav.addObject("question", question);
		mav.addObject("course", course);

		mav.setViewName("adminpanel/question/courseQuestionsManagementSingle");
		return mav;
	}

	@GetMapping("coursequestionsManagement/{idcourse}/{single}/{dificulty}")
	public ModelAndView coursequestionsManagement(HttpServletRequest request,
			@PathVariable("idcourse") Integer idcourse, @PathVariable("single") Boolean single,
			@PathVariable("dificulty") Integer dificulty) {

		Course course = courseRepository.findOneByid(idcourse);

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}

		mav.addObject("course", course);
		mav.addObject("dificulty", dificulty);
		mav.addObject("single", single);

		mav.setViewName("adminpanel/question/courseQuestionsManagement");
		return mav;
	}

	@GetMapping("changeSingle{idquestion}")
	public RedirectView changeSingle(HttpServletRequest request, @PathVariable("idquestion") Integer idquestion) {

		Question question = questionRepository.findOneById(idquestion);
		if (question.getSingle()) {
			question.setSingle(false);
		} else {
			question.setSingle(true);
		}

		questionRepository.flush();

		return new RedirectView("/question/editQuestion" + idquestion);
	}

	@GetMapping("changeDificulty{idquestion}")
	public ModelAndView changeDificulty(HttpServletRequest request, @PathVariable("idquestion") Integer idquestion) {

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}

		Question question = questionRepository.findOneById(idquestion);
		Integer dificulty = question.getDificulty();
		Integer questionId = question.getIdquestion();

		mav.addObject("questionId", questionId);
		mav.addObject("dificulty", dificulty);

		mav.setViewName("adminpanel/question/templateChangeDificulty");
		return mav;
	}

	@PostMapping("changeDificultyQuestion{idquestion}")
	public RedirectView changeDificultyQuestion(HttpServletRequest request,
			@RequestParam("dificulty") Integer dificulty, @PathVariable("idquestion") Integer idquestion) {

		Question question = questionRepository.findOneById(idquestion);
		question.setDificulty(dificulty);
		questionRepository.flush();

		return new RedirectView("/question/editQuestion" + idquestion);
	}

	@GetMapping("templatechangeContentQuestion{idquestion}")
	public ModelAndView templatechangeContentQuestion(HttpServletRequest request,
			@PathVariable("idquestion") Integer idquestion) {

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}

		Question question = questionRepository.findOneById(idquestion);
		String content = question.getContent_question();
		Integer questionId = question.getIdquestion();

		mav.addObject("questionId", questionId);
		mav.addObject("content", content);

		mav.setViewName("adminpanel/question/templatechangeContentQuestion");
		return mav;
	}

	@PostMapping("changeContentQuestion{idquestion}")
	public RedirectView changeContentQuestion(HttpServletRequest request, @RequestParam("newContent") String newContent,
			@PathVariable("idquestion") Integer idquestion) {

		Question question = questionRepository.findOneById(idquestion);
		question.setContent_question(newContent);
		questionRepository.flush();

		return new RedirectView("/question/editQuestion" + idquestion);
	}

	@GetMapping("templateChangeContentAnswere{idanswere}")
	public ModelAndView templateChangeContentAnswere(HttpServletRequest request,
			@PathVariable("idanswere") Integer idanswere) {

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login 5555555 " + user);
			mav.addObject("login", userloin);
		}

		Answere answere = answereRepository.findOneById(idanswere);
		Boolean correct = answere.getCorrect();
		String content = answere.getAnswere();
		Integer answereId = answere.getIdanswere();

		mav.addObject("answereId", answereId);
		mav.addObject("content", content);
		mav.addObject("correct", correct);

		mav.setViewName("adminpanel/question/templateChangeContentAnswere");
		return mav;
	}

	@PostMapping("changeContentAnswere{idanswere}")
	public RedirectView changeContentAnswere(HttpServletRequest request, @RequestParam("newContent") String newContent,
			@RequestParam("correct") Integer correct, @PathVariable("idanswere") Integer idanswere) {

		Answere answere = answereRepository.findOneById(idanswere);
		answere.setAnswere(newContent);
		if (correct == 1) {
			answere.setCorrect(true);
		} else {
			answere.setCorrect(false);
		}
		Integer idquestion = answere.getQuestion().getIdquestion();
		answereRepository.flush();

		return new RedirectView("/question/editQuestion" + idquestion);
	}

	@GetMapping("changeArchive{idquestion}")
	public RedirectView changeArchive(HttpServletRequest request, @PathVariable("idquestion") Integer idquestion) {

		Question question = questionRepository.findOneById(idquestion);

		if (question.getArchive()) {
			question.setArchive(false);
		} else {
			question.setArchive(true);
		}
		questionRepository.flush();

		return new RedirectView("/question/editQuestion" + idquestion);
	}

	@GetMapping("removeAnswere{idanswere}")
	public RedirectView removeAnswere(HttpServletRequest request, @PathVariable("idanswere") Integer idanswere) {

		Answere answere = answereRepository.findOneById(idanswere);
		Question question = answere.getQuestion();
		Integer idquestion = question.getIdquestion();
		Set<Answere> answereList = question.getAnsweres();
		if (answereList.size() > 2) {
			answereList.remove(answere);
			question.setAnsweres(answereList);
			answereRepository.delete(answere);
		}

		answereRepository.flush();
		questionRepository.flush();

		return new RedirectView("/question/editQuestion" + idquestion);
	}

	@GetMapping("templateAddAnswere{idquestion}")
	public ModelAndView templateAddAnswere(HttpServletRequest request, @PathVariable("idquestion") Integer idquestion) {

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}

		mav.addObject("questionId", idquestion);

		mav.setViewName("adminpanel/question/templateAddAnswere");
		return mav;
	}

	@PostMapping("addAnswere{idquestion}")
	public RedirectView addAnswere(HttpServletRequest request, @RequestParam("newContent") String newContent,
			@RequestParam("correct") Integer correct, @PathVariable("idquestion") Integer idquestion) {

		Question question = questionRepository.findOneById(idquestion);
		Set<Answere> answerList = question.getAnsweres();
		Answere answere = new Answere();
		answere.setAnswere(newContent);
		if (correct == 1) {
			answere.setCorrect(true);
			answere.setQuestion(question);
			answereRepository.save(answere);
		} else {
			answere.setCorrect(false);
			answere.setQuestion(question);
			answereRepository.save(answere);
		}

		answerList.add(answere);
		question.setAnsweres(answerList);

		answereRepository.flush();
		questionRepository.flush();

		return new RedirectView("/question/editQuestion" + idquestion);
	}

	@GetMapping("removeQuestionFromCourse{idquestion}")
	public RedirectView removeQuestionFromCourse(HttpServletRequest request,
			@PathVariable("idquestion") Integer idquestion) {

		Question question = questionRepository.findOneById(idquestion);
		Course course = question.getCourse();
		Set<Question> questionList = course.getQuestions();
		questionList.remove(question);
		course.setQuestions(questionList);
		questionRepository.delete(question);

		questionRepository.flush();
		courseRepository.flush();

		return new RedirectView("singleQuestion" + idquestion);
	}

	@GetMapping("templateRelocateQuestion{idquestion}")
	public ModelAndView templateRelocateQuestion(HttpServletRequest request,
			@PathVariable("idquestion") Integer idquestion) {

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login 5555555 " + user);
			mav.addObject("login", userloin);
		}
		Question question = questionRepository.findOneById(idquestion);

		List<Course> courseList = courseRepository.findAll();
		courseList.remove(question.getCourse());
		Course curentCourse = question.getCourse();

		mav.addObject("curentCourse", curentCourse);
		mav.addObject("courseList", courseList);
		mav.addObject("idquestion", idquestion);

		mav.setViewName("adminpanel/question/templateRelocateQuestion");
		return mav;
	}

	@PostMapping("relocateQuestion{idquestion}")
	public RedirectView relocateQuestion(HttpServletRequest request, @RequestParam("ids") Integer ids,
			@PathVariable("idquestion") Integer idquestion) {

		Question question = questionRepository.findOneById(idquestion);
		Course course = question.getCourse();

		Course newCourse = courseRepository.findOneByid(ids);

		if (course.getIdcourse() != newCourse.getIdcourse()) {
			Set<Question> curentCourseQuestList=course.getQuestions();
			curentCourseQuestList.remove(question);
			course.setQuestions(curentCourseQuestList);
			
			Set<Question> newCourseQuestList=newCourse.getQuestions();
			newCourseQuestList.add(question);
			newCourse.setQuestions(newCourseQuestList);
			
			question.setCourse(newCourse);
			
			questionRepository.flush();
			courseRepository.flush();
		}

		

		return new RedirectView("singleQuestion" + idquestion);
	}

	@GetMapping("editQuestion{idquestion}")
	public ModelAndView editQuestion(HttpServletRequest request, @PathVariable("idquestion") Integer idquestion) {

		Question question = questionRepository.findOneById(idquestion);

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}

		mav.addObject("question", question);

		mav.setViewName("adminpanel/question/editQuestion");
		return mav;
	}
	
	@GetMapping("lookahead{idquestion}")
	public ModelAndView lookahead(HttpServletRequest request, @PathVariable("idquestion") Integer idquestion) {

		Question question = questionRepository.findOneById(idquestion);

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}

		mav.addObject("question", question);

		mav.setViewName("adminpanel/question/lookahead");
		return mav;
	}
	
	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public String handleException() {
	       return "Wystąpił błąd";
	}
	
}