package com.ciglink.common.web.entity.service;

import com.ciglink.common.core.web.entity.base.BaseEntity;
import com.ciglink.common.core.web.entity.base.SubBaseEntity;

/**
 * 服务层 主子基类通用数据处理
 *
 * @param <D> Dto
 * @param <S> SubDto
 */
public interface ISubBaseService<D extends SubBaseEntity<S>, S extends BaseEntity> extends IBaseService<D>,ISubService<D,S> {
}
