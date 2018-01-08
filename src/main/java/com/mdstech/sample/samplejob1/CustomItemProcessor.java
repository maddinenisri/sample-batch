package com.mdstech.sample.samplejob1;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor implements ItemProcessor<TransactionDomain, TransactionDomain> {

    public TransactionDomain process(TransactionDomain item) {
        return item;
    }
}
