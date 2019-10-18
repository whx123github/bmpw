package com.jt.baimo.pw.web;

import com.jt.baimo.pw.constant.TagCst;
import com.jt.baimo.pw.service.PageMyService;
import com.jt.baimo.pw.todo.UserEditTo;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.MyCommonInfoVo;
import com.jt.baimo.pw.vo.ResultData;
import com.jt.baimo.pw.vo.UserSimpleInfoVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author Forever丶诺
 * @data 2019/9/18 9:09
 */
@RestController
@Validated
@RequestMapping("my")
public class PageMyController {


    @Autowired
    private PageMyService pageMyService;

    @GetMapping("simpleInfo")
    @ApiOperation(tags = TagCst.MY, value = "01-显示个人简单信息-沙增达")
    public ResultData<UserSimpleInfoVo> getUserSimpleInfo(HttpServletRequest request) {
        return pageMyService.getUserSimpleInfo(AppUtil.getUserId(request));
    }

    @GetMapping("commonInfo")
    @ApiOperation(tags = {TagCst.MY_WALLET, TagCst.HOME_ONE_ALBUM, TagCst.MY,TagCst.NEW_VERSION_3_UPDATE}, value = "00-获取用户的通用信息-沙增达",notes = "(版本3修改:新增返回字段:status 判断用户的状态)")
    public ResultData<MyCommonInfoVo> getCommonInfo(HttpServletRequest request) {
        return pageMyService.getCommonInfo(AppUtil.getUserId(request));
    }


    @DeleteMapping("voice")
    @ApiOperation(tags = {TagCst.MY, TagCst.HOME_ONE_ALBUM, TagCst.MY}, value = "00-删除用户语音-沙增达")
    public ResultData deleteMyVoice(HttpServletRequest request) {
        return pageMyService.deleteMyVoice(AppUtil.getUserId(request));
    }


    @PutMapping("voice")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "url", value = "语音的地址", paramType = "form", required = true)
    })
    @ApiOperation(tags = {TagCst.MY, TagCst.HOME_ONE_ALBUM, TagCst.MY}, value = "00-上传用户语音-沙增达")
    public ResultData updateMyVoice(@NotBlank String url, HttpServletRequest request) {
        return pageMyService.updateMyVoice(AppUtil.getUserId(request), url);
    }


    @PutMapping("userInfo")
    @ApiOperation(tags = {TagCst.MY,TagCst.NEW_VERSION_3_UPDATE}, value = "01-编辑资料-01-总-沙增达",notes = "(版本3修改:新增参数:sex)")
    public ResultData editUserInfo(HttpServletRequest request, @Valid @RequestBody UserEditTo userEditTo, BindingResult result) {
        String userId = AppUtil.getUserId(request);
        return pageMyService.editUserInfo(userId, userEditTo);
    }

}
