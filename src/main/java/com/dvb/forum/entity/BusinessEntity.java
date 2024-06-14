package com.dvb.forum.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "business")
@PrimaryKeyJoinColumn(name = "user_id")
public class BusinessEntity extends UserEntity {

    @Column(name = "business_uuid_id")
    private UUID businessUuidId;
    @Column(name = "business_name")
    private String businessName;
    @Column(name = "contact_first_name")
    private String contactFirstName;
    @Column(name = "contact_last_name")
    private String contactLastName;
    @Column(name = "business_created_date_time")
    private LocalDateTime businessCreatedDateTime;
    @Column(name = "business_updated_date_time")
    private LocalDateTime businessUpdatedDateTime;

    @Override
    public int hashCode() {
        return getBusinessUuidId().hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof BusinessEntity))
            return false;
        return getBusinessUuidId().equals(((BusinessEntity) obj).getBusinessUuidId());
    }

}