package com.kdu.smartHome.entity;

import java.time.Instant;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Base entity with audit timestamps.
 */

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Column(name = "created_date", nullable = false, updatable = false)
    private Instant createdDate;

    @Column(name = "modified_date", nullable = false)
    private Instant modifiedDate;

    @Column(name = "deleted_date")
    private Instant deletedDate;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.createdDate = now;
        this.modifiedDate = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.modifiedDate = Instant.now();
    }

}
