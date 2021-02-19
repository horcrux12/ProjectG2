package com.test.helpdesk.service;

import com.test.helpdesk.model.Penanganan;
import com.test.helpdesk.model.Tiket;

import java.util.List;
import java.util.Map;

public interface PenangananService {
    List<Penanganan> readData();
    List<Penanganan> readDataByQuery(Map<Object, Object> params);
    Penanganan findById(String id);
    void createData(Penanganan penanganan);
    void deleteDataById(Penanganan penanganan);
    void updateData(Penanganan penanganan);
}
