package com.example.demo.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartRequest;

import com.example.demo.service.ArticleService;
import com.example.demo.service.BoardService;
import com.example.demo.service.GenFileService;
import com.example.demo.service.ReactionPointService;
import com.example.demo.service.ReplyService;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.Board;
import com.example.demo.vo.Reply;
import com.example.demo.vo.ResultData;
import com.example.demo.vo.Rq;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UsrArticleController {

	// req를 관리하는 객체 생성
	@Autowired
	private Rq rq;

	// 아티클 관련 서비스와 연결
	@Autowired
	private ArticleService articleService;

	// 게시판 관련 서비스와 연결
	@Autowired
	private BoardService boardService;

	// 댓글관련 서비스와 연결
	@Autowired
	private ReplyService replyService;

	// 파일 업로드 서비스와 연결
	@Autowired
	private GenFileService genFileService;

	// 좋아요 관련 서비스와 연결
	@Autowired
	private ReactionPointService reactionPointService;

	// 생성자
	public UsrArticleController() {

	}

	// 리스트 관련 메서드
	@RequestMapping("/usr/article/list")
	public String showList(HttpServletRequest req, Model model, @RequestParam(defaultValue = "1") int boardId,
			@RequestParam(defaultValue = "1") int page,
			@RequestParam(defaultValue = "title,body") String searchKeywordTypeCode,
			@RequestParam(defaultValue = "") String searchKeyword) {
		// 파라미터로 page를 받는데 page값을 따로 설정해 주지않는다면 기본적으로 1번 페이지로 이동할수 있도록 설정
		// 검색어 타입을 기본적으로 제목 + 내용으로 검색될 수 있도록
		// 검색어가 아무것도 없다면 공백으로 해놓기 위해서

		// 만들어놓은 rq 객체 선언
		Rq rq = (Rq) req.getAttribute("rq");

		// 게시판 번호로 게시판 정보 조회
		Board board = boardService.getBoardById(boardId);

		// 게시판이 없으면 이전페이지로 이동
		if (board == null) {
			return rq.historyBackOnView("없는 게시판이야");
		}

		// 게시글 총 개수 조회
		int articlesCount = articleService.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);

		// 한페이지에 글 10개씩이야
		// 글 20개 -> 2 page
		// 글 24개 -> 3 page
		int itemsInAPage = 9;

		// 전체 페이질 수 계산
		int pagesCount = (int) Math.ceil(articlesCount / (double) itemsInAPage);

		// 페이징 처리를 위한 게시글 목록 조회
		List<Article> articles = articleService.getForPrintArticles(boardId, itemsInAPage, page, searchKeywordTypeCode,
				searchKeyword);
		// footer에 표시할 태그 목록 조회
		List<String> footTags = articleService.getArticlesTags();

		// 모델에 데이터 추가
		model.addAttribute("footTags", footTags); // 태그정보
		model.addAttribute("board", board); // 게시판 정보
		model.addAttribute("boardId", boardId);// 게시판 번호
		model.addAttribute("page", page);// 현재 페이지 번호
		model.addAttribute("pagesCount", pagesCount);// 전체 페이지 수
		model.addAttribute("searchKeywordTypeCode", searchKeywordTypeCode);// 검색어 타입 코드
		model.addAttribute("searchKeyword", searchKeyword);// 검색어
		model.addAttribute("articlesCount", articlesCount);// 총 게시글 수
		model.addAttribute("articles", articles);// 현재 페이지의 게시글 목록
		return "usr/article/list";// list 페이지로 이동
	}

	// 글 상세 페이지를 보여주는 메서드
	@RequestMapping("/usr/article/detail")
	public String showDetail(HttpServletRequest req, Model model, int id, @RequestParam(defaultValue = "1") int page) {
		// page는 상세보기의 댓글의 페이지내이션을 위한 정보

		// rq 객체를 받아옴
		Rq rq = (Rq) req.getAttribute("rq");
		// 해당 게시글의 정보를 가져옴
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);
		// 로그인한 회원의 정보를 토대로 좋아요를 줄수 있는지 없는지 판단
		ResultData usersReactionRd = reactionPointService.usersReaction(rq.getLoginedMemberId(), "article", id);
		// 로그인한 회원이 반응을 할 수 있는지 여부를 모델에 추가
		if (usersReactionRd.isSuccess()) {
			model.addAttribute("userCanMakeReaction", usersReactionRd.isSuccess());
		}
		// 태그를 #기준으로 나누어 배열로 변환
		String[] tags = article.getTag().split("#");
		// footer에 표시할 태그 목록 조회
		List<String> footTags = articleService.getArticlesTags();
		// model에 보내주기
		model.addAttribute("footTags", footTags);
		// 해당 글의 달린 댓글의 개수 조회
		int repliesCount = replyService.getRepliesCount(id);
		// 한 페이지에 표시될 댓글의 개수
		int itemsInAPageReply = 10;
		// 전체 댓글 페이지 수 계산
		int replyPagesCount = (int) Math.ceil(repliesCount / (double) itemsInAPageReply);
		// 현재 페이지의 댓글 목록 조회
		List<Reply> replies = replyService.getForPrintReplies(rq.getLoginedMemberId(), "article", id, itemsInAPageReply,
				page);
		// model에 데이터 추가
		model.addAttribute("page", page);// 현재 댓글 페이지 번호
		model.addAttribute("replyPagesCount", replyPagesCount);// 전체 댓글 페이지 수
		model.addAttribute("tags", tags);// 글의 태그 목록
		model.addAttribute("article", article);// 현재 글 정보
		model.addAttribute("replies", replies);// 현재 페이지의 댓글 목록
		model.addAttribute("repliesCount", repliesCount);// 총 댓글 개수
		model.addAttribute("isAlreadyAddGoodRp",
				reactionPointService.isAlreadyAddGoodRp(rq.getLoginedMemberId(), id, "article"));// 로그인한 회원이 해당 글에 좋아요를
																									// 이미 눌렀는지 여부
		model.addAttribute("isAlreadyAddBadRp",
				reactionPointService.isAlreadyAddBadRp(rq.getLoginedMemberId(), id, "article"));// 로그인한 회원이 해당 글에 싫어요를
																								// 이미 눌렀는지 여부

		// 게시물 상세보기 페이지로 이동
		return "usr/article/detail";
	}

	// 조회수 증가 처리를 위한 메서드
	@RequestMapping("/usr/article/doIncreaseHitCountRd")
	@ResponseBody
	public ResultData doIncreaseHitCountRd(int id) {
		// 해당 글의 조회수를 증가시키고 결과를 받아온다
		ResultData increaseHitCountRd = articleService.increaseHitCount(id);

		// 조회수 증가가 실패했다면 실패 결과를 반환
		if (increaseHitCountRd.isFail()) {
			return increaseHitCountRd;
		}

		// 성공정으로 조회수가 증가했을 경우, resultData에 증가된 조회수를 포함하여 반환
		ResultData rd = ResultData.newData(increaseHitCountRd, "hitCount", articleService.getArticleHitCount(id));

		// 데이터에 글의 id도 추가하여 반환
		rd.setData2("id", id);

		return rd;

	}

	// 글 작성 페이지로 이동
	@RequestMapping("/usr/article/write")
	public String showJoin(Model model) {
		int currentId = articleService.getCurrentArticleId();
		List<String> footTags = articleService.getArticlesTags();
		model.addAttribute("footTags", footTags);

		model.addAttribute("currentId", currentId);

		return "usr/article/write";
	}

	// 실제로 글을 작성해 주는 메서드
	@RequestMapping("/usr/article/doWrite")
	@ResponseBody
	public String doWrite(HttpServletRequest req, int boardId, int contentTypeId, String title, String body, String tag,
			String firstImage, String firstImage2, String address, String mapX, String mapY, String replaceUri,
			MultipartRequest multipartRequest) {

		// rq 객체 받아옴
		Rq rq = (Rq) req.getAttribute("rq");

		// 입력된 파라미터들을 확인 null이나 공백을 제하기 위함
		if (Ut.isNullOrEmpty(title)) {
			return Ut.jsHistoryBack("F-1", "제목을 입력해주세요");
		}
		if (Ut.isNullOrEmpty(body)) {
			return Ut.jsHistoryBack("F-2", "내용을 입력해주세요");
		}
		if (Ut.isNullOrEmpty(tag)) {
			return Ut.jsHistoryBack("F-2", "태그를 입력해주세요");
		}
		if (Ut.isNullOrEmpty(firstImage)) {
			return Ut.jsHistoryBack("F-2", "첫번째사진주소를 입력해주세요");
		}
		if (Ut.isNullOrEmpty(firstImage2)) {
			return Ut.jsHistoryBack("F-2", "두번쨰사진주소를 입력해주세요");
		}
		if (Ut.isNullOrEmpty(address)) {
			return Ut.jsHistoryBack("F-2", "주소를 입력해주세요");
		}
		if (Ut.isNullOrEmpty(mapX)) {
			return Ut.jsHistoryBack("F-2", "X좌표를 입력해주세요");
		}
		if (Ut.isNullOrEmpty(mapY)) {
			return Ut.jsHistoryBack("F-2", "Y좌표를 입력해주세요");
		}

		// 글 작성을 수행하고 결과를 받아옴
		ResultData<Integer> writeArticleRd = articleService.writeArticle(boardId, rq.getLoginedMemberId(),
				contentTypeId, title, body, tag, firstImage, firstImage2, address, mapX, mapY);

		// 작성된 글의 id를 받아옴
		int id = (int) writeArticleRd.getData1();

		// 작성된 글의 정보를 받아옴
		Article article = articleService.getArticle(id);

		// 첨부된 파일을 저장함
		Map<String, MultipartFile> fileMap = multipartRequest.getFileMap();

		for (String fileInputName : fileMap.keySet()) {
			MultipartFile multipartFile = fileMap.get(fileInputName);

			if (multipartFile.isEmpty() == false) {
				genFileService.save(multipartFile, id);
			}
		}
		// 글 작성 후 작성된 글의 상세보기 페이지로 이동
		return Ut.jsReplace(writeArticleRd.getResultCode(), writeArticleRd.getMsg(), "../article/detail?id=" + id);

	}

	// 글 수정페이지로 이동
	@RequestMapping("/usr/article/modify")
	public String showModify(HttpServletRequest req, Model model, int id) {
		Rq rq = (Rq) req.getAttribute("rq");

		// 수정할 글의 정보를 가져옴
		Article article = articleService.getForPrintArticle(rq.getLoginedMemberId(), id);

		// 해당 글이 존재하지 않으면 실패 메시지를 포함한 결과로 이전 페이지로 돌아감
		if (article == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다", id));
		}
		// 푸터에 표시할 태그 목록 조회
		List<String> footTags = articleService.getArticlesTags();
		model.addAttribute("footTags", footTags);
		// 수정할 글의 정보를 모델에 추가하여 반환
		model.addAttribute("article", article);
		// 수정 페이지로 이동
		return "usr/article/modify";
	}

	// 실제로 글을 수정해주는 메서드
	@RequestMapping("/usr/article/doModify")
	@ResponseBody
	public String doModify(HttpServletRequest req, int id, String title, String body) {
		Rq rq = (Rq) req.getAttribute("rq");
		// 수정할 글의 정보를 가져옴
		Article article = articleService.getArticle(id);
		// 해당 글이 존재하지 않을시 이전 페이지로 이동
		if (article == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다", id));
		}
		// 현재 로그인한 회원이 해당 글을 수정할 수 있는지 확인하고 결과를 받아옴
		ResultData loginedMemberCanModifyRd = articleService.userCanModify(rq.getLoginedMemberId(), article);

		// 수정 권한이 있다면 글을 수정
		if (loginedMemberCanModifyRd.isSuccess()) {
			articleService.modifyArticle(id, title, body);
		}
		// 수정 후 수정한 게시글로 이동해서 수정된 상태 확인
		return Ut.jsReplace(loginedMemberCanModifyRd.getResultCode(), loginedMemberCanModifyRd.getMsg(),
				"../article/detail?id=" + id);
	}

	// 글 삭제
	// 로그인 체크 -> 유무 체크 -> 권한 체크 -> 삭제
	@RequestMapping("/usr/article/doDelete")
	@ResponseBody
	public String doDelete(HttpServletRequest req, int id) {
		Rq rq = (Rq) req.getAttribute("rq");

		// 삭제할 글의 정보를 가져옴
		Article article = articleService.getArticle(id);

		// 삭제할 글이 없을 시 이전페이지로 이동
		if (article == null) {
			return Ut.jsHistoryBack("F-1", Ut.f("%d번 글은 존재하지 않습니다", id));
		}

		// 현재 로그인한 회원이 해당 글을 삭제할 수 있는지 확인하고 결과를 받아옴
		ResultData loginedMemberCanDeleteRd = articleService.userCanDelete(rq.getLoginedMemberId(), article);
		// 삭제 권한이 있다면 글을 삭제함
		if (loginedMemberCanDeleteRd.isSuccess()) {
			articleService.deleteArticle(id);
		}
		// 삭제 후 리스트 페이지로 이동
		return Ut.jsReplace(loginedMemberCanDeleteRd.getResultCode(), loginedMemberCanDeleteRd.getMsg(),
				"../article/list");
	}
	
	//모든 게시물중 무작위로 하나의 게시물을 가져오는 메서드
	//메인 페이지에서 랜덤뽑기를 할때 사용
	@RequestMapping("/usr/article/random")
	@ResponseBody
	public Article random() {
		//랜덤으로 게시물 하나 가져오기
		Article article = articleService.getRandomArticle();
		//가져온 게시물 반환
		return article;
	}

}