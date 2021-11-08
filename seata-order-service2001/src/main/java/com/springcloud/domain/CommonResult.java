package com.springcloud.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 传给前端页面的一个数据封装
 * @author duzhihui
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {
    // 状态码  比如404之类的
    private Integer code;
    // 具体的消息，是成功还是异常
    private String message;
    // 具体的数据
    private T data;

    public CommonResult(Integer code, String message) {
        this(code, message, null);
    }
}
