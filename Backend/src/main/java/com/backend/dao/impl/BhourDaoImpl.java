package com.backend.dao.impl;

import com.backend.dao.BhourDao;
import com.backend.dto.bhour.Bhour;
import com.backend.mapper.BhourMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BhourDaoImpl implements BhourDao {

    @Autowired
    BhourMapper mapper;

    @Override
    public List<Bhour> findById(Long id) {
        return mapper.findById(id);
    }
}
