package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.BoardDto;
import com.example.demo.dto.BoardGetDto;
import com.example.demo.dto.BoardListReqDto;
import com.example.demo.repository.BoardDao;

@Service
public class BoardService {
	@Autowired
	private BoardDao bdao;

	public List<BoardDto> boardList(BoardListReqDto bkdto) {


		return bdao.boardList(bkdto);
	}

	public int getBoardCount(BoardListReqDto bkdto) {

		return bdao.getBoardCount(bkdto);
	}

	public BoardGetDto getBoardOne(int board_no) {

		return bdao.getBoardOne(board_no);
	}

	public BoardGetDto selectPassword(int board_no) {

		return bdao.selectPassword(board_no);
	}



}
