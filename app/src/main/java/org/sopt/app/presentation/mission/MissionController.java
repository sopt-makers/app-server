package org.sopt.app.presentation.mission;

import java.util.List;
import lombok.AllArgsConstructor;
import org.sopt.app.application.mission.MissionService;
import org.sopt.app.common.s3.S3Service;
import org.sopt.app.domain.entity.Mission;
import org.sopt.app.presentation.BaseController;
import org.sopt.app.presentation.mission.dto.MissionRequestDto;
import org.sopt.app.presentation.mission.dto.MissionResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/mission")
public class MissionController extends BaseController {

  private final MissionService missionService;
  private final S3Service s3Service;


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
  @PostMapping()
  public ResponseEntity<?> uploadMission(
      @RequestPart("missionContent") MissionRequestDto missionRequestDto,
      @RequestPart(name = "imgUrl", required = false) List<MultipartFile> multipartFiles) {

    //MultipartFile을 리스트에 넣어줬기 때문에 List 내부의 이미지파일에 isEmpty()를 적용해야 한다.
    int checkNum = 1;
    for (MultipartFile image : multipartFiles) {
      if (image.isEmpty()) {
        checkNum = 0;
      }
    }

    MissionResponseDto result = MissionResponseDto.builder().build();
    if (checkNum == 0) {
      Mission mission = missionService.uploadMission(missionRequestDto);
      result.setMissionId(mission.getId());
    } else {
      List<String> imgPaths = s3Service.upload(multipartFiles);
      Mission uploadMissionWithImg = missionService.uploadMissionWithImg(missionRequestDto,
          imgPaths);
      result.setMissionId(uploadMissionWithImg.getId());
    }

    return new ResponseEntity<>(result, getSuccessHeaders(), HttpStatus.OK);
  }

}
