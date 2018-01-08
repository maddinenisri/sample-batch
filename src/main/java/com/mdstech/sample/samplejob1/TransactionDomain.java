package com.mdstech.sample.samplejob1;

import com.mdstech.sample.common.AbstractDomain;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(name = "TRN_DATA")
public class TransactionDomain extends AbstractDomain {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String username;
    private Integer userId;
    private Date transactionDate;
    private double amount;
}

