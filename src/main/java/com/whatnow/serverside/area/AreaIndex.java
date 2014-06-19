package com.whatnow.serverside.area;

import com.eaio.uuid.UUID;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "areaindex")
public class AreaIndex implements Serializable {

    private UUID areaid;
    private String username;

    public AreaIndex() {
        // this form used by Hibernate
    }

    @Id
    @Column(name = "areaid")
    public UUID getAreaid() {
        return areaid;
    }

    public void setAreaid(UUID area) {
        this.areaid = area;
    }

    /**
     * @return the username
     */
    @Column(name = "username")
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
