package com.example.demo.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.BoardDto;
import com.example.demo.dto.BoardDtoList;
import com.example.demo.dto.BoardListReqDto;
import com.example.demo.dto.Paging;
import com.example.demo.service.BoardService;

@Controller
public class BoardController {

	@Autowired
	private BoardService bs;
	/**
	 * <PRE>
	 * 1. 개요 : TB_MEMBER 목록 조회 _ JSP로 / MemberDto로 처리V
	 * 2. 처리내용  : TB_MEMBER 목록 조회 _ JSP로 / MemberDto로 처리
	 * 3. Comment   :
	 * </PRE>
	 * @Method Name : memberList
	 * @date : 2022. 6. 21.
	 * @author : AN
	 * @history :
	 *   -----------------------------------------------------------------------
	 *   변경일            작성자                  변경내용
	 *   ----------- ------------------- ---------------------------------------
	 *   2022. 6. 21.      AN            최초작성
	 *   -----------------------------------------------------------------------
	 *
	 */
	@RequestMapping(value="/")
	public ModelAndView main(Model model, HttpServletRequest request ) {

		ModelAndView mav = new ModelAndView();
		//TODO 카테고리 목록 조회 가져오기
		System.out.println("~~~~~~~~ 페이징 로딩~~~~~~~~~");
		mav.setViewName("main");

		return mav;

	}

	@GetMapping(value = "/boardList"/* , method =RequestMethod.GET */)
	@ResponseBody
	public BoardDtoList boardList(BoardListReqDto bkdto) {

		//리스트 가져오기
		BoardDtoList boardDtoList = new BoardDtoList();
		List<BoardDto> boardList = bs.boardList(bkdto);


		int count  = bs.getBoardCount(bkdto);
		//페이징
		Paging paging = new Paging();

		//검색어 리스트 가져오기
		//BoardListReqDto boardkeyList = new BoardListReqDto();

		//list 파일 개수 확인 하는 법
		for( BoardDto board : boardList) {
		//	System.out.println("\n!!!!   " + board);
		//	System.out.println("\n!!!!!!! : " + board.getRef_pk());
		}
		System.out.println(boardList);

		System.out.println(bkdto.getKeyword() + bkdto.getType() + bkdto.getCategory());
		boardDtoList.setBoardList(boardList);
		boardDtoList.setCount(count);

		return boardDtoList;
	}

}