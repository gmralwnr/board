package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.MemberDto;
import com.example.demo.service.MemberService;

@Controller
@RequestMapping(value="/member")
public class MemberController {

	@Autowired
	private MemberService memberService;

	/**
	 * <PRE>
	 * 1. 개요 : TB_MEMBER 목록 조회 _ JSP로 / MemberDto로 처리
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
	@RequestMapping(value="/memberList")
	public String memberList(Model model) {

		int a = 0;

		a = 10;

		a = 20;

		/* 리스트 조회 */
		List<MemberDto> listMember = memberService.getAllMember();

		System.out.println("1" );
		System.out.println("2" );
		System.out.println("3" );
		System.out.println("4" );
		System.out.println("5" );
		System.out.println("6" );

		/* 모델에 셋팅 */
		model.addAttribute("listMember",listMember);

		return "memberList";

	}




}
