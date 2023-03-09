package com.ciglink.common.web.entity.service;

import com.ciglink.common.core.web.entity.base.BaseEntity;
import com.ciglink.common.core.web.entity.base.SubTreeEntity;

/**
 * 服务层 主子树型通用数据处理
 *
 * @param <D> Dto
 * @param <S> SubDto
 */
public interface ISubTreeService<D extends SubTreeEntity<D, S>, S extends BaseEntity> extends ITreeService<D>, ISubService<D, S> {
}
