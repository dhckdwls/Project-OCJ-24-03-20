package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.ArticleService;

@Controller
public class UsrHomeController {

	@Autowired
	private ArticleService articleService;

	@RequestMapping("/usr/home/main")
	public String showMain() {

		return "/usr/home/main";
	}

	@RequestMapping("/")
	public String showRoot() {

		return "redirect:/usr/home/main";
	}

	@RequestMapping("/usr/home/map")
	public String showMap(Model model) {
		String[] titles = articleService.getArticlesTitles();
		String[] addresses = articleService.getArticlesAddress();
		String[] titleAddress = new String[titles.length];
		for (int i = 0; i < titles.length; i++) {
			titleAddress[i] = titles[i] + "/" + addresses[i];
		}

		model.addAttribute("titleAddress", titleAddress);

		return "/usr/home/map";
	}

//	 테스트용
	@RequestMapping("/usr/home/APITest")
	public String APITest() {

		return "/usr/home/APITest";
	}

	@RequestMapping("/usr/home/APITest2")
	public String APITes2() {

		return "/usr/home/APITest2";
	}

	@RequestMapping("/usr/home/APITest3")
	public String APITest3() {

		return "/usr/home/APITest3";
	}

	@RequestMapping("/usr/home/APITest4")
	public String APITest4() {

		return "/usr/home/APITest4";
	}

	@RequestMapping("/usr/home/APITest5")
	public String APITest5() {

		return "/usr/home/APITest5";
	}

	@RequestMapping("/usr/home/testmain")
	public String testmain() {

		return "/usr/home/testmain";
	}

	@RequestMapping("/usr/home/testlogin")
	public String testlogin() {

		return "/usr/home/testlogin";
	}

	@RequestMapping("/usr/home/testjoin")
	public String testjoin() {

		return "/usr/home/testjoin";
	}

	@RequestMapping("/usr/home/testrandom")
	public String testrandom() {

		return "/usr/home/testrandom";
	}

	@RequestMapping("/usr/home/testmap")
	public String testmap() {

		return "/usr/home/testmap";
	}

	@RequestMapping("/usr/home/testmap2")
	public String testmap2() {

		return "/usr/home/testmap2";
	}

	@RequestMapping("/usr/home/testmap3")
	public String testmap3() {

		return "/usr/home/testmap3";
	}

	@RequestMapping("/usr/home/testmap4")
	public String testmap4() {

		return "/usr/home/testmap4";
	}

	@RequestMapping("/usr/home/testqna")
	public String testqna() {

		return "/usr/home/testqna";
	}

	@RequestMapping("/usr/home/testapi")
	public String testapi() {

		return "/usr/home/testapi";
	}

	@RequestMapping("/usr/home/testapi2")
	public String testapi2() {

		return "/usr/home/testapi2";
	}

	@RequestMapping("/usr/home/testapi3")
	public String testapi3() {

		return "/usr/home/testapi3";
	}

	@RequestMapping("/usr/home/testweather")
	public String testweather() {

		return "/usr/home/testweather";
	}

}