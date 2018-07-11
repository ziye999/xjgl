package com.hy.domain;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "user",catalog = "ssh")
public class User implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id",unique = true)
    private Integer id;
    @Column(name = "user",length = 10)
    private String user;
    public User() {
    }

    public User(Integer id,String user) {
        this.user = user;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getUser() {
        return user;
    }
    public void setUser(String user) {
        this.user = user;
    }
}
