package org.sopt.app.presentation.stamp;

import com.fasterxml.jackson.databind.ser.Serializers.Base;
import java.util.List;
import lombok.AllArgsConstructor;
import org.sopt.app.application.stamp.StampService;
import org.sopt.app.common.s3.S3Service;
import org.sopt.app.domain.entity.Mission;
import org.sopt.app.domain.entity.Stamp;
import org.sopt.app.presentation.BaseController;
import org.sopt.app.presentation.mission.dto.MissionRequestDto;
import org.sopt.app.presentation.stamp.dto.StampRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/stamp")
public class StampController extends BaseController {

  private final StampService stampService;

  private final S3Service s3Service;


  /**
   * missionId, userId로 stamp 조회하기
   */
  @GetMapping("/{missionId}")
  public Stamp findStampByMissionAndUserId(
      @RequestHeader("userId") String userId,
      @PathVariable Long missionId) {

    return stampService.findStamp(userId, missionId);
  }

  /**
   * 미션 달성을 위해 내용을 Stamp 내용 등록
   */
  @PostMapping("/{missionId}")
  public ResponseEntity<?> uploadStamp(
      @RequestHeader("userId") String userId,
      @PathVariable Long missionId,
      @RequestPart("stampContent") StampRequestDto stampRequestDto,
      @RequestPart(name = "imgUrl", required = false) List<MultipartFile> multipartFiles
  ) {

//    //MultipartFile을 리스트에 넣어줬기 때문에 List 내부의 이미지파일에 isEmpty()를 적용해야 한다.
//    int checkNum = 1;
//    for (MultipartFile image : multipartFiles) {
//      if (image.isEmpty()) {
//        checkNum = 0;
//      }
//    }

    List<String> imgPaths = s3Service.upload(multipartFiles);
    Stamp uploadStamp = stampService.uploadStamp(stampRequestDto,
        imgPaths, userId, missionId);

//      result.setMissionId(uploadMissionWithImg.getId());
    return new ResponseEntity<>(uploadStamp, getSuccessHeaders(), HttpStatus.OK);
  }

}
