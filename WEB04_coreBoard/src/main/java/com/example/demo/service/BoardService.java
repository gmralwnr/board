package com.example.demo.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dto.BoardCtgDto;
import com.example.demo.dto.BoardDto;
import com.example.demo.dto.BoardFileDto;
import com.example.demo.dto.BoardGetDto;
import com.example.demo.dto.BoardListReqDto;
import com.example.demo.dto.BoardSetDto;
import com.example.demo.dto.ReplyDto;
import com.example.demo.repository.BoardDao;

@Service
@Transactional
public class BoardService {
	@Autowired
	private BoardDao bdao;

	String file_ref_tbl_board = "bt_tb_board";
	String file_ref_key_board = "board_attach_file";

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
	@Transactional(rollbackFor = Exception.class)
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

	@Transactional(rollbackFor = Exception.class)
	public int fileUploadInsert(BoardFileDto bfdto) {

		return bdao.fileUploadInsert(bfdto);

	}

	public int fileUploadUpdate(BoardFileDto bfdto) {

		return bdao.fileUploadUpdate(bfdto);
	}

	public List<BoardFileDto> getFileOne(String ref_pk) {

		return bdao.getFileOne(ref_pk);
	}

	public BoardFileDto file_no_Select(String ref_pk) {

		return bdao.file_no_Select(ref_pk);
	}

	/**파일 삭제 */
	public int boardFileDelete(int file_no) {

		return bdao.boardFileDelete(file_no);
	}

	public BoardFileDto selectFile(int file_no) {

		return bdao.selectFile(file_no);
	}

	/**파일 다운로드 */
	public BoardFileDto fileNumSelect(int file_no) {

		return bdao.fileNumSelect(file_no);
	}
	/** 다운로드 조회수 올리기*/
	public int downloadCount(int file_no) {

		return bdao.downloadCount(file_no);
	}

	/** 전체 삭제 -파일 삭제 보드 삭제 */
	public int boardAllFileDelete(BoardGetDto bgdto) {

		return bdao.boardAllFileDelete(bgdto);
	}


	/** 게시판 상세 수정 or 등록
	 * @throws Exception */  //<--- try/catch 문 사용하기 위해 쓰여짐
	@Transactional(rollbackFor = Exception.class)
	public BoardSetDto update(BoardSetDto bsdto, MultipartFile[] uploadFile) throws Exception {

		BoardGetDto bgdto = new BoardGetDto();

		String resultMsg ="";  //BoardSetDto 에 담겨져있음
		int result =0;
		/*
		 * if(uploadFile.length==0) { resultMsg="파일 등록은 필 수 입 니 다 " }
		 */

		//**** board_no가 조회 될 때는 update 구문
		if(bsdto.getBoard_no()!=null && bsdto.getBoard_no() !=0) {//equals 쓰기


			result = this.boardUpdate(bsdto);  //update 나 insert 할 경우 성공하게 되면 1이 출력 됨 //성공한 건에 대한 개수
			System.out.println("update board_no" + bsdto.getBoard_no());

			if(result == 0) { //update 를 result 에 담아서 0이라면 실패 // if긍정적 else부정

				resultMsg ="수정 실! 패! ㅜㅜㅜ";

			}else { //수정이 완료 되면 1 이 뜰 테니 수정 성공

				resultMsg ="수정 완료입니다~~❤️";
				bsdto.setFlag("update");
			}

		//**** board_no 가 없을경우 insert 구문
		}else {

			//리절트로 선언해서 받기
			this.boardInsert(bsdto);  //->bs.boardInsert(bsdto) 였으나 서비스에서 사용하기 떄문에 this 를 사용

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

		//기존파일 삭제 후 업로드


		// 0. MultipartFile[] 로 파일이 가져왔는지 확인 !
		//System.out.println("UPLOADFILE 확인 : "+ uploadFile[0] );
		//System.out.println("UPLOADFILE 확인 : "+ uploadFile[1] );
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

			String file_board_no = Integer.toString(bsdto.getBoard_no());
			List<BoardFileDto> fileList = this.getFileOne(file_board_no);


			//List<BoardFileDto> fileList = this.getFileOne(file_board_no);

			 if(fileList.size()>=1) {
				for(int i=0; i<fileList.size(); i++)
					if(!fileList.get(i).getOrigin_file_nm().equals("")  && !uploadFile[i].getOriginalFilename().equals("")) {

						System.out.println("기존파일 삭제할거야 0번째 일때 " + "기존파일 : " + fileList.get(i).getOrigin_file_nm() + "새로운 파일 : " + uploadFile[i].getOriginalFilename() );
						System.out.println("기존파일 삭제할거야 0번쨰 파일 ");
						String save_file_nm = fileList.get(i).getSave_path() +  fileList.get(i).getSave_file_nm();
						Path filePath = Paths.get(save_file_nm);
						System.out.println("0번쨰 물리적 파일 삭제 경로 " + filePath);

							try {
								Files.delete(filePath);
								System.out.println("0번쨰 삭제완료");

							}catch (NoSuchFileException e) {
								System.out.println("삭제하려는 파일/디렉토리가 없습니다");

							}
							result = this.boardFileDelete(fileList.get(i).getFile_no());
					}
			}

/*			if(fileList.size()!=0) {
				if(!fileList.get(0).getOrigin_file_nm().equals("")  && !uploadFile[0].getOriginalFilename().equals("")) {

					System.out.println("기존파일 삭제할거야 0번째 일때 " + "기존파일 : " + fileList.get(0).getOrigin_file_nm() + "새로운 파일 : " + uploadFile[0].getOriginalFilename() );
					System.out.println("기존파일 삭제할거야 0번쨰 파일 ");
					String save_file_nm = fileList.get(0).getSave_path() +  fileList.get(0).getSave_file_nm();
					Path filePath = Paths.get(save_file_nm);
					System.out.println("0번쨰 물리적 파일 삭제 경로 " + filePath);

						try {
							Files.delete(filePath);
							System.out.println("0번쨰 삭제완료");

						}catch (NoSuchFileException e) {
							System.out.println("삭제하려는 파일/디렉토리가 없습니다");

						}
						result = this.boardFileDelete(fileList.get(0).getFile_no());
				}
			}
			if(fileList.size()>1) {
				if(!fileList.get(1).getOrigin_file_nm().equals("")  && !uploadFile[1].getOriginalFilename().equals("")) {

					String save_file_nm = fileList.get(1).getSave_path() +  fileList.get(1).getSave_file_nm();
					Path filePath = Paths.get(save_file_nm);
					System.out.println("1번쨰 물리적 파일 삭제 경로 " + filePath);

					try {
						Files.delete(filePath);
						System.out.println("1번쨰 삭제완료" + fileList.get(1).getOrigin_file_nm());

					}catch (NoSuchFileException e) {
						System.out.println("삭제하려는 파일/디렉토리가 없습니다");

					}
					result = this.boardFileDelete(fileList.get(1).getFile_no());


				}
			}


			if(fileList.size() >2) { if(!fileList.get(2).getOrigin_file_nm().equals("") && !uploadFile[2].getOriginalFilename().equals("")) {

				String save_file_nm = fileList.get(2).getSave_path() +
				fileList.get(2).getSave_file_nm(); Path filePath = Paths.get(save_file_nm);
				System.out.println("2번쨰 물리적 파일 삭제 경로 " + filePath);

				try {
					Files.delete(filePath); System.out.println("2번쨰 삭제완료" +
					fileList.get(2).getOrigin_file_nm());

				}catch (NoSuchFileException e) { System.out.println("삭제하려는 파일/디렉토리가 없습니다");

				} result = this.boardFileDelete(fileList.get(2).getFile_no());

				}


			}

*/
			//3. 업로드 파일이 3개 이상이니 반복문으로 체크 후 저장될 변수 조회
			for( int i=0; i<uploadFile.length; i++ ) { //파일 업로드 가 총 3개씩 올라가니까 length 를 줘서 반복문 돌리기

				String fileName = uploadFile[i].getOriginalFilename(); //오리진 파일 네임을 담음
				System.out.println("업로드시 확인 할 것들 ~~~~~~" + fileName);
				System.out.println("업로드한 파일 0번째" +  uploadFile[0].getOriginalFilename());
				System.out.println("업로드한 파일 1번째" +  uploadFile[1].getOriginalFilename());
				System.out.println("업로드한 파일 2번째" +  uploadFile[2].getOriginalFilename());


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
				//String file_board_no = Integer.toString(bsdto.getBoard_no());  //위에 선언
				bfdto.setRef_pk(file_board_no);
				bfdto.setRef_key(file_ref_key_board);
				//System.out.println("파일사이즈ㅜ " + bfdto.getSize() );
				bfdto.setExt(ext);
				//sql에서 사용하기 때문에 주석 - ord가 2번인 걸 삭제 하면 다음 첨부파일이 max +1를 헤줌
				//bfdto.setOrd(i +1); //0부터 시작하기 떄문에 +1을 해줌 순서 1,2,3

				System.out.println("파일 업로드 보드넘 : " + file_board_no);
				System.out.println("파일 순서 :  " + i +1);


				//fresult +=bs.fileUploadUpdate(bfdto);


				//insert
				System.out.println("file_no 확인 " +  bfdto.getFile_no());
				fresult +=	this.fileUploadInsert(bfdto);

				if(fresult > 0 ) {
					bfdto.getFile_no();
					System.out.println(bfdto.getFile_no());
					//resultMsg ="파일 등록완료";

				}else {
					resultMsg ="파일업로드 실패";
				}

			}

		} catch (Exception e){
			//e.printStackTrace();
			throw new Exception("Rollback 애러");
		}



		bsdto.setResultMsg(resultMsg);  //bsdto 에 담기 위해 BoardSetDto 필드변수 생성 해야함

		return bsdto;  //ajax 로 보내기위한 리턴
	}

	/** 파일 삭제 */
	@Transactional(rollbackFor = Exception.class)
	public int deleteBoardFile(int file_no, BoardFileDto bfdto)  throws Exception{
		int result =0;
		try {
			System.out.println("파일넘버 " + file_no);
			bfdto = this.selectFile(file_no);  //bfdto에 담아야하기때문에 get으로 가져와야해서 선언함 save_nm, save_path를 불러오기 위해서 select 함

			System.out.println("야야야야야야" + file_no);
			result = this.boardFileDelete(file_no);

//			if(bfdto.getSave_file_nm().equals("")) {
				//물리저 파일 삭제
				System.out.println(bfdto.getSave_path() + bfdto.getSave_file_nm()  );
				String save_fileName = bfdto.getSave_path() + bfdto.getSave_file_nm();
				Path filePath = Paths.get(save_fileName);
					try {

						Files.delete(filePath);
						System.out.println("삭제완료 ");

					} catch (NoSuchFileException e) {
						System.out.println("삭제하려는 파일/디렉토리가 없습니다");
						/*
						 * throw new Exception("삭제하려는 파일/디렉토리가 없습니다"); //Rollback 하기 위해서는 throw new
						 * Exception 을 사용 }catch (IOException e) { e.printStackTrace(); throw new
						 * Exception("Rollback 에러");
						 */
					}
//			}


		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception("Rollback 에러");
		}

		return result;
	}


	/** 보드 삭제(파일, 물리적파일 삭제) */
	@Transactional(rollbackFor = Exception.class)
	public int boardAllDeleteForm(BoardGetDto bgdto/*, BoardFileDto bfdto*/) throws Exception {
		int result =0;
		try {
			result = this.boardDelete(bgdto);
			System.out.println("삭제 결과 : " + result);
			System.out.println("board삭제" + bgdto);



			String ref_pk = Integer.toString(bgdto.getBoard_no());
			System.out.println("~~~~~~~~~~~~~~~~~~~~" + ref_pk);
			List<BoardFileDto> fileList = this.getFileOne(ref_pk); //bgdto에서 이미 List조회를 가지고 있음
			System.out.println("fileList" + fileList);

			BoardFileDto bfdto= null;
			if(fileList.size() > 0) {
				//bgdto.setFileList(fileList);  //파일을 List로 가져오는 경우는 하나의 board_no 에 파일이 3개까지 들어있으니 List로 가져와서 boardGetDto 에 담아준다
				//물리적파일 삭제도 하기

				result = this.boardAllFileDelete(bgdto); //삭제 개수에따라 값이 변동

				System.out.println("삭제 결과 : " + result);

				for(int i=0; i<=fileList.size()-1; i++) {//size는 1부터 이고 for문에 반복은 0이므로 -1로 주고 시작
					String save_file_nm = fileList.get(i).getSave_path() +  fileList.get(i).getSave_file_nm();
					Path filePath = Paths.get(save_file_nm);
					System.out.println("물리적 파일 삭제 경로 " + filePath);

					try {
						Files.delete(filePath);
						System.out.println("삭제완료");
					}catch (NoSuchFileException e) {
						System.out.println("삭제하려는 파일/디렉토리가 없습니다");

					}
				}
			}
		} catch (Exception e){
			e.printStackTrace();  //오류 메세지 보는법
			throw new Exception("Rollback 애러");
		}

		return result;
	}

	/** 파일다운로드 */
	@Transactional(rollbackFor = Exception.class)
	public int BoardDownloadCount(int file_no) throws Exception {
		try {

			int downloadresult = this.downloadCount(file_no);

		}catch (Exception e){
			e.printStackTrace();  //오류 메세지 보는법
			throw new Exception("Rollback 애러");
		}

		return 0;
	}

	public List<ReplyDto> replyList(Integer board_no) {

		return bdao.replyList(board_no);
	}

	public int insertReply(ReplyDto rdto) {

		return bdao.insertReply(rdto);
	}

	public ReplyDto getSelectReply(int reply_no) {

		return bdao.getSelectReply(reply_no);
	}

	public int updateReply(ReplyDto rdto) {

		return bdao.updateReply(rdto);
	}

	public int deleteReply(int reply_no) {

		return bdao.deleteReply(reply_no);
	}

	public int replyCount(int board_no) {

		return bdao.replyCount(board_no);
	}







}
