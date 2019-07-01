package com.example.xml.service;

import com.example.xml.dtos.LR1DTO;
import com.example.xml.repository.DocumentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LR1ServiceImpl implements LR1Service {

    @Autowired
    private DocumentRepository documentRepository;

    public void save(LR1DTO lr1, String id){
        try {
            documentRepository.save(lr1, id);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public LR1DTO findById(String id) {
        try {
            return documentRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



}
