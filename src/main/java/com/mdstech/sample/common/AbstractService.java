package com.mdstech.sample.common;

import java.io.Serializable;
import java.util.List;

public interface AbstractService<D extends AbstractDomain, ID extends Serializable, R extends AbstractRepository<D, ID>> {
    D get(ID id);
    List<D> getAll();
    D saveOrUpdate(D d);
    void delete(ID id);
}
