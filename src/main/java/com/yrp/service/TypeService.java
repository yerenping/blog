package com.yrp.service;

import com.yrp.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 分类管理接口
 */
public interface TypeService {

    /**
     * 新增
     * @return
     */
    Type saveType(Type type);


    /**
     * 单个查询
     * @param id
     * @return
     */
    Type getType(Long id);

    /**
     * 分页查询
     * @param pageable
     * @return
     */
    Page<Type> ListType(Pageable pageable);

    /**
     * 查询所有分类
     * @return
     */
    List<Type> listType();


    List<Type> listTypeTop(Integer size);

    /**
     * 修改
     * @param id
     * @param type
     * @return
     */
    Type updateType(Long id,Type type);

    /**
     * 删除
     * @param id
     */
    void deleteType(Long id);

    /**
     * 通过分类名称查询分类
     * @return
     */
    Type getTypeByName(String name);

}
