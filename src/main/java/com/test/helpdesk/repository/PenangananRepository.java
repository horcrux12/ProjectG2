package com.test.helpdesk.repository;

import com.test.helpdesk.model.Penanganan;
import com.test.helpdesk.model.Tiket;

import java.util.List;

public interface PenangananRepository {
    List<Penanganan> readData();
    List<Penanganan> readDataByQuery(String query);
    Penanganan findById(String id);
    void createData(Penanganan tiket);
    void deleteDataById(Penanganan tiket);
    void updateData(Penanganan tiket);
}
