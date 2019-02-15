package com.freestyle.wenda.service;

import com.freestyle.wenda.dao.CommentDAO;
import com.freestyle.wenda.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.websocket.server.ServerEndpoint;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    CommentDAO commentDAO;

    public List<Comment> getCommentByEntity(int entityId, int entityType) {
        return commentDAO.selectCommentByEntity(entityId, entityType);
    }

    public int addComment(Comment comment) {
        return commentDAO.addComment(comment) > 0 ? comment.getId() : 0;
    }

    public int getCommentCount(int entityId, int entityType) {
        return commentDAO.getCommentCount(entityId, entityType);
    }

    public boolean deleteComment(int commentId) {
        return commentDAO.updateStatus(commentId, 1) > 0;
    }
}
