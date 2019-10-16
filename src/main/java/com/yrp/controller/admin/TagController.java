package com.yrp.controller.admin;


import com.yrp.po.Tag;
import com.yrp.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * 标签
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
   private TagService tagServiceImpl;

    /**
     * 分页查询标签列表
     * @return
     */
    @GetMapping("/tags")
    public String types(@PageableDefault(size = 8,sort = {"id"},direction = Sort.Direction.DESC)
                                Pageable pageable, Model model) {
        model.addAttribute("page",tagServiceImpl.ListTag(pageable));
        return "admin/tags";
    }

    /**
     * 显示添加标签页面
     * @param model
     * @return
     */
    @GetMapping("/tags/input")
    public String showInput(Model model){
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    /**
     * 新增标签
     * @param tag
     * @param result
     * @param attributes
     * @return
     */
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        Tag tag1 = tagServiceImpl.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name", "nameError", "不能添加重复的标签");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagServiceImpl.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("新增失败");
        } else {
            attributes.addFlashAttribute("新增成功");
        }
        return "redirect:/admin/tags";

    }
    /**
     * 显示添加标签页面
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable("id") Long id, Model model){
        model.addAttribute("tag",tagServiceImpl.getTag(id));
        return "admin/tags-input";
    }

    /**
     * 修改标签名
     * @param tag
     * @param result
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag,BindingResult result,@PathVariable("id")Long id,RedirectAttributes attributes){
        Tag tag1 = tagServiceImpl.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name","nameError","不能添加重复的分类");
        }
        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagServiceImpl.updateTag(id, tag);
        if (t == null ) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 删除功能
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/tags/{id}/delete")
    public String deleteById(@PathVariable("id") Long id,RedirectAttributes attributes){
        tagServiceImpl.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";
    }
}
