package com.whatnow.serverside.area;

import com.eaio.uuid.UUID;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "processingarea")
public class ProcessingArea implements Serializable {

    private UUID areaid;
    private int sequenceid;
    private String area;

    public ProcessingArea() {
        // this form used by Hibernate
    }

    @Id
    @Column(name = "areaid")
    public UUID getAreaid() {
        return areaid;
    }

    public void setAreaid(UUID areaid) {
        this.areaid = areaid;
    }

    @Id
    @Column(name = "sequenceid")
    public int getSequenceid() {
        return sequenceid;
    }

    public void setSequenceid(int sequenceid) {
        this.sequenceid = sequenceid;
    }

    @Column(name = "area")
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

}
