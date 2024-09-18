package com.coding24.personalblog.service.impl;

import com.coding24.personalblog.entity.Sort;
import com.coding24.personalblog.mapper.SortMapper;
import com.coding24.personalblog.service.SortService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SortServiceImpl implements SortService {
    @Autowired
    private SortMapper sortMapper;

    @Override
    public List<Sort> getAllSort() {
        return sortMapper.getAllSort();
    }
}
