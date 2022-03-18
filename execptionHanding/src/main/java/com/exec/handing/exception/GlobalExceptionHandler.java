package com.exec.handing.exception;

import com.exec.handing.enums.ResultEnum;
import com.exec.handing.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.nio.file.AccessDeniedException;
import java.util.Objects;

/**
 * @author LiFang
 * @version 1.0
 * @since 2022/3/18 15:28
 * SpringBoot AOP 配置全局异常
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * 功能描述：处理所有不可知的异常
     *
     * @param e 异常 Throwable(异常的根类)
     * @return 异常对象信息
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Result<String>> handleException(Throwable e) {
        // 打印堆栈信息
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Result.<String>error(ResultEnum.SEVER_ERROR.getCode(), ResultEnum.SEVER_ERROR.getMsg()));
    }

    /**
     * 功能描述：处理自定义异常
     *
     * @param e 自定义异常
     * @return restful风格的异常信息
     */
    @ExceptionHandler(value = YcclException.class)
    public ResponseEntity<Result<String>> badRequestException(YcclException e) {
        log.error(e.getMessage(), e);
        //默认到后端的请求，状态码都为200，自定义的异常由封装的code去控制
        return ResponseEntity.status(HttpStatus.OK).body(Result.<String>error(e.getMessage()));
    }

    /**
     * description: security的角色权限不足异常
     *
     * @param e 权限不足异常
     * @return 200状态码 403自定义code
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Result<String>> handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(Result.<String>error(ResultEnum.IDENTITY_NOT_POW.getCode(),
                ResultEnum.IDENTITY_NOT_POW.getMsg()));
    }

    /**
     * description:处理所有接口数据验证异常
     *
     * @param e 接口数据验证异常
     * @return 统一异常结果处理
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e);
        String[] str = Objects.requireNonNull(e.getBindingResult().getAllErrors().get(0).getCodes())[1].split("\\.");
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        String msg = "不能为空";
        if (msg.equals(message)) {
            message = str[1] + ":" + message;
        }
        return ResponseEntity.status(HttpStatus.OK).body(Result.<String>error(ResultEnum.PARAM_VERIFY_FAILURE.getCode(),
                message));
    }
}
