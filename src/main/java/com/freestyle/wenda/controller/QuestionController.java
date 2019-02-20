package com.freestyle.wenda.controller;

import com.freestyle.wenda.model.*;
import com.freestyle.wenda.service.CommentService;
import com.freestyle.wenda.service.LikeService;
import com.freestyle.wenda.service.QuestionService;
import com.freestyle.wenda.service.UserService;
import com.freestyle.wenda.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    CommentService commentService;

    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

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

        List<Comment> commentList = commentService.getCommentByEntity(qId, EntityType.ENTITY_QUESTION);
        List<ViewObject> vos = new ArrayList<>();

        for (Comment comment : commentList) {
            ViewObject viewObject = new ViewObject();
            viewObject.set("comment", comment);
            viewObject.set("user", userService.selectById(comment.getUserId()));


            if (hostHolder.getUser() == null) {
                viewObject.set("liked", 0);
            } else {
                viewObject.set("liked", likeService.getLikeStatus(hostHolder.getUser().getId(), EntityType.ENTITY_COMMENT, comment.getId()));
            }

            viewObject.set("likeCount", likeService.getLikeCount(EntityType.ENTITY_COMMENT, comment.getId()));

            vos.add(viewObject);
        }
        model.addAttribute("comments", vos);

        return "detail";
    }
}
