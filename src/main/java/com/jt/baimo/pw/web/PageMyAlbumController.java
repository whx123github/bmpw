package com.jt.baimo.pw.web;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jt.baimo.pw.model.Album;
import com.jt.baimo.pw.service.AlbumService;
import com.jt.baimo.pw.todo.AlbumUploadTo;
import com.jt.baimo.pw.util.AppUtil;
import com.jt.baimo.pw.vo.AlbumMyVo;
import com.jt.baimo.pw.vo.AlbumTargetVo;
import com.jt.baimo.pw.vo.ResultData;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.List;

import static com.jt.baimo.pw.constant.TagCst.HOME;
import static com.jt.baimo.pw.constant.TagCst.MY_ALBUM;


/**
 * @author Forever丶诺
 * @data 2019/7/26 16:03
 */
@RestController
@RequestMapping("album")
@Validated
public class PageMyAlbumController {


    @Autowired
    private AlbumService albumService;

    @Autowired
    private ModelMapper modelMapper;


    @PostMapping
    @ApiOperation(tags = MY_ALBUM, value = "01-上传图片-沙增达")
    public ResultData uploadAlbum(HttpServletRequest request, @Valid @RequestBody AlbumUploadTo album, BindingResult bindingResult) {
        return albumService.uploadAlbum(album, AppUtil.getUserId(request));
    }


    @PostMapping("multi")
    @ApiOperation(tags = MY_ALBUM, value = "01-批量上传图片-沙增达", hidden = true)
    public ResultData multiUploadAlbum(HttpServletRequest request, @Valid @RequestBody List<AlbumUploadTo> Albums, BindingResult bindingResult) {
        return albumService.multiUploadAlbum(Albums, AppUtil.getUserId(request));
    }


    @DeleteMapping("multi")
    @ApiOperation(tags = MY_ALBUM, value = "02-批量删除-沙增达", notes = "请求示例:[1,2,3]")
    public ResultData multiDeleteAlbum(@NotNull @Size(min = 1) @RequestBody List<Integer> ids, BindingResult bindingResult) {
        albumService.removeByIds(ids);
        return new ResultData();
    }

    @DeleteMapping("{id}")
    @ApiOperation(tags = MY_ALBUM, value = "02-单个删除-沙增达")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", paramType = "path", required = true, value = "照片的id"))
    public ResultData deleteAlbum(@NotNull @PathVariable("id") Integer id) {
        albumService.removeById(id);
        return new ResultData();
    }

    @PutMapping("price")
    @ApiOperation(tags = MY_ALBUM, value = "03-设置照片的价格-沙增达")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "price", value = "照片的价格", paramType = "query", dataType = "bigDecimal", required = true),
            @ApiImplicitParam(name = "id", value = "照片的主键", paramType = "query", dataType = "int", required = true),
    })
    public ResultData updatePrice(@NotNull BigDecimal price, @NotNull Integer id) {
        albumService.updateById(new Album().setId(id).setPrice(price));
        return new ResultData();
    }

    @GetMapping("myList")
    @ApiOperation(tags = MY_ALBUM, value = "04-获取当前用户的相册列表-沙增达")
    public ResultData<List<AlbumMyVo>> getMyList(HttpServletRequest request) {
        String userId = AppUtil.getUserId(request);
        List<Album> albumList = albumService.list(new LambdaQueryWrapper<Album>().eq(Album::getUid, userId).ne(Album::getStatus, 3));
        List<AlbumMyVo> albums = modelMapper.map(albumList, new TypeToken<List<AlbumMyVo>>() {
        }.getType());
        return new ResultData().setData(albums);
    }

    



}
