package com.example.demo.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BoardCtgDto;
import com.example.demo.dto.BoardDto;
import com.example.demo.dto.BoardGetDto;
import com.example.demo.dto.BoardListReqDto;
import com.example.demo.dto.BoardSetDto;
import com.example.demo.repository.BoardDao;

@Service
public class BoardService {
	@Autowired
	private BoardDao bdao;
	/** 메인 List*/
	public List<BoardDto> boardList(BoardListReqDto bkdto) {

		return bdao.boardList(bkdto);
	}

	/** 게시판 총 개수*/
	public int getBoardCount(BoardListReqDto bkdto) {

		return bdao.getBoardCount(bkdto);
	}

	/** 게시판 하나 가져오기*/
	public BoardGetDto getBoardOne(int board_no) {

		return bdao.getBoardOne(board_no);
	}

	/** 게시판 비밀번호 */
	public BoardGetDto selectPassword(int board_no) {

		return bdao.selectPassword(board_no);
	}

	/** update 수정*/
	public int boardUpdate(BoardSetDto bsdto) {

		return bdao.boardUpdate(bsdto);
	}

	/** Insert 추가 */
	public int boardInsert(BoardSetDto bsdto) {

		return bdao.boardInsert(bsdto);
	}

	/** category 목록 가져오기 */
	public List<BoardCtgDto> categoryGet() {

		return bdao.categoryGet();
	}

	/** 게시물 삭제*/
	public int boardDelete(BoardGetDto bgdto) {

		return bdao.boardDelete(bgdto);

	}

	/** 게시판 조회수 */
	public int updateViewcnt(int board_no) {

		return bdao.updateViewcnt(board_no);
	}





}
