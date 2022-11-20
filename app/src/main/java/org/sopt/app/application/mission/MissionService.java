package org.sopt.app.application.mission;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.sopt.app.domain.entity.Mission;
import org.sopt.app.interfaces.postgres.MissionRepository;
import org.sopt.app.presentation.mission.dto.MissionRequestDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MissionService {

  private final MissionRepository missionRepository;
//  private final EntityManager entityManager;


  public List<Mission> findAllMission(){
    return missionRepository.findAll();
  }


  // 게시글 작성 - 이미지 미포함
  @Transactional
  public Mission uploadMission(MissionRequestDto missionRequestDto) {
    Mission mission = this.convertMission(missionRequestDto);
    return missionRepository.save(mission);
  }

  // 게시글 작성 -  이미지 포함
  @Transactional
  public Mission uploadMissionWithImg(MissionRequestDto missionRequestDto, List<String> imgPaths) {

    List<String> imgList = new ArrayList<>(imgPaths);
    Mission mission = this.convertMissionImg(missionRequestDto, imgList);
    return missionRepository.save(mission);

  }





  //Mission Entity 양식에 맞게 데이터 세팅
  private Mission convertMissionImg(MissionRequestDto missionRequestDto, List<String> imgList) {
    return Mission.builder()
        .title(missionRequestDto.getTitle())
        .level(missionRequestDto.getLevel())
        .display(true)
        .profileImage(imgList)
        .build();
  }

  private Mission convertMission(MissionRequestDto missionRequestDto) {

    return Mission.builder()
        .title(missionRequestDto.getTitle())
        .level(missionRequestDto.getLevel())
        .display(true)
        .build();
  }

}
