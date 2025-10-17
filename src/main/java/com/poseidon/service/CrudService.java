package com.poseidon.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CrudService<REQ, RES, ID> {
    RES create(REQ dto);
    Page<RES> findAll(Pageable pageable);
    RES findById(ID id);
    RES update(ID id, REQ dto);
    void delete(ID id);
}
