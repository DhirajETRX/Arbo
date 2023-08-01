package com.example.demo.controller;



import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import com.example.demo.entities.User;
import com.example.demo.helper.Message;
import com.example.demo.repository.UserRepo;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UserRepo userRepo;
	
	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("title" , "Home - Smart Contact Manager");
		return "home";
	}
	
	
	
	
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("title" , "Register - Smart Contact Manager");
		model.addAttribute("user",new User());
		return "signup";
	}
	
	//Handler for registering user
	@RequestMapping(value = "/do_register",method = RequestMethod.POST)
	public String registerUser(@Valid@ModelAttribute("user") User user,BindingResult result1,@RequestParam(
			value = "agreement", defaultValue = "false") boolean agreement, Model model,
			 HttpSession session) {
		
		try {
			
			if(!agreement) {
				System.out.println("You have not agreed the terms and conditions");
				throw new Exception("You have not agreed the terms and conditions");
			}
			
			if(result1.hasErrors()) {
				System.out.println("ERROR "+result1.toString());
				model.addAttribute("user",user);
				return "signup";
			}
			user.setRole("ROLE_USER");
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			
			System.out.println("Agreement "+agreement);
			System.out.println("User "+user);
			
			User result = this.userRepo.save(user);
			model.addAttribute("user",new User());
			
			session.setAttribute("message", new Message("Registered Successfully", "alert-success"));
			return "signup";
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("user" , user);
			session.setAttribute("message", new Message("Something went wrong!!"+e.getMessage(), "alert-danger"));
			return "signup";
		}
	
	}
	
	//Handler for custom login
	@GetMapping("/signin")
	public String customLogin(Model model) {
		model.addAttribute("title","login page");
		return "login";
	}
	
	
	
	
	
	

	
	
	
	
	
	
	

	
}
