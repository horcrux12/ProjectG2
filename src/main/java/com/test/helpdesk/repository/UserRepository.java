package com.test.helpdesk.repository;

import com.test.helpdesk.model.User;

import java.util.List;

public interface UserRepository {
    User findById(String id);
    User findByUsername(String username);
    User validateUser(String username, String id);
    int countAllData();
    int countAllDataQery(String query);
    List<User> readData();
    List<User> readDataByQuery(String query);
    void createData(User user);
    void deleteDataById (User user);
    void updateData (User user);
}
