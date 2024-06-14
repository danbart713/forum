package com.dvb.forum.repository;

import com.dvb.forum.entity.IndividualEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IndividualRepository extends JpaRepository<IndividualEntity, Long> {

}