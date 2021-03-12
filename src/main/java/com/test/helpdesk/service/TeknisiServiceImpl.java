package com.test.helpdesk.service;

import com.test.helpdesk.model.Teknisi;
import com.test.helpdesk.repository.TeknisiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("TeknisiService")
public class TeknisiServiceImpl implements TeknisiService{
    @Autowired
    TeknisiRepository teknisiRepository;

    @Override
    public List<Teknisi> readData() {
        synchronized (this) {
            return teknisiRepository.readData();
        }
    }

    @Override
    public Teknisi findDataByIdUser(String id) {
        synchronized (this) {
            return teknisiRepository.findDataByIdUser(id);
        }
    }

    @Override
    public Teknisi findDataById(String id) {
        synchronized (this) {
            return teknisiRepository.findDataById(id);
        }
    }
}
