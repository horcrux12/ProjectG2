package com.test.helpdesk.service;

import com.test.helpdesk.model.Penanganan;
import com.test.helpdesk.model.Tiket;
import com.test.helpdesk.model.User;

import java.util.List;
import java.util.Map;

public interface PenangananService {
    List<Penanganan> readData();
    List<Penanganan> readDataByQuery(Map<Object, Object> params);
    int countAllData();
    int countAllDataQuery(Map<Object, Object> params);
    Penanganan findById(String id);
    Penanganan findByIdTeknisi(String id);
    Tiket findDetailTiket(String idTiket, String idPenanganan);
    List<User> getUserPenanganan (String id);
    List<User> getUserPenanganan (String id, Map<Object, Object> params);
    int countUserPenanganan (String id);
    int countUserPenanganan (String id, Map<Object, Object> params);
    void createData(Penanganan penanganan);
    void deleteDataById(Penanganan penanganan);
    void deleteDetailTiket(String idTiket, String idPenanganan);
    void updateData(Penanganan penanganan);
}
