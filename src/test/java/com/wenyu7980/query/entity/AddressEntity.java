package com.wenyu7980.query.entity;

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
@Table(name = "test_address")
public class AddressEntity {
    @Id
    private String id;
    private String address;
    @OneToMany(mappedBy = "address")
    private List<InfoEntity> infos;

    public AddressEntity() {
    }

    public AddressEntity(String id, String address) {
        this.id = id;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public List<InfoEntity> getInfos() {
        return infos;
    }

    public void setInfos(List<InfoEntity> infos) {
        this.infos = infos;
    }
}
