package com.test.helpdesk.repository;

import com.test.helpdesk.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository("UserRepository")
public class UserRepositoryImpl implements UserRepository{
    @Autowired
    JdbcTemplate jdbcTemplate;

    public String getLastID() {
        int countUser;
        String lastId;
        countUser = jdbcTemplate.queryForObject("SELECT COUNT(*) as count FROM user", Integer.class);

        if (countUser == 0){
            lastId = "USR0000";
        }else{
            lastId = jdbcTemplate.queryForObject("SELECT idUser FROM user LIMIT 1 OFFSET " + (countUser - 1), String.class);
        }

        return lastId;
    }

    @Override
    public User findById(String id) {
        User findUser;
        try{
            findUser = jdbcTemplate.queryForObject("SELECT * FROM user WHERE idUser=?",
                    new Object[]{id},
                    (resultSet, i) ->
                            new User(
                                    resultSet.getString("idUser"),
                                    resultSet.getString("username"),
                                    resultSet.getString("password"),
                                    resultSet.getString("role"),
                                    resultSet.getString("nama")
                            )
            );
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            findUser = null;
        }
        return findUser;
    }

    @Override
    public User findByUsername(String username) {
        User findUser;
        try{
            findUser = jdbcTemplate.queryForObject("SELECT * FROM user WHERE username=?",
                    new Object[]{username},
                    (resultSet, i) ->
                            new User(
                                    resultSet.getString("idUser"),
                                    resultSet.getString("username"),
                                    resultSet.getString("password"),
                                    resultSet.getString("role"),
                                    resultSet.getString("nama")
                            )
            );
        }catch (EmptyResultDataAccessException e){
            e.printStackTrace();
            findUser = null;
        }
        return findUser;
    }

    @Override
    public List<User> readData() {
        List<User> users = new ArrayList<>();
        users = jdbcTemplate.query("SELECT * FROM user",
                    (resultSet, i) ->
                            new User(
                                resultSet.getString("idUser"),
                                    resultSet.getString("username"),
                                    resultSet.getString("password"),
                                    resultSet.getString("role"),
                                    resultSet.getString("nama")
                            )
                );
        return users;
    }

    @Override
    public List<User> readDataByQuery(String query) {
        List<User> users = new ArrayList<>();
        users = jdbcTemplate.query("SELECT * FROM user " + query,
                (resultSet, i) ->
                        new User(
                                resultSet.getString("idUser"),
                                resultSet.getString("username"),
                                resultSet.getString("password"),
                                resultSet.getString("role"),
                                resultSet.getString("nama")
                        )
        );
        return users;
    }

    @Override
    public void createData(User user) {
        String lastID = getLastID();

        int lastNumber = Integer.parseInt(lastID.substring(3)) + 1;

        String number = String.valueOf(lastNumber).length() == 1 ? "000"+String.valueOf(lastNumber) :
                String.valueOf(lastNumber).length() == 2 ? "00"+String.valueOf(lastNumber) :
                        String.valueOf(lastNumber).length() == 3 ? "0"+String.valueOf(lastNumber) : String.valueOf(lastNumber);
        String idUser = new String("USR" + number );

        jdbcTemplate.update("INSERT INTO user (idUser, username, password, role, nama) VALUES (?, ?, ?, ?, ?)",
                    idUser, user.getUsername(), user.getPassword(), user.getRole(), user.getNama()
                );

        String idTeknisi = String.valueOf(UUID.randomUUID());

        if (user.getRole().equals("Teknisi")){
            jdbcTemplate.update("INSERT INTO teknisi (idTeknisi, idUser) VALUES (?,?)",
                    idTeknisi, idUser
            );
        }
    }

    @Override
    public void deleteDataById(User user) {
        jdbcTemplate.update("DELETE FROM user WHERE idUser=?",
                preparedStatement -> preparedStatement.setString(1, user.getIdUser()));
        if (user.getRole().equals("Teknisi")){
            jdbcTemplate.update("DELETE FROM teknisi WHERE idUser = ?",user.getIdUser());
        }
    }

    @Override
    public void updateData(User user) {
        jdbcTemplate.update("UPDATE user SET idUser=?, username=?, password=?, role=?, nama=?",
                user.getIdUser(), user.getUsername(), user.getPassword(), user.getRole(),
                user.getNama());
    }
}
