package org.sopt.app.application.mission;

import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.sopt.app.domain.entity.Mission;
import org.sopt.app.interfaces.postgres.MissionRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MissionService {

  private final MissionRepository missionRepository;
//  private final EntityManager entityManager;


  public List<Mission> findAllMission(){
    return missionRepository.findAll();
  }

}
