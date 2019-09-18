package com.zxk.demofacade.controller;

import com.zxk.demofacade.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@Api("测试学习接口")
public class TestController {

    @Autowired
    private TestService testService;

    /**
     * 测试读取参数
     * @return
     */
    @ApiOperation(value = "读取配置文件接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "输入姓名", dataType = "String", paramType = "query")
    })
    @RequestMapping(value="/v1.0/facade/hi", method = RequestMethod.GET)
    public @ResponseBody
    String home(@RequestParam(value = "name") String name) {

        return testService.home(name);
    }
}
