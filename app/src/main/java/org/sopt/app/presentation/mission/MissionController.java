package org.sopt.app.presentation.mission;

import lombok.AllArgsConstructor;
import org.sopt.app.application.mission.MissionService;
import org.sopt.app.presentation.BaseController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/mission")
public class MissionController extends BaseController {

  private final MissionService missionService;


  /**
   * 전체 mission 조회하기
   */
  @GetMapping(value = "/all")
  @ResponseBody
  public ResponseEntity<?> findAllMission() {
    return new ResponseEntity<>(missionService.findAllMission(), getSuccessHeaders(),
        HttpStatus.OK);
  }



  /**
   * 미션 업로드 하기
   */
//  // 게시글 작성
//  @PostMapping("/notice")
//  public ResponseEntity<?> uploadPost(@RequestPart("noticeContent") NoticeRequestDTO noticeRequestDTO,
//      @RequestPart("imgUrl") List<MultipartFile> multipartFiles) {
//
//    //MultipartFile을 리스트에 넣어줬기 때문에 List 내부의 이미지파일에 isEmpty()를 적용해야 한다.
//    int checkNum = 1;
//    for(MultipartFile image: multipartFiles){
//      if(image.isEmpty()) checkNum = 0;
//    }
//
//    if (checkNum == 0) {
//      noticeService.uploadPost(noticeRequestDTO);
//    } else {
//      List<String> imgPaths = s3Service.upload(multipartFiles);
//      noticeService.uploadPostWithImg(noticeRequestDTO, imgPaths);
//    }
//    return new ResponseEntity<>(getSuccessHeaders(), HttpStatus.OK);
//  }

}
