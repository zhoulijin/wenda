package com.freestyle.wenda;

import com.freestyle.wenda.dao.UserDAO;
import com.freestyle.wenda.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {

	@Autowired
	UserDAO userDAO;

	@Test
	public void initDatabase() {

		for (int i = 0; i < 100; i ++) {
			User user = new User();
			user.setName("user" + i);
			user.setPassword("");
			user.setSalt("dfsahjf");
			user.setHeadUrl("fdsfa");
			userDAO.addUser(user);
		}
	}
}

