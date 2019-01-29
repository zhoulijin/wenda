package com.freestyle.wenda.controller;

import com.freestyle.wenda.model.Question;
import com.freestyle.wenda.service.QuestionService;
import com.freestyle.wenda.service.UserService;
import com.freestyle.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @RequestMapping(path = {"question/add"}, method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title, @RequestParam("content") String content) {

        try {
            Question question = new Question();
            question.setUserId(1);
            question.setTitle(title);
            question.setContent(content);
            question.setCommentCount(0);
            question.setCreatedDate(new Date());

            if (questionService.addQuestion(question) > 0) {
                return WendaUtil.getJsonString(0);
            }
        } catch (Exception e) {

        }
        return WendaUtil.getJsonString(1, "fabu yi chang");
    }


    @RequestMapping(path = {"question/{qid}"}, method = {RequestMethod.GET})
    public String question(Model model, @PathVariable("qid") int qId) {
        Question question = questionService.selectById(qId);

        model.addAttribute("question", question);
        model.addAttribute("user", userService.selectById(question.getUserId()));

        return "detail";
    }
}
