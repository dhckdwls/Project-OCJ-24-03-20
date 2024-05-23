package com.example.demo.controller;

import java.util.List;

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
	public String showMain(Model model) {
		List<String> tags = articleService.getArticlesTags();
//		List<String> images = articleService.getImagesFromArticle();
		
		model.addAttribute("tags", tags);
		

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


}