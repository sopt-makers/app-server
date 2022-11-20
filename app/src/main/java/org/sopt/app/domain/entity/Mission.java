package org.sopt.app.domain.entity;


import com.vladmihalcea.hibernate.type.array.ListArrayType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.List;
import org.sopt.app.presentation.mission.dto.MissionRequestDto;

@Entity
@Table(name = "mission")
@TypeDef(
    name = "list-array",
    typeClass = ListArrayType.class
)
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mission {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "mission_id")
  private Long id;

  @Column
  private String title;

  @Column(name = "level")
  private Long level;

  @Column
  private boolean display;

  @Type(type = "list-array")
  @Column(
      name = "profile_image",
      columnDefinition = "text[]"
  )
  private List<String> profileImage;
}
