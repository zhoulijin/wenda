package com.freestyle.wenda.controller;

import com.freestyle.wenda.aspect.LogAspect;
import com.freestyle.wenda.dao.CommentDAO;
import com.freestyle.wenda.model.Comment;
import com.freestyle.wenda.model.EntityType;
import com.freestyle.wenda.model.HostHolder;
import com.freestyle.wenda.util.WendaUtil;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;

@Controller
public class CommentController {


    private Log log = LogFactory.getFactory().getInstance(CommentController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentDAO commentDAO;

    @RequestMapping(path = {"/addComment"}, method = {RequestMethod.POST})
    public String index(@RequestParam("questionId") int questionId,
                        @RequestParam("content") String content) {

        try {
            Comment comment = new Comment();
            comment.setContent(content);
            if (hostHolder.getUser() == null) {
                comment.setUserId(0);
            } else {
                comment.setUserId(hostHolder.getUser().getId());
            }

            comment.setCreatedDate(new Date());
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setEntityId(questionId);
            comment.setStatus(0);

            commentDAO.addComment(comment);

        } catch (Exception e) {
            log.error("增加评论失败" + e.getMessage());
        }
        return "redirect:/question/" + String.valueOf(questionId);
    }
}
