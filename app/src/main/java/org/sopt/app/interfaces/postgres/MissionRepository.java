package org.sopt.app.interfaces.postgres;

import org.sopt.app.domain.entity.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MissionRepository extends JpaRepository<Mission, Long> {

}
