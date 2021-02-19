package com.test.helpdesk.repository;

import com.test.helpdesk.model.Tiket;
import com.test.helpdesk.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository("TiketRepository")
public class TiketRepositoryImpl implements TiketRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Tiket> readData() {
        List<Tiket> listTiket;

        listTiket = jdbcTemplate.query("SELECT t.idTiket, t.problemDesc," +
                "t.status, t.createdDate, t.updatedDate, u.* FROM tiket_helpdesk t " +
                "JOIN user u ON t.idUser=u.idUser ORDER BY t.createdDate ASC",
                (resultSet, i) ->
                    new Tiket (
                            resultSet.getString("idTiket"),
                            resultSet.getString("problemDesc"),
                            resultSet.getString("status"),
                            new User(
                                    resultSet.getString("idUser"),
                                    resultSet.getString("username"),
                                    resultSet.getString("password"),
                                    resultSet.getString("role"),
                                    resultSet.getString("nama")
                            ),
                            resultSet.getDate("createdDate"),
                            resultSet.getDate("updatedDate")
                    )
                );

        return listTiket;
    }

    @Override
    public List<Tiket> readDataByQuery(String query) {
        List<Tiket> listTiket;

        listTiket = jdbcTemplate.query("SELECT t.idTiket, t.problemDesc," +
                        "t.status, t.createdDate, t.updatedDate, u.* FROM tiket_helpdesk t " +
                        "JOIN user u ON t.idUser=u.idUser "+query+" ORDER BY t.createdDate ASC",
                (resultSet, i) ->
                        new Tiket (
                                resultSet.getString("idTiket"),
                                resultSet.getString("problemDesc"),
                                resultSet.getString("status"),
                                new User(
                                        resultSet.getString("idUser"),
                                        resultSet.getString("username"),
                                        resultSet.getString("password"),
                                        resultSet.getString("role"),
                                        resultSet.getString("nama")
                                ),
                                resultSet.getDate("createdDate"),
                                resultSet.getDate("updatedDate")
                        )
        );

        return listTiket;
    }

    @Override
    public Tiket findById(String id) {
        Tiket tiket;
        try{
            tiket = jdbcTemplate.queryForObject("SELECT t.idTiket, t.problemDesc," +
                    "t.status, t.createdDate, t.updatedDate, u.* FROM tiket_helpdesk t " +
                            "JOIN user u ON t.idUser=u.idUser WHERE t.idTiket = ?",
                    new Object[]{id},
                        (resultSet, i) ->
                                new Tiket(
                                        resultSet.getString("idTiket"),
                                        resultSet.getString("problemDesc"),
                                        resultSet.getString("status"),
                                        new User(
                                                resultSet.getString("idUser"),
                                                resultSet.getString("username"),
                                                resultSet.getString("password"),
                                                resultSet.getString("role"),
                                                resultSet.getString("nama")
                                        ),
                                        resultSet.getDate("createdDate"),
                                        resultSet.getDate("updatedDate")
                                )
                    );
        } catch (DataAccessException e) {
            e.printStackTrace();
            tiket = null;
        }

        return tiket;
    }

    @Override
    public void createData(Tiket tiket) {
        String idTiket = String.valueOf(UUID.randomUUID());
        jdbcTemplate.update("INSERT INTO tiket_helpdesk " +
                        "(idTiket, problemDesc, status, idUser, createdDate, UpdatedDate) " +
                        "VALUES (?, ?, ?, ?, ?, ?)",
                idTiket, tiket.getProblemDesc(), tiket.getStatus(),
                tiket.getUser().getIdUser(), tiket.getCreatedDate(), null);

    }

    @Override
    public void deleteDataById(Tiket tiket) {
        jdbcTemplate.update("DELETE FROM tiket_helpdesk WHERE idTiket = ?",
                tiket.getIdTiket());
    }

    @Override
    public void updateData(Tiket tiket) {
        jdbcTemplate.update("UPDATE tiket_helpdesk SET idTiket=?, problemDesc=?," +
                " status=?, idUser=?, createdDate=?, updatedDate=?",
                tiket.getIdTiket(), tiket.getProblemDesc(), tiket.getStatus(),
                tiket.getUser().getIdUser(), tiket.getCreatedDate(), tiket.getUpdatedDate());
    }
}
