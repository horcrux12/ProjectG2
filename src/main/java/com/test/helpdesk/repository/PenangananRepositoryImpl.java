package com.test.helpdesk.repository;

import com.test.helpdesk.model.Penanganan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

@Repository("PenangananRepository")
public class PenangananRepositoryImpl implements PenangananRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Penanganan> readData() {
        return null;
    }

    @Override
    public List<Penanganan> readDataByQuery(String query) {
        return null;
    }

    @Override
    public Penanganan findById(String id) {
        return null;
    }

    @Override
    public void createData(Penanganan tiket) {

    }

    @Override
    public void deleteDataById(Penanganan tiket) {

    }

    @Override
    public void updateData(Penanganan tiket) {

    }
}
