package com.dvb.forum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "user_id")
public class AdminEntity extends UserEntity {

    @Column(name = "admin_uuid_id")
    private UUID adminUuidId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "is_super_admin")
    private boolean superAdmin;
    @Column(name = "admin_created_date_time")
    private LocalDateTime adminCreatedDateTime;
    @Column(name = "admin_updated_date_time")
    private LocalDateTime adminUpdatedDateTime;

    @Override
    public int hashCode() {
        return getAdminUuidId().hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof AdminEntity))
            return false;
        return getAdminUuidId().equals(((AdminEntity) obj).getAdminUuidId());
    }

}