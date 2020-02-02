package com.wenyu7980.query.entity;

import javax.persistence.*;
import java.util.List;

/**
 *
 * @author:wenyu
 * @date:2019/10/23
 */
@Entity
@Table(name = "test_info")
public class InfoEntity {
    @Id
    private String id;
    private String info;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private AddressEntity address;
    @OneToMany(mappedBy = "info")
    private List<UserEntity> users;

    public InfoEntity() {
    }

    public InfoEntity(String id, String info, AddressEntity address) {
        this.id = id;
        this.info = info;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
