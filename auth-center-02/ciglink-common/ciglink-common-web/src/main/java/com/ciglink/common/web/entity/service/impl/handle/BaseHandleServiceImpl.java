package com.ciglink.common.web.entity.service.impl.handle;

import com.ciglink.common.core.web.entity.base.BaseEntity;
import com.ciglink.common.web.entity.dao.BaseDao;
import com.ciglink.common.web.entity.mapper.BaseMapper;
import com.github.jeffreyning.mybatisplus.base.MppBaseMapper;
import com.github.jeffreyning.mybatisplus.service.MppServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 服务层 操作方法 基类实现通用数据处理
 *
 * @param <D>  Dto
 * @param <DG> DtoManager
 * @param <DM> DtoMapper
 * 
 */
public class BaseHandleServiceImpl<D extends BaseEntity, DG extends BaseDao<D, DM>, DM extends BaseMapper<D>>
		extends MppServiceImpl<MppBaseMapper<D>, D> {

	@Autowired
	protected DG baseDao;
}
