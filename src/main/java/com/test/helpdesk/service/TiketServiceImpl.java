package com.test.helpdesk.service;

import com.test.helpdesk.model.Tiket;
import com.test.helpdesk.repository.TiketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("TiketService")
public class TiketServiceImpl implements TiketService{
    @Autowired
    TiketRepository tiketRepository;

    @Override
    public List<Tiket> readData() {
        synchronized (this){
            return tiketRepository.readData();
        }
    }

    @Override
    public int countAllData() {
        synchronized (this){
            return tiketRepository.countAllData();
        }
    }

    @Override
    public int countAllDataQery(Map<Object, Object> params) {
        synchronized (this){
            String query = "";

            ArrayList<String> whereQuery = new ArrayList<>();

            if (params.containsKey("idTiket") && !String.valueOf(params.get("idTiket")).isBlank())
                whereQuery.add("t.idTiket='"+params.get("idTiket")+"'");
            if (params.containsKey("idUser") && !String.valueOf(params.get("idUser")).isBlank())
                whereQuery.add("u.idUser='"+params.get("idUser")+"'");
            if (params.containsKey("status") && !String.valueOf(params.get("status")).isBlank())
                whereQuery.add("t.status='"+params.get("status")+"'");
            if (params.containsKey("problemDesc") && !String.valueOf(params.get("problemDesc")).isBlank())
                whereQuery.add("t.problemDesc='"+params.get("problemDesc")+"'");
            if (params.containsKey("createdDate") && !String.valueOf(params.get("createdDate")).isBlank())
                whereQuery.add("t.createdDate='"+params.get("createdDate")+"'");
            if (params.containsKey("updatedDate") && !String.valueOf(params.get("updatedDate")).isBlank())
                whereQuery.add("t.updatedDate='"+params.get("updatedDate")+"'");
            if (params.containsKey("search") && !String.valueOf(params.get("search")).isBlank()){
                Object q = params.get("search");
                whereQuery.add("u.idUser LIKE '%"+q+"%'" +
                        " OR t.status LIKE '%"+ q+ "%'" +
                        " OR t.problemDesc LIKE '%"+q+"%'" +
                        " OR t.createdDate LIKE '%"+q+"%'" +
                        " OR t.updatedDate LIKE '%"+q+"%'");
            }
            if (!whereQuery.isEmpty()) query += "WHERE " + String.join(" AND ", whereQuery);

            return tiketRepository.countAllDataQery(query);
        }
    }

    @Override
    public List<Tiket> readDataByQuery(Map<Object, Object> params) {
        synchronized (this){
            String query = "";

            ArrayList<String> whereQuery = new ArrayList<>();
            ArrayList<String> pageQuery = new ArrayList<>();

            if (params.containsKey("idTiket") && !String.valueOf(params.get("idTiket")).isBlank())
                whereQuery.add("t.idTiket='"+params.get("idTiket")+"'");
            if (params.containsKey("idUser") && !String.valueOf(params.get("idUser")).isBlank())
                whereQuery.add("u.idUser='"+params.get("idUser")+"'");
            if (params.containsKey("status") && !String.valueOf(params.get("status")).isBlank())
                whereQuery.add("t.status='"+params.get("status")+"'");
            if (params.containsKey("problemDesc") && !String.valueOf(params.get("problemDesc")).isBlank())
                whereQuery.add("t.problemDesc='"+params.get("problemDesc")+"'");
            if (params.containsKey("createdDate") && !String.valueOf(params.get("createdDate")).isBlank())
                whereQuery.add("t.createdDate='"+params.get("createdDate")+"'");
            if (params.containsKey("updatedDate") && !String.valueOf(params.get("updatedDate")).isBlank())
                whereQuery.add("t.updatedDate='"+params.get("updatedDate")+"'");
            if (params.containsKey("search") && !String.valueOf(params.get("search")).isBlank()){
                Object q = params.get("search");
                whereQuery.add("u.idUser LIKE '%"+q+"%'" +
                        " OR t.status LIKE '%"+ q+ "%'" +
                        " OR t.problemDesc LIKE '%"+q+"%'" +
                        " OR t.createdDate LIKE '%"+q+"%'" +
                        " OR t.updatedDate LIKE '%"+q+"%'");
            }
            if (params.containsKey("limit") && !String.valueOf(params.get("limit")).isBlank())
                pageQuery.add(" LIMIT "+params.get("limit"));
            if (params.containsKey("offset") && !String.valueOf(params.get("offset")).isBlank())
                pageQuery.add(" OFFSET "+params.get("offset"));


            if (!whereQuery.isEmpty()) query += "WHERE " + String.join(" AND ", whereQuery);
            query += "ORDER BY t.createdDate ASC";
            if (!pageQuery.isEmpty()) query += String.join(" ",pageQuery);

            return tiketRepository.readDataByQuery(query);
        }
    }

    @Override
    public Tiket findById(String id) {
        synchronized (this){
            return tiketRepository.findById(id);
        }
    }

    @Override
    public void createData(Tiket tiket) {
        synchronized (this){
            tiketRepository.createData(tiket);
        }
    }

    @Override
    public void deleteDataById(Tiket tiket) {
        synchronized (this){
            tiketRepository.deleteDataById(tiket);
        }
    }

    @Override
    public void updateData(Tiket tiket) {
        synchronized (this){
            tiketRepository.updateData(tiket);
        }
    }
}
