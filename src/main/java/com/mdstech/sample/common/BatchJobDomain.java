package com.mdstech.sample.common;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="JOB_STATUS")
public class BatchJobDomain extends AbstractDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ID")
    private Integer id;

    @Column(name="JOB_NAME")
    private String name;
}
