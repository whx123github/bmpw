package com.jt.baimo.pw.web;

import com.jt.baimo.pw.model.Attention;
import com.jt.baimo.pw.model.Dynamic;
import com.jt.baimo.pw.model.DynamicPraise;
import com.jt.baimo.pw.service.DynamicService;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 动态模块
 * @author Ms.WenJing
 * @Description:
 * @Date 2019/9/17 18:49
 */
@RestController
@RequestMapping("dynamic")
@Validated
public class DynamicController {
    @Autowired
    private DynamicService dynamicService;

    @ApiOperation(tags = "动态模块",value = "1发布动态-王文静")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content",value = "动态内容",paramType = "query",example = "1"),
            @ApiImplicitParam(name = "picture",value = "动态配图",paramType = "query",example = "1")
    })
    @PostMapping("/addDynamic")
    public ResultData<Dynamic> addDynamic(String content, String picture, HttpServletRequest request) {
        return dynamicService.addDynamic(content,picture, AppUtil.getUserId(request));
    }

    @ApiOperation(tags = "动态模块",value = "2删除动态-王文静")
    @ApiImplicitParam(name = "id",value = "动态ID",paramType = "query", dataType = "int",required = true,example = "1")
    @DeleteMapping("/deleteDynamic")
    public ResultData<Dynamic> deleteDynamic(@NotNull Integer id, HttpServletRequest request) {
        return dynamicService.deleteDynamic(id,AppUtil.getUserId(request));
    }

    @ApiOperation(tags = "动态模块",value = "3.1动态列表(广场)-王文静")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lastId",value = "最后一条数据的id",paramType = "query", dataType = "int",required = false,example = "1"),
            @ApiImplicitParam(name = "size",value = "每页显示的个数",paramType = "query", dataType = "int",required = true,example = "1"),
            @ApiImplicitParam(name = "uid",value = "用户UID",paramType = "query", dataType = "String",required = false,example = "da3e324aa8a1f5ad135339678a46ba11")
    })
    @GetMapping("/serviceAllList")
    public ResultData<List<DynamicAllVo>> serviceAllList(Integer lastId, @NotNull Integer size,String uid, HttpServletRequest request) {
        return dynamicService.serviceAllList(lastId,size,uid,AppUtil.getUserId(request));
    }

    @ApiOperation(tags = "动态模块",value = "4动态点赞-王文静")
    @ApiImplicitParam(name = "id", required = true, paramType = "query", dataType = "Integer", value = "动态ID",example = "1")
    @PostMapping("/dynamicPraise")
    public ResultData<DynamicPraise> dynamicPraise(Integer id, HttpServletRequest request) {
        return dynamicService.dynamicPraise(id,AppUtil.getUserId(request));
    }

    @ApiOperation(tags = "动态模块",value = "5动态取消点赞-王文静")
    @ApiImplicitParam(name = "id", required = true, paramType = "query", dataType = "Integer", value = "动态ID",example = "1")
    @DeleteMapping("/cancelLike")
    public ResultData<DynamicPraise> cancelLike(Integer id, HttpServletRequest request) {
        return dynamicService.cancelLike(id,AppUtil.getUserId(request));
    }

    @ApiOperation(tags = "动态模块",value = "6某用户详情(资料)-王文静", hidden = true)
    @ApiImplicitParam(name = "uid", required = true, paramType = "query", dataType = "string", value = "用户uid",example = "da3e324aa8a1f5ad135339678a46ba11")
    @GetMapping("/userDetails")
    public ResultData<UserDetailsVo> userDetails(String uid, HttpServletRequest request) {
        return dynamicService.userDetails(uid,AppUtil.getUserId(request));
    }

    @ApiOperation(tags = "动态模块",value = "7某用户详情(关注用户)-王文静", hidden = true)
    @ApiImplicitParam(name = "uid", required = true, paramType = "query", dataType = "string", value = "用户uid",example = "da3e324aa8a1f5ad135339678a46ba11")
    @PostMapping("/userAttention")
    public ResultData<Attention> userAttention(String uid, HttpServletRequest request) {
        return dynamicService.userAttention(uid,AppUtil.getUserId(request));
    }

    @ApiOperation(tags = "动态模块",value = "8某用户详情(取消用户)-王文静", hidden = true)
    @ApiImplicitParam(name = "uid", required = true, paramType = "query", dataType = "string", value = "用户uid",example = "da3e324aa8a1f5ad135339678a46ba11")
    @DeleteMapping("/userUnsubscribe")
    public ResultData<Attention> userUnsubscribe(String uid, HttpServletRequest request) {
        return dynamicService.userUnsubscribe(uid,AppUtil.getUserId(request));
    }
}
