package com.sintra.loanapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class MainPageController {
	
    @RequestMapping("/")
	public String index(Model model) {
		return "index";
	}

}
