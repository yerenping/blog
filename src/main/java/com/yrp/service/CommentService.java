package com.yrp.service;


import com.yrp.po.Comment;

import java.util.List;

/**
 * 评论
 */
public interface CommentService {

    /**
     * 通过id查询评论列表
     * @param blogId
     * @return
     */
    List<Comment> listCommentByBlogId(Long blogId);

    /**
     * 保存评论信息
     * @param comment
     * @return
     */
    Comment saveComment(Comment comment);

}
