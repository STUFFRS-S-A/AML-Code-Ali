package com.appopay.visa.repository;

import com.appopay.visa.entity.IamEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface IamRepository extends JpaRepository<IamEntity, Long> {
    Optional<IamEntity> findByUserName(String userName);

}
