package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.MemberDto;
import com.example.demo.repository.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memberRepository;

	/**  alt +shft+ J
	 /**
	 * <PRE>
	 * 1. 개요 : 모든 멤버가져오기
	 * 2. 처리내용  :
	 * 3. Comment   :
	 * </PRE>
	 * @Method Name : getAllMember
	 * @date : 2022. 7. 13.
	 * @author : gmral
	 * @history :
	 *   -----------------------------------------------------------------------
	 *   변경일            작성자                  변경내용
	 *   ----------- ------------------- ---------------------------------------
	 *   2022. 7. 13.      gmral            최초작성
	 *   -----------------------------------------------------------------------
	 *
	 */

	public List<MemberDto> getAllMember(){

		return memberRepository.getAllMember();


		
	}
}
