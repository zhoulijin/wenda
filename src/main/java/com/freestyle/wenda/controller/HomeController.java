package com.freestyle.wenda.controller;

import com.freestyle.wenda.dao.QuestionDAO;
import com.freestyle.wenda.dao.UserDAO;
import com.freestyle.wenda.model.Question;
import com.freestyle.wenda.model.ViewObject;
import com.freestyle.wenda.service.QuestionService;
import com.freestyle.wenda.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @RequestMapping(path = {"/", "/index", "test"}, method = {RequestMethod.GET})
    public String index(Model model) {
        model.addAttribute("vos", getQuestions(0, 0, 10));
        return "index";
    }



    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getQuestions(userId, 0, 10));
        return "index";
    }


    public List<ViewObject> getQuestions(int userId, int offset, int limit) {
        List<Question> questionList = questionService.getLeastQuestions(userId, offset, limit);
        List<ViewObject> viewObjects = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject viewObject = new ViewObject();
            viewObject.set("question", question);
            viewObject.set("user", userService.getUSer(question.getUserId()));
            viewObjects.add(viewObject);
        }
        return viewObjects;
    }
}
