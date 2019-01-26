package com.freestyle.wenda.service;

import com.freestyle.wenda.dao.UserDAO;
import com.freestyle.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public User getUSer(int id) {
        return userDAO.selectUserById(id);
    }
}
