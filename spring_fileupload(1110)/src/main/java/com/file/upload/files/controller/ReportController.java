package com.file.upload.files.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.file.upload.files.model.vo.ReportCommand;


@Controller
public class ReportController {
	@RequestMapping(value = "reportForm.do", method = RequestMethod.GET)
	public String form() {
		return "reportForm";
	}
	
	// 1. @RequestParam 사용
	@RequestMapping(value = "submitReport1.do", method = RequestMethod.POST)
	public String submitReport1(
			@RequestParam(name="studentNumber") String studentNumber
			, @RequestParam(name="report") MultipartFile report
			, HttpServletRequest request
			) {
		printInfo(studentNumber, report);
		saveFile(report, request);
		
		return "reportComplete";
	}
	
	// 2. MultipartHttpServletRequest 사용
	@RequestMapping(value = "submitReport2.do", method = RequestMethod.POST)
	public String submitReport2(MultipartHttpServletRequest request) {
		String studentNumber = request.getParameter("studentNumber");
		MultipartFile report = request.getFile("report");
		printInfo(studentNumber, report);
		saveFile(report, request);
		return "reportComplete";
	}
	
	// 3. 커맨드 객체 사용
	@RequestMapping(value = "submitReport3.do", method = RequestMethod.POST)
	public String submitReport3(ReportCommand reportCommand, HttpServletRequest request) {
		printInfo(reportCommand.getStudentNumber(), reportCommand.getReport());
		saveFile(reportCommand.getReport(), request);
		return "reportComplete";
	}
	
	
	// 파일 업로드 정보 출력
	private void printInfo(String studentNumber, MultipartFile report) {
		System.out.println(studentNumber + "가 업로드 한 파일: " + report.getOriginalFilename());
	}
	
	
	// 실제 파일 업로드 메소드 구현
	private void saveFile(MultipartFile report, HttpServletRequest request) {
		String root = request.getSession().getServletContext().getRealPath("resources");
		// resources 폴더의 실제 물리경로를 찾아라
		
		String savePath = root + "\\uploadFiles";
		// savePath : 파일을 실제로 저장할 경로  cf) uploadFiles가 파일을 저장할 폴더명이다.
		
		// 경로에 폴더가 없으면 만들어라
		File folder = new File(savePath);
		if (!folder.exists()) {
			folder.mkdirs();
		}
		
		String filePath = null;
		// 폴더를 포함한 파일의 경로
		
		// 파일 저장
		try {
			System.out.println(report.getOriginalFilename() + "을 저장합니다.");
			
			filePath = folder + "\\" + report.getOriginalFilename();
			System.out.println("filePath(파일 경로) : " + filePath);
			System.out.println("root(물리경로) : " + root);
			System.out.println("savePath(저장 경로) : " + savePath);
			System.out.println("folder(폴더 경로) : " + folder);
			System.out.println("파일 명 : " + report.getOriginalFilename());
			
			report.transferTo(new File(filePath));
			// 파일을 저장한다
			
			System.out.println("파일 전송이 완료되었습니다.");
		} catch (Exception e) {
			System.out.println("파일 전송 에러 : " + e.getMessage());
			e.printStackTrace();
		}
	}
}
