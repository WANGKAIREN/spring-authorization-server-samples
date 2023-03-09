package com.ciglink.common.web.entity.mapper;

import com.ciglink.common.core.web.entity.base.BaseEntity;
import com.ciglink.common.core.web.entity.base.SubTreeEntity;

/**
 * 数据层 主子树型通用数据处理
 *
 * @param <D> Dto
 * @param <S> SubDto
 */
public interface SubTreeMapper<D extends SubTreeEntity<D, S>, S extends BaseEntity> extends TreeMapper<D>{
}
