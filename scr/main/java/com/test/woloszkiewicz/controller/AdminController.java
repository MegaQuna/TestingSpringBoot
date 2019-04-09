package com.test.woloszkiewicz.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.test.woloszkiewicz.entity.Answere;
import com.test.woloszkiewicz.entity.Authority;
import com.test.woloszkiewicz.entity.Course;
import com.test.woloszkiewicz.entity.Group;
import com.test.woloszkiewicz.entity.ImgFileAnswere;
import com.test.woloszkiewicz.entity.ImgFileQuestion;
import com.test.woloszkiewicz.entity.Loginfail;
import com.test.woloszkiewicz.entity.NewUser;
import com.test.woloszkiewicz.entity.Note;
import com.test.woloszkiewicz.entity.Question;
import com.test.woloszkiewicz.entity.Systemresult;
import com.test.woloszkiewicz.entity.Systemscore;
import com.test.woloszkiewicz.entity.Testsetting;
import com.test.woloszkiewicz.entity.User;
import com.test.woloszkiewicz.service.AnswereRepository;
import com.test.woloszkiewicz.service.CourseRepository;
import com.test.woloszkiewicz.service.GroupRepository;
import com.test.woloszkiewicz.service.ImgFileAnswereRepository;
import com.test.woloszkiewicz.service.QuestionRepository;
import com.test.woloszkiewicz.service.SystemresultRepository;
import com.test.woloszkiewicz.service.SystemscoreRepository;
import com.test.woloszkiewicz.service.TestSettingsRepository;
import com.test.woloszkiewicz.service.ImgFileQuestionRepository;
import com.test.woloszkiewicz.service.NewUserRepository;
import com.test.woloszkiewicz.service.NoteRepository;
import com.test.woloszkiewicz.service.UserRepository;

@Controller
@RequestMapping("admin")
public class AdminController {

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
	
	@Autowired
	SystemresultRepository systemresultRepository;
	
	@Autowired
	SystemscoreRepository systemscoreRepository;
	
	@Autowired
	NoteRepository noteRepository;
	
	@Autowired
	NewUserRepository newUserRepository;

	@GetMapping("index")
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		List<Note> findAll = noteRepository.findAll();
		
		mav.addObject("notes", findAll);
		mav.setViewName("adminpanel/main/adminpanel");
		return mav;
	}

	@GetMapping("groupManagement")
	public ModelAndView groupManagement(Model model, HttpServletRequest request) {
		List<Group> list = groupRepository.findAll(new Sort(Sort.Direction.ASC, "createdate"));
		System.out.println(list.size());

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}

		mav.addObject("groups", list);

		mav.setViewName("adminpanel/group/groupManagement");
		return mav;
	}

	@GetMapping("courseManagement")
	public ModelAndView courseManagement(Model model, HttpServletRequest request) {
		List<Course> list = courseRepository.findAllByArchive(false);
		System.out.println(list.size());

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}

		mav.addObject("courses", list);

		mav.setViewName("adminpanel/course/coursManagement");
		return mav;
	}

	@GetMapping("showAllCourses")
	public ModelAndView showAllCourses(Model model, HttpServletRequest request) {
		List<Course> list = courseRepository.findAll();
		System.out.println(list.size());

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}

		mav.addObject("courses", list);

		mav.setViewName("adminpanel/course/coursManagement");
		return mav;
	}
	
	@GetMapping("accountManagement{role}/{active}")
	public ModelAndView accountManagement(Model model, HttpServletRequest request, @PathVariable("role") String role,
			@PathVariable("active") Boolean active) {
		List<User> listinRepo=userRepository.findAll(new Sort(Sort.Direction.DESC, "username"));
		List<User> list =new ArrayList<User>();
		switch (role) {
		case "User": {
			for (User user : listinRepo) {
				if(user.getAuthorityName().contains("ROLE_USER") && user.getEnabled()==active) {
					list.add(user);
				}
			}
			break;
		}
		case "All": {
			for (User user : listinRepo) {
				if(user.getAuthorityName().contains("ROLE_USER")) {
					list.add(user);
				}
			}
			break;
		}
		default: {
			break;
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

		mav.addObject("users", list);

		mav.setViewName("adminpanel/account/accountManagement");
		return mav;
	}

	@GetMapping("testManagement")
	public ModelAndView testManagement(Model model, HttpServletRequest request) {
		List<Testsetting> list = testSettingsRepository.findAllOrderByDate_of_test();
		

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}

		mav.addObject("testssettings", list);

		mav.setViewName("adminpanel/test/testManagement");
		return mav;
	}

	@PostMapping("uploadImgQuestion{idquestion}")
	public RedirectView uploadImgQuestion(HttpServletRequest request, @RequestParam("file") MultipartFile file,
			@PathVariable("idquestion") Integer idquestion) {

		Question question = questionRepository.findOneById(idquestion);

		if (!file.isEmpty()) {
			if (file != null) {
				
				String mimeType=file.getContentType();
				System.out.println("MIMEEEEEEEE: "+mimeType);
				if(mimeType.contains("image/jpeg")) {
				System.out.println("Saving file: " + file.getOriginalFilename());

				try {
					if (question.getImgFile() != null) {
						System.out.println("NIE JEST PUSTEEEEEEEE");
						question.getImgFile().setData(file.getBytes());
						question.getImgFile().setFile_name(file.getOriginalFilename());
						;
						questionRepository.flush();
					} else {
						
							question.setImgFile(new ImgFileQuestion(file.getBytes(), file.getOriginalFilename(), question));
							questionRepository.flush();
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

			}

		}
		return new RedirectView("../question/editQuestion"+idquestion);
	}

	@PostMapping("uploadImgAnswere{idquestion}/{idanswere}")
	public RedirectView uploadImgAnswere(HttpServletRequest request, @RequestParam("file") MultipartFile file,
			@PathVariable("idquestion") Integer idquestion, @PathVariable("idanswere") Integer idanswere) {
	
		Answere answere = answereRepository.findOneById(idanswere);
		

		if (!file.isEmpty()) {
			if (file != null) {
				
				String mimeType=file.getContentType();
				System.out.println("MIMEEEEEEEE: "+mimeType);
				if(mimeType.contains("image/jpeg")) {
					
				System.out.println("Saving file: " + file.getOriginalFilename());

				try {
					if (answere.getImgFile() != null) {
						System.out.println("NIE JEST PUSTEEEEEEEE");
						answere.getImgFile().setData(file.getBytes());
						answere.getImgFile().setFile_name(file.getOriginalFilename());
						;
						questionRepository.flush();
					} else {
						answere.setImgFile(new ImgFileAnswere(file.getBytes(), file.getOriginalFilename(), answere));
						questionRepository.flush();
					}

				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				}
			}

		}
		return new RedirectView("../../question/editQuestion"+idquestion);
	}
	
	@GetMapping("deleteImgQuestion{idquestion}")
	public RedirectView deleteImgQuestion(HttpServletRequest request,
			@PathVariable("idquestion") Integer idquestion) {
		
		System.out.println("JESTEM W DELETE");
		Question question = questionRepository.findOneById(idquestion);
		Integer idfile = new Integer(question.getImgFile().getId());
		question.setImgFile(null);
		imgFileQuestionRepository.deleteById(idfile);

		return new RedirectView("../question/editQuestion"+idquestion);
	}
	
	@GetMapping("deleteImgAnswere{idquestion}/{idanswere}")
	public RedirectView deleteImgAnswere(HttpServletRequest request,
			@PathVariable("idquestion") Integer idquestion, @PathVariable("idanswere") Integer idanswere) {
		
		System.out.println("JESTEM W DELETE");
		Answere answere= answereRepository.findOneById(idanswere);
		Integer idfile = new Integer(answere.getImgFile().getId());
		answere.setImgFile(null);
		imgFileAnswereRepository.deleteById(idfile);

		return new RedirectView("../../question/editQuestion"+idquestion);
	}
	
	@GetMapping("groupdetail{idgroup}")
	@ResponseBody
	public ModelAndView groupDetails(Model model, HttpServletRequest request, @PathVariable("idgroup") Integer idgroup) {
		Group group_detail = groupRepository.findOneByid(idgroup);

		System.out.println(group_detail.getGroupname());

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}

		mav.addObject("group", group_detail);

		mav.setViewName("adminpanel/group/groupDetails");
		return mav;
	}

	@GetMapping("coursedetail{idcourse}")
	public ModelAndView courseDetails(Model model, HttpServletRequest request, @PathVariable("idcourse") Integer idcourse) {
		Course course_detail = courseRepository.findOneByid(idcourse);

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}

		mav.addObject("course", course_detail);

		mav.setViewName("adminpanel/course/couseDetails");
		return mav;
	}

	@GetMapping("accountdetails{iduser}")
	@ResponseBody
	public ModelAndView accountDetails(Model model, HttpServletRequest request, @PathVariable("iduser") Integer iduser) {
		User user = userRepository.findOneByid(iduser);

		ModelAndView mav = new ModelAndView();

		String userid = request.getRemoteUser();
		System.out.println("login " + user);
		if (userid != null && !userid.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(userid);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}

		mav.addObject("user", user);

		mav.setViewName("adminpanel/account/accountDetails");
		return mav;
	}

	@GetMapping("testdetail{idtest}")
	@ResponseBody
	public ModelAndView testDetail(Model model, HttpServletRequest request, @PathVariable("idtest") Integer idtest) {
		Testsetting testsetting = testSettingsRepository.findOneById(idtest);

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}

		mav.addObject("testsetting", testsetting);

		mav.setViewName("adminpanel/test/testDetails");
		return mav;
	}

	@GetMapping("scoreAndResultManagement")
	public ModelAndView scoreAndResultManagement(HttpServletRequest request) {
		List<Group> list = groupRepository.findAll(new Sort(Sort.Direction.ASC, "createdate"));
		System.out.println(list.size());

		ModelAndView mav = new ModelAndView();

		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		
		List<Systemresult> systemresultListrepo=systemresultRepository.findAll();	
		List<Systemscore> systemscoreList=systemscoreRepository.findAll();
		
		List<Systemscore> jwSystemscoreList= new ArrayList<Systemscore>();
		List<Systemscore> wwSystemscoreList= new ArrayList<Systemscore>();
		
		for (Systemscore systemscore : systemscoreList) {
			if(!systemscore.getArchive()) {
				if(systemscore.getSingle()) {
					jwSystemscoreList.add(systemscore);
				}else {
					wwSystemscoreList.add(systemscore);
				}
			}	
		}
		List<Systemresult> systemresultList=new ArrayList<Systemresult>();
		
		for (Systemresult systemresult : systemresultListrepo) {
			if(!systemresult.getArchive()) {
				systemresultList.add(systemresult);
			}
		}

		mav.addObject("systemresultList", systemresultList);
		mav.addObject("jwsystemscoreList", jwSystemscoreList);
		mav.addObject("wwsystemscoreList", wwSystemscoreList);

		mav.setViewName("adminpanel/systemscoreandresult/scoreAndResultManagement");
		return mav;
	}
	
	@GetMapping("templateModificationNote{idnote}")
	public ModelAndView templateModificationNote(HttpServletRequest request, @PathVariable("idnote") Integer idnote ) {
		
		ModelAndView mav = new ModelAndView();
		Optional<Note> findById = noteRepository.findById(idnote);
		if(findById.isPresent()) {
			mav.addObject("note", findById.get());
			mav.addObject("newNote", false);
		}else {
			mav.addObject("error", "Nieznaleziono notatki");
			mav.addObject("newNote", false);
		}
		
		mav.setViewName("adminpanel/main/templateModificationNote");
		return mav;
	}
	
	@GetMapping("templateAddNote")
	public ModelAndView templateAddNote(HttpServletRequest request ) {
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("newNote", true);
		mav.setViewName("adminpanel/main/templateModificationNote");
		return mav;
	}
	
	@GetMapping("deleteNote{idnote}")
	public RedirectView deleteNote(HttpServletRequest request, @PathVariable("idnote") Integer idnote ) {
		Optional<Note> findById = noteRepository.findById(idnote);
		if(findById.isPresent()) {
			noteRepository.delete(findById.get());
		}
		
		return new RedirectView("index");
	}
	
	@PostMapping("addNote")
	public RedirectView addNote(HttpServletRequest request, @RequestParam("description") String description ) {
		
		Note note=new Note();
		note.setCereate(new Date());
		note.setDescription(description);
		noteRepository.save(note);
		
		return new RedirectView("index");
	}
	
	@PostMapping("modificationNote{idnote}")
	public RedirectView modificationNote(HttpServletRequest request, @PathVariable("idnote") Integer idnote, @RequestParam("description") String description ) {
		Optional<Note> findById = noteRepository.findById(idnote);
		if(findById.isPresent()) {
			Note note=findById.get();
			note.setCereate(new Date());
			note.setDescription(description);
			noteRepository.flush();	
		}
		
		return new RedirectView("index");
	}
	
	@ExceptionHandler({ Exception.class })
	@ResponseBody
	public String handleException() {
	       return "Wystąpił błąd";
	}

	@GetMapping("newAccount")
	public ModelAndView newAccount(HttpServletRequest request) {
		
		ModelAndView mav = new ModelAndView();
		
		String user = request.getRemoteUser();
		System.out.println("login " + user);
		if (user != null && !user.isEmpty()) {
			User user_from_repository = userRepository.findOneByUsername(user);
			String userloin = user_from_repository.getName() + " " + user_from_repository.getSurname();
			System.out.println("login " + user);
			mav.addObject("login", userloin);
		}
		
		List<NewUser> findAll = newUserRepository.findAll();

		mav.addObject("newUser", findAll);

		mav.setViewName("adminpanel/account/accountManagement");
		return mav;
	}

	@GetMapping("newAccountAccept{idnewaccount}")
	public RedirectView newAccountAccept(HttpServletRequest request, @PathVariable("idnewaccount") Integer idnewaccount) {
		
		Optional<NewUser> findById = newUserRepository.findById(idnewaccount);
		if(findById.isPresent()) {
			NewUser accout=findById.get();
			
			User user= new User();
			user.setEmail(accout.getEmail());
			user.setName(accout.getName());
			user.setSurname(accout.getSurname());
			user.setUsername(accout.getUsername());
			user.setPassword(accout.getPassword());
			
			user.setEnabled(true);
			user.setLoginfail(new Loginfail(accout.getUsername(), 0, false, user));
			user.setAuthority(new Authority("ROLE_USER", accout.getUsername(), user));
			userRepository.save(user);
			newUserRepository.delete(accout);
		}

		return new RedirectView("newAccount");
	}
	
	@GetMapping("newAccountReject{idnewaccount}")
	public RedirectView newAccountReject(HttpServletRequest request, @PathVariable("idnewaccount") Integer idnewaccount) {
		
		Optional<NewUser> findById = newUserRepository.findById(idnewaccount);
		if(findById.isPresent()) {
			NewUser accout=findById.get();
			newUserRepository.delete(accout);
		}

		return new RedirectView("newAccount");
	}


}