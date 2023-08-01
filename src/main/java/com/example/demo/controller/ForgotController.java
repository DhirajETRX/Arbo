package com.example.demo.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entities.User;
import com.example.demo.repository.UserRepo;
import com.example.demo.service.EmailService;

@Controller
public class ForgotController {
	
	@Autowired
	private EmailService emailService;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	
	//email id form open handler
	@RequestMapping("/forgot")
	public String openEmailForm() {
		
		return "forgot_email_form";
	}
	
	
	
	
	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam("email") String email, HttpSession session) {
		System.out.println("Email "+email);
		//generating otp of 4 digit
		Random random = new Random();
		int otp = random.nextInt(1000, 9999);
		System.out.println("OTP "+otp);
		
		//write code for send otp to mail
		
		String subject = "OTP from SCM";
		String message = ""
				+ "<div style='border:1px solid #e2e2e2; padding:20px'>"
				+ "<h1>"
				+ "OTP is "
				+ "<b>"+otp
				+ "</b>"
				+ "</h1>"
				+ "</div>";
		String to = email;
		
		boolean flag = this.emailService.sendEmail(subject, message, to);
		if(flag) {
			session.setAttribute("myOtp", otp);
			session.setAttribute("email", email);
			return "verify_otp";
		}else {
			
			
			session.setAttribute("message", "Check your email id!!");
			return "forgot_email_form";
		}
	}
	
	
	
	
	
	//verify otp
	@PostMapping("/verify-otp")
	public String verifyOTP(@RequestParam("otp") int otp, HttpSession session) {
		int myOtp = (int)session.getAttribute("myOtp");
		String email = (String)session.getAttribute("email");
		if(myOtp==otp) {
			//password change form
			
			User user = this.userRepo.getUserByUserName(email);
			
			if(user==null) {
				//send error message
				session.setAttribute("message", "No user exists with this email!!");
				return "forgot_email_form";
				
			}else {
				//send change password form
				
			}
			
			return "password_change_form";
		}else {
			session.setAttribute("message", "You have entered wrong otp!!");
			return "verify_otp";
		}
	}
	
	
	
	
	
	//change password
	@PostMapping("/change-password")
	public String changePassword(@RequestParam("newPassword") String newPassword, HttpSession session) {
		String email = (String)session.getAttribute("email");
		User user = this.userRepo.getUserByUserName(email);
		user.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
		this.userRepo.save(user);
		return "redirect:/signin?change=password changed successfully....";
	}
	
}
