package com.ciglink.common.web.entity.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ciglink.common.core.constant.basic.BaseConstants;
import com.ciglink.common.core.utils.StringUtils;
import com.ciglink.common.core.web.entity.base.BaseEntity;
import com.ciglink.common.web.entity.dao.BaseDao;
import com.ciglink.common.web.entity.mapper.BaseMapper;
import com.ciglink.common.web.entity.service.IBaseService;
import com.ciglink.common.web.entity.service.impl.handle.BaseHandleServiceImpl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 服务层 基类实现通用数据处理
 *
 * @param <D>  Dto
 * @param <DG> DtoManager
 * @param <DM> DtoMapper
 * 
 */
public class BaseServiceImpl<D extends BaseEntity, DG extends BaseDao<D, DM>, DM extends BaseMapper<D>> extends BaseHandleServiceImpl<D, DG, DM> implements IBaseService<D> {

    /**
     * 查询数据对象列表
     *
     * @param d 数据对象
     * @return 数据对象集合
     */
    @Override
    public List<D> selectList(D d) {
        return baseDao.selectList(d);
    }

    /**
     * 查询数据对象列表 | 附加数据
     *
     * @param d 数据对象
     * @return 数据对象集合
     */
    @Override
    public List<D> selectListExtra(D d) {
        return baseDao.selectListExtra(d);
    }

    /**
     * 查询数据对象列表 | 数据权限 | 附加数据
     *
     * @param d 数据对象
     * @return 数据对象集合
     */
    @Override
    public List<D> selectListScope(D d) {
        return baseDao.selectListExtra(d);
    }

    /**
     * 根据Id集合查询数据对象列表
     *
     * @param idList Id集合
     * @return 数据对象集合
     */
    @Override
    public List<D> selectListByIds(Collection<? extends Serializable> idList) {
        return baseDao.selectListByIds(idList);
    }

    /**
     * 根据Id查询单条数据对象
     *
     * @param id Id
     * @return 数据对象
     */
    @Override
    public D selectById(Serializable id) {
        return baseDao.selectById(id);
    }

    /**
     * 根据Id查询单条数据对象 | 附加数据
     *
     * @param id Id
     * @return 数据对象
     */
    @Override
    public D selectByIdExtra(Serializable id) {
        return baseDao.selectByIdExtra(id);
    }

    /**
     * 新增数据对象
     *
     * @param d 数据对象
     * @return 结果
     */
    @Override
    public int insert(D d) {
        return baseDao.insert(d);
    }

    /**
     * 新增数据对象（批量）
     *
     * @param entityList 数据对象集合
     */
    @Override
    public int insertBatch(Collection<D> entityList) {
        return baseDao.insertBatch(entityList);
    }

    /**
     * 修改数据对象
     *
     * @param d 数据对象
     * @return 结果
     */
    @Override
    public int update(D d) {
        return baseDao.update(d);
    }

    /**
     * 修改数据对象（批量）
     *
     * @param entityList 数据对象集合
     */
    @Override
    public int updateBatch(Collection<D> entityList) {
        return baseDao.updateBatch(entityList);
    }

    /**
     * 修改数据对象状态
     *
     * @param id     Id
     * @param status 状态
     * @return 结果
     */
    @Override
    public int updateStatus(Serializable id, String status) {
        return baseDao.updateStatus(id, status);
    }

    /**
     * 根据Id删除数据对象
     *
     * @param id Id
     * @return 结果
     */
    @Override
    public int deleteById(Serializable id) {
        return baseDao.deleteById(id);
    }

    /**
     * 根据Id集合删除数据对象
     *
     * @param idList Id集合
     * @return 结果
     */
    @Override
    public int deleteByIds(Collection<? extends Serializable> idList) {
        return baseDao.deleteByIds(idList);
    }

    /**
     * 校验名称是否唯一
     *
     * @param id   Id
     * @param name 名称
     * @return 结果 | true/false 唯一/不唯一
     */
    @Override
    public boolean checkNameUnique(Serializable id, String name) {
        return ObjectUtil.isNotNull(baseDao.checkNameUnique(ObjectUtil.isNull(id) ? BaseConstants.NONE_ID : id, name));
    }

    /**
     * 根据Id查询数据对象状态
     *
     * @param id Id
     * @return 结果 | NORMAL 正常 | DISABLE 停用 | EXCEPTION 异常（值不存在）
     */
    @Override
    public BaseConstants.Status checkStatus(Serializable id) {
        D info = StringUtils.isNotNull(id) ? baseDao.selectById(id) : null;
        return ObjectUtil.isNull(info)
                ? BaseConstants.Status.EXCEPTION
                : StrUtil.equals(BaseConstants.Status.NORMAL.getCode(), info.getStatus())
                ? BaseConstants.Status.NORMAL
                : BaseConstants.Status.DISABLE;
    }

}