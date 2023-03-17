package com.wang.model.common;

import java.io.Serializable;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import lombok.Data;

/**
 * 分页排序通用Dto
 * @author jyw
 * @date 2018/9/10 10:10
 */
@Data
public class BaseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 当前页数 */
    @Min(value = 1, message = "当前页数不能小于1")
    private Integer page;

    /** 每页条数 */
    @Min(value = 1, message = "每页条数不能小于1")
    @Max(value = 50, message = "每页条数不能大于50")
    private Integer rows;

    /** 排序的列名 */
    private String sidx;

    /** 排序规则(DESC或者ESC) */
    private String sord;
}
