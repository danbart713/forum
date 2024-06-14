package com.dvb.forum.repository;

import com.dvb.forum.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByDisplayName(String displayName);

    @Query("SELECT user FROM UserEntity user JOIN FETCH user.emailEntity email WHERE email.emailAddress = :emailAddress")
    Optional<UserEntity> findByEmailAddress(@Param("emailAddress") String emailAddress);

}