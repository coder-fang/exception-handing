package com.exec.handing.test;

import com.exec.handing.enums.ResultEnum;
import com.exec.handing.exception.YcclException;
import com.exec.handing.result.Result;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author LiFang
 * @version 1.0
 * @since 2022/3/18 16:29
 * 测试统一异常捕获功能
 */
@RestController
public class TestResultController {
    @GetMapping("/getTestResult/{id}")
    public Result testObjectResult(@PathVariable("id") Long id) {
        Map<String, Integer> map = new HashMap<>();
        map.put("小明", 16);
        map.put("小红", 17);
        map.put("小强", 18);
        if (ObjectUtils.isEmpty(id) || id == 0) {
            throw new YcclException(ResultEnum.PARAM_IS_BLANK);
        }
        return Result.success();
    }
}
