package com.appopay.aml.repository;

import com.appopay.aml.entity.Agent;
import com.appopay.aml.entity.IAM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgentRepository extends JpaRepository<Agent, Long> {

}
