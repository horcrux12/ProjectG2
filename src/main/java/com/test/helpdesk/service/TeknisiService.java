package com.test.helpdesk.service;

import com.test.helpdesk.model.Teknisi;

import java.util.List;

public interface TeknisiService {
    List<Teknisi> readData();
    Teknisi findDataByIdUser(String id);
    Teknisi findDataById(String id);
}
