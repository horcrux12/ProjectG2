package com.test.helpdesk.service;

import com.test.helpdesk.model.Penanganan;
import com.test.helpdesk.model.Tiket;
import com.test.helpdesk.repository.PenangananRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("PenangananService")
public class PenangananServiceImpl implements PenangananService{
    @Autowired
    PenangananRepository penangananRepository;


    @Override
    public List<Penanganan> readData() {
        synchronized (this){
            return penangananRepository.readData();
        }
    }

    @Override
    public List<Penanganan> readDataByQuery(Map<Object, Object> params) {
        synchronized (this){
            String query = "";
            return penangananRepository.readDataByQuery(query);
        }
    }

    @Override
    public Penanganan findById(String id) {
        synchronized (this){
            return penangananRepository.findById(id);
        }
    }

    @Override
    public void createData(Penanganan penanganan) {
        synchronized (this){
            penangananRepository.createData(penanganan);
        }
    }

    @Override
    public void deleteDataById(Penanganan penanganan) {
        synchronized (this){
            penangananRepository.deleteDataById(penanganan);
        }
    }

    @Override
    public void updateData(Penanganan penanganan) {
        synchronized (this){
            penangananRepository.updateData(penanganan);
        }
    }
}
