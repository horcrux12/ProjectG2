package com.test.helpdesk.repository;

import com.test.helpdesk.model.Penanganan;
import com.test.helpdesk.model.Tiket;
import com.test.helpdesk.model.User;

import java.util.List;

public interface PenangananRepository {
    List<Penanganan> readData();
    List<Penanganan> readDataByQuery(String query);
    Tiket findDetailTiket(String idTiket, String idPenanganan);
    Penanganan findByIdTeknisi(String id);
    Penanganan findById(String id);
    List<User> getUserPenanganan (String id);
    List<User> getUserPenanganan (String id, String query);
    int countUserPenanganan (String id);
    int countUserPenanganan (String id, String query);
    int countAllData();
    int countAllDataQuery(String query);
    void createData(Penanganan penanganan);
    void deleteDataById(Penanganan penanganan);
    void deleteDetailTiket(String idTiket, String idPenanganan);
    void updateData(Penanganan penanganan);
}
