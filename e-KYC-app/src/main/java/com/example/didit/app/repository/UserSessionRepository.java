package com.example.didit.app.repository;

import com.example.didit.app.entity.UserSessionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSessionEntity, Long> {

    Optional<UserSessionEntity> findByWorkflowId(String workflowId);

    List<UserSessionEntity> findByUserId(Long userId);
}
