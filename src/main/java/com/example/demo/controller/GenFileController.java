package com.example.demo.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartRequest;

import com.example.demo.exception.GenFileNotFoundException;
import com.example.demo.service.GenFileService;
import com.example.demo.vo.GenFile;
import com.example.demo.vo.ResultData;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class GenFileController {
    @Value("${custom.genFileDirPath}")
    private String genFileDirPath; // 파일이 저장될 디렉토리 경로

    @Autowired
    private GenFileService genFileService;

    // 파일 업로드 요청을 처리
    @RequestMapping("/common/genFile/doUpload")
    @ResponseBody
    public ResultData doUpload(@RequestParam Map<String, Object> param, MultipartRequest multipartRequest) {
        return genFileService.saveFiles(param, multipartRequest);
    }

    // 파일 다운로드 요청을 처리
    @GetMapping("/common/genFile/doDownload")
    public ResponseEntity<Resource> downloadFile(int id, HttpServletRequest request) throws IOException {
        GenFile genFile = genFileService.getGenFile(id);

        if (genFile == null) {
            throw new GenFileNotFoundException(); // 파일을 찾지 못하면 예외 발생
        }

        String filePath = genFile.getFilePath(genFileDirPath); // 파일 경로 가져오기

        Resource resource = new InputStreamResource(new FileInputStream(filePath)); // 파일을 InputStream으로 읽기

        // 파일의 콘텐츠 타입을 확인
        String contentType = request.getServletContext().getMimeType(new File(filePath).getAbsolutePath());

        if (contentType == null) {
            contentType = "application/octet-stream"; // 기본 콘텐츠 타입 설정
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + genFile.getOriginFileName() + "\"")
                .contentType(MediaType.parseMediaType(contentType)).body(resource);
    }

    // 파일을 보여주는 요청을 처리
    @GetMapping("/common/genFile/file/{relTypeCode}/{relId}/{typeCode}/{type2Code}/{fileNo}")
    public ResponseEntity<Resource> showFile(HttpServletRequest request, @PathVariable String relTypeCode,
                                             @PathVariable int relId, @PathVariable String typeCode, @PathVariable String type2Code,
                                             @PathVariable int fileNo) throws FileNotFoundException {
        GenFile genFile = genFileService.getGenFile(relTypeCode, relId, typeCode, type2Code, fileNo);

        if (genFile == null) {
            throw new GenFileNotFoundException(); // 파일을 찾지 못하면 예외 발생
        }

        String filePath = genFile.getFilePath(genFileDirPath); // 파일 경로 가져오기
        Resource resource = new InputStreamResource(new FileInputStream(filePath)); // 파일을 InputStream으로 읽기

        // 파일의 콘텐츠 타입을 확인
        String contentType = request.getServletContext().getMimeType(new File(filePath).getAbsolutePath());

        if (contentType == null) {
            contentType = "application/octet-stream"; // 기본 콘텐츠 타입 설정
        }

        return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).body(resource);
    }
}