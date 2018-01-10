package com.mdstech.sample.samplejob2;

import com.mdstech.sample.common.AbstractDomain;
import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "CUSTOMER_LARGE")
public class CustomerDomain extends AbstractDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name="ID_CUSTOMER")
    private Integer customerId;

    @Column(name="NAME")
    private String name;

    @Column(name="HOUSE_NO")
    private Integer houseNo;

    @Column(name="ST_NAME")
    private String streetName;

    @Column(name="STATE")
    private String state;

    @Column(name="ZIP_CODE")
    private Integer zipCode;
}
