package com.example.demo.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.DefaultNamingPolicy;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.dto.BoardCtgDto;
import com.example.demo.dto.BoardDto;
import com.example.demo.dto.BoardDtoList;
import com.example.demo.dto.BoardFileDto;
import com.example.demo.dto.BoardGetDto;
import com.example.demo.dto.BoardListReqDto;
import com.example.demo.dto.BoardSetDto;
import com.example.demo.dto.Paging;
import com.example.demo.service.BoardService;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@Controller
public class BoardController {

	String file_ref_tbl_board = "bt_tb_board";
	String file_ref_key_board = "board_attach_file";

	@Autowired
	private BoardService bs;

	@Autowired
	ServletContext context;
	/**
	 * <PRE>
	 * 1. 개요 : TB_MEMBER 목록 조회 _ JSP로 / MemberDto로 처리V
	 * 2. 처리내용  : TB_MEMBER 목록 조회 _ JSP로 / MemberDto로 처리
	 * 3. Comment   :
	 * </PRE>
	 *
	 * @Method Name : memberList
	 * @date : 2022. 6. 21.
	 * @author : AN
	 * @history :
	 *     -----------------------------------------------------------------------
	 *     변경일 작성자 변경내용 ----------- -------------------
	 *     --------------------------------------- 2022. 6. 21. AN 최초작성
	 *     -----------------------------------------------------------------------
	 *
	 */
	@RequestMapping(value = "/"	 , method = {RequestMethod.GET, RequestMethod.POST} )
	public ModelAndView main(Model model, HttpServletRequest request, BoardListReqDto brdto) {
		System.out.println("~~~~~~~~ 페이징 로딩~~~~~~~~~");

		ModelAndView mav = new ModelAndView();

		// TODO 카테고리 목록 조회 가져오기
		//BoardCtgDto bcdto = bs.categoryGet();
		List<BoardCtgDto> categoryList =bs.categoryGet();
		mav.addObject("category", categoryList);

		//검색 조건 유지
		mav.addObject("brdto", brdto);
		System.out.println("검색조건 유지!!!!!!!!!!!!!!!!!!!" + brdto);

		mav.setViewName("main");

		return mav;

	}

	@PostMapping(value = "/boardList" /* , method =RequestMethod.GET */)
	@ResponseBody
	public BoardDtoList boardList(BoardListReqDto bkdto) {

			// 리스트 가져오기
			BoardDtoList boardDtoList = new BoardDtoList();
			List<BoardDto> boardList = bs.boardList(bkdto);

			int count = bs.getBoardCount(bkdto);
			// 페이징
			Paging paging = new Paging();

			// 검색어 리스트 가져오기
			// BoardListReqDto boardkeyList = new BoardListReqDto();

			// list 파일 개수 확인 하는 법
			for (BoardDto board : boardList) {
				// System.out.println("\n!!!! " + board);
				// System.out.println("\n!!!!!!! : " + board.getRef_pk());
			}
			// System.out.println(boardList);

			// System.out.println(bkdto.getKeyword() + bkdto.getType() +
			// bkdto.getCategory());
			boardDtoList.setBoardList(boardList);
			boardDtoList.setCount(count); // boardDtoList 에 받아서 ajax에 보내기

			return boardDtoList;
	}

	/* 컨트롤러로 받아옮 !
	 * @PostMapping(value="/boardDetail") public String
	 * boardDetail( @RequestParam(value="board_no") int board_no, Model model)
	 * {//main.jsp function(boardDetail)로가는 함수에 board_no을 받아서 넘겨옴
	 * System.out.println("######## : " );
	 *
	 * System.out.println("######## : " + board_no); BoardGetDto getboardDetail = new
	 * BoardGetDto();
	 *
	 * getboardDetail=bs.getBoardOne(board_no);
	 *
	 * model.addAttribute("getBoard", getboardDetail); //board_no 를 이제 ajax에 넘기기 위해
	 * boardDetail에 넘겨줌으로써 조회를 할수 있도록 return "boardDetail"; }
	 */


	//게시판 상세보기 ajax 뿌리기전 동기식
	@PostMapping(value="/boardDetail")
	public String boardDetail( @RequestParam(value="board_no") int board_no,  Model model, BoardListReqDto brdto) {//main.jsp function(boardDetail)로가는 함수에 board_no을 받아서 넘겨옴


			System.out.println("######## : " + board_no);
		//	BoardGetDto getboardDetail = new BoardGetDto();  의미없음
		//	getboardDetail.setBoard_no(board_no);
			System.out.println("검색조건 유지!!!!!!!~~~~~~~~~~~~!!!!!!!!!!!!" + brdto);

			model.addAttribute("brdto", brdto);
			model.addAttribute("board_no", board_no); //board_no 를 이제 ajax에 넘기기 위해 boardDetail에 넘겨줌으로써 조회를 할수 있도록 //name 지정을 해서 변경
			return "boardDetail";
	}

    //Ajax 는 Ajax에서만 쓸수 있음
	@GetMapping(value="/getboardDetail")
	@ResponseBody
	public BoardGetDto getboardDetail(@RequestParam(value="board_no", required=true) Integer board_no /* Model model*/){
			System.out.println("dfdfdsfd:!!!!!!!!!!!!!" + board_no );

			int view_cnt=bs.updateViewcnt(board_no);  //파라미터로 받은 값으로 전달
			BoardGetDto bgdto =bs.getBoardOne(board_no);


			//날짜 시간 보이게 출력
			//DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//String date = df.format(bgdto.getReg_dt());
			//	int view_cnt=bs.updateViewcnt(bgdto.getBoard_no());


			System.out.println("!!!!!!!!!!!뷰 카운트 :  "+ bgdto.getView_cnt());
			System.out.println("board_no:" + board_no);
			System.out.println("게시판 글 :" + bgdto);
			//System.out.println("날짜아아아앙"  + dateToStr);
			//System.out.println(date);
			//model.addAttribute("getBoard", bgdto);  의미없음

			//bgdto.setDate(date);
			return bgdto;
	}



	//password는 컨트롤러에서 비교해주기 떄문에 창만 열어주는 컨트롤러
	@GetMapping(value="/boardPassword")
	public String boardPassword(/*Model model , @RequestParam(value="board_no", required=true) Integer board_no */) {

			//System.out.println("비밀번호 확인 위해 " + board_no);
			//BoardGetDto bgdto = bs.selectPassword(board_no);

			//javascript에서 사용 하려고 가져옮 !
			//model.addAttribute("pass", bgdto.getPassword()); //의미 없음 번호가 다 보이기 때문

			//model.addAttribute("board_no", board_no);
			return "boardPassword";
	}

	@PostMapping("/checkPass")
	@ResponseBody
	public BoardGetDto checkPass( Model model , @RequestParam("board_no") int board_no,
			@RequestParam(value= "board_pass", required=true) String password) {
		//System.out.println("boardCheckpass 보드 넘버 확인  : " + board_no);
		//System.out.println("password 확인" +  password);

			String msg;
			//String getflag;
			BoardGetDto bgdto = bs.selectPassword(board_no);
			model.addAttribute("board_no", board_no);


			if(password.equals(bgdto.getPassword())) {
				msg="비밀번호 일치 ";
				bgdto.setGflag("true");

			}else {
				model.addAttribute("message", "비밀번호가 맞지 않습니다 확인해 주세요");
				bgdto.setGflag("false");
				msg="비밀번호가 일치 하지 않습니다 다시 입력하세요 ";

			}

			bgdto.setMsg(msg);
			return bgdto;
	}



	//게시판 수정시 비밀번호 확인 컨트롤러로 사용시
	/*@PostMapping("/boardCheckpass")
	public String boardCheckpass( Model model , @RequestParam("board_no") int board_no,
			@RequestParam(value= "password", required=true) String password) {
		//System.out.println("boardCheckpass 보드 넘버 확인  : " + board_no);
		//System.out.println("password 확인" +  password);

			BoardGetDto bgdto = bs.selectPassword(board_no);
			model.addAttribute("board_no", board_no);


			if(password.equals(bgdto.getPassword())) {
				return "boardCheckPass";
			}else {
				model.addAttribute("message", "비밀번호가 맞지 않습니다 확인해 주세요");
				return "boardPassword";
			}

	}
	*/

	//ajax 보내기전 동기식 - 게시판 수정 폼 보내기
	@PostMapping("/boardUpdateForm")
	public String boardUpdateForm(Model model, @RequestParam("board_no") int board_no,BoardListReqDto brdto) {

		//board_no 로 수정 정보들 가져오기
		BoardGetDto bgdto = new BoardGetDto();
		bgdto =bs.getBoardOne(board_no);
		//model.addAttribute("bgdto", bgdto);
		//model.addAttribute("password", bgdto.getPassword());  //password는 필요없음 암호때문에

		//검색조건유지
		model.addAttribute("brdto", brdto);
		System.out.println("★★★★★★★★★★★검색조건유지 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!★"  + brdto);
		//카테고리 리스트
		List<BoardCtgDto> categoryList =bs.categoryGet();

		//file no 가져오기
		/*
		String ref_pk = Integer.toString(board_no);
		BoardFileDto bfdto = bs.file_no_Select(ref_pk);
		int file_no = bfdto.getFile_no();
*/
/*
	*/
		String ref_pk = Integer.toString(board_no);
		  List<BoardFileDto> fileList = bs.getFileOne(ref_pk);
		  bgdto.setFileList(fileList);  //파일을 List로 가져오는 경우는 하나의 board_no 에 파일이 3개까지 들어있으니 List로 가져와서 boardGetDto 에 담아준다
		  System.out.println("❤❤❤❤❤❤❤❤❤❤" + fileList);
		  System.out.println("❤❤❤❤❤❤❤❤❤❤/n" +fileList.get(0).getFile_no() );



		model.addAttribute("category", categoryList);
		model.addAttribute("board_no", board_no);
		model.addAttribute("file_no", fileList.get(0).getFile_no());
		//System.out.println("파일 넘버어어어어어어어어엉 : " + file_no );
		return "boardwrite";
	}

	//ajax 게시판 수정 내보내기
	@GetMapping("/updateBoard")
	@ResponseBody
	public BoardGetDto updateBoard(Model model, @RequestParam(value="board_no",required=true) Integer board_no) {
		//BoardGetDto bgdto = bs.boardUpdate(board_no);

		/* */
		  BoardGetDto bgdto = new BoardGetDto();
		  bgdto =bs.getBoardOne(board_no);

		  String ref_pk = Integer.toString(board_no);
		  List<BoardFileDto> fileList = bs.getFileOne(ref_pk);
		  bgdto.setFileList(fileList);  //파일을 List로 가져오는 경우는 하나의 board_no 에 파일이 3개까지 들어있으니 List로 가져와서 boardGetDto 에 담아준다



			model.addAttribute("getBoard", bgdto);
			//model.addAttribute("board_no", board_no);

			return  bgdto;
	}

	//ajax 게시판 수정
	@PostMapping("/update")
	@ResponseBody
	public BoardSetDto update(Model model, BoardSetDto bsdto, HttpServletRequest request
			, MultipartFile[] uploadFile // <input> file type name이랑 변수명이랑 똑같다.
		) {
/*
		// 0. MultipartFile[] 로 파일이 가져왔는지 확인 !
		System.out.println("UPLOADFILE 확인 : "+ uploadFile[0] );
		System.out.println("UPLOADFILE 확인 : "+ uploadFile[1] );

		// 1. uploadFile 에 null 체크 파일 들어있는지 1개 이상은 무조건 있겠져.

		String dirName = "C:/JAVA02/WEB04_coreBoard/src/main/webapp/upload/"; //업롣할 파일 경로
		BoardFileDto bfdto = new BoardFileDto();
		try {

		File folder = new File(dirName);  //dirName 폴더 가져오기

		if (!folder.exists()) folder.mkdirs();  //폴더가 없으면 만들어서 생성 하기 dirName 디렉토리에서

		//저장파일명 - 이름값 변경 위한 설정
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");  //이름값 변경을 위한 설정
		int rand =(int)(Math.random()*1000);

		int fresult = 0;

		for( int i=0; i<uploadFile.length; i++ ) { //파일 업로드 가 총 3개씩 올라가니까 length 를 줘서 반복문 돌리기

			String fileName = uploadFile[i].getOriginalFilename(); //오리진 파일 네임

			if( "".equals(fileName) || fileName == null ) continue;  //멈추면 다음걸로 증감해서 다시 돎

			//기존 파일 이름을 받고 확장자 저장
			String ext 		= fileName.substring(fileName.lastIndexOf(".") +1);

			//파일 이름 변경
			String reName =sdf.format(System.currentTimeMillis()) + "_" +rand + "." + ext;

			//파일이 없으면 파일 생성을 해줌
			File destination = new File(dirName + File.separator + reName); //저장할 파일이름

			//uploadFile[i].transferTo(destination);  //transferTo 파일 데이터를 지정한 file 로 폴더에 저장
			System.out.println("ORIGIN 파일 : " + destination);

			System.out.println("원본파일명 : " + fileName);
			System.out.println("확장자 : " +  ext);
			System.out.println("변경한 파일 이름 : " + reName);

			//bfdto.setOrigin_file_nm( uploadFile[i].getOriginalFilename() ); // 원본파일명

			//디비저장 할껀데
			// 0. dto 만들고 > 컬럼대로 object 만들기
			// 1. set 해서 담고 인서트


			uploadFile[i].transferTo(destination);  //transferTo 파일 데이터를 지정한 file 로 폴더에 저장

			bfdto.setOrigin_file_nm(fileName);
			bfdto.setSave_file_nm(reName);
			bfdto.setSave_path(dirName);
			//bfdto.setSize(uploadFile[i].getSize());
			byte eee = (byte) uploadFile[i].getSize();
			bfdto.setSize(eee & 0xff);
			bfdto.setRef_tbl(file_ref_tbl_board);
			bfdto.setRef_pk("3");
			bfdto.setRef_key(file_ref_key_board);
			//System.out.println("파일사이즈ㅜ " + bfdto.getSize() );
			bfdto.setExt(ext);
			bfdto.setOrd(i +1); //0부터 시작하기 떄문에 +1을 해줌 순서 1,2,3

			fresult +=	bs.fileUploadInsert(bfdto);

		}

		} catch (Exception e){

		}
		// 2. localhost:8000/file/20220801/board/ 예를들어 이 경로 설정 먼저

		// 3.  for 문 돌려서 서버 파일 저장 -> db 저장 ( 구글링 )

		// 3.  있는것까지만 저장해 놓기
*/
/////////////////////////////////////////////////////////////////아래는 업로드 성공 시
		BoardGetDto bgdto = new BoardGetDto();

		String resultMsg ="";  //BoardSetDto 에 담겨져있음
		int result =0;

		//****board_no가 조회 될 때는 update 구문
		if(bsdto.getBoard_no()!=null && bsdto.getBoard_no() !=0) {//equals 쓰기

			result = bs.boardUpdate(bsdto);  //update 나 insert 할 경우 성공하게 되면 1이 출력 됨 //성공한 건에 대한 개수
			System.out.println("update board_no" + bsdto.getBoard_no());


			if(result == 0) { //update 를 result 에 담아서 0이라면 실패 // if긍정적 else부정
				resultMsg ="수정 실! 패! ㅜㅜㅜ";
			}else { //수정이 완료 되면 1 이 뜰 테니 수정 성공
				resultMsg ="수정 완료입니다~~❤️";
				bsdto.setFlag("update");
			}

		//****board_no 가 없을경우 insert
		}else {

			//리절트로 선언해서 받기
			bs.boardInsert(bsdto);

			System.out.println("insert board_no 성공!!" + bsdto.getBoard_no());

			if(bsdto.getBoard_no() !=0) {

				//bsdto.getBoard_no();
				resultMsg="등록 완료 입니다 ~!!";
				bsdto.setFlag("insert");

			}else {
				resultMsg="등록 실패입니다 입니다 ~!!";
			}
		}



		//등륵 1.물리적 파일 저장   2.파일  DB저장
		//보드넘이 있으면 슈정 ㅡ,ㅡ 없으면 등록

/////////////////////////////////////파일 업로드

			// 0. MultipartFile[] 로 파일이 가져왔는지 확인 !
		System.out.println("UPLOADFILE 확인 : "+ uploadFile[0] );
		System.out.println("UPLOADFILE 확인 : "+ uploadFile[1] );
			// 1. uploadFile 에 null 체크 파일 들어있는지 1개 이상은 무조건 있겠져.
		BoardFileDto bfdto = new BoardFileDto();
		String dirName = "C:/JAVA02/WEB04_coreBoard/src/main/webapp/upload/"; //업로드 할 파일 경로

		try {
			//2.폴더가 생성이 안되면 생성 시기기
			File folder = new File(dirName);  //dirName 폴더 가져오기

			if (!folder.exists()) folder.mkdirs();  //폴더가 없으면 만들어서 생성 하기 dirName 디렉토리에서

			//저장파일명 - 이름값 변경 위한 설정
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");  //이름값 변경을 위한 설정, 위로 뺴준 이유는 매모리 잡아 먹어서
			int rand =(int)(Math.random()*1000);

			int fresult = 0;

				//3. 업로드 파일이 3개 이상이니 반복문으로 체크 후 저장될 변수 조회
			for( int i=0; i<uploadFile.length; i++ ) { //파일 업로드 가 총 3개씩 올라가니까 length 를 줘서 반복문 돌리기

				String fileName = uploadFile[i].getOriginalFilename(); //오리진 파일 네임을 담음

				if( "".equals(fileName) || fileName == null ) continue;  //continue : 멈추면 다음걸로 증감해서 다시 돎, breake 는 끊켜서 안도는데 countinue 는 다음게 없으면 다시 차례대로 돌아서 검사함

				//기존 파일 이름을 받고 확장자 저장
				String ext 		= fileName.substring(fileName.lastIndexOf(".") +1);  //+1은 .을 빼고 난 뒤를 찾기 위해서

				//파일 이름 변경
				String reName =sdf.format(System.currentTimeMillis()) + "_" +rand + "." + ext;

				//이름을 변경한 파일을 dirName(디렉토리)에 저장
				//File destination = new File(dirName + File.separator + uploadFile[i].getOriginalFilename()); //오리진 파일로 저장되서 reName으로 변경
				File destination = new File(dirName + File.separator + reName); //저장할 파일이름

				System.out.println("ORIGIN 파일 : " + destination);
				System.out.println("원본파일명 : " + fileName);
				System.out.println("확장자 : " +  ext);
				System.out.println("변경한 파일 이름 : " + reName);

					//4.폴더에 저장
				uploadFile[i].transferTo(destination);  //transferTo 파일 데이터를 지정한 file 로 폴더에 저장

					//디비저장 할껀데
					// 0. dto 만들고 > 컬럼대로 object 만들기
					// 1. set 해서 담고 인서트
					//
				bfdto.setOrigin_file_nm(fileName);
				bfdto.setSave_file_nm(reName);
				bfdto.setSave_path(dirName);
				//bfdto.setSize(uploadFile[i].getSize());
				byte eee = (byte) uploadFile[i].getSize();
				bfdto.setSize(eee & 0xff);
				bfdto.setRef_tbl(file_ref_tbl_board);
				String file_board_no = Integer.toString(bsdto.getBoard_no());
				bfdto.setRef_pk(file_board_no);
				bfdto.setRef_key(file_ref_key_board);
				//System.out.println("파일사이즈ㅜ " + bfdto.getSize() );
				bfdto.setExt(ext);
				bfdto.setOrd(i +1); //0부터 시작하기 떄문에 +1을 해줌 순서 1,2,3

				System.out.println("파일 업로드 보드넘 : " + file_board_no);
				System.out.println("파일 순서 :  " + i +1);


				//fresult +=bs.fileUploadUpdate(bfdto);


				//insert
				System.out.println("dsdsd" +  bfdto.getFile_no());
				fresult +=	bs.fileUploadInsert(bfdto);
				if(fresult>0) {
					bfdto.getFile_no();
					System.out.println(bfdto.getFile_no());
					resultMsg ="파일 등록완료";
				}else {
					resultMsg ="파일업로드 실패";
				}

			}

		} catch (Exception e){

		}



		bsdto.setResultMsg(resultMsg);  //bsdto 에 담기 위해 BoardSetDto 필드변수 생성 해야함

		return bsdto;

	}


	@PostMapping("/boardWriteForm")//{board_no} 등 넘어오지 않기 때문에 Get 으로 보내줌
	public String boardWriteForm(BoardListReqDto brdto, Model model) {
		//검색조건 유지
		model.addAttribute("brdto", brdto);

		//카테고리 리스트
		List<BoardCtgDto> categoryList =bs.categoryGet();
		model.addAttribute("category", categoryList);
		//System.out.println("category" + categoryList);
		System.out.println("★★★★★★★★★★★검색조건유지 !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!★"  + brdto);

		return "boardwrite";
	}

	//삭제
	@PostMapping("/boardDeleteForm")
	//@ResponseBody
		public String boardDeleteForm(BoardGetDto bgdto){

		int result = bs.boardDelete(bgdto);
		System.out.println("삭제 결과 : " + result);
		System.out.println("board삭제" + bgdto);

		return "redirect:/";
	}



}
