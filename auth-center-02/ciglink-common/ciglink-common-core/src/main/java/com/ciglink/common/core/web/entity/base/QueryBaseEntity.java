package com.ciglink.common.core.web.entity.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: Neihan
 * @Date: 2022/09/16/15:59
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class QueryBaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("current_page")
    private Integer currentPage;

    @JsonProperty("page_size")
    private Integer pageSize;
}
