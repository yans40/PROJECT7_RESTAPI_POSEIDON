package com.nnk.springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class HomeController
{
//	@GetMapping("/home")
//	public String homeDispatch(Model model, Authentication authentication){
//
//		Collection<? extends GrantedAuthority> authorities =authentication.getAuthorities();
//		for(GrantedAuthority authority:authorities){
//			if (authority.getAuthority().equals(("ADMIN"))){
//				return "redirect:/admin/home";
//			}
//		}
//		return "redirect:/";
//	}
	@RequestMapping("/")
	public String home(Model model)
	{
		return "home";
	}

	@RequestMapping("/admin/home")
	public String adminHome(Model model)
	{
		return "redirect:/bidList/list";
	}


}
