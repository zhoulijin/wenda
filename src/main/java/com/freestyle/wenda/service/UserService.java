package com.freestyle.wenda.service;

import com.freestyle.wenda.dao.UserDAO;
import com.freestyle.wenda.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.UserDataHandler;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public void add(User user) {
        userDAO.addUser(user);
    }
}
