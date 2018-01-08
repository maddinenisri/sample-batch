package com.mdstech.sample.samplejob1;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;
import com.mdstech.sample.SpringUnitTestCaseHelper;
import org.junit.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

public class SampleJobTest extends SpringUnitTestCaseHelper {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("firstBatchJob")
    private Job job;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testSampleJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobExecution  jobExecution = jobLauncher.run(job, new JobParameters());
        System.out.println(jobExecution.getStatus());
        System.out.println("Completed");
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TransactionDomain> criteriaQuery = criteriaBuilder.createQuery(TransactionDomain.class);
        Root<TransactionDomain> rootQuery = criteriaQuery.from(TransactionDomain.class);
        CriteriaQuery<TransactionDomain> all = criteriaQuery.select(rootQuery);
        TypedQuery<TransactionDomain> allQuery = entityManager.createQuery(all);
        assertThat("Expected 3 records", allQuery.getResultList().size(), equalTo(3));
    }
}
