package com.jt.baimo.pw.web;

import com.jt.baimo.pw.service.JpushService;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.ResultData;
import com.jt.baimo.pw.vo.SystemPushVo;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * 极光推送模块
 * @author Ms.WenJing
 * @Description:
 * @Date 2019/9/17 18:38
 */
@RequestMapping("/jpush")
@RestController
@Validated
public class JpushController {
    @Autowired
    private JpushService jpushService;

    @ApiOperation(tags = "极光推送模块",value = "1.系统消息推送(审核)-王文静")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content",value = "推送内容",paramType = "query",required = true,example = "1"),
            @ApiImplicitParam(name = "rUid",value = "接收推送用户UID",paramType = "query",required = true,example = "ac5862a79929d77c2bfd32ea39b0743a"),
            @ApiImplicitParam(name = "type",value = "推送类型:1.游戏大神审核通过2.游戏大神审核不通过3.动态不通过4.照片不通过" +
                    "5.头像不通过6.昵称屏蔽7.语音不通过8.个人介绍不通过9.发布动态13.已取消客服取消",paramType = "query",required = true,example = "1"),
            @ApiImplicitParam(name = "id",value = "订单ID",paramType = "query",required = false,example = "11"),
    })
    @GetMapping("/systemPush")
    public ResultData systemPush(String rUid, String content, Integer type){
        return jpushService.systemPush(rUid,content,type);
    }

    @ApiOperation(tags = "极光推送模块",value = "2.系统消息推送(全部人)-王文静")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "content",value = "推送内容",paramType = "query",required = true,example = "1"),
            @ApiImplicitParam(name = "type",value = "推送类型:10.系统通知",paramType = "query",required = true,example = "1")
    })
    @GetMapping("/allPush")
    public ResultData allPush(String content,Integer type){
        return jpushService.allPush(content,type);
    }

    @ApiOperation(tags = "极光推送模块",value = "3.系统推送列表-王文静")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lastTime",value = "最后一条数据的时间",paramType = "query",required = false,example = "1"),
            @ApiImplicitParam(name = "size",value = "每页显示的个数",paramType = "query", dataType = "int",required = true,example = "1")
    })

    @GetMapping("/querySystemMsgList")
    public ResultData<List<SystemPushVo>> querySystemMsgList(Date lastTime, @NotNull Integer size, HttpServletRequest request){
        return jpushService.querySystemMsgList(lastTime, size, AppUtil.getUserId(request));
    }

}
