package com.mdstech.sample.common;

import org.springframework.batch.core.Job;
import org.springframework.stereotype.Repository;

@Repository
public interface BatchJobRepository extends AbstractRepository<BatchJobDomain, Integer> {
}
