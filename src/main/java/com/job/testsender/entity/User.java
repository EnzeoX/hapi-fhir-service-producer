package com.job.testsender.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.apache.logging.log4j.message.AsynchronouslyFormattable;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User implements Serializable {

    @Id
    @GeneratedValue
    private String id;

    @Column(name = "username")
    private String username;
    @Column(name = "password")
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
