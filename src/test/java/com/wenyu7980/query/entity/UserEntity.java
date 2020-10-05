package com.wenyu7980.query.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author:wenyu
 * @date:2019/10/23
 */

@Entity
@Table(name = "test_user")
public class UserEntity {
    @Id
    private String id;
    private String username;
    @ManyToOne
    @JoinColumn(name = "info_id")
    private InfoEntity info;
    @ManyToOne
    @JoinColumn(name = "mobile_id")
    private MobileEntity mobile;
    @ManyToMany
    @JoinTable(name = "test_label_user", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "label_id"))
    private List<LabelEntity> labels;

    public UserEntity() {
    }

    public UserEntity(String id, String username, InfoEntity info,
            MobileEntity mobile, Collection<LabelEntity> labels) {
        this.id = id;
        this.username = username;
        this.info = info;
        this.mobile = mobile;
        this.labels = new ArrayList<>(labels);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public InfoEntity getInfo() {
        return info;
    }

    public void setInfo(InfoEntity info) {
        this.info = info;
    }

    public MobileEntity getMobile() {
        return mobile;
    }

    public void setMobile(MobileEntity mobile) {
        this.mobile = mobile;
    }

}
