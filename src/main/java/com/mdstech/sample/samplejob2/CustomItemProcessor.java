package com.mdstech.sample.samplejob2;

import org.springframework.batch.item.ItemProcessor;

public class CustomItemProcessor<CustomerDomain> implements ItemProcessor<CustomerDomain, CustomerDomain> {
    @Override
    public CustomerDomain process(CustomerDomain customerDomain) throws Exception {
        return customerDomain;
    }
}
