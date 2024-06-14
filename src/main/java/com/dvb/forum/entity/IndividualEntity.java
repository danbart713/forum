package com.dvb.forum.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "individual")
@PrimaryKeyJoinColumn(name = "user_id")
public class IndividualEntity extends UserEntity {

    @Column(name = "individual_uuid_id")
    private UUID individualUuidId;
    @Column(name = "individual_created_date_time")
    private LocalDateTime individualCreatedDateTime;
    @Column(name = "individual_updated_date_time")
    private LocalDateTime individualUpdatedDateTime;

    @Override
    public int hashCode() {
        return getIndividualUuidId().hashCode();
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj)
            return true;
        if (!(obj instanceof IndividualEntity))
            return false;
        return getIndividualUuidId().equals(((IndividualEntity) obj).getIndividualUuidId());
    }

}