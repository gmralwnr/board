package com.example.demo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.jasper.tagplugins.jstl.core.Out;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.DefaultNamingPolicy;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import com.example.demo.dto.ReplyDto;
import com.example.demo.service.BoardService;

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
	public BoardGetDto getboardDetail(@RequestParam(value="board_no", required=true) Integer board_no
			, Model model){
			System.out.println("dfdfdsfd:!!!!!!!!!!!!!" + board_no );

			int view_cnt=bs.updateViewcnt(board_no);  //파라미터로 받은 값으로 전달 바로 cunt 업 되게
			BoardGetDto bgdto =bs.getBoardOne(board_no);


			//날짜 시간 보이게 출력
			//DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			//String date = df.format(bgdto.getReg_dt());
			//	int view_cnt=bs.updateViewcnt(bgdto.getBoard_no());

			int filecount = 0;
			String ref_pk = Integer.toString(board_no);
			List<BoardFileDto> fileList = bs.getFileOne(ref_pk);
			//fileList size 체크를 해주는 이유는 만약 파일이 없다면 파일 도 조회되어 조회가 안되면 안나올 수 있으므로 1개라도 조회할 수 있게
			if(fileList.size() > 0) {
				bgdto.setFileList(fileList);  //파일을 List로 가져오는 경우는 하나의 board_no 에 파일이 3개까지 들어있으니 List로 가져와서 boardGetDto 에 담아준다
			//	int filecount = fileList.size() ;*********
			//	bgdto.setFilecount(filecount);
			}

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


		int count=0;
		String ref_pk = Integer.toString(board_no);
		  List<BoardFileDto> fileList = bs.getFileOne(ref_pk);

		//fileList size 체크를 해주는 이유는 만약 파일이 없다면 파일 도 조회되어 조회가 안되면 안나올 수 있으므로 1개라도 조회할 수 있게
		  if(fileList.size() >0) {
			  bgdto.setFileList(fileList);  //파일을 List로 가져오는 경우는 하나의 board_no 에 파일이 3개까지 들어있으니 List로 가져와서 boardGetDto 에 담아준다

			//  model.addAttribute("save_file_nm1", fileList.get(0).getSave_file_nm());
			//  model.addAttribute("save_file_nm2", fileList.get(1).getSave_file_nm());
			 // model.addAttribute("save_file_nm3", fileList.get(2).getSave_file_nm());

			  for(BoardFileDto bfdto : fileList) { //fileList를 dto 에 담아서 개수 체크 해줘서 x박스 뜨게하기
				  System.out.println(count + " 번쨰 " + bfdto);
				  count ++;
				 // model.addAttribute("ref_pk", bfdto.getRef_pk()); //필요할까...?
				  System.out.println("\n\n\n SaveFileName : " + bfdto.getSave_file_nm());
				  //★조회할 때도 for문 안에서 돌리기 파일 조회가 안되었을 경우 에러남
				  System.out.println( model.addAttribute("count", count));
				  System.out.println("❤❤❤❤❤❤❤❤❤❤/n" +fileList.get(0).getFile_no() );

			  }

		  }
		  model.addAttribute("bfdto", fileList);
		  model.addAttribute("count", count); //파일 업로드 x박스 (delete)여부



		model.addAttribute("category", categoryList);
		model.addAttribute("board_no", board_no);

		//model.addAttribute("file_no", fileList.get(0).getFile_no());
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

		  if(fileList.size() > 0) {
		  bgdto.setFileList(fileList);  //파일을 List로 가져오는 경우는 하나의 board_no 에 파일이 3개까지 들어있으니 List로 가져와서 boardGetDto 에 담아준다

		  }


			model.addAttribute("getBoard", bgdto);
			//model.addAttribute("board_no", board_no);

			return  bgdto;
	}

	//ajax 게시판 수정
	@PostMapping("/update")
	@ResponseBody
	public BoardSetDto update(Model model, BoardSetDto bsdto, HttpServletRequest request
			, MultipartFile[] uploadFile // <input> file type name이랑 변수명이랑 똑같다.
		) throws Exception { //트랜잭션 하려고 service 에 옮김


		BoardSetDto resultDto = null;
		resultDto = bs.update(bsdto, uploadFile);
		//트랜잭션으로 서비스 넘어감 !!!


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

		return resultDto;  //resultDto 가 ajax로 이동
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
	@ResponseBody
		public int boardDeleteForm(BoardGetDto bgdto/*, BoardFileDto bfdto */) throws Exception{
		int result = 0;
		result = bs.boardAllDeleteForm(bgdto/* , bfdto */);






		return result;
	}

	//@DeleteMapping("boardFileDelete")
	@PostMapping("boardFileDelete")
	@ResponseBody
	public int boardFileDelete( @RequestParam(value = "file_no", required=true) int file_no , BoardFileDto bfdto
		/*, @RequestParam(value="board_no") int board_no*/) throws Exception {

			//int로 담아서 삭제가 되었는지 확인 후 result 로 반환을 해줌

			int result = 0;
			result =bs.deleteBoardFile(file_no, bfdto);

			/*
			int result =0;
			bfdto = bs.selectFile(file_no);  //bfdto에 담아야하기때문에 get으로 가져와야해서 선언함 save_nm, save_path를 불러오기 위해서 select 함
			System.out.println(bfdto.getSave_path() + bfdto.getSave_file_nm()  );
			String save_fileName = bfdto.getSave_path() + bfdto.getSave_file_nm();
			Path filePath = Paths.get(save_fileName);



				//파일 삭제
				try {

						Files.delete(filePath);
						System.out.println("삭제완료 ");

				} catch (NoSuchFileException e) {
					System.out.println("삭제하려는 파일/디렉토리가 없습니다");
				} catch (IOException e) {

					e.printStackTrace();
				}

			System.out.println("야야야야야야" + file_no);
			result = bs.boardFileDelete(file_no);
		*/
		return result;
	}


	@GetMapping("/filedownload")
	public void fileDownload(@RequestParam("file_no") int file_no, HttpServletRequest request, HttpServletResponse response) throws Exception {

		//String directory = "C:/JAVA02/WEB04_coreBoard/src/main/webapp/upload/";
		//String files[] =new File(directory).list();

		//파일정보 조회
		BoardFileDto bfdto = bs.fileNumSelect(file_no);

		//파일명 가져오기 (오리진 네임과 , 경로 찾을 save파일네임)
		String origin_fileName = bfdto.getOrigin_file_nm();
		String save_fileName = bfdto.getSave_file_nm();
		System.out.println("fileName" + save_fileName);

		//파일이 실제 업로드 되어있는 (파일이 존재하는 )경로를 지정을 가져오기
		String filePath = bfdto.getSave_path();
				//"C:/JAVA02/WEB04_coreBoard/src/main/webapp/upload/";

		//경로와 파일명으로 파일 객체 생성, 폴더안엔 save_fileName 으로 지정 되어있기떄문에 save파일로 찾음
		File file = new File(filePath,save_fileName);

		//파일 데이터를 알려줌 (확장자)
		//String mimeType = getServletContext().getMimeType(file.toString());
		String mimeType = bfdto.getExt();
		if(mimeType ==null ) { //오류가 발생하지 않도록 무조건 다운로드 되도록 설정
			response.setContentType("appliction/octet-stream");  //파일주고 받을떄 필수!!!응답할 정보 데이터 받을 곳이 파일데이터인지 . 이진데이터 형식 octet-stram
		}//내가 받는 파일이구나라는 걸 알 수 있음

		//실제 다운로드 받을 네임 지정
		String downloadName =null;
		//인터넷 익스플로우 =MSIE 다운로드가 아니라 다운 브라우저라면
		if(request.getHeader("user-agent").indexOf("MSIE") == -1) {  //익스플로우가 아니라면 아니라면
			downloadName = new String(origin_fileName.getBytes("UTF-8"), "8859_1"); //바꿔줌 8859_1
		}else {
			downloadName =new String(origin_fileName.getBytes("EUC-KR"), "8859_1");
		}
		response.setHeader("Content-Disposition", "attachment;filename=\""
				+ downloadName + "\";");   //oringinName으로 다운로드 수행 setHeader

		FileInputStream fileInputStream = new FileInputStream(file);
		ServletOutputStream servletOutputStream = response.getOutputStream();

		//크기지정
		byte b[] = new byte[1024];
		int data =0;
		while((data = (fileInputStream.read(b, 0,b.length))) !=-1) {//b크기만큼 -1이 아닐떄 까지
			servletOutputStream.write(b, 0, data); //b의 크기만큼 읽어들임

		}


//		int downloadresult = bs.downloadCount(file_no);
		int downloadresult = bs.BoardDownloadCount(file_no);


		servletOutputStream.flush();
		servletOutputStream.close();
		fileInputStream.close();

	}

///////////////////////////////////////////////////////////////////

	@GetMapping(value="/replylist")
	@ResponseBody
	public ReplyDto replyList(@RequestParam(value="board_no") int board_no) {
		//댓글 조회
		List<ReplyDto> replyList = bs.replyList(board_no);
		ReplyDto rdto = new ReplyDto();
		rdto.setReplyList(replyList);

		int reply_count = bs.replyCount(board_no);
		rdto.setReply_count(reply_count);

		return rdto;
	}//
	//댓글 insert
	@PostMapping(value="/insertReply")
	@ResponseBody
	public int getboardDetail(@RequestParam(value="replycontent",required=true) String content, @RequestParam(value="reply_pw") String reply_password,
			@RequestParam(value="reply_nm") String reply_nm, @RequestParam(value="board_no" , required=true) Integer board_no) {

		System.out.println(content);
		System.out.println(reply_password);
		System.out.println(reply_nm);
		System.out.println("SDfsdf" + board_no);
		int result =0;
		ReplyDto rdto = new ReplyDto();
		rdto.setContent(content);
		rdto.setReply_password(reply_password);
		rdto.setReply_nm(reply_nm);
		rdto.setBoard_no(board_no);

		result =bs.insertReply(rdto);


		return result;
	}

	//댓글 비밀번호 확인 확인창
	@GetMapping(value="/replyPw")
	public String replyPw() {

			return "replyPw";
	}

	//댓글 비밀번호 체크
	@PostMapping(value="/replypwcheck")
	@ResponseBody
	public ReplyDto replyPwCheck( @RequestParam(value= "reply_no" ,required=true) Integer reply_no,  @RequestParam(value ="replyPw") String replyPw) {

		ReplyDto rdto = bs.getSelectReply(reply_no);

		System.out.println("댓글비밀번호 확인" + replyPw);
		System.out.println("댓글넘 확인" + reply_no);
		System.out.println(rdto.getReply_password());
		String msg ="";
		String flag ="";
		if(rdto.getReply_password().equals(replyPw)) {
			msg ="수정하시겠습니까?";
			flag ="true";
		}else if(!rdto.getReply_password().equals(replyPw)) {
			msg =" 비밀번호가 틀렸습니다";
			flag ="false";
		}else {
			msg ="관리자에게 문의하세요";
			flag ="false";
		}

		rdto.setMsg(msg);
		rdto.setFlag(flag);

		return rdto;
	}

	@GetMapping(value="/replyUpdate")
	@ResponseBody
	public int replyUpdate(@RequestParam(value= "reply_no") int reply_no, ReplyDto rdto ) {
		System.out.println(rdto);
		System.out.println(reply_no);
		int result =0;

		result=bs.updateReply(rdto);

		System.out.println(result);


		return result;
	}
	@PostMapping(value="/replyDelete")
	@ResponseBody
	public int replyDelete(@RequestParam(value= "reply_no") int reply_no) {

		int result =0;

		result=bs.deleteReply(reply_no);

		return result;
	}

}
