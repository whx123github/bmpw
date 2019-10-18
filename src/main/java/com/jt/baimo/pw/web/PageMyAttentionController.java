package com.jt.baimo.pw.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jt.baimo.pw.constant.TagCst;
import com.jt.baimo.pw.model.Attention;
import com.jt.baimo.pw.service.AttentionService;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.AttentionPageListVo;
import com.jt.baimo.pw.vo.ResultData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.jt.baimo.pw.constant.TagCst.MY_ATTENTION;


/**
 * 关注接口
 *
 * @author Administrator
 */
@RestController
@RequestMapping("attention")
@Validated
public class PageMyAttentionController {

    @Autowired
    private AttentionService attentionService;


    /**
     * 确认关注
     *
     * @return
     */
    @PostMapping
    @ApiOperation(tags = {MY_ATTENTION, TagCst.HOME_ONE}, value = "01-确认关注-沙增达")
    @ApiImplicitParam(name = "targetId", required = true, paramType = "query", value = "对方的ID")
    public ResultData addAttention(HttpServletRequest request,
                                   @NotBlank String targetId) {
        return attentionService.addAttention(targetId, AppUtil.getUserId(request));
    }


    @DeleteMapping("{id}")
    @ApiOperation(tags = {MY_ATTENTION}, value = "02-取消关注(根据主键id)-沙增达")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "主键id", paramType = "path", required = true, example = "1")
    })
    public ResultData deleteById(@PathVariable Integer id) {
        attentionService.removeById(id);
        return new ResultData();
    }

    @DeleteMapping("targetId/{targetId}")
    @ApiOperation(tags = {MY_ATTENTION, TagCst.HOME_ONE}, value = "02-取消关注(根据targetId)-沙增达")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "targetId", value = "目标id", paramType = "path", required = true, example = "1")
    })
    public ResultData deleteByTargetId(@PathVariable String targetId, HttpServletRequest request) {
        attentionService.remove(new LambdaQueryWrapper<Attention>().eq(Attention::getTargetId, targetId).eq(Attention::getUid, AppUtil.getUserId(request)));
        return new ResultData();
    }

    @GetMapping("pageList")
    @ApiOperation(tags = MY_ATTENTION, value = "03-获取关注的列表-沙增达")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页显示的个数", paramType = "query", required = true, example = "10"),
            @ApiImplicitParam(name = "lastAttentionId", value = "最后一条数据的attentionId", paramType = "query")
    })
    public ResultData<AttentionPageListVo> pageList(@NotNull Integer pageSize, Integer lastAttentionId, HttpServletRequest request) {
        return attentionService.pageList(AppUtil.getUserId(request), pageSize, lastAttentionId);
    }


}
