package com.mdstech.sample.common;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import com.mdstech.sample.SpringUnitTestCaseHelper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class BatchRepositoryTest extends SpringUnitTestCaseHelper {

    @Autowired
    private BatchJobRepository batchJobRepository;

    @Test
    public void testBatchJobCRUD() {
        assertThat("Expected empty collection", batchJobRepository.findAll().size(), equalTo(0));
    }
}
