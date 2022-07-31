package com.example.demo.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.dto.BoardCtgDto;
import com.example.demo.dto.BoardDto;
import com.example.demo.dto.BoardGetDto;
import com.example.demo.dto.BoardListReqDto;
import com.example.demo.dto.BoardSetDto;

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











}
