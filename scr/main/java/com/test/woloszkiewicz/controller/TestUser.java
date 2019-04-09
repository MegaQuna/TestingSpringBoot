package com.test.woloszkiewicz.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
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

import com.test.woloszkiewicz.entity.Question;
import com.test.woloszkiewicz.entity.Test;
import com.test.woloszkiewicz.entity.TestQuestionDeliveryAgent;
import com.test.woloszkiewicz.entity.TestQuestionDeliveryAgentPK;
import com.test.woloszkiewicz.entity.Testsetting;
import com.test.woloszkiewicz.entity.User;
import com.test.woloszkiewicz.service.AnswereRepository;
import com.test.woloszkiewicz.service.CourseRepository;
import com.test.woloszkiewicz.service.GroupRepository;
import com.test.woloszkiewicz.service.ImgFileAnswereRepository;
import com.test.woloszkiewicz.service.ImgFileQuestionRepository;
import com.test.woloszkiewicz.service.PKRepository;
import com.test.woloszkiewicz.service.QuestionRepository;
import com.test.woloszkiewicz.service.TestRepository;
import com.test.woloszkiewicz.service.TestSettingsRepository;
import com.test.woloszkiewicz.service.UserRepository;

@Controller
@RequestMapping("test")
public class TestUser {

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
	TestRepository testRepository;

	@Autowired
	AnswereRepository answereRepository;

	@Autowired
	ImgFileQuestionRepository imgFileQuestionRepository;

	@Autowired
	ImgFileAnswereRepository imgFileAnswereRepository;
	
	@Autowired
	PKRepository questionsTestRepository;

	@GetMapping("start{test}")
	public ModelAndView start(HttpServletRequest request, @PathVariable("test") Integer test) {
		    ModelAndView mav = new ModelAndView();
		    String user=request.getRemoteUser();
		    
		    
		    if(user!=null && !user.isEmpty()) {
		    		User user_from_repository=userRepository.findOneByUsername(user);
				    String userloin=user_from_repository.getName()+" "+user_from_repository.getSurname();
				    System.out.println("login "+user);
				    
				    
				    Optional<Test> testOption=testRepository.findById(test);
				    
				    if(testOption.isPresent()) {
				    	Test findingtest=testOption.get();
				    	if(findingtest.getUser()== user_from_repository && findingtest.getAvailable()) {
				    		Testsetting testsetting=findingtest.getTestsetting();
					    	
					    	 mav.addObject("time", testsetting.getTime_for_test());
					    	 mav.addObject("numberOfQuestion", testsetting.getNumber_of_questions());
					    	 mav.addObject("single", testsetting.getSingle());
					    	 mav.addObject("testnumber", findingtest.getIdtest());	
				    	}
				    }
				    
				    mav.addObject("login", userloin);
		    }
		    
		    mav.setViewName("user/start");
		    return mav;
    }
	
	@GetMapping("solvestart{test}")
	public ModelAndView solvestart(HttpServletRequest request,  @PathVariable("test") Integer test) {
		    ModelAndView mav = new ModelAndView();
		    String user=request.getRemoteUser();
		    
		    
		    if(user!=null && !user.isEmpty()) {
		    		User user_from_repository=userRepository.findOneByUsername(user);
				    
		    		String userloin=user_from_repository.getName()+" "+user_from_repository.getSurname();
				    System.out.println("login "+user);
				    System.out.println("Pierwszy solve");
				    
				    Optional<Test> testOption=testRepository.findById(test);
				    
				    if(testOption.isPresent()) {
				    	System.out.println("A");
				    	Test findingtest=testOption.get();
				    	if(findingtest.getUser() == user_from_repository && findingtest.getAvailable()) {
				    		System.out.println("B");
				    		//test nalezy do zalogowanego urzytkownika i jest dostępny
				    		
				    		if(findingtest.getFirst_init()) {
				    			//była pierwsza inicjalizacja testu
				    			
				    			Date currentDate= new Date();
					    		Date testEndDate=findingtest.getDate_of_end();
					    		
					    		
					    		
					    		if (currentDate.compareTo(testEndDate) > 0) {
					    			System.out.println("C");
					    			//przekroczony czas
					    			findingtest.setAvailable(false);
					    			findingtest.ratingTest();
					    			testRepository.flush();
					    			mav.addObject("error", "Przekroczono czas na test");
					    			mav.setViewName("user/solve");
					    			return mav;
					               
					            }else {
					            	System.out.println("D");
					            	
					    			mav.addObject("question", questionsTestRepository.findOneByTestAndNumber(findingtest, 1).getQuestion());
					    			mav.addObject("testquestion", questionsTestRepository.findOneByTestAndNumber(findingtest, 1));
					    			mav.addObject("test", test);
					    			mav.addObject("max", findingtest.getTestsetting().getNumber_of_questions());
					    			long time=testEndDate.getTime()-currentDate.getTime();
					    			mav.addObject("timeleft", time);
					    			mav.setViewName("user/solve");
					    			
					    			
					    			
					    			
					    			return mav;
					            }

				    			
				    		}else {		
				    			System.out.println("E");
				    			//niebyło pierswzej inicjalizacji testu 
	
				    			findingtest.setFirst_init(true);
				    			
				    			Calendar date = Calendar.getInstance();
				    			long t= date.getTimeInMillis();
				    			findingtest.setDate_of_start(new Date(t));
				    			findingtest.setDate_of_end(new Date(t + (findingtest.getTestsetting().getTime_for_test() * 60000)));
				    			
				    			Testsetting testsetting=findingtest.getTestsetting();
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
				    		        	System.out.println("F "+i);
				    		            DrawQuestions.add(questions.get(list.get(i)));
				    		        }
				    		       
				    			int i=1;
				    			for (Question question : DrawQuestions) {
				    				System.out.println("G");
				    				
				    				TestQuestionDeliveryAgent testQuestionDeliveryAgent=new TestQuestionDeliveryAgent(question, findingtest,i);
				    				i++;
				    				List<Integer> myList =new ArrayList<Integer>();
				    			              
				    				testQuestionDeliveryAgent.setAnswere(myList);
				    				Set<TestQuestionDeliveryAgent> a = new HashSet<TestQuestionDeliveryAgent>();
				    				a.add(testQuestionDeliveryAgent);
				    				question.setTest(a);
				    				findingtest.setQuestion(a);
				    				
				    				TestQuestionDeliveryAgentPK testQuestionDeliveryAgentPK=new TestQuestionDeliveryAgentPK(question.getIdquestion(), findingtest.getIdtest());
				    				testQuestionDeliveryAgent.setId(testQuestionDeliveryAgentPK);
				    				questionsTestRepository.save(testQuestionDeliveryAgent);
				    				
				    				
				    			}
				    			mav.addObject("question", questionsTestRepository.findOneByTestAndNumber(findingtest, 1).getQuestion());
				    			mav.addObject("testquestion", questionsTestRepository.findOneByTestAndNumber(findingtest, 1));
			    				mav.addObject("test", test);
			    				Date currentDate= new Date();
					    		Date testEndDate=findingtest.getDate_of_end();
			    				long time=testEndDate.getTime()-currentDate.getTime();
				    			mav.addObject("timeleft", time);
			    				mav.addObject("max", findingtest.getTestsetting().getNumber_of_questions());
				    			
				    			
				    		}
				    	}else {
				    		mav.addObject("error", "Wystąpił błąd Kod T1");
				    	}
				    	
				    }else {
				    	mav.addObject("error", "Wystąpił błąd Kod T2");	
				    }
				   
				    mav.addObject("login", userloin);
		    }
		    
		    mav.setViewName("user/solve");
		    return mav;
    }
	
	@PostMapping("solve{test}")
	public ModelAndView solveQuestion(HttpServletRequest request, @RequestParam("action") String action, @PathVariable("test") Integer test,
			@RequestParam("single") Optional<Integer> single, @RequestParam("multi") Optional<Integer[]> multi, @RequestParam("id") Integer id) {
		
		   ModelAndView mav = new ModelAndView();
		    String user=request.getRemoteUser();
		    System.out.println("Drugi solve");
		    
		    
		    if(user!=null && !user.isEmpty()) {
		    		User user_from_repository=userRepository.findOneByUsername(user);
				    
		    		String userloin=user_from_repository.getName()+" "+user_from_repository.getSurname();
				    System.out.println("login "+user);
				    
				    
				    Optional<Test> testOption=testRepository.findById(test);
				    
				    if(testOption.isPresent()) {
				    	Test findingtest=testOption.get();
				    	if(findingtest.getUser() == user_from_repository && findingtest.getAvailable()) {
				    		System.out.println("B");
				    		//test nalezy do zalogowanego urzytkownika i jest dostępny
				    		
				    		if(findingtest.getFirst_init()) {
				    			//była pierwsza inicjalizacja testu
				    			
				    			Date currentDate= new Date();
					    		Date testEndDate=findingtest.getDate_of_end();
					    		if (currentDate.compareTo(testEndDate) > 0) {
					    			System.out.println("C");
					    			//przekroczony czas
					    			findingtest.setAvailable(false);
					    			findingtest.ratingTest();
					    			testRepository.flush();
					    			mav.addObject("error", "Przekroczono czas na test");
					    			mav.setViewName("user/solve");
					    			return mav;
					               
					            }else {
					            	System.out.println("D");
					            	TestQuestionDeliveryAgent testQuestionDeliveryAgent=questionsTestRepository.findOneByTestAndNumber(findingtest, id);
					            	if(multi.isPresent() && !single.isPresent()) {
					            		System.out.println("E");
					            		Integer[] numberOfAnsweres=multi.get();
					            		List<Integer> answere=new ArrayList<Integer>();
					            		for (Integer integer : numberOfAnsweres) {
					            			answere.add(integer);
										}
					            		testQuestionDeliveryAgent.setAnswere(answere);
					            		questionsTestRepository.flush();
					            		
					            	}else if(!multi.isPresent() && single.isPresent()) {
					            		Integer numberOfAnsweres =single.get();
					            		System.out.println("F "+numberOfAnsweres);
					            		List<Integer> answere=new ArrayList<Integer>();
					            		answere.add(numberOfAnsweres);
					            		testQuestionDeliveryAgent.setAnswere(answere);
					            		questionsTestRepository.flush();
					            	}else {
					            		
					            	}
					            	
					            	Integer numberofquestion=findingtest.getTestsetting().getNumber_of_questions();
					            	
					            	
					            	if( action.equals("Poprzednie") ){
					            		TestQuestionDeliveryAgent testQuestion1;
					            		if(id<=1) {
					        	    		testQuestion1=questionsTestRepository.findOneByTestAndNumber(findingtest, 1);
					        	    	}else {
					        	    		testQuestion1=questionsTestRepository.findOneByTestAndNumber(findingtest, id-1);
					        	    	}
					            		mav.addObject("question", testQuestion1.getQuestion());
						    			mav.addObject("testquestion", testQuestion1);
					    				mav.addObject("test", test);
					    				mav.addObject("max", findingtest.getTestsetting().getNumber_of_questions());
					    				long time=testEndDate.getTime()-currentDate.getTime();
						    			mav.addObject("timeleft", time);
					    				mav.setViewName("user/solve");
					    				return mav;
					            		
					        	    }
					        	    else if( action.equals("Zakończ") ){
					        	    	findingtest.setAvailable(false);
					        	    	findingtest.ratingTest();
						    			testRepository.flush();
					        	    	mav.addObject("error", "Test został zakończony");
					    				mav.setViewName("user/solve");
					    				return mav;
					        	    	
					        	    }
					        	    else if( action.equals("Następne") ){
					        	    	System.out.println("G");
					        	    	TestQuestionDeliveryAgent testQuestion1;
					            		if(id>numberofquestion-1) {
					        	    		testQuestion1=questionsTestRepository.findOneByTestAndNumber(findingtest, numberofquestion);
					        	    	}else {
					        	    		testQuestion1=questionsTestRepository.findOneByTestAndNumber(findingtest, id+1);
					        	    	}
					            		mav.addObject("question", testQuestion1.getQuestion());
						    			mav.addObject("testquestion", testQuestion1);
					    				mav.addObject("test", test);
					    				mav.addObject("max", findingtest.getTestsetting().getNumber_of_questions());
					    				long time=testEndDate.getTime()-currentDate.getTime();
						    			mav.addObject("timeleft", time);
					    				mav.setViewName("user/solve");
					    				return mav;
					        		}
					            }

				    			
				    		}else {		
				    			
				    			mav.addObject("error", "Wystąpił błąd Kod T6");
				    		}
				    	}else {
				    		mav.addObject("error", "Wystąpił błąd Kod T1");
				    	}
				    	
				    }else {
				    	mav.addObject("error", "Wystąpił błąd Kod T2");
				    }
				    
				    mav.addObject("login", userloin);
		    }
		    
		    mav.setViewName("user/solve");
		    return mav;

	}
	
	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public String handleException() {
	       return "Wystąpił błąd";
	}
}