package com.mdstech.sample.common;

import java.io.Serializable;
import java.util.List;

public abstract class AbstractServiceImpl<D extends AbstractDomain, ID extends Serializable, R extends AbstractRepository<D, ID>> implements AbstractService<D, ID, R> {
    protected R repository;

    public abstract void injectRepository(R repository);

    public R getRepository() {
        return repository;
    }

    @Override
    public D get(ID id) {
        return repository.getOne(id);
    }

    @Override
    public List<D> getAll() {
        return repository.findAll();
    }

    @Override
    public D saveOrUpdate(D d) {
        return repository.save(d);
    }

    @Override
    public void delete(ID id) {
        repository.delete(id);
    }
}
