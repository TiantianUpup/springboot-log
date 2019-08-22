package com.h2t.study.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.h2t.study.service.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;

/**
 * @Description: 基本业务实现类
 *
 * @Author: hetiantian
 * @Date:2019/8/3 11:54 
 * @Version: 1.0
 */
public class BaseServiceImpl<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> implements BaseService<T> {
    Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);

    /**
     * 添加
     *
     * @param obj
     * @return
     * */
    public boolean insert(T obj) {
        return this.save(obj);
    }

    /**
     * 批量添加
     *
     * @param objList
     * @return
     * */
    public boolean insertBatch(List<T> objList) {
        return this.saveBatch(objList);
    }


    /**
     * 根据id删除
     *

     * @param obj
     */
    @Override
    public boolean modifyById(T obj) {
        return this.updateById(obj);
    }

    /**
     * 根据传入参数条件进行删除
     *
     * @param obj
     * @return
     * */
    public boolean delete(T obj) {
        Wrapper wrapper = new QueryWrapper<T>(obj);
        return this.remove(wrapper);
    }

    /**
     * 批量删除
     *
     * @param idList
     * @return
     * */
    public boolean deleteByIds(List<Long> idList) {
        return this.removeByIds(idList);
    }

    /**
     * 批量id删除
     *
     * @param id
     * @return
     * */
    public boolean deleteById(Long id) {
        return this.removeById(id);
    }

    /**
     * 根据id进行更新
     *
     * @param obj
     * @return
     * */
//    @Override
//    public boolean modify(T obj) {
//        Wrapper wrapper  = new QueryWrapper<>(obj);
//        return this.update(wrapper);
//    }

    /**
     * 根据id查找
     *

     * @param id
     */
    public T selectById(Long id) {
        return this.getById(id);
    }

    /**
     * 根据条件进行查询
     *
     * @param obj
     * @return
     * */
    public List<T> selectList(T obj) {
        Wrapper wrapper = new QueryWrapper<T>(obj);
        return this.list(wrapper);
    }

    /**
     * 根据id批量查询
     *
     * @param idList
     * @return
     * */
    public Collection<T> selectByIds(List<Long> idList) {
        return this.listByIds(idList);
    }

    /**
     * 分页查询
     *
     * @param pageNo 页码
     * @param pageSize 页数
     * @param obj
     * @return
     * */
    public IPage<T> selectPage(T obj, Integer pageNo, Integer pageSize) {
        if (obj == null || pageNo == null || pageSize == null) {
            logger.error("传参错误");
        }

        logger.info("根据查询条件：{} 查询，当前页码：{}，分页数：{}", obj, pageNo, pageSize);
        Page<T> page = new Page<T>(pageNo, pageSize);
        Wrapper wrapper = new QueryWrapper<T>(obj);
        return this.page(page, wrapper);
    }
}
