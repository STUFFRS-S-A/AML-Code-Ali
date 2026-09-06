package com.example.didit.app.repository;

import com.example.didit.app.entity.UserMasterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserMasterRepository extends JpaRepository<UserMasterEntity, Long> {
    Optional<UserMasterEntity> findByUserName(String userName);

    Optional<UserMasterEntity> findByEmail(String email);

    @Query("select new com.example.didit.app.model.UserResponse(e) from UserMasterEntity e" +
            " where e.id != :superAdminUserId")

    boolean existsByUserNameAndIdNot(String userName, Long userId);

    boolean existsByEmail(String email);

//    @Query("SELECT e FROM UserMasterEntity e WHERE e.id IN :ids")
//    List<UserMasterEntity> findAllByIds(List<Long> ids);
}
