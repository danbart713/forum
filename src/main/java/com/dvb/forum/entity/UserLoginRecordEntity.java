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
@Table(name = "user_login_record")
public class UserLoginRecordEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "uuid_id")
    private UUID uuidId;
    @Column(name = "ip_address")
    private String ipAddress;
    @Column(name = "login_date_time")
    private LocalDateTime loginDateTime;
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "user_id")
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
        if (!(obj instanceof UserLoginRecordEntity))
            return false;
        return getUuidId().equals(((UserLoginRecordEntity) obj).getUuidId());
    }

}