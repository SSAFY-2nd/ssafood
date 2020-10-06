package com.backend.service.impl;

import com.backend.dao.BhourDao;
import com.backend.dao.StoreDao;
import com.backend.dto.bhour.Bhour;
import com.backend.service.BhourService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BhourServiceImpl implements BhourService {
    @Autowired
    BhourDao dao;

    @Override
    public List<Bhour> findById(Long id) {
        return dao.findById(id);
    }
}
