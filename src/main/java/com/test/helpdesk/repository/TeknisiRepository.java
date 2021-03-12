package com.test.helpdesk.repository;

import com.test.helpdesk.model.Teknisi;

import java.util.List;

public interface TeknisiRepository {
    List<Teknisi> readData();
    Teknisi findDataByIdUser(String id);
    Teknisi findDataById(String id);
}
