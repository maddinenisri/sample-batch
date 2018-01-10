package com.mdstech.sample.samplejob2;

import com.mdstech.sample.SpringUnitTestCaseHelper;
import com.mdstech.sample.samplejob1.TransactionDomain;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class SampleJob2Test extends SpringUnitTestCaseHelper {

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    @Qualifier("readFiles")
    private Job job;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void testSampleJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        JobExecution jobExecution = jobLauncher.run(job, new JobParameters());
        System.out.println(jobExecution.getStatus());
        System.out.println("Completed");

        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
        countQuery.select(criteriaBuilder.count(countQuery.from(CustomerDomain.class)));
        Long count = entityManager.createQuery(countQuery) .getSingleResult();

        assertThat("Expected nearly 2.5 million", count, equalTo(2498925L));
    }

}
