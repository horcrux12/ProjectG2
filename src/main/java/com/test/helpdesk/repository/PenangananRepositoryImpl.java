package com.test.helpdesk.repository;

import com.test.helpdesk.model.Penanganan;
import com.test.helpdesk.model.Teknisi;
import com.test.helpdesk.model.Tiket;
import com.test.helpdesk.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("PenangananRepository")
public class PenangananRepositoryImpl implements PenangananRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public List<Penanganan> readData() {
        List<Penanganan> listPenanganan = new ArrayList<>();
        listPenanganan = jdbcTemplate.query("SELECT p.* FROM penanganan p " +
                        "JOIN teknisi t ON p.idTeknisi=t.idTeknisi",
                (res, i) ->
                        new Penanganan(
                                res.getString("idPenanganan"),
                                jdbcTemplate.queryForObject("SELECT u.* FROM teknisi t " +
                                                "JOIN user u ON t.idUser=u.idUser WHERE t.idTeknisi=?",
                                        new Object[]{res.getString("idTeknisi")},
                                        (rs, in) ->
                                                new Teknisi(
                                                        res.getString("idTeknisi"),
                                                        new User(
                                                                rs.getString("idUser"),
                                                                rs.getString("username"),
                                                                rs.getString("password"),
                                                                rs.getString("role"),
                                                                rs.getString("nama")
                                                        )
                                                )
                                ),
                                res.getDate("createdDate"),
                                jdbcTemplate.query("SELECT th.idTiket, th.problemDesc, " +
                                                "th.status, th.createdDate, th.updatedDate, u.* FROM detail_penanganan dp " +
                                                "JOIN tiket_helpdesk th ON dp.idTiket=th.idTiket " +
                                                "JOIN user u ON th.idUser=u.idUser " +
                                                "WHERE dp.idPenanganan=?",
                                        preparedStatement -> preparedStatement.setString(1,res.getString("idPenanganan")),
                                        (resultSet, index) ->
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
                                )
                        )
        );

        return listPenanganan;
    }

    @Override
    public List<Penanganan> readDataByQuery(String query) {
        List<Penanganan> listPenanganan = new ArrayList<>();
        listPenanganan = jdbcTemplate.query("SELECT p.* FROM penanganan p " +
                "JOIN teknisi t ON p.idTeknisi=t.idTeknisi " +
                "JOIN user u ON t.idUser=u.idUser " + query,
                (res, i) ->
                        new Penanganan(
                                res.getString("idPenanganan"),
                                jdbcTemplate.queryForObject("SELECT u.* FROM teknisi t " +
                                                "JOIN user u ON t.idUser=u.idUser WHERE t.idTeknisi=?",
                                        new Object[]{res.getString("idTeknisi")},
                                        (rs, in) ->
                                                new Teknisi(
                                                        res.getString("idTeknisi"),
                                                        new User(
                                                                rs.getString("idUser"),
                                                                rs.getString("username"),
                                                                rs.getString("password"),
                                                                rs.getString("role"),
                                                                rs.getString("nama")
                                                        )
                                                )
                                ),
                                res.getDate("createdDate"),
                                jdbcTemplate.query("SELECT th.idTiket, th.problemDesc, " +
                                                "th.status, th.createdDate, th.updatedDate, u.* FROM detail_penanganan dp " +
                                                "JOIN tiket_helpdesk th ON dp.idTiket=th.idTiket " +
                                                "JOIN user u ON th.idUser=u.idUser " +
                                                "WHERE dp.idPenanganan=?",
                                        preparedStatement -> preparedStatement.setString(1,res.getString("idPenanganan")),
                                        (resultSet, index) ->
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
                                )
                        )
                );

        return listPenanganan;
//        return null;
    }

    @Override
    public int countAllDataQuery(String query) {
        int countUser;
        countUser = jdbcTemplate.queryForObject("SELECT COUNT(*) as count FROM penanganan p " +
                "JOIN teknisi t ON p.idTeknisi=t.idTeknisi " +
                "JOIN user u ON t.idUser=u.idUser " + query, Integer.class);
        return countUser;
    }

    @Override
    public int countAllData() {
        int countUser;
        countUser = jdbcTemplate.queryForObject("SELECT COUNT(*) as count FROM penanganan p " +
                "JOIN teknisi t ON p.idTeknisi=t.idTeknisi", Integer.class);
        return countUser;
    }

    @Override
    public Penanganan findById(String id) {
        return null;
    }

    @Override
    public Penanganan findByIdTeknisi(String id) {
        Penanganan penanganan;
        try{
            penanganan = jdbcTemplate.queryForObject("SELECT p.* FROM penanganan p " +
                            "JOIN teknisi t ON p.idTeknisi=t.idTeknisi WHERE p.idTeknisi=?",
                    new Object[]{id},
                    (res, i) ->
                            new Penanganan(
                                    res.getString("idPenanganan"),
                                    jdbcTemplate.queryForObject("SELECT u.* FROM teknisi t " +
                                                    "JOIN user u ON t.idUser=u.idUser WHERE t.idTeknisi=?",
                                            new Object[]{res.getString("idTeknisi")},
                                            (rs, in) ->
                                                    new Teknisi(
                                                            res.getString("idTeknisi"),
                                                            new User(
                                                                    rs.getString("idUser"),
                                                                    rs.getString("username"),
                                                                    rs.getString("password"),
                                                                    rs.getString("role"),
                                                                    rs.getString("nama")
                                                            )
                                                    )
                                    ),
                                    res.getDate("createdDate"),
                                    jdbcTemplate.query("SELECT th.idTiket, th.problemDesc, " +
                                                    "th.status, th.createdDate, th.updatedDate, u.* FROM detail_penanganan dp " +
                                                    "JOIN tiket_helpdesk th ON dp.idTiket=th.idTiket " +
                                                    "JOIN user u ON th.idUser=u.idUser " +
                                                    "WHERE dp.idPenanganan=?",
                                            preparedStatement -> preparedStatement.setString(1,res.getString("idPenanganan")),
                                            (resultSet, index) ->
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
                                    )
                            )
                    );
        } catch (DataAccessException e) {
            System.out.println(e);
            penanganan= null;
        }
        return penanganan;
    }

    @Override
    public void createData(Penanganan penanganan) {
//        String idPenanganan = String.valueOf(UUID.randomUUID());
//        jdbcTemplate.update("INSERT INTO penanganan (idPenanganan, idTeknisi, createdDate) VALUE(?, ?, ?)",
//                penanganan.getIdPenanganan(), penanganan.getTeknisi().getIdTeknisi(),
//                penanganan.getCreatedDate());

        for (Tiket tik : penanganan.getDataTiket()){
            String idDetailPen = String.valueOf(UUID.randomUUID());
            jdbcTemplate.update("INSERT INTO detail_penanganan (idDetailPen,idPenanganan, idTiket) VALUES(?, ?, ?)",
                    idDetailPen, penanganan.getIdPenanganan(), tik.getIdTiket());
        }
    }

    @Override
    public void deleteDataById(Penanganan penanganan) {

    }

    @Override
    public void updateData(Penanganan penanganan) {

    }

    @Override
    public Tiket findDetailTiket(String idTiket, String idPenanganan) {
        Tiket tiket;

        try{
            tiket = jdbcTemplate.queryForObject("SELECT th.idTiket, th.problemDesc, " +
                    "th.status, th.createdDate, th.updatedDate, u.* FROM detail_penanganan dp " +
                    "JOIN tiket_helpdesk th ON dp.idTiket=th.idTiket " +
                    "JOIN user u ON th.idUser=u.idUser " +
                    "WHERE dp.idTiket=? AND dp.idPenanganan=?",
                    new Object[]{idTiket, idPenanganan},
                        (result,i) ->
                                new Tiket(
                                        result.getString("idTiket"),
                                        result.getString("problemDesc"),
                                        result.getString("status"),
                                        new User(
                                                result.getString("idUser"),
                                                result.getString("username"),
                                                result.getString("password"),
                                                result.getString("role"),
                                                result.getString("nama")
                                        ),
                                        result.getDate("createdDate"),
                                        result.getDate("updatedDate")
                                )
                );
        } catch (DataAccessException e) {
            System.out.println(e);
            tiket = null;
        }

        return tiket;
    }

    @Override
    public void deleteDetailTiket(String idTiket, String idPenanganan) {
        jdbcTemplate.update("DELETE FROM detail_penanganan WHERE idUser=? AND idPenanganan=?",
                idTiket, idPenanganan);
    }

    @Override
    public List<User> getUserPenanganan(String id) {
        List <User> users = new ArrayList<>();

        try{
            users = jdbcTemplate.query("SELECT DISTINCT u.* FROM detail_penanganan dp " +
                    "RIGHT JOIN tiket_helpdesk th ON dp.idTiket=th.idTiket " +
                    "JOIN user u ON th.idUser=u.idUser " +
                    "WHERE dp.idPenanganan IN (SELECT idPenanganan FROM penanganan WHERE idTeknisi='"+id+"')",
                    (resultSet, i) ->
                        new User(
                                resultSet.getString("idUser"),
                                resultSet.getString("username"),
                                resultSet.getString("password"),
                                resultSet.getString("role"),
                                resultSet.getString("nama")
                        )
                    );
        } catch (DataAccessException e) {
            System.out.println(e);
        }

        return users;
    }

    @Override
    public List<User> getUserPenanganan(String id, String query) {
        List <User> users = new ArrayList<>();

        try{
            users = jdbcTemplate.query("SELECT DISTINCT u.* FROM detail_penanganan dp " +
                    "RIGHT JOIN tiket_helpdesk th ON dp.idTiket=th.idTiket " +
                    "JOIN user u ON th.idUser=u.idUser " +
                    "WHERE dp.idPenanganan IN (SELECT idPenanganan FROM penanganan WHERE idTeknisi='"+id+"') AND " + query,
                        (resultSet, i) ->
                                new User(
                                        resultSet.getString("idUser"),
                                        resultSet.getString("username"),
                                        resultSet.getString("password"),
                                        resultSet.getString("role"),
                                        resultSet.getString("nama")
                                )
                    );
        } catch (DataAccessException e) {
            System.out.println(e);
        }

        return users;
    }

    @Override
    public int countUserPenanganan(String id) {
        int countUser;
        countUser = jdbcTemplate.queryForObject("SELECT DISTINCT u.* FROM detail_penanganan dp " +
                "RIGHT JOIN tiket_helpdesk th ON dp.idTiket=th.idTiket " +
                "JOIN user u ON th.idUser=u.idUser " +
                "WHERE dp.idPenanganan IN (SELECT idPenanganan FROM penanganan WHERE idTeknisi='"+id+"')", Integer.class);
        return countUser;
    }

    @Override
    public int countUserPenanganan(String id, String query) {
        int countUser;
        countUser = jdbcTemplate.queryForObject("SELECT DISTINCT u.* FROM detail_penanganan dp " +
                "RIGHT JOIN tiket_helpdesk th ON dp.idTiket=th.idTiket " +
                "JOIN user u ON th.idUser=u.idUser " +
                "WHERE dp.idPenanganan IN (SELECT idPenanganan FROM penanganan WHERE idTeknisi='"+id+"') AND " + query, Integer.class);
        return countUser;
    }
}
