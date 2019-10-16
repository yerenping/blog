package com.yrp.controller;

import com.yrp.service.BlogService;
import com.yrp.service.TagService;
import com.yrp.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 首页index.html
 */
@Controller
public class IndexController {

	@Autowired
	private BlogService blogServiceImpl;

	@Autowired
	private TypeService typeServiceImpl;

	@Autowired
	private TagService tagServiceImpl;

	/**
	 * 首页面显示
	 * @param model
	 * @param pageable
	 * @return
	 */
	@GetMapping("/")
	public String index(Model model, @PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC)
			Pageable pageable){
		//1. 获取分页的博客列表
		model.addAttribute("page",blogServiceImpl.ListBlog(pageable));
		//2. 获取分类的内容(显示6条)
		model.addAttribute("types",typeServiceImpl.listTypeTop(6));
		//3. 获取标签的内容
		model.addAttribute("tags",tagServiceImpl.ListTagTop(10));
		//4. 显示推荐博客列表
		model.addAttribute("recommendBlogs",blogServiceImpl.listRecommendBlogTop(8));
		return "index";
	}

	/**
	 * 导航栏中的搜索功能实现
	 * @param pageable
	 * @return
	 * @param model
	 */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 8, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam String query, Model model) {
        model.addAttribute("page", blogServiceImpl.ListBlog(pageable, "%"+query+"%"));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/blog/{id}")
    public  String blog(@PathVariable("id") Long id,Model model){
        model.addAttribute("blog",blogServiceImpl.getAadConvertBlog(id));
        return "blog";
    }

    @GetMapping("/footer/newblog")
    public String newblos(Model model){
		model.addAttribute("newblogs",blogServiceImpl.listRecommendBlogTop(3));
    	return "_fragments :: newblogList";
	}

}
