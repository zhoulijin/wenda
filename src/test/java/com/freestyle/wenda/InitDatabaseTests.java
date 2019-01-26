package com.freestyle.wenda;

import com.freestyle.wenda.dao.QuestionDAO;
import com.freestyle.wenda.dao.UserDAO;
import com.freestyle.wenda.model.Question;
import com.freestyle.wenda.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.Random;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = WendaApplication.class)
@Sql("/init-schema.sql")
public class InitDatabaseTests {

	@Autowired
	UserDAO userDAO;

	@Autowired
	QuestionDAO questionDAO;

	@Test
	public void initDatabase() {

		Random random = new Random();

		for (int i = 0; i < 100; i ++) {
			User user = new User();
			user.setName("user" + i);
			user.setPassword("");
			user.setSalt("");
			user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png", random.nextInt(10000)));
			userDAO.addUser(user);

			user.setPassword("xx");
			userDAO.updatePassword(user);

			Question question = new Question();
			question.setTitle("title" + i);
			question.setContent("balabala");

			Date date = new Date();
			date.setTime(date.getTime() + 3600 * 1000 * i);
			question.setCreatedDate(date);
			question.setCommentCount(i);

			question.setUserId(i);

			questionDAO.addQuestion(question);
		}

		Assert.assertEquals("xx", userDAO.selectUserById(1).getPassword());
		userDAO.deleteUserById(1);
		Assert.assertNull(userDAO.selectUserById(1));

	}
}

