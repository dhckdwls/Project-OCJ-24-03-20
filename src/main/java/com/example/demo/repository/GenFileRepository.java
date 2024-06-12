package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.vo.GenFile;

@Mapper
public interface GenFileRepository {
	   // 파일 정보 저장
	void saveMeta(Map<String, Object> param);
	
	// 특정 조건에 해당하는 파일 정보 조회
	GenFile getGenFile(@Param("relTypeCode") String relTypeCode, @Param("relId") int relId,
			@Param("typeCode") String typeCode, @Param("type2Code") String type2Code, @Param("fileNo") int fileNo);


    // ID를 기반으로 파일 정보 조회
	GenFile getGenFileById(@Param("id") int id);
	
	 // 파일의 연결 ID 변경
	void changeRelId(@Param("id") int id, @Param("relId") int relId);
	
	// 특정 조건에 해당하는 파일 삭제
	void deleteFiles(@Param("relTypeCode") String relTypeCode, @Param("relId") int relId);
	
	 // 특정 조건에 해당하는 파일 목록 조회
	List<GenFile> getGenFiles(@Param("relTypeCode") String relTypeCode, @Param("relId") int relId,
			@Param("typeCode") String typeCode, @Param("type2Code") String type2Code);
	
	  // 특정 관련 타입 코드와 관련 ID에 해당하는 파일 목록 조회
	List<GenFile> getGenFilesByRelTypeCodeAndRelId(@Param("relTypeCode") String relTypeCode, @Param("relId") int relId);
	
    // 특정 ID에 해당하는 파일 삭제
	void deleteFile(@Param("id") int id);
	
	// 특정 조건에 해당하는 파일 목록 조회
	List<GenFile> getGenFilesRelTypeCodeAndRelIdsAndTypeCodeAndType2Code(@Param("relTypeCode") String relTypeCode,
			@Param("relIds") List<Integer> relIds, @Param("typeCode") String typeCode,
			@Param("type2Code") String type2Code);
}