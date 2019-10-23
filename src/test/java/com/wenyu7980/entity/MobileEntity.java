package com.wenyu7980.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

/**
 *
 * @author:wenyu
 * @date:2019/10/23
 */

@Entity
@Table(name = "test_mobile")
public class MobileEntity {
    @Id
    private String id;
    private String mobile;
    @OneToMany(mappedBy = "mobile")
    private List<UserEntity> users;

    public MobileEntity() {
    }

    public MobileEntity(String id, String mobile) {
        this.id = id;
        this.mobile = mobile;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(List<UserEntity> users) {
        this.users = users;
    }
}
