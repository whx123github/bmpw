package com.jt.baimo.pw.web;

import com.jt.baimo.pw.constant.TagCst;
import com.jt.baimo.pw.service.PageHomeOneService;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.HomeOneUserInfoVo;
import com.jt.baimo.pw.vo.HomeSkillListVo;
import com.jt.baimo.pw.vo.ResultData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * @author Forever丶诺
 * @data 2019/9/19 12:41
 */
@RestController
@RequestMapping("homeOne")
@Validated
public class PageHomeOneController {

    @Autowired
    private PageHomeOneService pageHomeOneService;


    @GetMapping("userInfo")
    @ApiOperation(tags = {TagCst.HOME_ONE,TagCst.NEW_VERSION_3_UPDATE}, value = "01-获取某个用户的信息-沙增达",notes ="(版本3修改:新增返回字段authNum)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "targetId", value = "目标用户id", paramType = "query", required = true)
    })
    public ResultData<HomeOneUserInfoVo> getUserInfo(@NotBlank String targetId, HttpServletRequest request) {
        return pageHomeOneService.getUserInfo(targetId, AppUtil.getUserId(request));
    }

}
