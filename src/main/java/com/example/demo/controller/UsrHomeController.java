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
	//articleService랑 연결

	@RequestMapping("/usr/home/main")
	public String showMain(Model model) {
		List<String> tags = articleService.getArticlesTags();
		//위에서 연결한 articleservice에서 tag를 가져오기
		
		model.addAttribute("tags", tags);
		//model을 통해서 tags를 jsp파일에서 사용할수 있도록 보내주기

		return "/usr/home/main";
		//home/main jsp 로 이동
	}

	@RequestMapping("/")
	public String showRoot() {
		//localhost:8081/ 까지만 쳐도 main페이지로 이동될수 있도록 편의성을 제공해 주기 위해서 만든 메서드
		return "redirect:/usr/home/main";
		//redirect를 사용해서 home/main으로 이동시키기
	}

	@RequestMapping("/usr/home/map")
	public String showMap(Model model) {
		String[] titles = articleService.getArticlesTitles();
		//articleService를 통해서 article들의 title을 모두 가져와서 배열에 저장
		String[] addresses = articleService.getArticlesAddress();
		//articleService를 통해서 article들의 address을 모두 가져와서 배열에 저장
		String[] titleAddress = new String[titles.length];
		//만들어진 titles과 addresses의 배열 크기는 같다 그리고 이와 같은 크기의 titleAddress를 생성 
		for (int i = 0; i < titles.length; i++) {
			titleAddress[i] = titles[i] + "/" + addresses[i];
			// 배열은 순서가 정해져(index) 있기 때문에 같은 article의 제목과 주소가 /를 기준으로 연결되게 만든다
			// 슬래쉬(/)를 기준으로 문자열을 이어 붙여서 jsp에서 사용할수 있게 model을 통해서 보내준다
		}

		model.addAttribute("titleAddress", titleAddress);

		return "/usr/home/map";
		//map jsp 로 이동
	}


}