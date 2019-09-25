package com.zxk.demofacade.controller;

import com.zxk.democommonservice.feigin.test.FeiginTestService;
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
      /**
       * 暂时废弃此类型该注入，使用demo-common-service 打包类
       * 需修改 @EnableFeignClients(basePackages = "com.zxk.demofacade.service")
       * **/
//    @Autowired
//    private TestService testService;

      @Autowired
      private FeiginTestService feiginTestService;

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

//        return testService.home(name);
        return feiginTestService.home(name);
    }
}
