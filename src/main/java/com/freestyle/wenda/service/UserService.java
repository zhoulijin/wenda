package com.freestyle.wenda.service;

import com.freestyle.wenda.dao.LoginTicketDAO;
import com.freestyle.wenda.dao.UserDAO;
import com.freestyle.wenda.model.LoginTicket;
import com.freestyle.wenda.model.User;
import com.freestyle.wenda.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.*;

@Service
public class UserService {

    @Autowired
    UserDAO userDAO;

    public User getUSer(int id) {
        return userDAO.selectUserById(id);
    }

    public Map<String, String> register(String name, String password) {
        Map<String, String> map = new HashMap<>();

        if (StringUtils.isBlank(name)) {
            map.put("msg", "name bu neng wei kong");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "password bu neng wei kong");
            return map;
        }

        User user = userDAO.selectUserByName(name);
        if (user != null) {
            map.put("msg", "yijing bei zhuce");
            return map;
        }

        user = new User();
        user.setName(name);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setPassword(WendaUtil.MD5(password + user.getSalt()));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", new Random().nextInt(100)));
        userDAO.addUser(user);


        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);

        return map;
    }

    public Map<String, String> login(java.lang.String name, java.lang.String password) {
        Map<java.lang.String, java.lang.String> map = new HashMap<>();

        if (StringUtils.isBlank(name)) {
            map.put("msg", "name bu neng wei kong");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("msg", "password bu neng wei kong");
            return map;
        }

        User user = userDAO.selectUserByName(name);
        if (user == null) {
            map.put("msg", "yonghu ming bu cun zai ");
            return map;
        }

        if (!WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "mi ma cuowu ");
            return map;
        }

        String ticket = addLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    public void logout(String ticket) {
        loginTicketDAO.updateStatus(ticket, 1);
    }

    @Autowired
    LoginTicketDAO loginTicketDAO;

    private String addLoginTicket(int userId) {
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 3600 * 1000 * 24);
        loginTicket.setExpired(date);
        loginTicket.setTicket(UUID.randomUUID().toString().replaceAll("-", ""));
        loginTicket.setStatus(0);
        loginTicketDAO.addLoginTicket(loginTicket);
        return loginTicket.getTicket();
    }
}
