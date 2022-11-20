package org.sopt.app.presentation.mission.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MissionRequestDto {

  private String title; // 미션 제목
  private Long level; // 미션 레벨

}
