package com.dvb.forum.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "email")
public class EmailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "uuid_id")
    private UUID uuidId;
    @Column(name = "email_address")
    private String emailAddress;
    @Column(name = "is_registered")
    private boolean registered;
    @Column(name = "is_two_factor_authentication_enabled")
    private boolean twoFactorAuthenticationEnabled;
    @OneToOne(mappedBy = "emailEntity", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnore
    private UserEntity userEntity;
    @Version
    @Column(name = "optimistic_locking_version")
    private Long optimisticLockingVersion;
    @Column(name = "created_date_time")
    private LocalDateTime createdDateTime;
    @Column(name = "updated_date_time")
    private LocalDateTime updatedDateTime;

    @Override
    public int hashCode() {
        return getUuidId().hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof EmailEntity))
            return false;
        return getUuidId().equals(((EmailEntity) obj).getUuidId());
    }

}