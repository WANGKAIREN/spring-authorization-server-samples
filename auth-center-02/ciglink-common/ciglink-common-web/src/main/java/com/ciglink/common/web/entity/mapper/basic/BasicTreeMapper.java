package com.ciglink.common.web.entity.mapper.basic;

import com.ciglink.common.core.web.entity.base.TreeEntity;

/**
 * 数据层 树型基类数据处理
 * 仅用于基类调用 禁止被继承使用
 */
public interface BasicTreeMapper<D> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<TreeEntity<D>> {
}
