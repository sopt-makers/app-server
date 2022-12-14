package org.sopt.app.presentation.stamp;

import lombok.AllArgsConstructor;
import org.sopt.app.application.stamp.StampService;
import org.sopt.app.common.s3.S3Service;
import org.sopt.app.domain.entity.Stamp;
import org.sopt.app.presentation.BaseController;
import org.sopt.app.presentation.stamp.dto.StampRequestDto;
import org.sopt.app.presentation.stamp.dto.StampResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/stamp")
public class StampController extends BaseController {

  private final StampService stampService;

  private final S3Service s3Service;


  /**
   * stamp 조회 missionId, userId로 stamp 조회하기
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

    //스탬프 중복 검사체크
    stampService.checkDuplicateStamp(userId, missionId);

    List<String> imgPaths = s3Service.upload(multipartFiles);
    Stamp uploadStamp = stampService.uploadStamp(stampRequestDto,
        imgPaths, userId, missionId);

    return new ResponseEntity<>(uploadStamp, getSuccessHeaders(), HttpStatus.OK);
  }


  @PutMapping("/{missionId}")
  public ResponseEntity<?> editStamp(
      @RequestHeader("userId") String userId,
      @PathVariable Long missionId,
      @RequestPart(value = "stampContent", required = false) StampRequestDto stampRequestDto,
      @RequestPart(name = "imgUrl", required = false) List<MultipartFile> multipartFiles

  ) {
    //MultipartFile을 리스트에 넣어줬기 때문에 List 내부의 이미지파일에 isEmpty()를 적용해야 한다.
    int checkNum = 1;
    for (MultipartFile image : multipartFiles) {
      if (image.isEmpty()) {
        checkNum = 0;
      }
    }

    StampResponseDto result = StampResponseDto.builder().build();
    if (checkNum == 0) {

      Stamp stamp = stampService.editStampContents(stampRequestDto, userId, missionId);
      result.setStampId(stamp.getId());

    } else {

      List<String> imgPaths = s3Service.upload(multipartFiles);
      Stamp uploadStamp = stampService.editStampWithImg(stampRequestDto,
          imgPaths, userId, missionId);

      result.setStampId(uploadStamp.getId());
    }
    return new ResponseEntity<>(result, getSuccessHeaders(), HttpStatus.OK);
  }

  /**
   * Stamp 개별 삭제
   */
  @DeleteMapping("/{stampId}")
  public ResponseEntity<?> deleteStampById(@PathVariable Long stampId) {
    stampService.deleteByStampId(stampId);
    return new ResponseEntity<>("{}", getSuccessHeaders(), HttpStatus.OK);
  }

  /**
   * 전체 Stamp삭제
   */
  @DeleteMapping("/all")
  public ResponseEntity<?> deleteStampByUserId(@RequestHeader Long userId){
    stampService.deleteStampByUserId(userId);
    return new ResponseEntity<>("{}", getSuccessHeaders(), HttpStatus.OK);
  }
}
