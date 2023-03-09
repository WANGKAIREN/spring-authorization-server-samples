package com.ciglink.common.core.web.entity.base;

import com.baomidou.mybatisplus.annotation.TableField;

import java.util.List;

/**
 * SubTree 基类
 *
 * @param <D>  Dto
 * @param <SD> SubDto
 * 
 */
public class SubTreeEntity<D, SD> extends TreeEntity<D> {

	private static final long serialVersionUID = 1L;
	
	/** 子数据集合 */
    @TableField(exist = false)
    private List<SD> subList;

    public List<SD> getSubList() {
        return subList;
    }

    public void setSubList(List<SD> subList) {
        this.subList = subList;
    }
}
