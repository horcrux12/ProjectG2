package com.test.helpdesk.service;

import com.test.helpdesk.model.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    User findById(String id);
    User findByUsername(String username);
    User validateUser(String username, String id);
    int countAllData();
    int countAllDataQuery(Map <Object, Object> params);
    List<User> readData();
    List<User> readDataByQuery(Map <Object, Object> params);
    void createData(User user);
    void deleteDataById (User user);
    void updateData (User user);
}
