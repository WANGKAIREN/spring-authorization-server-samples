package com.ciglink.common.web.entity.dao.handle;

import com.ciglink.common.core.web.entity.base.TreeEntity;
import com.ciglink.common.web.entity.dao.BaseDao;
import com.ciglink.common.web.entity.mapper.TreeMapper;

/**
 * 数据封装层 操作方法 树型通用数据处理
 *
 * @param <D>  Dto
 * @param <DM> DtoMapper
 * 
 */
public class TreeHandleManager<D extends TreeEntity<D>, DM extends TreeMapper<D>> extends BaseDao<D, DM> {
}
