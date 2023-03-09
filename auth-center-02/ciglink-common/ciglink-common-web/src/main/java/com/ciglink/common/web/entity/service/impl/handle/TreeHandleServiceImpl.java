package com.ciglink.common.web.entity.service.impl.handle;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.ciglink.common.core.constant.basic.BaseConstants;
import com.ciglink.common.core.web.entity.base.TreeEntity;
import com.ciglink.common.web.entity.dao.TreeDao;
import com.ciglink.common.web.entity.mapper.TreeMapper;
import com.ciglink.common.web.entity.service.impl.BaseServiceImpl;

import java.io.Serializable;

/**
 * 服务层 操作方法 树型实现通用数据处理
 *
 * @param <D>  Dto
 * @param <DG> DtoManager
 * @param <DM> DtoMapper
 * 
 */
public class TreeHandleServiceImpl<D extends TreeEntity<D>, DG extends TreeDao<D, DM>, DM extends TreeMapper<D>> extends BaseServiceImpl<D, DG, DM> {

    /**
     * 新增/修改 树型 检查父级状态
     * 是否启用，非启用则启用
     *
     * @param parentId 父级Id
     * @param status   状态
     * @see com.ciglink.common.web.entity.service.impl.TreeServiceImpl#insert(TreeEntity)
     * @see com.ciglink.common.web.entity.service.impl.TreeServiceImpl#update(TreeEntity)
     */
    protected void AUHandleParentStatusCheck(Serializable parentId, String status) {
        if (ObjectUtil.equals(BaseConstants.Status.NORMAL.getCode(), status)) {
            BaseConstants.Status parentStatus = checkStatus(parentId);
            if (BaseConstants.Status.DISABLE == parentStatus)
            	baseDao.updateStatus(parentId, BaseConstants.Status.NORMAL.getCode());
        }
    }

    /**
     * 修改状态 树型 检查父级状态
     * 是否启用，非启用则启用
     *
     * @param Id     Id
     * @param status 状态
     * @see com.ciglink.common.web.entity.service.impl.TreeServiceImpl#updateStatus(Serializable, String)
     */
    protected void USHandelParentStatusCheck(Serializable Id, String status) {
        D nowD = baseDao.selectById(Id);
        if (ObjectUtil.equals(BaseConstants.Status.NORMAL.getCode(), status)
                && BaseConstants.Status.DISABLE == checkStatus(nowD.getParentId()))
        	baseDao.updateStatus(nowD.getParentId(), BaseConstants.Status.NORMAL.getCode());
    }

    /**
     * 新增 树型 设置祖籍
     *
     * @param d 数据对象 | parentId 父Id
     * @see com.ciglink.common.web.entity.service.impl.TreeServiceImpl#insert(TreeEntity)
     */
    protected void AHandleAncestorsSet(D d) {
        if (ObjectUtil.equals(BaseConstants.TOP_ID, d.getParentId())) {
            d.setAncestors(String.valueOf(BaseConstants.TOP_ID));
        } else {
            D parent = baseDao.selectById(d.getParentId());
            d.setAncestors(parent.getAncestors() + StrUtil.COMMA + d.getParentId());
        }
    }

    /**
     * 修改 树型 检验祖籍是否变更
     * 是否变更，变更则同步变更子节点祖籍
     *
     * @param d 数据对象 | id id | parentId 父Id
     * @see com.ciglink.common.web.entity.service.impl.TreeServiceImpl#update(TreeEntity)
     */
    protected void UHandleAncestorsCheck(D d) {
        D original = baseDao.selectById(d.getId());
        if (!ObjectUtil.equals(d.getParentId(), original.getParentId())) {
            String oldAncestors = original.getAncestors();
            if (ObjectUtil.equals(BaseConstants.TOP_ID, d.getParentId()))
                d.setAncestors(String.valueOf(BaseConstants.TOP_ID));
            else {
                D parent = baseDao.selectById(d.getParentId());
                d.setAncestors(parent.getAncestors() + StrUtil.COMMA + d.getParentId());
            }
            baseDao.updateChildrenAncestors(d.getId(), d.getAncestors(), oldAncestors);
        }
    }

    /**
     * 修改/修改状态 树型 检查子节点状态
     * 是否变更，变更则同步禁用子节点
     *
     * @param id     id
     * @param status 状态
     * @see com.ciglink.common.web.entity.service.impl.TreeServiceImpl#update(TreeEntity)
     * @see com.ciglink.common.web.entity.service.impl.TreeServiceImpl#updateStatus(Serializable, String)
     */
    protected void UUSChildrenStatusCheck(Serializable id, String status) {
        D original = baseDao.selectById(id);
        if (ObjectUtil.notEqual(original.getStatus(), status)
                && ObjectUtil.equals(BaseConstants.Status.DISABLE.getCode(), status)
                && ObjectUtil.isNotNull(baseDao.checkHasNormalChild(id))) {
        	baseDao.updateChildrenStatus(id, BaseConstants.Status.DISABLE.getCode());
        }
    }
}
