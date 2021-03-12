package com.test.helpdesk.service;

import com.test.helpdesk.model.Penanganan;
import com.test.helpdesk.model.Tiket;
import com.test.helpdesk.model.User;
import com.test.helpdesk.repository.PenangananRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

            ArrayList<String> whereQuery = new ArrayList<>();
            ArrayList<String> pageQuery = new ArrayList<>();

            if (params.containsKey("idUser") && !String.valueOf(params.get("idUser")).isBlank())
                whereQuery.add("t.idUser='"+params.get("idUser")+"'");
            if (params.containsKey("search") && !String.valueOf(params.get("search")).isBlank()){
                Object q = params.get("search");
                whereQuery.add("t.idUser LIKE '%"+q+"%' OR u.username LIKE '%"+
                        q+"%' OR u.role LIKE '%"+
                        q+"%' OR u.nama LIKE '%"+q+"%'");
            }

            if (params.containsKey("limit") && !String.valueOf(params.get("limit")).isBlank())
                pageQuery.add(" LIMIT "+params.get("limit"));
            if (params.containsKey("offset") && !String.valueOf(params.get("offset")).isBlank())
                pageQuery.add(" OFFSET "+params.get("offset"));

            if (!whereQuery.isEmpty()) query += "WHERE " + String.join(" AND ", whereQuery);
            if (!pageQuery.isEmpty()) {
                query += String.join(" ", pageQuery);
            }
            return penangananRepository.readDataByQuery(query);
        }
    }

    @Override
    public int countAllData() {
        synchronized (this){
            return penangananRepository.countAllData();
        }
    }

    @Override
    public int countAllDataQuery(Map<Object, Object> params) {
        synchronized (this){
            String query = "";

            ArrayList<String> whereQuery = new ArrayList<>();
            ArrayList<String> pageQuery = new ArrayList<>();

            if (params.containsKey("idUser") && !String.valueOf(params.get("idUser")).isBlank())
                whereQuery.add("t.idUser='"+params.get("idUser")+"'");
            if (params.containsKey("search") && !String.valueOf(params.get("search")).isBlank()){
                Object q = params.get("search");
                whereQuery.add("t.idUser LIKE '%"+q+"%' OR u.username LIKE '%"+
                        q+"%' OR u.role LIKE '%"+
                        q+"%' OR u.nama LIKE '%"+q+"%'");
            }

            if (!whereQuery.isEmpty()) query += "WHERE " + String.join(" AND ", whereQuery);

            return penangananRepository.countAllDataQuery(query);
        }
    }

    @Override
    public Penanganan findById(String id) {
        synchronized (this){
            return penangananRepository.findById(id);
        }
    }

    @Override
    public Penanganan findByIdTeknisi(String id) {
        synchronized (this){
            return penangananRepository.findByIdTeknisi(id);
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

    @Override
    public Tiket findDetailTiket(String idTiket, String idPenanganan) {
        synchronized (this){
            return penangananRepository.findDetailTiket(idTiket, idPenanganan);
        }
    }

    @Override
    public void deleteDetailTiket(String idTiket, String idPenanganan) {
        synchronized (this){
            penangananRepository.deleteDetailTiket(idTiket, idPenanganan);
        }
    }

    @Override
    public List<User> getUserPenanganan(String id) {
        synchronized (this){
            return penangananRepository.getUserPenanganan(id);
        }
    }

    @Override
    public List<User> getUserPenanganan(String id, Map<Object, Object> params) {
        synchronized (this){
            String query = "";

            ArrayList<String> whereQuery = new ArrayList<>();
            ArrayList<String> pageQuery = new ArrayList<>();

            if (params.containsKey("search") && !String.valueOf(params.get("search")).isBlank()){
                Object q = params.get("search");
                whereQuery.add("idUser LIKE '%"+q+"%' OR username LIKE '%"+
                        q+"%' OR role LIKE '%"+
                        q+"%' OR nama LIKE '%"+q+"%'");
            }
            if (params.containsKey("limit") && !String.valueOf(params.get("limit")).isBlank())
                pageQuery.add(" LIMIT "+params.get("limit"));
            if (params.containsKey("offset") && !String.valueOf(params.get("offset")).isBlank())
                pageQuery.add(" OFFSET "+params.get("offset"));

            return penangananRepository.getUserPenanganan(id, query);
        }
    }

    @Override
    public int countUserPenanganan(String id) {
        synchronized (this){
            return penangananRepository.countUserPenanganan(id);
        }
    }

    @Override
    public int countUserPenanganan(String id, Map<Object, Object> params) {
        synchronized (this){
            return 0;
        }
    }
}
