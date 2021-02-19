package com.test.helpdesk.repository;

import com.test.helpdesk.model.Tiket;

import java.util.List;
import java.util.Map;

public interface TiketRepository {
    List<Tiket> readData();
    List<Tiket> readDataByQuery(String query);
    Tiket findById(String id);
    void createData(Tiket tiket);
    void deleteDataById(Tiket tiket);
    void updateData(Tiket tiket);
}
