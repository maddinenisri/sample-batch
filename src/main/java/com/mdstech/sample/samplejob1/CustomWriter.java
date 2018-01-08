package com.mdstech.sample.samplejob1;

import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

public class CustomWriter extends JpaItemWriter<TransactionDomain> {

    @Override
    @Transactional
    protected void doWrite(EntityManager entityManager, List<? extends TransactionDomain> items) {
        for(TransactionDomain transactionDomain : items) {
            entityManager
                    .persist(transactionDomain);
        }
        entityManager.flush();
        entityManager.clear();
    }
}
