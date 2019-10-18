package com.jt.baimo.pw.web;

import com.jt.baimo.pw.model.Banner;
import com.jt.baimo.pw.service.BannerService;
import com.jt.baimo.pw.service.DynamicService;
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
 * @Date 2019/9/19 9:45
 */
@RestController
@RequestMapping("banner")
@Validated
public class BannerController {

    @Autowired
    private BannerService bannerService;

    @ApiOperation(tags = "Banner模块",value = "Banner列表(表单)-王文静")
    @ApiImplicitParam(name = "type",value = "位置:1广场(陪玩)-1广场(跨城)2首页(陪玩)-2首页(跨城)3首页中间(陪玩)-3首页中间(跨城)"
            ,paramType = "query",required = true,example = "1")
    @GetMapping("/queryBannerList")
    public ResultData<List<Banner>> queryBannerList(Integer type,HttpServletRequest request) {
        return bannerService.queryBannerList(type);
    }
}
