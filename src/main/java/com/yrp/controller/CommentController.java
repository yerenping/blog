package com.yrp.controller;

import com.yrp.po.Comment;
import com.yrp.po.User;
import com.yrp.service.BlogService;
import com.yrp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * 评论
 */
@Controller
public class CommentController {

	@Autowired
	private CommentService commentServiceImpl;

	@Autowired
	private BlogService blogServiceImpl;

	@Value("${comment.avatar}")
	private String avatar;

	/**
	 * 显示评论列表
	 * @param blogId
	 * @param model
	 * @return
	 */
	@GetMapping("/comments/{blogId}")
	public String comments(@PathVariable Long blogId, Model model){
		model.addAttribute("comments",commentServiceImpl.listCommentByBlogId(blogId));
		return "blog :: commentList";
	}

	/**
	 * 提交评论
	 * @param comment
	 * @return
	 */
	@PostMapping("/comments")
	public String post(Comment comment, HttpSession session){
		Long blogId = comment.getBlog().getId();
		//处理评论和博客之间的关系
		comment.setBlog(blogServiceImpl.getBlog(blogId));
		User user = (User) session.getAttribute("user");
		//管理员发
		if(user != null){
			comment.setAvatar(user.getAvatar());
			comment.setAdminComment(true);
			comment.setNickname(user.getNickname());
			comment.setEmail(user.getEmail());
		//普通用户
		} else {
			comment.setAvatar(avatar);
		}
		commentServiceImpl.saveComment(comment);
		return "redirect:/comments/" +blogId;
	}


}
