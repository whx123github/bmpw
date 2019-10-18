package com.jt.baimo.pw.web;

import com.jt.baimo.pw.constant.TagCst;
import com.jt.baimo.pw.model.ListType;
import com.jt.baimo.pw.service.ListTypeService;
import com.jt.baimo.pw.service.PageMySkillPwService;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.*;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/20 13:33
 */
@RestController
@Validated
@RequestMapping("skillPw")
public class PageMySkillPwController {


    @Autowired
    private PageMySkillPwService pageMySkillPwService;


    @Autowired
    private ListTypeService listTypeService;




    @GetMapping("targetSkills/{targetId}")
    @ApiOperation(tags = {TagCst.HOME_ONE_SKILL,TagCst.NEW_VERSION_3_ADD}, value = "01-获取对方服务的游戏技能-沙增达")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "targetId", value = "对方的id", paramType = "path", required = true)
    })
    public ResultData<List<MySkillPwTargetSkillVo>> getTargetSkills(@PathVariable @NotBlank String targetId) {
        return pageMySkillPwService.getTargetSkills(targetId);
    }


    @GetMapping("buyPwList")
    @ApiOperation(tags = {TagCst.My_Skill_PW}, value = "01-00-我购买的陪玩列表-沙增达")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "pageSize", value = "每页显示的个数", required = true, dataType = "int", paramType = "query"),
                    @ApiImplicitParam(name = "lastTime", value = "最后一个数据的addTime", dataType = "dateTime"),
            }
    )
    public ResultData<List<MySkillPwBuyPwListVo>> getBuyPwList(@NotNull Integer pageSize, Date lastTime, HttpServletRequest request) {
        return pageMySkillPwService.getBuyPwList(pageSize, lastTime, AppUtil.getUserId(request));
    }


    @GetMapping("sellPwList")
    @ApiOperation(tags = {TagCst.My_Skill_PW}, value = "02-00-我出售的陪玩列表-沙增达")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "pageSize", value = "每页显示的个数", required = true, dataType = "int", paramType = "query"),
                    @ApiImplicitParam(name = "lastTime", value = "最后一个数据的addTime", dataType = "dateTime"),
            }
    )
    public ResultData<List<MySkillPwSellPwListVo>> getSellPwList(@NotNull Integer pageSize, Date lastTime, HttpServletRequest request) {
        return pageMySkillPwService.getSellPwList(pageSize, lastTime, AppUtil.getUserId(request));
    }


    @GetMapping("onePwInfo/{id}")
    @ApiOperation(tags = {TagCst.My_Skill_PW}, value = "03-获取某个陪玩的订单的详情-沙增达")
    @ApiImplicitParams(
            {
                    @ApiImplicitParam(name = "id", value = "主键的id", required = true, dataType = "string", paramType = "path"),

            }
    )
    public ResultData<MySkillPwOnePwInfoVo> getOnePwInfo(@NotBlank @PathVariable String id) {
        return pageMySkillPwService.getOnePwInfo(id);
    }


    @PutMapping("sellerTakePwOrder")
    @ApiOperation(tags = {TagCst.My_Skill_PW}, value = "02-01-陪玩者接受陪玩订单-沙增达")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单主键id", paramType = "form", required = true)
    })
    public ResultData sellerTakePwOrder(@NotBlank String id,HttpServletRequest request) {
        return pageMySkillPwService.sellerTakePwOrder(id,AppUtil.getUserId(request));
    }

    @PutMapping("sellerStartPwOrder")
    @ApiOperation(tags = {TagCst.My_Skill_PW}, value = "02-02-陪玩者开始游戏(订单)-沙增达")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单主键id", paramType = "form", required = true),
            @ApiImplicitParam(name = "pwImgs", value = "陪玩的图片证明", paramType = "form", required = true)
    })
    public ResultData sellerStartPwOrder(@NotBlank String id, @NotBlank String pwImgs,HttpServletRequest request) {
        return pageMySkillPwService.sellerStartPwOrder(id, pwImgs,AppUtil.getUserId(request));
    }

    @PutMapping("sellerFinishPwOrder")
    @ApiOperation(tags = {TagCst.My_Skill_PW}, value = "02-03-陪玩者完成服务(订单)-沙增达")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单主键id", paramType = "form", required = true)
    })
    public ResultData sellerFinishPwOrder(@NotBlank String id,HttpServletRequest request) {
        return pageMySkillPwService.sellerFinishPwOrder(id,AppUtil.getUserId(request));
    }



    @GetMapping("cancelReasonType")
    @ApiOperation(tags = {TagCst.My_Skill_PW}, value = "01-01-获取取消订单原因的类型-沙增达")
    public ResultData<List<ListType>> getCancelReasonType() {
        return listTypeService.getDataByType(1);
    }


    @PutMapping("buyerCancelPwOrder")
    @ApiOperation(tags = {TagCst.My_Skill_PW}, value = "01-02-玩家(购买者)取消订单-沙增达")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单主键id", paramType = "form", required = true),
            @ApiImplicitParam(name = "cancelReasonType", value = "取消订单原因", paramType = "form", required = true),
            @ApiImplicitParam(name = "cancelReason", value = "取消订单原因", paramType = "form", required = true),
    })
    public ResultData buyerCancelPwOrder(@NotBlank String id,@NotNull Integer cancelReasonType,@Length(max = 100) String cancelReason,HttpServletRequest request) {
        return pageMySkillPwService.buyerCancelPwOrder(id,cancelReasonType,cancelReason,AppUtil.getUserId(request));
    }

    @PutMapping("buyerFinishPwOrder")
    @ApiOperation(tags = {TagCst.My_Skill_PW}, value = "01-03-玩家(购买者)确认完成-沙增达")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单主键id", paramType = "form", required = true)
    })
    public ResultData buyerFinishPwOrder(@NotBlank String id,HttpServletRequest request) {
        return pageMySkillPwService.buyerFinishPwOrder(id,AppUtil.getUserId(request));
    }








}
