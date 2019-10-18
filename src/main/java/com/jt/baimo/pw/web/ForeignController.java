package com.jt.baimo.pw.web;

import cn.hutool.system.UserInfo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.annotation.JsonView;

import com.jt.baimo.pw.constant.TagCst;
import com.jt.baimo.pw.mapper.UserMapper;
import com.jt.baimo.pw.model.User;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.util.Assert;
import com.jt.baimo.pw.vo.ForeignUserInfoVo;
import com.jt.baimo.pw.vo.ResultData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;

/**
 * @author Forever丶诺
 * @data 2019/5/30 20:38
 */
@RestController
@RequestMapping("foreign")
public class ForeignController {

    @Autowired
    private UserMapper userMapper;


    @Autowired
    private ModelMapper modelMapper;

    @GetMapping("userInfo")
    @ApiOperation(tags = TagCst.HOME, value = "五五来客获取用户id-沙增达", hidden = true)
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "userId", value = "用户id", dataType = "int"),
            }
    )
    public ResultData getUserInfo(@NotBlank String userId, HttpServletRequest request) {
        Assert.isNotEquals(userId, AppUtil.getUserId(request), "用户验证不通过");
        User user = userMapper.selectById(userId);
        ForeignUserInfoVo foreignUserInfoVo = modelMapper.map(user, ForeignUserInfoVo.class);
        return new ResultData<>().setData(foreignUserInfoVo);
    }

}
