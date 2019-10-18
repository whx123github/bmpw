package com.jt.baimo.pw.web;

import com.jt.baimo.pw.constant.TagCst;
import com.jt.baimo.pw.service.PageHomeOneAlbumService;
import com.jt.baimo.pw.service.PageHomeOneService;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.AlbumTargetVo;
import com.jt.baimo.pw.vo.HomeOneAlbumTargetListVo;
import com.jt.baimo.pw.vo.ResultData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 个人首页的香车
 *
 * @author Forever丶诺
 * @data 2019/9/19 14:44
 */
@RestController
@RequestMapping("homeOneAlbum")
@Validated
public class PageHomeOneAlbumController {




    @Autowired
    private PageHomeOneAlbumService pageHomeOneAlbumService;

    @GetMapping("targetList")
    @ApiOperation(tags = TagCst.HOME_ONE_ALBUM, value = "01-获取对方用户的相册列表-沙增达")
    @ApiImplicitParam(name = "targetId", value = "目标id", paramType = "query", dataType = "string", required = true)
    public ResultData<List<HomeOneAlbumTargetListVo>> getTargetList(String targetId, HttpServletRequest request) {
        return  pageHomeOneAlbumService.getTargetList(targetId,AppUtil.getUserId(request));
    }



}
