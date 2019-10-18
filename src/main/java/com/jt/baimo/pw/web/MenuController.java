package com.jt.baimo.pw.web;

import com.jt.baimo.pw.service.MenuService;
import com.jt.baimo.pw.vo.MenuVo;
import com.jt.baimo.pw.vo.ResultData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author Ms.WenJing
 * @Description:
 * @Date 2019/9/23 19:28
 */
@RestController
@RequestMapping("menu")
@Validated
public class MenuController {

    @Autowired
    private MenuService menuService;
    @ApiOperation(tags = "菜单模块",value = "1更多菜单-王文静")
    @ApiImplicitParam(name = "type",value = "位置:1.首页2.更多3工具",paramType = "query",required = true,example = "1")
    @GetMapping("/queryMenu")
    public ResultData<List<MenuVo>> userDetails(Integer type, HttpServletRequest request) {
        return menuService.queryMenu(type);
    }
}
