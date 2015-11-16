package com.maoxin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/hello")
public class HelloController {
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView printWelcome(ModelMap model) {
		ModelAndView mv = new ModelAndView("hello");
		mv.addObject("message", "Hello world!");
		return mv;
	}
}