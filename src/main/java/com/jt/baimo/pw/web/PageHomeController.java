package com.jt.baimo.pw.web;

import com.jt.baimo.pw.constant.TagCst;
import com.jt.baimo.pw.service.PageHomeService;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;


/**
 * @author Forever丶诺
 * @data 2019/9/18 22:36
 */
@RestController
@RequestMapping("home")
@Validated
public class PageHomeController {


    @Autowired
    private PageHomeService pageHomeService;


    @GetMapping("consultantClasses")
    @ApiOperation(tags = {TagCst.HOME, TagCst.NEW_VERSION_3_ADD}, value = "04-首页-获取所有的顾问大分类-沙增达")
    public ResultData<List<HomeConsultantClassesVo>> getConsultantClasses() {
        return pageHomeService.getConsultantClasses();
    }

    @GetMapping("skillList")
    @ApiOperation(tags = {TagCst.HOME, TagCst.NEW_VERSION_3_UPDATE}, value = "01-获取技能列表-沙增达", notes = "(版本3修改:新增参数:gwTypeId)")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "gwTypeId", value = "顾问分类的id", dataType = "int", type = "query"),
            }
    )
    public ResultData<List<HomeSkillListVo>> getSkillList(Integer gwTypeId) {
        return pageHomeService.getSkillList(gwTypeId);
    }


    @GetMapping("userList")
    @ApiOperation(tags = {TagCst.HOME, TagCst.NEW_VERSION_3_UPDATE}, value = "02-用户列表-沙增达", notes = "(版本3修改:新增参数:gwTypeId)")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "pageSize", value = "每页显示的个数", required = true, dataType = "int", paramType = "query"),
                    @ApiImplicitParam(name = "lastDistance", value = "最后一个数据的距离", dataType = "double"),
                    @ApiImplicitParam(name = "lastUid", value = "最后一个数据的uid", dataType = "int"),
                    @ApiImplicitParam(name = "skillId", value = "技能的id", dataType = "int"),
                    @ApiImplicitParam(name = "gwTypeId", value = "顾问分类的id", dataType = "int"),
            }
    )
    public ResultData<List<HomeUserListVo>> getUserList(@NotNull Integer pageSize, Double lastDistance, Integer lastUid, Integer skillId, Integer gwTypeId, HttpServletRequest request) {
        return pageHomeService.getUserList(AppUtil.getUserId(request), pageSize, lastDistance, skillId, lastUid, gwTypeId);

    }

    @GetMapping("userSearchList")
    @ApiOperation(tags = {TagCst.HOME, TagCst.NEW_VERSION_3_UPDATE}, value = "03-用户列表搜索-沙增达",notes = "(版本3修改:新增参数:gwTypeId=3.0)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickname", value = "搜索的nickname", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "分页的每页个数", paramType = "query", required = true),
            @ApiImplicitParam(name = "lastAuditTime", value = "最后一个数据的AuditTime", paramType = "query"),
            @ApiImplicitParam(name = "lastUid", value = "最后一个数据的uid", paramType = "query"),
    })
    public ResultData<List<HomeUserSearchListVo>> searchUserList(String nickname, Integer pageSize, Date lastAuditTime, Integer lastUid, HttpServletRequest request) {
        return pageHomeService.searchUserList(nickname, pageSize, lastAuditTime, lastUid, AppUtil.getUserId(request));
    }


}
