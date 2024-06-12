package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import com.example.demo.vo.Board;

@Mapper
public interface BoardRepository {
	
	//boardid에 맞는 게시판의 정보를 불러온다
	@Select("""
				SELECT *
				FROM board
				WHERE id = #{boardId}
				AND delStatus = 0;
			""")
	public Board getBoardById(int boardId);

}