package com.dvb.forum.entity;

import com.dvb.forum.enums.UserRoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "user")
@Inheritance(strategy = InheritanceType.JOINED)
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_uuid_id")
    private UUID userUuidId;
    @Column(name = "display_name")
    private String displayName;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "email_id")
    private EmailEntity emailEntity;
    @Column(name = "password")
    @JsonIgnore
    private String password;
    @Column(name = "user_role")
    private UserRoleEnum userRole;
    @Column(name = "registration_ip_address")
    private String registrationIpAddress;
    @Column(name = "last_login_date_time")
    private LocalDateTime lastLoginDateTime;
    @Column(name = "is_user_suspended")
    private boolean userSuspended;
    @Column(name = "is_user_banned")
    private boolean userBanned;
    @Column(name = "is_password_expired")
    private boolean passwordExpired;
    @Column(name = "is_user_locked")
    private boolean userLocked;
    @Column(name = "is_user_deleted")
    private boolean userDeleted;
    @OneToMany(mappedBy = "userEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<UserLoginRecordEntity> userLoginRecordEntityList;
    @Version
    @Column(name = "optimistic_locking_version")
    private Long optimisticLockingVersion;
    @Column(name = "user_created_date_time")
    private LocalDateTime userCreatedDateTime;
    @Column(name = "user_updated_date_time")
    private LocalDateTime userUpdatedDateTime;

    @Override
    public int hashCode() {
        return getUserUuidId().hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof UserEntity))
            return false;
        return getUserUuidId().equals(((UserEntity) obj).getUserUuidId());
    }

}