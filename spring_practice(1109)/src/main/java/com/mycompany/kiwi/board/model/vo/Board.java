package com.mycompany.kiwi.board.model.vo;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public class Board {
	private int boardNum;
	private String boardTitle;
	private String boardWriter;
	private String boardContent;
	private String boardOriginalFileName;
	private String boardRenameFileName;
	private String boardDate;
	private int boardLevel;
	private int boardRef;
	private int boardReplyRef;
	private int boardReplySeq;
	private int boardReadCount;
	
	private MultipartFile report;
	// input의 name과 이름이 같아야한다.
	
	public MultipartFile getReport() {
		return report;
	}
	public void setReport(MultipartFile report) {
		this.report = report;
	}
	// 이렇게 하면 따로
	// @RequestParam(name="report") MultipartFile upfile,
	// MultipartHttpServletRequest multi
	// 이것을 작성하지 않아도 된다. 어차피 VO에 실려들어오니까
	// 하지만 이 방법은 DB에 '파일이름만' 저장하는 방식을 사용할떄는 자주 사용하지 않는다.(쓸데없이 볼륨감 크게 VO를 들고다니니까)
	// DB에 파일 자체를 다이렉트로 저장할 때는 유용하다.
	
	
	public int getBoardNum() {
		return boardNum;
	}

	public void setBoardNum(int boardNum) {
		this.boardNum = boardNum;
	}
	public String getBoardTitle() {
		return boardTitle;
	}
	public void setBoardTitle(String boardTitle) {
		this.boardTitle = boardTitle;
	}
	public String getBoardWriter() {
		return boardWriter;
	}
	public void setBoardWriter(String boardWriter) {
		this.boardWriter = boardWriter;
	}
	public String getBoardContent() {
		return boardContent;
	}
	public void setBoardContent(String boardContent) {
		this.boardContent = boardContent;
	}
	public String getBoardOriginalFileName() {
		return boardOriginalFileName;
	}
	public void setBoardOriginalFileName(String boardOriginalFileName) {
		this.boardOriginalFileName = boardOriginalFileName;
	}
	public String getBoardRenameFileName() {
		return boardRenameFileName;
	}
	public void setBoardRenameFileName(String boardRenameFileName) {
		this.boardRenameFileName = boardRenameFileName;
	}
	public String getBoardDate() {
		return boardDate;
	}
	public void setBoardDate(String boardDate) {
		this.boardDate = boardDate;
	}
	public int getBoardLevel() {
		return boardLevel;
	}
	public void setBoardLevel(int boardLevel) {
		this.boardLevel = boardLevel;
	}
	public int getBoardRef() {
		return boardRef;
	}
	public void setBoardRef(int boardRef) {
		this.boardRef = boardRef;
	}
	public int getBoardReplyRef() {
		return boardReplyRef;
	}
	public void setBoardReplyRef(int boardReplyRef) {
		this.boardReplyRef = boardReplyRef;
	}
	public int getBoardReplySeq() {
		return boardReplySeq;
	}
	public void setBoardReplySeq(int boardReplySeq) {
		this.boardReplySeq = boardReplySeq;
	}
	public int getBoardReadCount() {
		return boardReadCount;
	}
	public void setBoardReadCount(int boardReadCount) {
		this.boardReadCount = boardReadCount;
	}
	
	public Board() {}
	
	@Override
	public String toString() {
		return "Board [boardNum=" + boardNum + ", boardTitle=" + boardTitle + ", boardWriter=" + boardWriter
				+ ", boardContent=" + boardContent + ", boardOriginalFileName=" + boardOriginalFileName
				+ ", boardRenameFileName=" + boardRenameFileName + ", boardDate=" + boardDate + ", boardLevel="
				+ boardLevel + ", boardRef=" + boardRef + ", boardReplyRef=" + boardReplyRef + ", boardReplySeq="
				+ boardReplySeq + ", boardReadCount=" + boardReadCount + "]";
	}
}