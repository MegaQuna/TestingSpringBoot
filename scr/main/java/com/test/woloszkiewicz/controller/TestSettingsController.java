package com.test.woloszkiewicz.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.VerticalPositionMark;
import com.test.woloszkiewicz.entity.Answere;
import com.test.woloszkiewicz.entity.Course;
import com.test.woloszkiewicz.entity.Group;
import com.test.woloszkiewicz.entity.Question;
import com.test.woloszkiewicz.entity.Systemresult;
import com.test.woloszkiewicz.entity.Systemscore;
import com.test.woloszkiewicz.entity.Test;
import com.test.woloszkiewicz.entity.TestQuestionDeliveryAgent;
import com.test.woloszkiewicz.entity.Testsetting;
import com.test.woloszkiewicz.entity.User;
import com.test.woloszkiewicz.service.AnswereRepository;
import com.test.woloszkiewicz.service.CourseRepository;
import com.test.woloszkiewicz.service.GroupRepository;
import com.test.woloszkiewicz.service.ImgFileAnswereRepository;
import com.test.woloszkiewicz.service.ImgFileQuestionRepository;
import com.test.woloszkiewicz.service.PKRepository;
import com.test.woloszkiewicz.service.QuestionRepository;
import com.test.woloszkiewicz.service.SystemresultRepository;
import com.test.woloszkiewicz.service.SystemscoreRepository;
import com.test.woloszkiewicz.service.TestRepository;
import com.test.woloszkiewicz.service.TestSettingsRepository;
import com.test.woloszkiewicz.service.UserRepository;

@Controller
@RequestMapping("testsettings")
public class TestSettingsController {

	@Autowired
	UserRepository userRepository;

	@Autowired
	GroupRepository groupRepository;

	@Autowired
	CourseRepository courseRepository;

	@Autowired
	TestSettingsRepository testSettingsRepository;
	
	@Autowired
	TestRepository testRepository;

	@Autowired
	QuestionRepository questionRepository;

	@Autowired
	AnswereRepository answereRepository;

	@Autowired
	ImgFileQuestionRepository imgFileQuestionRepository;

	@Autowired
	ImgFileAnswereRepository imgFileAnswereRepository;
	
	@Autowired
	SystemscoreRepository systemscoreRepository;
	
	@Autowired
	SystemresultRepository systemresultRepository;
	
	@Autowired
    private JavaMailSender emailSender;
	
	@Autowired
	PKRepository pkRepository;

	@GetMapping("templateSystemScoreEdit{idsystemscore}")
	public ModelAndView templateSystemScoreEdit(HttpServletRequest request, @PathVariable("idsystemscore") Integer idsystemscore) {

		Systemscore systemscore=systemscoreRepository.findOneById(idsystemscore);

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		
		mav.addObject("idsystemscore", systemscore.getIdsystemscore());
		mav.addObject("description", systemscore.getDescription());
		mav.addObject("onlycorect", systemscore.getOnly_corect());
		mav.addObject("pointforcorect", systemscore.getPoint_for_corect());
		mav.addObject("pointforincorect", systemscore.getPoint_for_incorect());
		mav.addObject("single", systemscore.getSingle());

		mav.setViewName("adminpanel/test/templateSystemScoreEdit");
		return mav;
		
	}

	@PostMapping("SystemScoreEdit{idsystemscore}")
	public RedirectView SystemScoreEdit (HttpServletRequest request, @PathVariable("idsystemscore") Integer idsystemscore, @RequestParam("description") String description, @RequestParam("single") Integer single,
			@RequestParam("onlycorect") Integer onlycorect, @RequestParam("onlycorectPointCorrect") Integer onlycorectPointCorrect, @RequestParam("notonlycorectPointCorrect") Integer notonlycorectPointCorrect ) {
		
		Systemscore systemscore=systemscoreRepository.findOneById(idsystemscore);
		
		systemscore.setDescription(description);
		if(onlycorectPointCorrect>10) {
			onlycorectPointCorrect=10;
		}
		if(onlycorectPointCorrect<1) {
			onlycorectPointCorrect=1;
		}
		
		if(notonlycorectPointCorrect>10) {
			notonlycorectPointCorrect=10;
		}
		if(notonlycorectPointCorrect<1) {
			notonlycorectPointCorrect=1;
		}
		
		systemscore.setPoint_for_corect(Double.valueOf(onlycorectPointCorrect));
		systemscore.setPoint_for_incorect(Double.valueOf(notonlycorectPointCorrect));
		
		if(single==0) {
			systemscore.setSingle(false);
		}else {
			systemscore.setSingle(true);
		}
		
		if(onlycorect==0) {
			systemscore.setOnly_corect(false);
		}else {
			systemscore.setOnly_corect(true);
		}
		
		systemscoreRepository.flush();
		
		return new RedirectView("../admin/scoreAndResultManagement");
	}
	
	@GetMapping("systemScoreArchive{idsystemscore}")
	public RedirectView systemScoreArchive(HttpServletRequest request, @PathVariable("idsystemscore") Integer idsystemscore) {

		Systemscore systemscore=systemscoreRepository.findOneById(idsystemscore);

		if(systemscore.canBeChange()) {
			systemscoreRepository.delete(systemscore);
		}else {
			systemscore.setArchive(true);
			systemscoreRepository.flush();	
		}
		

		return new RedirectView("../admin/scoreAndResultManagement");
		
	}
		
	@GetMapping("templateAddNewSystemScore")
	public ModelAndView templateAddNewSystemScore(HttpServletRequest request) {

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		
		mav.addObject("onlycorect", true);
		mav.addObject("pointforcorect", 1);
		mav.addObject("pointforincorect", 1);
		mav.addObject("single", true);

		mav.setViewName("adminpanel/test/templateAddNewSystemScore");
		return mav;
		
	}
	
	@PostMapping("AddNewSystemScore")
	public RedirectView AddNewSystemScore (HttpServletRequest request, @RequestParam("description") String description, @RequestParam("single") Integer single,
			@RequestParam("onlycorect") Integer onlycorect, @RequestParam("onlycorectPointCorrect") Integer onlycorectPointCorrect, @RequestParam("notonlycorectPointCorrect") Integer notonlycorectPointCorrect ) {
		
		Systemscore systemscore=new Systemscore();
		
		systemscore.setArchive(false);
		systemscore.setDescription(description);
		if(onlycorectPointCorrect>10) {
			onlycorectPointCorrect=10;
		}
		if(onlycorectPointCorrect<1) {
			onlycorectPointCorrect=1;
		}
		
		if(notonlycorectPointCorrect>10) {
			notonlycorectPointCorrect=10;
		}
		if(notonlycorectPointCorrect<1) {
			notonlycorectPointCorrect=1;
		}
		
		systemscore.setPoint_for_corect(Double.valueOf(onlycorectPointCorrect));
		systemscore.setPoint_for_incorect(Double.valueOf(notonlycorectPointCorrect));
		
		if(single==0) {
			systemscore.setSingle(false);
		}else {
			systemscore.setSingle(true);
		}
		
		if(onlycorect==0) {
			systemscore.setOnly_corect(false);
		}else {
			systemscore.setOnly_corect(true);
		}
		
		systemscoreRepository.save(systemscore);
		
		return new RedirectView("../admin/scoreAndResultManagement");
	}
	
	@GetMapping("systemResultArchive{idsystemresult}")
	public RedirectView systemResultArchive(HttpServletRequest request, @PathVariable("idsystemresult") Integer idsystemresult) {

		Systemresult systemresult=systemresultRepository.findOneById(idsystemresult);

		if(systemresult.canBeChange()) {
			systemresultRepository.delete(systemresult);
		}else {
			systemresult.setArchive(true);
			systemresultRepository.flush();	
		}
		

		return new RedirectView("../admin/scoreAndResultManagement");
		
	}

	@GetMapping("templateAddNewSystemResult")
	public ModelAndView templateAddNewSystemResult(HttpServletRequest request, @RequestParam("error") Optional<String> error,
			 @RequestParam("description") Optional<String> description, @RequestParam("three") Optional<Integer> three, @RequestParam("threeAndHalf") Optional<Integer> threeAndHalf, @RequestParam("four") Optional<Integer> four,
			 @RequestParam("fourAndHalf") Optional<Integer> fourAndHalf, @RequestParam("five") Optional<Integer> five) {

		ModelAndView mav = new ModelAndView();
		if(error.isPresent()){
			mav.addObject("error", error.get());
		  }
		if(description.isPresent()){
			mav.addObject("description", description.get());
		  }else {
			mav.addObject("description", " "); 
		  }	
		
		if(three.isPresent()){
			mav.addObject("three", three.get());
		  }else {
			  mav.addObject("three", 1);
		  }
		
		if(threeAndHalf.isPresent()){
			mav.addObject("threeAndHalf", threeAndHalf.get());
		  }else {
			  mav.addObject("threeAndHalf", 1);
		  }
		
		if(four.isPresent()){
			mav.addObject("four", four.get());
		  }else {
			  mav.addObject("four", 1);
		  }
		
		if(fourAndHalf.isPresent()){
			mav.addObject("fourAndHalf", fourAndHalf.get());
		  }else {
			  mav.addObject("fourAndHalf", 1);
		  }
		
		if(five.isPresent()){
			mav.addObject("five", five.get());
		  }else {
			  mav.addObject("five", 1);
		  }
		

		String user = request.getRemoteUser();
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		
		mav.setViewName("adminpanel/test/templateAddNewSystemResult");
		return mav;
		
	}

	@PostMapping("AddNewSystemResult")
	public RedirectView AddNewSystemResult (RedirectAttributes attributes ,HttpServletRequest request, @RequestParam("description") String description, @RequestParam("three") Integer three,
			@RequestParam("threeAndHalf") Integer threeAndHalf, @RequestParam("four") Integer four, @RequestParam("fourAndHalf") Integer fourAndHalf, @RequestParam("five") Integer five) {
		
		System.out.println("3 :"+three+" 3,5 :"+threeAndHalf+" 4 :"+four+" 4,5 :"+fourAndHalf+" 5 :"+five);
		System.out.println("3>3,5 :"+(three < threeAndHalf)+" 3,5>4 :"+(threeAndHalf<four)+" 4>4,5 :"+(four<fourAndHalf)+" 4,4>5 :"+(fourAndHalf <five));
		
		if((three < threeAndHalf) && (threeAndHalf < four) && four < fourAndHalf && fourAndHalf < five) {
			
			if(three>0 && three<101 && threeAndHalf>0 && threeAndHalf<101 && four>0 && four<101 && fourAndHalf>0 && fourAndHalf<101 && five>0 && five<101) {
				Systemresult systemresult=new Systemresult();
				systemresult.setThree(Double.valueOf(three)/100);
				systemresult.setThreeAndHalf(Double.valueOf(threeAndHalf)/100);
				systemresult.setFour(Double.valueOf(four)/100);
				systemresult.setFourAndHalf(Double.valueOf(fourAndHalf)/100);
				systemresult.setFive(Double.valueOf(five)/100);
				systemresult.setName(description);
				systemresult.setArchive(false);
				systemresultRepository.save(systemresult);
				
				return new RedirectView("../admin/scoreAndResultManagement");
			}else {
				attributes.addAttribute("error", "Zakres procentowy musi być miedzy 1 a 100");
				attributes.addAttribute("three", three);
				attributes.addAttribute("threeAndHalf", threeAndHalf);
				attributes.addAttribute("four", four);
				attributes.addAttribute("fourAndHalf", fourAndHalf );
				attributes.addAttribute("five", five);
				attributes.addAttribute("description", description);
				return new RedirectView("templateAddNewSystemResult");
			}
				
		}else {
			attributes.addAttribute("error", "Oceny wyższe muszą mieć wiekszą wartość procentową od ocen niższych");
			attributes.addAttribute("three", three);
			attributes.addAttribute("threeAndHalf", threeAndHalf);
			attributes.addAttribute("four", four);
			attributes.addAttribute("fourAndHalf", fourAndHalf );
			attributes.addAttribute("five", five);
			attributes.addAttribute("description", description);
			return new RedirectView("templateAddNewSystemResult");
		}
		
		
	}

	@GetMapping("testEdit{idtestsetting}")
	public ModelAndView testEdit(HttpServletRequest request, @PathVariable("idtestsetting") Integer idtestsetting, @RequestParam("error") Optional<String> error) {

		
		
		Testsetting testsetting=testSettingsRepository.findOneById(idtestsetting);
		Course courseNow=testsetting.getCourse();
		
		List<Course> courseList=courseRepository.findAll();
		courseList.remove(courseNow);
		
		Systemresult systemresult=testsetting.getSystem_result();
		List<Systemresult> systemresultList=systemresultRepository.findAll();
		systemresultList.remove(systemresult);
		
		
		List<Systemscore> systemscoretListAll=systemscoreRepository.findAll();
		systemscoretListAll.remove(testsetting.getSystem_score());
		List<Systemscore> systemscoretList=new ArrayList<Systemscore>();
		for (Systemscore systemscore : systemscoretListAll) {
			if(systemscore.getSingle()== testsetting.getSingle()) {
				 systemscoretList.add(systemscore);
			}
		}
		
		
		List<User> users= new ArrayList<User>();
		Set<Test> tests=testsetting.getTests();
		for (Test test : tests) {
			users.add(test.getUser());
		}
		
		Collections.sort(users, new Comparator<Object>() {
            @Override
            public int compare(Object softDrinkOne, Object softDrinkTwo) {
                return ((User)softDrinkOne).getSurname()
                        .compareTo(((User)softDrinkTwo).getSurname());
            }
        }); 
		
		Integer dificulty=testsetting.getDificulty();
		Integer dificultyFrom=1;
		Integer dificultyTo=1;
		switch (dificulty) {
		case 1:{
			dificultyFrom=1;
			dificultyTo=1;
			break;
		}
		case 2:{
			dificultyFrom=1;
			dificultyTo=2;
			break;
		}
		case 3:{
			dificultyFrom=1;
			dificultyTo=3;
			break;
		}
		case 4:{
			dificultyFrom=2;
			dificultyTo=2;
			break;
		}
		case 5:{
			dificultyFrom=2;
			dificultyTo=3;
			break;
		}
		case 6:{
			dificultyFrom=3;
			dificultyTo=3;
			break;
		}
		default:{
			dificultyFrom=1;
			dificultyTo=1;
			break;
		}
		}
		
		Set<Question> questionList=courseNow.getQuestions(false, dificultyFrom, dificultyTo, testsetting.getSingle());

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		
		List<User> allusers=userRepository.findAll();
		List<User> userWithNoGroups= new ArrayList<User>();
		for (User user2 : allusers) {
			if(user2.getGroups().isEmpty() && user2.getAuthorityName().contentEquals("ROLE_USER") && user2.getEnabled()) {
				userWithNoGroups.add(user2);
			}		
		}
		
		for (User user2 : users) {
			userWithNoGroups.remove(user2);	
		}
		
		if(!testsetting.getEverRun()) {
			mav.addObject("testsetting", testsetting);
			mav.addObject("systemscore", testsetting.getSystem_score());
			mav.addObject("courseList", courseList);
			mav.addObject("questionList", questionList.size());
			mav.addObject("systemresultList", systemresultList);
			mav.addObject("systemscoretList", systemscoretList);
			mav.addObject("users", users);
			mav.addObject("userWithNoGroups", userWithNoGroups);
			mav.addObject("groups", groupRepository.findAll());
		}else {
			mav.addObject("testsetting", testsetting);
			mav.addObject("systemscore", testsetting.getSystem_score());
			mav.addObject("systemresultList", systemresultList);
			mav.addObject("userWithNoGroups", userWithNoGroups);
			mav.addObject("groups", groupRepository.findAll());
			mav.addObject("users", users);
		}
		
		if(error.isPresent()){
			mav.addObject("error", error.get());
		  }
		
		mav.setViewName("adminpanel/test/testEdit");
		return mav;
		
	}
		
	@GetMapping("templateAddNewTest")
	public ModelAndView templateAddNewTest(HttpServletRequest request, @RequestParam("error") Optional<String> error) {

		ModelAndView mav = new ModelAndView();
		
		if(error.isPresent()) {
			mav.addObject("error", error.get());
		}
		
		
		String user = request.getRemoteUser();
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		List<Course> courses=courseRepository.findAll();
		List<Group> groups=groupRepository.findAll();
		
		mav.addObject("courses", courses);
		mav.addObject("groups", groups);

		mav.setViewName("adminpanel/test/templateAddNewTest");
		return mav;
		
	}

	@PostMapping("changeSingle{idtest}")
	public RedirectView changeSingle (HttpServletRequest request,RedirectAttributes attributes, @PathVariable("idtest") Integer idtest, @RequestParam("single") Integer single) {
		
		Testsetting testsetting=testSettingsRepository.findOneById(idtest);
		if(testsetting.getEverRun()) {
			attributes.addAttribute("error", "Testy zostały przeprowadzone nie można zmienić tego parametru");
			return new RedirectView("/testsettings/testEdit"+idtest);
		}else {
			if(single == 0) {
				testsetting.setSingle(false);
				testsetting.setSystem_score(null);
			}else {
				testsetting.setSingle(true);
				testsetting.setSystem_score(null);
			}
			testsetting.setPossible_max_score(Double.valueOf(0));
			testsetting.setNumber_of_questions(0);
			
			testSettingsRepository.flush();
		}
				
		return new RedirectView("/testsettings/testEdit"+idtest);
	}
	
	@PostMapping("changeCourse{idtest}")
	public RedirectView changeCourse (HttpServletRequest request,RedirectAttributes attributes, @PathVariable("idtest") Integer idtest, @RequestParam("course") Integer course) {
		
		Testsetting testsetting=testSettingsRepository.findOneById(idtest);
		if(testsetting.getEverRun()) {
			attributes.addAttribute("error", "Testy zostały przeprowadzone nie można zmienić Kursu");
			return new RedirectView("/testsettings/testEdit"+idtest);
		}else {
			Course currentCourse=testsetting.getCourse();
			Set<Testsetting> testsettingscurrentCourse=currentCourse.getTestsetting();
			testsettingscurrentCourse.remove(testsetting);
			currentCourse.setTestsetting(testsettingscurrentCourse);
			
			Course newCourse=courseRepository.findOneByid(course);
			Set<Testsetting> testsettingsCourse=newCourse.getTestsetting();
			testsettingsCourse.add(testsetting);
			newCourse.setTestsetting(testsettingsCourse);
			
			testsetting.setCourse(newCourse);
			
			testsetting.setPossible_max_score(Double.valueOf(0));
			testsetting.setNumber_of_questions(0);
			
			courseRepository.flush();
			testSettingsRepository.flush();
		}
				
		return new RedirectView("/testsettings/testEdit"+idtest);
	}
	
	@PostMapping("changeDifficulty{idtest}")
	public RedirectView changeDifficulty (HttpServletRequest request,RedirectAttributes attributes, @PathVariable("idtest") Integer idtest, @RequestParam("difficulty") Integer difficulty) {
		
		Testsetting testsetting=testSettingsRepository.findOneById(idtest);
		if(testsetting.getEverRun()) {
			attributes.addAttribute("error", "Testy zostały przeprowadzone nie można zmienić Stopnia trudnośći");
			return new RedirectView("/testsettings/testEdit"+idtest);
		}else {
			testsetting.setDificulty(difficulty);
			
			testsetting.setPossible_max_score(Double.valueOf(0));
			testsetting.setNumber_of_questions(0);
			
			testSettingsRepository.flush();
		}
				
		return new RedirectView("/testsettings/testEdit"+idtest);
	}
	
	@PostMapping("changeNumberQuestion{idtest}")
	public RedirectView changeNumberQuestion (HttpServletRequest request,RedirectAttributes attributes, @PathVariable("idtest") Integer idtest, @RequestParam("numberQuestions") Integer numberQuestions) {
		
		Testsetting testsetting=testSettingsRepository.findOneById(idtest);
		if(testsetting.getEverRun()) {
			attributes.addAttribute("error", "Testy zostały przeprowadzone nie można zmienić Ilości pytań");
			return new RedirectView("/testsettings/testEdit"+idtest);
		}else {
			Integer dificulty=testsetting.getDificulty();
			Integer dificultyFrom=1;
			Integer dificultyTo=1;
			switch (dificulty) {
			case 1:{
				dificultyFrom=1;
				dificultyTo=1;
				break;
			}
			case 2:{
				dificultyFrom=1;
				dificultyTo=2;
				break;
			}
			case 3:{
				dificultyFrom=1;
				dificultyTo=3;
				break;
			}
			case 4:{
				dificultyFrom=2;
				dificultyTo=2;
				break;
			}
			case 5:{
				dificultyFrom=2;
				dificultyTo=3;
				break;
			}
			case 6:{
				dificultyFrom=3;
				dificultyTo=3;
				break;
			}
			default:{
				dificultyFrom=1;
				dificultyTo=1;
				break;
			}
			}
			
			Integer maxquestion=testsetting.getCourse().getQuestions(false, dificultyFrom, dificultyTo, testsetting.getSingle()).size();	
			
			if(maxquestion<numberQuestions) {
				attributes.addAttribute("error", "Niedostępna ilość pytań");
				return new RedirectView("/testsettings/testEdit"+idtest);
			}else {
				testsetting.setNumber_of_questions(numberQuestions);
				if(testsetting.getSystem_score()!= null) {
					testsetting.setPossible_max_score(Double.valueOf(numberQuestions*testsetting.getSystem_score().getPoint_for_corect()));	
				}else {
					testsetting.setPossible_max_score(0.0);
				}
				
				
				testSettingsRepository.flush();
			}
		}
				
		return new RedirectView("/testsettings/testEdit"+idtest);
	}
	
	@PostMapping("changeSystemResult{idtest}")
	public RedirectView changeSystemResult (HttpServletRequest request,RedirectAttributes attributes, @PathVariable("idtest") Integer idtest, @RequestParam("systemResult") Integer systemResult) {
		
		Testsetting testsetting=testSettingsRepository.findOneById(idtest);
		if(testsetting.getSystem_result() != null) {
			Systemresult currentSystem=testsetting.getSystem_result();
			Set<Testsetting> currentSystemTest=currentSystem.getTest_setting();
			currentSystemTest.remove(testsetting);
			currentSystem.setTest_setting(currentSystemTest);
		}
		
		
		Systemresult system=systemresultRepository.findOneById(systemResult);
		Set<Testsetting> systemTest = system.getTest_setting();	
		systemTest.add(testsetting);
		system.setTest_setting(systemTest);
		
		testsetting.setSystem_result(system);

		systemresultRepository.flush();
		testSettingsRepository.flush();
			
		return new RedirectView("/testsettings/testEdit"+idtest);
	}
	
	@PostMapping("changeSystemScore{idtest}")
	public RedirectView changeSystemScore (HttpServletRequest request,RedirectAttributes attributes, @PathVariable("idtest") Integer idtest, @RequestParam("systemScore") Integer systemScore) {
		
		Testsetting testsetting=testSettingsRepository.findOneById(idtest);
		if(testsetting.getEverRun()) {
			attributes.addAttribute("error", "Testy zostały przeprowadzone nie można zmienić systemu punktacji");
			return new RedirectView("/testsettings/testEdit"+idtest);
		}else {
			Systemscore currentSystem=testsetting.getSystem_score();
			if(currentSystem != null) {
				Set<Testsetting> currentSystemTest=currentSystem.getTest_setting();
				currentSystemTest.remove(testsetting);
				currentSystem.setTest_setting(currentSystemTest);
			}
			
			
			Systemscore system=systemscoreRepository.findOneById(systemScore);
			Set<Testsetting> systemTest = system.getTest_setting();	
			systemTest.add(testsetting);
			system.setTest_setting(systemTest);
			
			testsetting.setSystem_score(system);
			testsetting.setPossible_max_score(Double.valueOf(testsetting.getNumber_of_questions()*system.getPoint_for_corect()));
			
			
			systemscoreRepository.flush();
			testSettingsRepository.flush();
		}
		
			
		return new RedirectView("/testsettings/testEdit"+idtest);
	}
	
	@GetMapping("addStudent{idtestsetting}")
	public RedirectView addStudent (HttpServletRequest request,  @PathVariable("idtestsetting") Integer idtestsetting, @RequestParam("ids") Optional<Integer[]> ids ) {
		
		Testsetting testSettings=testSettingsRepository.findOneById(idtestsetting);
		Set<Test> testSettingsTests = testSettings.getTests();
		
		if(ids.isPresent()) {
			Integer[] sendIds=ids.get();
			Set<Integer> sendIdsSet = new HashSet<Integer>();
			for (int num : sendIds) {
				sendIdsSet.add(num);
			}
			for (Integer integer : sendIdsSet) {
				User user=userRepository.findOneByid(integer);
				
				Test newTest= new Test();
				newTest.setFirst_init(false);
				newTest.setAvailable(false);
				newTest.setTestsetting(testSettings);
				newTest.setUser(user);
				newTest.setRating(false);
				
				testSettingsTests.add(newTest);
				
				Set<Test> userTests = user.getTests();
				userTests.add(newTest);
				user.setTests(userTests);
				userRepository.flush();
				
			}
			testSettingsRepository.flush();
		}
		
		return new RedirectView("testEdit"+idtestsetting);
	}

	@GetMapping("removeStudent{idtestsetting}/{iduser}")
	public RedirectView removeStudent (HttpServletRequest request,  @PathVariable("idtestsetting") Integer idtestsetting, @PathVariable("iduser") Integer iduser ) {
		
		Testsetting testSettings=testSettingsRepository.findOneById(idtestsetting);
		Set<Test> testSettingsTests = testSettings.getTests();
		User user=userRepository.findOneByid(iduser);
		Set<Test> userSettingsTests = user.getTests();
		Test testuser = null;
		
		for (Test test : testSettingsTests) {
			if(test.getUser()== user && !test.getFirst_init()) {
				testuser=test;
			}
		}
		
		testSettingsTests.remove(testuser);
		userSettingsTests.remove(testuser);
		testSettings.setTests(testSettingsTests);
		user.setTests(userSettingsTests);
		userRepository.flush();
		testSettingsRepository.flush();
		testRepository.delete(testuser);
		
		return new RedirectView("../testEdit"+idtestsetting);
	}
	
	@PostMapping("AddNewTest")
	public RedirectView AddNewTest (RedirectAttributes attributes ,HttpServletRequest request, @RequestParam("single") Integer single, @RequestParam("course") Integer course, @RequestParam("name") Optional<String> name
		,@RequestParam("ids") Optional<Integer[]> ids ) {
		
		Testsetting testSetting = new Testsetting();
		if(name.isPresent()) {
			String sendName=name.get();
			if(sendName.length()>40) {
				attributes.addAttribute("error", "Długość nazwy nie może przekraczać 40 znaków");
				return new RedirectView("templateAddNewTest");
			}else if(sendName.length()<1){
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				Date date= new Date();
				testSetting.setName_of_test("Test "+dateFormat.format(date));
			}else {
				testSetting.setName_of_test(sendName);
			}
		}else {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
			Date date= new Date();
			testSetting.setName_of_test("Test "+dateFormat.format(date));
		}
		
		if(single>0) {
			testSetting.setSingle(true);
		}else {
			testSetting.setSingle(false);
		}
		
		Course courseRepo=courseRepository.findOneByid(course);
		Set<Testsetting> courseList = courseRepo.getTestsetting();
		courseList.add(testSetting);
		testSetting.setCourse(courseRepo);
		
		testSetting.setNumber_of_questions(0);
		testSetting.setTime_for_test(0);
		testSetting.setTest_end(false);
		testSetting.setEverRun(false);
		testSetting.setSingle(true);
		testSetting.setDificulty(1);
		testSetting.setTime_for_test(0);
		testSetting.setPossible_max_score(0.0);
		
		Set<Test> testsList = new HashSet<Test>();
		
		
		
		if(ids.isPresent()) {
			Integer[] sendIds=ids.get();
			Set<Integer> sendIdsSet = new HashSet<Integer>();
			for (int num : sendIds) {
				sendIdsSet.add(num);
			}
			for (Integer integer : sendIdsSet) {
				User user=userRepository.findOneByid(integer);
				
				Test newTest= new Test();
				newTest.setFirst_init(false);
				newTest.setAvailable(false);
				newTest.setTestsetting(testSetting);
				newTest.setUser(user);
				newTest.setRating(false);
				
				testsList.add(newTest);
				
				Set<Test> userTests = user.getTests();
				userTests.add(newTest);
				user.setTests(userTests);
				userRepository.flush();
				
			}
			testSetting.setTests(testsList);
			
		}
		
		testSettingsRepository.save(testSetting);
		courseRepository.flush();
		
		return new RedirectView("../admin/testManagement");
	}
	
	@GetMapping("removeTest{idtestsetting}")
	public RedirectView removeTest (HttpServletRequest request,  @PathVariable("idtestsetting") Integer idtestsetting ) {
		
		Testsetting testSettings=testSettingsRepository.findOneById(idtestsetting);
		
		if(testSettings.getCourse()!=null) {
			Course course=testSettings.getCourse();
			Set<Testsetting> CourseTestsetting = course.getTestsetting();
			CourseTestsetting.remove(testSettings);
			course.setTestsetting(CourseTestsetting);
		}
		
		
		if(testSettings.getSystem_result()!=null) {
			Systemresult systemresult=testSettings.getSystem_result();
			Set<Testsetting> SystemResultTestsetting=systemresult.getTest_setting();
			SystemResultTestsetting.remove(testSettings);
			systemresult.setTest_setting(SystemResultTestsetting);
		}
		
		if(testSettings.getSystem_score()!=null) {
			Systemscore systemscore=testSettings.getSystem_score();
			Set<Testsetting> SystemScoreTestsetting=systemscore.getTest_setting();
			SystemScoreTestsetting.remove(testSettings);
			systemscore.setTest_setting(SystemScoreTestsetting);
		}
		
		Set<Test> testSettingsTests = testSettings.getTests();
		for (Test test : testSettingsTests) {
			User user=test.getUser();
			Set<Test> userTests = user.getTests();
			userTests.remove(test);
			user.setTests(userTests);
			
			testRepository.delete(test);
			userRepository.flush();
		
		}
		testSettingsRepository.delete(testSettings);
		
		
		return new RedirectView("../admin/testManagement");
	}

	@PostMapping("changeTime{idtest}")
	public RedirectView changeTime (HttpServletRequest request,RedirectAttributes attributes, @PathVariable("idtest") Integer idtest, @RequestParam("time") Integer time) {
		
		Testsetting testsetting=testSettingsRepository.findOneById(idtest);
		if(testsetting.getEverRun()) {
			attributes.addAttribute("error", "Testy zostały przeprowadzone nie można zmienić czasu na test");
			return new RedirectView("/testsettings/testEdit"+idtest);
		}else {
			testsetting.setTime_for_test(time);
			systemscoreRepository.flush();
			testSettingsRepository.flush();
		}
		
			
		return new RedirectView("/testsettings/testEdit"+idtest);
	}

	@GetMapping("testControl{idtestsetting}")
	public ModelAndView testControl(HttpServletRequest request, @PathVariable("idtestsetting") Integer idtestsetting, @RequestParam("error") Optional<String> error) {

		Testsetting testsetting=testSettingsRepository.findOneById(idtestsetting);
		
		Systemresult systemresult=testsetting.getSystem_result();
		List<Systemresult> systemresultList=systemresultRepository.findAll();
		systemresultList.remove(systemresult);
		
		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		
		mav.addObject("testsetting", testsetting);
		mav.addObject("tests", testsetting.getTestsList());
		mav.addObject("systemresultList", systemresultList);
		mav.addObject("systemscore", testsetting.getSystem_score());
		
		
		mav.setViewName("adminpanel/test/testControl");
		return mav;
		
	}

	@PostMapping("activateAllTest{idtestsettings}")
	public RedirectView activateAllTest (HttpServletRequest request,RedirectAttributes attributes, @PathVariable("idtestsettings") Integer idtestsettings) {
		
		Testsetting testsetting=testSettingsRepository.findOneById(idtestsettings);
		
		Set<Test> testList = testsetting.getTests();
		for (Test test : testList) {
			if(!test.getFirst_init()) {
				test.setAvailable(true);
			}
		}
		testsetting.setTests(testList);
		if(!testsetting.getEverRun()) {
			testsetting.setEverRun(true);
			Date date= new Date();
			testsetting.setDate_of_test(date);
		}
		testSettingsRepository.flush();	
		return new RedirectView("/testsettings/testControl"+idtestsettings);
	}

	@PostMapping("deactivateAllTest{idtestsettings}")
	public RedirectView deactivateAllTest (HttpServletRequest request,RedirectAttributes attributes, @PathVariable("idtestsettings") Integer idtestsettings) {
		
		Testsetting testsetting=testSettingsRepository.findOneById(idtestsettings);
		
		Set<Test> testList = testsetting.getTests();
		for (Test test : testList) {
				test.setAvailable(false);
				test.ratingTest();
		}
		testsetting.setTests(testList);
		testsetting.setTest_end(true);
		
		testSettingsRepository.flush();	
		return new RedirectView("/testsettings/testControl"+idtestsettings);
	}

	@PostMapping("changeSystemResultTest{idtestsettings}")
	public RedirectView changeSystemResultTest (HttpServletRequest request,RedirectAttributes attributes, @PathVariable("idtestsettings") Integer idtestsettings, @RequestParam("systemResult") Integer systemResult ) {
		
		Testsetting testsetting=testSettingsRepository.findOneById(idtestsettings);
		
		Systemresult systemresult=systemresultRepository.findOneById(systemResult);
		testsetting.setSystem_result(systemresult);
		
		Set<Test> testList = testsetting.getTests();
		for (Test test : testList) {
			if(!test.getAvailable() && test.getFirst_init()) {
				test.setResult(systemresult.getresult(testsetting.getPossible_max_score(), test.getScore()));
			}
		}
		testsetting.setTests(testList);
		
		testSettingsRepository.flush();	
		return new RedirectView("/testsettings/testControl"+idtestsettings);
	}
	
	//do dokończenia wysłać maile do wszystkich piszących
	@PostMapping("ratingAllTest{idtestsettings}")
	public RedirectView ratingAllTest (HttpServletRequest request,RedirectAttributes attributes, @PathVariable("idtestsettings") Integer idtestsettings) {
		
		Testsetting testsetting=testSettingsRepository.findOneById(idtestsettings);
		
		Set<Test> testList = testsetting.getTests();
		for (Test test : testList) {
			if(!test.getRating()) {
				test.setRating(true);

				SimpleMailMessage message = new SimpleMailMessage(); 
		        message.setTo(test.getUser().getEmail());
		        String subject="Test "+test.getTestsetting().getCourseName();
		        message.setSubject(subject);
		        String text="Oceniono test pisany w dniu "+test.getDate_of_end()+". Uzyskano punktów "+test.getScore()
		        +" na "+test.getTestsetting().getPossible_max_score()+" moliwych. Ocena uzyskana z testu to "+test.getResult();
		        message.setText(text);
		        emailSender.send(message);
			}
		}
		testsetting.setTests(testList);
		testSettingsRepository.flush();
		return new RedirectView("/testsettings/testControl"+idtestsettings);
	}

	@PostMapping("activateTest{idtest}")
	public RedirectView activateTest (HttpServletRequest request,RedirectAttributes attributes, @PathVariable("idtest") Integer idtest) {
		
		Optional<Test> test=testRepository.findById(idtest);
		if(test.isPresent()) {
			Test sendtest=test.get();
			Testsetting testsetting=sendtest.getTestsetting();
			if(!testsetting.getEverRun()) {
				testsetting.setEverRun(true);
				Date date= new Date();
				testsetting.setDate_of_test(date);
			}
			
			Set<Test> testList = testsetting.getTests();
			for (Test test1 : testList) {
				if(test1.equals(sendtest)) {
					test1.setAvailable(true);
					testsetting.setTests(testList);
					testSettingsRepository.flush();
					
					return new RedirectView("/testsettings/testControl"+testsetting.getIdsettings());
				}
			}
			
		}
			
		return new RedirectView("/admin/testManagement");
	}

	@PostMapping("deactivateTest{idtest}")
	public RedirectView deactivateTest (HttpServletRequest request,RedirectAttributes attributes, @PathVariable("idtest") Integer idtest) {
		
		Optional<Test> test=testRepository.findById(idtest);
		if(test.isPresent()) {
			Test sendtest=test.get();
			Testsetting testsetting=sendtest.getTestsetting();
			
			
			Set<Test> testList = testsetting.getTests();
			for (Test test1 : testList) {
				if(test1.equals(sendtest)) {
					test1.setAvailable(false);
					test1.ratingTest();
					testsetting.setTests(testList);
					testSettingsRepository.flush();
					if(testsetting.getAllTestEnd()) {
						
					}
					
					return new RedirectView("/testsettings/testControl"+testsetting.getIdsettings());
				}
			}
			
		}
			
		return new RedirectView("/admin/testManagement");
	}
	
	//do dokończenia wysłać maile do piszącego
	@PostMapping("ratingTest{idtest}")
	public RedirectView ratingTest (HttpServletRequest request,RedirectAttributes attributes, @PathVariable("idtest") Integer idtest) {
		
		Optional<Test> test=testRepository.findById(idtest);
		if(test.isPresent()) {
			Test sendtest=test.get();
			Testsetting testsetting=sendtest.getTestsetting();
			
			
			Set<Test> testList = testsetting.getTests();
			for (Test test1 : testList) {
				if(test1.equals(sendtest)) {
					test1.setRating(true);
					// wysłać mail z ocenami i danymi
					
					SimpleMailMessage message = new SimpleMailMessage(); 
			        message.setTo(test1.getUser().getEmail());
			        String subject="Test "+test1.getTestsetting().getCourseName();
			        message.setSubject(subject);
			        String text="Oceniono test pisany w dniu "+test1.getDate_of_end()+". Uzyskano punktów "+test1.getScore()
			        +" na "+test1.getTestsetting().getPossible_max_score()+" moliwych. Ocena uzyskana z testu to "+test1.getResult();
			        message.setText(text);
			        emailSender.send(message);
					
					testsetting.setTests(testList);
					testSettingsRepository.flush();
					return new RedirectView("/testsettings/testControl"+testsetting.getIdsettings());
				}
			}
			
		}
			
		return new RedirectView("/admin/testManagement");
	}

	@PostMapping("addTimeTest{idtest}")
	public RedirectView addTimeTest (HttpServletRequest request,RedirectAttributes attributes, @PathVariable("idtest") Integer idtest, @RequestParam("time") Integer time) {
		
		Optional<Test> test=testRepository.findById(idtest);
		if(test.isPresent()) {
			Test sendtest=test.get();
			Testsetting testsetting=sendtest.getTestsetting();
			
			Set<Test> testList = testsetting.getTests();
			for (Test test1 : testList) {
				if(test1.equals(sendtest)) {
					test1.setAvailable(true);
					
					
					Calendar cal = Calendar.getInstance();
					cal.add(Calendar.MINUTE, time);
					Date date= cal.getTime();		
					test1.setDate_of_end(date);
					
					testsetting.setTests(testList);
					testSettingsRepository.flush();
					
					return new RedirectView("/testsettings/testControl"+testsetting.getIdsettings());
				}
			}
			
		}
			
		return new RedirectView("/admin/testManagement");
	}
	
	@PostMapping("creatPDF{idtestsettings}")
	public ResponseEntity<byte[]> getPDF(@PathVariable("idtestsettings") Integer idtestsettings) throws DocumentException, MalformedURLException, IOException {
		
		Document document = new Document(PageSize.A4, 30,30,30,30);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		PdfWriter.getInstance(document, stream);
		document.open();
		
		BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
		
		Font helvetica12=new Font(helvetica,12);
		Font helvetica12B=new Font(helvetica,12, Font.BOLD);
		Font helvetica16B=new Font(helvetica,16, Font.BOLD);
		
		Testsetting testsetting = testSettingsRepository.findOneById(idtestsettings);
		List<Question> questions = new ArrayList<Question>(testsetting.getCourse().getQuestionsBySingleAndDificultyRange(testsetting.getSingle(), testsetting.getDificulty()));
		
		
		int high = questions.size();
		int numberOfDrawQuestions=testsetting.getNumber_of_questions();
		Set<Question> DrawQuestions = new HashSet<Question>();
		
		 ArrayList<Integer> list = new ArrayList<Integer>();
	        for (int i=0; i<high-1; i++) {
	            list.add(new Integer(i));
	        }
	        Collections.shuffle(list);
	        for (int i=0; i<numberOfDrawQuestions; i++) {
	            DrawQuestions.add(questions.get(list.get(i)));
	        }

		String mainName="Test "+testsetting.getCourseName();
		Paragraph paragraf=new Paragraph(new Chunk(mainName, helvetica16B));
		paragraf.setAlignment(Element.ALIGN_CENTER);
		
		String description="Jest to test ";
		if(testsetting.getSingle()){
			description+="jednokrotnego wyboru.";
		}else {
			description+="wielokrotnego wyboru.";
		}
		 
		
		Chunk glue = new Chunk(new VerticalPositionMark());
		Paragraph up1 = new Paragraph("......................................");
		up1.add(new Chunk(glue));
		up1.add(".....................");
		
		Paragraph up2 = new Paragraph("      Imię i nazwisko", helvetica12);
		up2.add(new Chunk(glue));
		up2.add("Grupa     ");
		
		document.add(up1);
		document.add(up2);
		
		document.add(paragraf);
		document.add(new Chunk(description, helvetica12B));
		
		List<String> AnswerList = Arrays.asList("A)", "B)", "C)", "D)", "E)","F)", "G)", "H)");
		
		int j =1;
		for (Question question : DrawQuestions) {
			PdfPTable table = new PdfPTable(3);
			table.setSpacingAfter(50f);
			float[] widths = new float[] { 7f, 50f, 50f};
		    table.setWidths(widths);
		    
		    PdfPCell cell;
	        cell = new PdfPCell(new Phrase("Pytanie "+j+": "+question.getContent_question()));
	        cell.setColspan(2);
	        cell.setBorder(Rectangle.NO_BORDER);
	        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	        table.addCell(cell);
	        
	        if(question.getImgFile() !=null) {
	        	Image img = Image.getInstance(question.getImgFile().getData());
	        	cell = new PdfPCell(img, true);
		        cell.setBorder(Rectangle.NO_BORDER);
		        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		        table.addCell(cell);
	        }else {
	        	cell = new PdfPCell(new Phrase(""));
		        cell.setBorder(Rectangle.NO_BORDER);
		        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		        table.addCell(cell);
	        }
	        
		    
		    int i=0;
	        for (Answere answere : question.getAnsweres()) {
				
	        	cell = new PdfPCell(new Phrase(AnswerList.get(i)));
	            cell.setBorder(Rectangle.NO_BORDER);
	            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            table.addCell(cell);
	            
	            cell = new PdfPCell(new Phrase(answere.getAnswere()));
	            cell.setBorder(Rectangle.NO_BORDER);
	            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
	            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
	            table.addCell(cell);
	            
	            if(answere.getImgFile() != null) {
	            	 Image img1 = Image.getInstance(answere.getImgFile().getData());
	            	 cell = new PdfPCell(img1, true);
			            cell.setBorder(Rectangle.NO_BORDER);
			            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
			            table.addCell(cell);
	            }else {
	            	cell = new PdfPCell(new Phrase(""));
		            cell.setBorder(Rectangle.NO_BORDER);
		            cell.setHorizontalAlignment(Element.ALIGN_LEFT);
		            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		            table.addCell(cell);
	            }
	        	
	        	i++;
			}
	        document.add(table);
	        j++;
		}
		
		
		/*
		
		Question question=questionRepository.findOneById(3);
		Image img = Image.getInstance(question.getImgFile().getData());
		//img.scaleAbsolute(200f,70f);
		
		PdfPTable table = new PdfPTable(3);
        float[] widths = new float[] { 7f, 50f, 50f};
        table.setWidths(widths);
        
        PdfPCell cell;
        cell = new PdfPCell(new Phrase("Name" ));
        cell.setColspan(2);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        cell = new PdfPCell(img, true);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase(AnswerList.get(0)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase(question.getContent_question()));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        cell = new PdfPCell(img, true);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        
        cell = new PdfPCell(new Phrase(AnswerList.get(1)));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        cell = new PdfPCell(new Phrase(question.getContent_question()));
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        cell = new PdfPCell(img, true);
        cell.setBorder(Rectangle.NO_BORDER);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        table.addCell(cell);
        
        table.setSpacingAfter(50f);*/
        
        
       /* PdfPTable table1 = new PdfPTable(3);
        table1.setWidthPercentage(100);
        PdfPCell cell1;
        cell1 = new PdfPCell(new Phrase("Name2"));
        cell1.setColspan(3);
        table1.addCell(cell1);
        
        table1.addCell(new PdfPCell(img, true));
        table1.addCell(new PdfPCell(img, true));
        table1.addCell(new PdfPCell(img, true));*/

        
        //document.add(table1);
		
        
		
		
		document.close();

	    // retrieve contents of "C:/tmp/report.pdf" that were written in showHelp
	    byte[] contents =stream.toByteArray();

	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.parseMediaType("application/pdf"));
	    // Here you have to set the actual filename of your pdf
	    String filename = "Test.pdf";
	    headers.setContentDispositionFormData(filename, filename);
	    headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
	    ResponseEntity<byte[]> response = new ResponseEntity<>(contents, headers, HttpStatus.OK);
	    return response;
	}	   

	@GetMapping("preview{idtest}")
	public ModelAndView preview(HttpServletRequest request, @PathVariable("idtest") Integer idtest) {

		ModelAndView mav = new ModelAndView();
		
		String user = request.getRemoteUser();
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		
		Optional<Test> findById = testRepository.findById(idtest);
		if(findById.isPresent()) {
			List<TestQuestionDeliveryAgent> findAllByTest = pkRepository.findAllByTest(findById.get());
			mav.addObject("testQuestions", findAllByTest);
		}

		mav.setViewName("adminpanel/test/preview");
		return mav;
		
	}
	
	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public String handleException() {
	       return "Wystąpił błąd";
	}
}