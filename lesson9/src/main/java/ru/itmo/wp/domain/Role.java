package ru.itmo.wp.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table
public class Role {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Name name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public enum Name {
        ADMIN,
        WRITER
    }
}

