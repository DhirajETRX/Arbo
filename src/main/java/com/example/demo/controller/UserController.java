package com.example.demo.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.entities.Contact;
import com.example.demo.entities.User;
import com.example.demo.helper.Message;
import com.example.demo.repository.ContactRepo;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.ContactService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	
	//Method to adding common data to response
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {
		String userName = principal.getName();
		System.out.println("USERNAME "+userName);
		//get the user using userName(email)
		User user = userRepo.getUserByUserName(userName);
		System.out.println("USER "+user);
		model.addAttribute("user",user);
	}
	
	
	
	
	//Dashboard Home
	@RequestMapping("/index")
	public String dashboard(Model model,Principal principal) {
		model.addAttribute("title","UserDashboard");
		return "normal/user_dashboard";
	}
	
	
	
	
	
	
	//open settings handler
	@GetMapping("/settings")
	public String openSettings() {
		return "normal/settings";
	}
	
	
	
	
	
	
	//change password handler....
	@PostMapping("/change-password")
	public String changePassword(@RequestParam("oldPassword") String oldPassword,
			@RequestParam("newPassword") String newPassword, Principal principal, HttpSession session) {
		System.out.println("OLD PASSWORD " + oldPassword);
		System.out.println("NEW PASSWORD " + newPassword);

		String userName = principal.getName();
		User currentUser = this.userRepo.getUserByUserName(userName);
		System.out.println(currentUser.getPassword());

		if (this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {
			// change password
			currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
			this.userRepo.save(currentUser);
			session.setAttribute("message", new Message("Your password has successfully changed!", "success"));

		} else {
			// error....
			session.setAttribute("message", new Message("Please enter correct old password!!", "danger"));
			return "redirect:/user/settings";
		}

		return "redirect:/user/index";
	}
	
	
	
	
}