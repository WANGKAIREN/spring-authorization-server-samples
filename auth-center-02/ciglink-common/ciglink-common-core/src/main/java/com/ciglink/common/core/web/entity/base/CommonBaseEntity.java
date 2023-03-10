package com.ciglink.common.core.web.entity.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.OrderBy;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * Base 普通基类
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class CommonBaseEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;
    /** 名称 */
    @TableField(value = "name", exist = false)
    private String name;

    /** 状态（0 启用 1 禁用） */
    @TableField(value = "status", exist = false)
    private String status;

    /** 显示顺序 */
    @TableField(value = "sort", exist = false)
    @OrderBy(asc = true, sort = 10)
    protected Integer sort;

    /** 备注 */
    @TableField(value = "remark", exist = false)
    private String remark;

    /** 创建者Id */
    @TableField(value = "create_by", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private Long createBy;

    /** 创建时间 */
    @OrderBy(sort = 20)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "create_time", fill = FieldFill.INSERT, updateStrategy = FieldStrategy.NEVER)
    private LocalDateTime createTime;

    /** 更新者Id */
    @TableField(value = "update_by", fill = FieldFill.INSERT_UPDATE, insertStrategy = FieldStrategy.NEVER)
    private Long updateBy;

    /** 更新时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /** 删除标志 */
    @TableField(value = "del_flag", select = false)
    @TableLogic(value = "0", delval = "1")
    private Long delFlag;

    /** 创建者 */
    @TableField(exist = false)
    private String createName;

    /** 创建者 */
    @TableField(exist = false)
    private String updateName;
}