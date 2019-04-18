package com.yixin.core.biz;

import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author yaojie
 * @Description: TODO
 * @date 2018/9/2916:08
 */
public abstract  class BaseServiceImpl<M extends Mapper<T>, T> implements BaseService<M,T> {

    @Autowired
    protected M mapper;

    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    @Override
    public T selectOne(T entity){

        return mapper.selectOne(entity);

    }
    @Override
    public T selectById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }

    @Override
    public List<T> selectListAll() {
        return mapper.selectAll();
    }

    @Override
    public Long selectCount(T entity) {
        return new Long(mapper.selectCount(entity));
    }
    @Override
    public void insertSelective(T entity) {
        mapper.insertSelective(entity);
    }
    @Override
    public void delete(T entity) {
        mapper.delete(entity);
    }

    @Override
    public void deleteById(Object id) {
        mapper.deleteByPrimaryKey(id);
    }

    @Override
    public void updateById(T entity) {
        mapper.updateByPrimaryKey(entity);
    }

    @Override
    public void updateSelectiveById(T entity) {
        mapper.updateByPrimaryKeySelective(entity);

    }
    @Override
    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }
    @Override
    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }
}
