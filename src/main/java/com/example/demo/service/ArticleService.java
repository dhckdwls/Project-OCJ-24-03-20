package com.example.demo.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.repository.ArticleRepository;
import com.example.demo.util.Ut;
import com.example.demo.vo.Article;
import com.example.demo.vo.ResultData;

@Service
public class ArticleService {
	
	//articleRepository와 연결
	@Autowired
	private ArticleRepository articleRepository;
	
	//articleService가 생성될때 articleRepository를 채움
	// 실행 순서 문제 때문에
	public ArticleService(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	
	public Article getForPrintArticle(int loginedMemberId, int id) {
		//id값을 통한 article 정보를 가져옴
		Article article = articleRepository.getForPrintArticle(id);
		
		//loginMemberId와 article을 넘겨서 수정과 삭제가 가능한지 체크
		controlForPrintData(loginedMemberId, article);
		
		//article 반환
		return article;
	}
	
	/*
	 * 현재 로그인한 회원의 관점에서 게시물에 대한 수정 및 삭제 가능 여부를 확인
	 * 해당 정보를 게시물 객체에 설정
	 * 
	 * loginedMemberId 현재 로그인한 회원의 ID
	 * article 게시물 객체
	 * 게시물이 null인 경우 아무 작업도 수행하지 않습니다.
	 *수정 및 삭제 가능 여부는 해당 회원의 권한에 따라 결정됩니다.
	 */
	private void controlForPrintData(int loginedMemberId, Article article) {
		if (article == null) {
			return;
		}
		ResultData userCanModifyRd = userCanModify(loginedMemberId, article);
		article.setUserCanModify(userCanModifyRd.isSuccess());

		ResultData userCanDeleteRd = userCanDelete(loginedMemberId, article);
		article.setUserCanDelete(userCanDeleteRd.isSuccess());
	}

	public ResultData userCanDelete(int loginedMemberId, Article article) {

		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("%d번 글에 대한 삭제 권한이 없습니다", article.getId()));
		}

		return ResultData.from("S-1", Ut.f("%d번 글이 삭제 되었습니다", article.getId()));
	}

	public ResultData userCanModify(int loginedMemberId, Article article) {

		if (article.getMemberId() != loginedMemberId) {
			return ResultData.from("F-2", Ut.f("%d번 글에 대한 수정 권한이 없습니다", article.getId()));
		}

		return ResultData.from("S-1", Ut.f("%d번 글을 수정했습니다", article.getId()));
	}

	//id에 맞는 article 삭제
	public void deleteArticle(int id) {
		articleRepository.deleteArticle(id);
	}

	//article에 맞는 id를 찾아서 제목과 내용을 수정
	public void modifyArticle(int id, String title, String body) {
		articleRepository.modifyArticle(id, title, body);
	}
	
	//글 정보 가져오기
	public Article getArticle(int id) {
		return articleRepository.getArticle(id);
	}
	
	//글 목록 가져오기
	public List<Article> getArticles() {
		return articleRepository.getArticles();
	}
	
	//boardid에 맞는 게시판에 쓰여진 글들중 searchKeywordTypeCode를 확인하고 serchkeyWord에 맞는 게시물 가져오기
	// -> 검색했을때 그에 맞는 article 가져오기
	public int getArticlesCount(int boardId, String searchKeywordTypeCode, String searchKeyword) {
		return articleRepository.getArticlesCount(boardId, searchKeywordTypeCode, searchKeyword);
	}

	//조회수를 증가
	public ResultData increaseHitCount(int id) {
		int affectedRow = articleRepository.increaseHitCount(id);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "해당 게시물 없음", "id", id);
		}

		return ResultData.from("S-1", "해당 게시물 조회수 증가", "id", id);

	}
	
	//id에 맞는 게시물의 조회수를 가져오기
	public Object getArticleHitCount(int id) {
		return articleRepository.getArticleHitCount(id);
	}
	
	//getforprint 출력하기 위해서 -> 지정된 게시판에서 특정 페이지의 해당하는 게시물 목록 가져오기
	//페이지 처리해서 가져오기
	public List<Article> getForPrintArticles(int boardId, int itemsInAPage, int page, String searchKeywordTypeCode,
			String searchKeyword) {

//		SELECT * FROM article WHERE boardId = 1 ORDER BY id DESC LIMIT 0, 10; 1page
//		SELECT * FROM article WHERE boardId = 1 ORDER BY id DESC LIMIT 10, 10; 2page
		
		//시작
		int limitFrom = (page - 1) * itemsInAPage;
		//끝
		int limitTake = itemsInAPage;

		return articleRepository.getForPrintArticles(boardId, limitFrom, limitTake, searchKeywordTypeCode,
				searchKeyword);
	}
	
	//게시물의 좋아요 수 증가
	public ResultData increaseGoodReactionPoint(int relId) {
		int affectedRow = articleRepository.increaseGoodReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "없는 게시물");
		}

		return ResultData.from("S-1", "좋아요 증가", "affectedRow", affectedRow);
	}
	
	//게시물의 싫어요 수 증가
	public ResultData increaseBadReactionPoint(int relId) {
		int affectedRow = articleRepository.increaseBadReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "없는 게시물");
		}

		return ResultData.from("S-1", "싫어요 증가", "affectedRow", affectedRow);
	}
	
	//게시물의 좋아요 수 감소
	public ResultData decreaseGoodReactionPoint(int relId) {
		int affectedRow = articleRepository.decreaseGoodReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "없는 게시물");
		}

		return ResultData.from("S-1", "좋아요 감소", "affectedRow", affectedRow);
	}
	//게시물의 싫어요 수 감소
	public ResultData decreaseBadReactionPoint(int relId) {
		int affectedRow = articleRepository.decreaseBadReactionPoint(relId);

		if (affectedRow == 0) {
			return ResultData.from("F-1", "없는 게시물");
		}

		return ResultData.from("S-1", "싫어요 감소", "affectedRow", affectedRow);
	}
	
	//게시물의 좋아요 수 가져오기
	public int getGoodRP(int relId) {
		return articleRepository.getGoodRP(relId);
	}

	
	//게시물 작성 시키기
	public ResultData<Integer> writeArticle(int boardId, int memberId,int contentTypeId,String title,String body,String tag,String firstImage,String firstImage2,String address,String mapX,String mapY) {
		articleRepository.writeArticle(boardId, memberId, contentTypeId, title,body,tag,firstImage,firstImage2,address,mapX,mapY);
		
		int id = articleRepository.getLastInsertId();
		
		return ResultData.from("S-1", Ut.f("%d번 글이 생성되었습니다", id), "id", id);
	}
	
	//게시물의 모든 주소를 가져오기
	public String[] getArticlesAddress() {
		String[] address = articleRepository.getArticlesAddress();
		return address;
	}
	// 게시물의 모든 제목을 가져오기
	public String[] getArticlesTitles() {
		String[] titles = articleRepository.getArticlesTitles();
		return titles;
	}
	
	//본인이 작성한 게시물 가져오기
	public List<Article> getMyArticles(int memberId) {
		 List<Article> articles = articleRepository.geyMyArticles(memberId);
		 return articles;
		
	}
	
	//특정 회원이 좋아요 한 게시글 가져오기
	public List<Article> getLikeArticles(int memberId) {
		List<Article> articles = articleRepository.getLikeArticles(memberId);
		return articles;
	}
	
	//글을 작성할때 미리 id를 정의하기 위해서 
	//현재 게시물의 번호 가져오기
	public int getCurrentArticleId() {
		return articleRepository.getCurrentArticleId();
		
	}
	
	//랜덤한 article 하나 가져오기
	public Article getRandomArticle() {
		Article article = articleRepository.getRandomArticle();
		return article;
		
	}

	/*
	 * public List<String> getArticlesTags() { List<String> tags =
	 * articleRepository.getArticlesTags(); Set<String> allTagsSet = new
	 * HashSet<>(); // 중복을 방지하기 위해 Set을 사용합니다.
	 * 
	 * for (String tag : tags) { String[] splitTag = tag.split("#"); for (String
	 * individualTag : splitTag) { if (!individualTag.isEmpty()) { // 빈 문자열이 아닌 경우에만
	 * 추가합니다. if (!allTagsSet.contains(individualTag)) { // 이미 존재하는 태그인지 확인합니다.
	 * allTagsSet.add(individualTag); // 존재하지 않으면 추가합니다. } } } }
	 * 
	 * // Set을 다시 List로 변환하여 반환합니다. List<String> allTags = new
	 * ArrayList<>(allTagsSet); return allTags; }
	 */
	
	//모든 게시물의 태그 가져오기
	//작성시 태그는 #예시1 , #예시2 , #예시3
	//#태그를 제거해서 가져오기 위함
	public List<String> getArticlesTags() {
	    List<String> tags = articleRepository.getArticlesTags();
	    List<String> allTags = new ArrayList<>();

	    for (String tag : tags) {
	        String[] splitTag = tag.split("#");
	        for (String individualTag : splitTag) {
	            if (!individualTag.isEmpty() && !allTags.contains(individualTag)) {
	                allTags.add(individualTag);
	            }
	        }
	    }

	    return allTags;
	}

	
	
}