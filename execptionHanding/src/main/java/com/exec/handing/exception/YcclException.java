package com.exec.handing.exception;

import com.exec.handing.enums.ResultEnum;
import lombok.Data;
import lombok.Getter;

/**
 * @author LiFang
 * @version 1.0
 * @since 2022/3/18 15:03
 */
@Getter
public class YcclException extends RuntimeException {
    // 异常状态码
    private Integer status;

    /**
     * @param status
     * @return
     * @description 通过状态码创建异常对象
     * @author LiFang
     * @date 2022/3/18 15:10
     */
    public YcclException(Integer status) {
        this.status = status;
    }

    /**
     * @param message, status
     * @return
     * @description 通过状态码和消息创建异常对象
     * @author LiFang
     * @date 2022/3/18 15:10
     */
    public YcclException(Integer status, String message) {
        super(message);
        this.status = status;
    }

    public YcclException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.status = resultEnum.getCode();
    }

    public YcclException(String msg) {
        super(msg);
    }

    @Override
    public String toString() {
        return "YcclException{" +
                "status=" + status +
                "message=" + this.getMessage() +
                '}';
    }
}
