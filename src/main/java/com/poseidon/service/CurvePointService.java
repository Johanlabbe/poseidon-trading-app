package com.poseidon.service;

import com.poseidon.dto.curve.CurvePointRequest;
import com.poseidon.dto.curve.CurvePointResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CurvePointService {
    CurvePointResponse create(CurvePointRequest dto);
    Page<CurvePointResponse> findAll(Pageable pageable);
    CurvePointResponse findById(Integer id);
    CurvePointResponse update(Integer id, CurvePointRequest dto);
    void delete(Integer id);
}
