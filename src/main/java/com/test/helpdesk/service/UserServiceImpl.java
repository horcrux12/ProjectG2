package com.test.helpdesk.service;

import com.test.helpdesk.model.User;
import com.test.helpdesk.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("UserService")
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;

    PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public User findById(String id) {
        synchronized (this){
            return userRepository.findById(id);
        }
    }

    @Override
    public User findByUsername(String username) {
        synchronized (this){
            return userRepository.findByUsername(username);
        }
    }

    @Override
    public List<User> readData() {
        synchronized (this){
            return userRepository.readData();
        }
    }

    @Override
    public int countAllData() {
        synchronized (this){
            return userRepository.countAllData();
        }
    }

    @Override
    public int countAllDataQuery(Map<Object, Object> params) {
        synchronized (this){
            String query = "";

            ArrayList<String> whereQuery = new ArrayList<>();
            ArrayList<String> pageQuery = new ArrayList<>();

            if (params.containsKey("idUser") && !String.valueOf(params.get("idUser")).isBlank())
                whereQuery.add("idUser='"+params.get("idUser")+"'");
            if (params.containsKey("username") && !String.valueOf(params.get("username")).isBlank())
                whereQuery.add("username='"+params.get("username")+"'");
            if (params.containsKey("role") && !String.valueOf(params.get("role")).isBlank())
                whereQuery.add("role='"+params.get("role")+"'");
            if (params.containsKey("nama") && !String.valueOf(params.get("nama")).isBlank())
                whereQuery.add("nama='"+params.get("nama")+"'");
            if (params.containsKey("search") && !String.valueOf(params.get("search")).isBlank()){
                Object q = params.get("search");
                whereQuery.add("idUser LIKE '%"+q+"%' OR username LIKE '%"+
                        q+"%' OR role LIKE '%"+
                        q+"%' OR nama LIKE '%"+q+"%'");
            }

            if (!whereQuery.isEmpty()) query += "WHERE " + String.join(" AND ", whereQuery);

            return userRepository.countAllDataQery(query);
        }
    }

    @Override
    public List<User> readDataByQuery(Map <Object, Object> params) {
        synchronized (this){
            String query = "";

            ArrayList<String> whereQuery = new ArrayList<>();
            ArrayList<String> pageQuery = new ArrayList<>();

            if (params.containsKey("idUser") && !String.valueOf(params.get("idUser")).isBlank())
                whereQuery.add("idUser='"+params.get("idUser")+"'");
            if (params.containsKey("username") && !String.valueOf(params.get("username")).isBlank())
                whereQuery.add("username='"+params.get("username")+"'");
            if (params.containsKey("role") && !String.valueOf(params.get("role")).isBlank())
                whereQuery.add("role='"+params.get("role")+"'");
            if (params.containsKey("nama") && !String.valueOf(params.get("nama")).isBlank())
                whereQuery.add("nama='"+params.get("nama")+"'");
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


            // WHERE idUSer = 'isi params'
            // SELECT * FROM users WHERE idUser='isi'
            if (!whereQuery.isEmpty()) query += "WHERE " + String.join(" AND ", whereQuery);
            if (!pageQuery.isEmpty()) {
                query += String.join(" ", pageQuery);
            }
            return userRepository.readDataByQuery(query);
        }
    }

    @Override
    public void createData(User user) {
        synchronized (this){
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.createData(user);
        }
    }

    @Override
    public void deleteDataById(User user) {
        synchronized (this){
            userRepository.deleteDataById(user);
        }
    }

    @Override
    public void updateData(User user) {
        synchronized (this){
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.updateData(user);
        }
    }

    @Override
    public User validateUser(String username, String id) {
        synchronized (this){
            return userRepository.validateUser(username, id);
        }
    }
}
