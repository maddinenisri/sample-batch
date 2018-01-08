package com.mdstech.sample.common;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@MappedSuperclass
@Data
public class AbstractDomain {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DT_CREATED")
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DT_LST_UPDT")
    private Date lastUpdatedDate;
}
