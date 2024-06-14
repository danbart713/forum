package com.dvb.forum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "token_blacklist")
public class TokenBlacklistEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "uuid_id")
    private UUID uuidId;
    @Column(name = "token")
    private String token;
    @Column(name = "token_expiration_date_time")
    private LocalDateTime tokenExpirationDateTime;
    @Column(name = "token_blacklisted_date_time")
    private LocalDateTime tokenBlacklistedDateTime;
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
        if (!(obj instanceof TokenBlacklistEntity))
            return false;
        return getUuidId().equals(((TokenBlacklistEntity) obj).getUuidId());
    }

}
