package com.mdstech.sample.common;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface AbstractRepository<D extends AbstractDomain, ID extends Serializable> extends JpaRepository<D, ID> {

}
