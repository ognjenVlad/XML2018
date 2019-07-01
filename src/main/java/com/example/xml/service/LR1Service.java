package com.example.xml.service;

import com.example.xml.dtos.LR1DTO;

public interface LR1Service {

    void save(LR1DTO lr1, String id);

    LR1DTO findById(String id);
}
