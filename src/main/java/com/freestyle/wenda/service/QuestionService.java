package com.freestyle.wenda.service;

import com.freestyle.wenda.dao.QuestionDAO;
import com.freestyle.wenda.model.Question;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    QuestionDAO questionDAO;

    public List<Question> getLeastQuestions(int userId, int offset, int limit) {
        return questionDAO.selectLatestQuestions(userId, offset, limit);
    }

    public int addQuestion(Question question) {
        // html guolv
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));

        // ming gan ci guo lv

        return questionDAO.addQuestion(question) > 0 ? question.getId() : 0;
    }

    public Question selectById(int id) {
        return questionDAO.selectById(id);
    }
}
