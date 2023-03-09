package com.ciglink.common.web.entity.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.dynamic.datasource.annotation.DSTransactional;
import com.ciglink.common.core.web.entity.base.BaseEntity;
import com.ciglink.common.core.web.entity.base.SubBaseEntity;
import com.ciglink.common.web.entity.dao.SubBaseDao;
import com.ciglink.common.web.entity.mapper.BaseMapper;
import com.ciglink.common.web.entity.mapper.SubBaseMapper;
import com.ciglink.common.web.entity.service.IBaseService;
import com.ciglink.common.web.entity.service.ISubBaseService;
import com.ciglink.common.web.entity.service.impl.handle.SubBaseHandleServiceImpl;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 服务层 主子基类实现通用数据处理
 *
 * @param <D>  Dto
 * @param <DG> DtoManager
 * @param <DM> DtoMapper
 * @param <S>  SubDto
 * @param <SS> SubService
 * @param <SM> SubMapper
 */
public abstract class SubBaseServiceImpl<D extends SubBaseEntity<S>, DG extends SubBaseDao<D, DM, S, SM>, DM extends SubBaseMapper<D, S>, S extends BaseEntity, SS extends IBaseService<S>, SM extends BaseMapper<S>> extends SubBaseHandleServiceImpl<D, DG, DM, S, SS, SM> implements ISubBaseService<D, S> {

    /**
     * 根据外键查询子数据对象集合 | 子数据
     *
     * @param foreignKey 外键
     * @return 子数据对象集合
     */
    @Override
    public List<S> selectSubByForeignKey(Serializable foreignKey) {
        return baseDao.selectSubByForeignKey(foreignKey);
    }

    /**
     * 新增数据对象
     *
     * @param d 数据对象
     * @return 结果
     */
    @Override
    @DSTransactional
    public int insert(D d) {
        int i = baseDao.insert(d);
        if (CollUtil.isNotEmpty(d.getSubList())) {
            setForeignKey(d.getSubList(), d);
            subService.insertBatch(d.getSubList());
        }
        return i;
    }

    /**
     * 修改数据对象
     * 同步停用 子数据状态
     *
     * @param d 数据对象
     * @return 结果
     * @see #UUSHandleSubStatusCheck(Serializable, String) 主子树型 检查归属数据状态
     */
    @Override
    @DSTransactional
    public int update(D d) {
        UUSHandleSubStatusCheck(d.getId(), d.getStatus());
        return super.update(d);
    }

    /**
     * 修改数据对象状态
     * 同步停用 子数据状态
     *
     * @param id     Id
     * @param status 状态
     * @return 结果
     * @see #UUSHandleSubStatusCheck(Serializable, String) 主子树型 检查归属数据状态
     */
    @Override
    @DSTransactional
    public int updateStatus(Serializable id, String status) {
        UUSHandleSubStatusCheck(id, status);
        return super.updateStatus(id, status);
    }

    /**
     * 根据Id删除数据对象
     * 同步删除 子数据
     *
     * @param id Id
     * @return 结果
     */
    @Override
    @DSTransactional
    public int deleteById(Serializable id) {
    	baseDao.deleteSub(id);
        return super.deleteById(id);
    }

    /**
     * 根据Id集合删除数据对象
     * 同步删除 子数据
     *
     * @param idList Id集合
     * @return 结果
     */
    @Override
    @DSTransactional
    public int deleteByIds(Collection<? extends Serializable> idList) {
        for (Serializable Id : idList)
        	baseDao.deleteSub(Id);
        return super.deleteByIds(idList);
    }

    /**
     * 根据Id修改其归属数据的状态
     *
     * @param id     Id
     * @param status 状态
     * @return 结果
     */
    @Override
    public int updateSubStatus(Serializable id, String status) {
        return baseDao.updateSubStatus(id, status);
    }

    /**
     * 根据Id删除其归属数据
     *
     * @param id Id
     * @return 结果
     */
    @Override
    public int deleteSub(Serializable id) {
        return baseDao.deleteSub(id);
    }

    /**
     * 校验是否存在子数据
     *
     * @param id Id
     * @return 结果 | true/false 存在/不存在
     */
    @Override
    public boolean checkExistSub(Serializable id) {
        return ObjectUtil.isNotNull(baseDao.checkExistSub(id));
    }

    /**
     * 校验是否存在启用（正常状态）的子数据
     *
     * @param id Id
     * @return 结果 | true/false 存在/不存在
     */
    @Override
    public boolean checkExistNormalSub(Serializable id) {
        return ObjectUtil.isNotNull(baseDao.checkExistNormalSub(id));
    }

}
