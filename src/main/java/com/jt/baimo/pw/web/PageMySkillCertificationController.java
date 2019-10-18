package com.jt.baimo.pw.web;

import com.jt.baimo.pw.constant.TagCst;
import com.jt.baimo.pw.model.Skill;
import com.jt.baimo.pw.service.PageMySkillCertificationService;
import com.jt.baimo.pw.todo.AuthenticationTo;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.jt.baimo.pw.constant.TagCst.*;

/**
 * 我的认证页面
 *
 * @author Forever丶诺
 * @data 2019/9/18 17:52
 */
@RestController
@Validated
@RequestMapping("skillCertification")
public class PageMySkillCertificationController {


    @Autowired
    private PageMySkillCertificationService pageMySkillCertificationService;


    @GetMapping("status")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gwTypeId", value = "顾问分类的id", paramType = "query")
    })
    @ApiOperation(tags = {TagCst.MY_CERTIFICATION, NEW_VERSION_3_UPDATE}, value = "01-获取认证状态-沙增达", notes = "(修改:添加一个返回type参数 status : -1:没有认证. 1待审核，2已通过，3未通过),(版本3修改:新增参数:gwTypeId)")
    public ResultData getCertificationStatus(@NotNull Integer gwTypeId, HttpServletRequest request) {
        return pageMySkillCertificationService.getCertificationStatus(AppUtil.getUserId(request), gwTypeId);
    }

    @GetMapping("skillList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gwTypeId", value = "顾问大分类的id", paramType = "query")
    })
    @ApiOperation(tags = {TagCst.MY_CERTIFICATION, NEW_VERSION_3_UPDATE}, value = "02-根据顾问分类id 获取技能列表-沙增达", notes = "(版本3修改:新增参数:gwTypeId)")
    public ResultData<List<Skill>> getSkillList(@NotNull Integer gwTypeId) {
        return pageMySkillCertificationService.getSkillList(gwTypeId);
    }

    @PostMapping
    @ApiOperation(tags = {TagCst.MY_CERTIFICATION, NEW_VERSION_3_UPDATE}, value = "03-新增技能认证-沙增达", notes = "(版本3修改:新增参数:gwTypeId,新增参数:image2)")
    public ResultData addSkillCertification(HttpServletRequest request, @Valid @RequestBody AuthenticationTo authenticationTo, BindingResult result) {
        return pageMySkillCertificationService.addSkillCertification(AppUtil.getUserId(request), authenticationTo);
    }


    @GetMapping("mySubmit")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "gwTypeId", value = "顾问大分类的id", paramType = "query")
    })
    @ApiOperation(tags = {TagCst.MY_CERTIFICATION, NEW_VERSION_3_UPDATE}, value = "04-获取我提交的技能认证-沙增达", notes = "(版本3修改:新增参数:gwTypeId,新增返回参数:image2,gwTypeName)")
    public ResultData<SkillCertificationMySubmitVo> getMySubmit(@NotNull Integer gwTypeId, HttpServletRequest request) {
        return pageMySkillCertificationService.getMySubmit(AppUtil.getUserId(request), gwTypeId);
    }


    @GetMapping("consultantClasses")
    @ApiOperation(tags = {TagCst.MY_CERTIFICATION, NEW_VERSION_3_ADD}, value = "05-认证-获取所有的顾问大分类-沙增达")
    public ResultData<List<SkillCertificationConsultantClassesVo>> getConsultantClasses() {
        return pageMySkillCertificationService.getConsultantClasses();
    }


    @GetMapping("hasRealName")
    @ApiOperation(tags = {TagCst.MY_CERTIFICATION, NEW_VERSION_3_ADD}, value = "06-认证-查看用户是否不需要填写真实姓名-沙增达", notes = "唯一key: hasRealName,  0:没有(需要再填写真实姓名), 其他:不需要 ")
    public ResultData hasRealName(HttpServletRequest request) {
        return pageMySkillCertificationService.hasRealName(AppUtil.getUserId(request));
    }


}
