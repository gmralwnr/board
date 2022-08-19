package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.BoardCtgDto;
import com.example.demo.dto.BoardDto;
import com.example.demo.dto.BoardFileDto;
import com.example.demo.dto.BoardGetDto;
import com.example.demo.dto.BoardListReqDto;
import com.example.demo.dto.BoardSetDto;
import com.example.demo.dto.ReplyDto;

@Mapper
public interface BoardDao {

	/** 메인 List*/
	List<BoardDto> boardList(BoardListReqDto bkdto);

	/** 게시판 총 개수*/
	int getBoardCount(BoardListReqDto bkdto);

	/** 게시판 하나 가져오기*/
	BoardGetDto getBoardOne(int board_no);

	/** 게시판 비밀번호 */
	BoardGetDto selectPassword(Integer board_no);

	/** update 수정*/
	int boardUpdate(BoardSetDto bsdto);

	/** Insert 추가 */
	int boardInsert(BoardSetDto bsdto);

	/** category 목록 가져오기 */
	List<BoardCtgDto> categoryGet();

	/** 게시물 삭제*/
	int boardDelete(BoardGetDto bgdto);

	/** 게시판 조회수 */
	int updateViewcnt(int board_no);

	int fileUploadInsert(BoardFileDto bfdto);

	int fileUploadUpdate(BoardFileDto bfdto);

	/** 파일 List */
	List<BoardFileDto> getFileOne(String ref_pk);

	/** file_no 가져오기 */
	BoardFileDto file_no_Select(String ref_pk);

	int boardFileDelete(int file_no);

	BoardFileDto selectFile(int file_no);

	BoardFileDto fileNumSelect(int file_no);

	int downloadCount(int file_no);

	int boardAllFileDelete(BoardGetDto bgdto);

	List<ReplyDto> replyList(Integer board_no);

	int insertReply(ReplyDto rdto);

	ReplyDto getSelectReply(int reply_no);

	int updateReply(ReplyDto rdto);

	int deleteReply(int reply_no);

	int replyCount(int board_no);













}
