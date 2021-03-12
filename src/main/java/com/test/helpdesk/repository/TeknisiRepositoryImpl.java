package com.test.helpdesk.repository;

import com.test.helpdesk.model.Teknisi;
import com.test.helpdesk.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository("TeknisiRepository")
public class TeknisiRepositoryImpl implements TeknisiRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Teknisi> readData() {
        List<Teknisi> listTeknisi = new ArrayList<>();

        listTeknisi = jdbcTemplate.query("SELECT t.idTeknisi,u.* FROM teknisi t JOIN user u ON t.idUser=u.idUser",
                    (resultSet, i) ->
                            new Teknisi(
                                    resultSet.getString("idTeknisi"),
                                    new User(
                                            resultSet.getString("idUser"),
                                            resultSet.getString("username"),
                                            resultSet.getString("password"),
                                            resultSet.getString("role"),
                                            resultSet.getString("nama")
                                    )
                            )
                );

        return listTeknisi;
    }

    @Override
    public Teknisi findDataByIdUser(String id) {
        Teknisi teknisi = new Teknisi();

        try{
            teknisi = jdbcTemplate.queryForObject("SELECT t.idTeknisi,u.* FROM teknisi t JOIN user u ON t.idUser=u.idUser" +
                    " WHERE t.idUser=?", new Object[]{id},
                    (resultSet, i) ->
                            new Teknisi(
                                    resultSet.getString("idTeknisi"),
                                    new User(
                                            resultSet.getString("idUser"),
                                            resultSet.getString("username"),
                                            resultSet.getString("password"),
                                            resultSet.getString("role"),
                                            resultSet.getString("nama")
                                    )
                            )
                    );
        } catch (DataAccessException e) {
            System.out.println(e);
            teknisi = null;
        }

        return teknisi;
    }

    @Override
    public Teknisi findDataById(String id) {
        Teknisi teknisi = new Teknisi();

        try{
            teknisi = jdbcTemplate.queryForObject("SELECT t.idTeknisi,u.* FROM teknisi t JOIN user u ON t.idUser=u.idUser" +
                            " WHERE t.idTeknisi=?", new Object[]{id},
                    (resultSet, i) ->
                            new Teknisi(
                                    resultSet.getString("idTeknisi"),
                                    new User(
                                            resultSet.getString("idUser"),
                                            resultSet.getString("username"),
                                            resultSet.getString("password"),
                                            resultSet.getString("role"),
                                            resultSet.getString("nama")
                                    )
                            )
            );
        } catch (DataAccessException e) {
            System.out.println(e);
            teknisi = null;
        }

        return teknisi;
    }
}
