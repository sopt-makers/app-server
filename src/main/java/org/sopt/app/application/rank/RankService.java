package org.sopt.app.application.rank;

import lombok.RequiredArgsConstructor;
import org.sopt.app.application.mission.MissionService;
import org.sopt.app.common.exception.ApiException;
import org.sopt.app.domain.entity.User;
import org.sopt.app.interfaces.postgres.UserRepository;
import org.sopt.app.presentation.rank.dto.FindAllRanksResponseDto;
import org.sopt.app.presentation.rank.dto.FindRankResponseDto;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static org.sopt.app.common.ResponseCode.INVALID_RESPONSE;

@Service
@RequiredArgsConstructor
public class RankService {

    private final UserRepository userRepository;

    private final MissionService missionService;

    //User 한마디 등록하기
    public User updateProfileMessage(Long userId, String profileMessage) {

        User user = userRepository.findUserById(userId)
                .orElseThrow(() -> new ApiException(INVALID_RESPONSE));

        user.updateProfileMessage(profileMessage);
        return userRepository.save(user);
    }

    public List<FindAllRanksResponseDto> findRanks(){
      List<User> users = userRepository.findAll();
      AtomicInteger rankPoint = new AtomicInteger(1);
      return users.stream().sorted(
              Comparator.comparing(User::getPoints).reversed())
              .map(user -> FindAllRanksResponseDto.builder()
                      .rank(rankPoint.getAndIncrement())
                      .userId(user.getId())
                      .nickname(user.getNickname())
                      .point(user.getPoints())
                      .profileMessage(user.getProfileMessage())
                      .build())
              .collect(Collectors.toList());
    }

    public FindRankResponseDto findRankById(Long userId) {
        User user = userRepository.findUserById(userId).orElseThrow(() -> new ApiException(INVALID_RESPONSE));
        return FindRankResponseDto.builder()
                .userId(userId)
                .nickname(user.getNickname())
                .profileMessage(user.getProfileMessage())
                .userMissions(missionService.getCompleteMission(String.valueOf(userId)))
                .build();
    }

}
