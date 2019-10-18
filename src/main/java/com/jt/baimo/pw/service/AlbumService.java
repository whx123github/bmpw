package com.jt.baimo.pw.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.jt.baimo.pw.model.Album;
import com.jt.baimo.pw.todo.AlbumUploadTo;
import com.jt.baimo.pw.vo.AlbumTargetVo;
import com.jt.baimo.pw.vo.ResultData;

import java.util.List;

/**
 * <p>
 * 相册表 服务类
 * </p>
 *
 * @author Forever丶诺
 * @since 2019-07-26
 */
public interface AlbumService extends IService<Album> {

    ResultData multiUploadAlbum(List<AlbumUploadTo> albums, String userId);



    ResultData uploadAlbum(AlbumUploadTo album, String userId);
}
