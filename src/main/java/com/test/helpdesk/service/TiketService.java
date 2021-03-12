package com.test.helpdesk.service;

import com.test.helpdesk.model.Tiket;

import java.util.List;
import java.util.Map;

public interface TiketService {
    List<Tiket> readData();
    List<Tiket> readDataByQuery(Map<Object, Object> params);
    int countAllData();
    int countAllDataQery(Map<Object, Object> params);
    Tiket findById(String id);
    void createData(Tiket tiket);
    void deleteDataById(Tiket tiket);
    void updateData(Tiket tiket);
}
