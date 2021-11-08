package com.mycompany.myapp06.board.model.vo;

public class Board {
//    BOARD_NUM NUMBER PRIMARY KEY, -- 게시글 번호
//    BOARD_TITLE VARCHAR2(50), -- 게시글 제목
//    BOARD_WRITER VARCHAR2(15), -- 게시글 작성자
//    BOARD_CONTENT VARCHAR2(2000), -- 게시글 내용
//    BOARD_ORIGINAL_FILENAME VARCHAR2(100), -- 업로드한 원래 파일명
//    BOARD_RENAME_FILENAME VARCHAR2(100), -- 바뀐 파일명
//    BOARD_DATE DATE DEFAULT SYSDATE,-- 게시글 올린 날짜
//    BOARD_LEVEL NUMBER DEFAULT 0,-- 글레벨 : 원글 0, 원글의 답글 1, 답글의 답글 2
//    BOARD_REF NUMBER, -- 원글일때는 자기번호, 답글일 때 참조하는 원글 번호
//    BOARD_REPLY_REF NUMBER,
//    -- 원글일때는 0, 레벨이 1이면 자기번호, 레벨이 2이면 참조하는 답글번호
//    BOARD_REPLY_SEQ NUMBER DEFAULT 0, -- 답글의 순번
//    BOARD_READCOUNT NUMBER DEFAULT 0 -- 조회수
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
	
	// 검색 조건 : title writer Content
	
	public Board() {}
	
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

	@Override
	public String toString() {
		return "Board [boardNum=" + boardNum + ", boardTitle=" + boardTitle + ", boardWriter=" + boardWriter
				+ ", boardContent=" + boardContent + ", boardOriginalFileName=" + boardOriginalFileName
				+ ", boardRenameFileName=" + boardRenameFileName + ", boardDate=" + boardDate + ", boardLevel="
				+ boardLevel + ", boardRef=" + boardRef + ", boardReplyRef=" + boardReplyRef + ", boardReplySeq="
				+ boardReplySeq + ", boardReadCount=" + boardReadCount + "]";
	}
}
