package com.yixin.core.biz;

import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author yaojie
 * @Description: TODO
 * @date 2018/9/2916:00
 */
public  interface BaseService<M extends Mapper<T>, T> {



    T selectOne(T entity);

    T selectById(Object id);

    List<T> selectList(T entity);


    List<T> selectListAll();


    Long selectCount(T entity);

    void insertSelective(T entity);

    void delete(T entity);


    void deleteById(Object id);


    void updateById(T entity);


    void updateSelectiveById(T entity);

    List<T> selectByExample(Object example);

    int selectCountByExample(Object example);
}