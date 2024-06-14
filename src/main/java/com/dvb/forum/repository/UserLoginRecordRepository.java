package com.dvb.forum.repository;

import com.dvb.forum.entity.UserLoginRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoginRecordRepository extends JpaRepository<UserLoginRecordEntity, Long> {

}