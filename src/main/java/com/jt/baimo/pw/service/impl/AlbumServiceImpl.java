package com.jt.baimo.pw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import com.jt.baimo.pw.mapper.AlbumMapper;
import com.jt.baimo.pw.model.Album;
import com.jt.baimo.pw.service.AlbumService;
import com.jt.baimo.pw.todo.AlbumUploadTo;
import com.jt.baimo.pw.vo.AlbumTargetVo;
import com.jt.baimo.pw.vo.ResultData;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 相册表 服务实现类
 * </p>
 *
 * @author Forever丶诺
 * @since 2019-07-26
 */
@Service
public class AlbumServiceImpl extends ServiceImpl<AlbumMapper, Album> implements AlbumService {

    @Autowired
    private ModelMapper modelMapper;


    @Autowired
    private AlbumMapper albumMapper;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResultData uploadAlbum(AlbumUploadTo album, String userId) {
        Album insertAlbum = modelMapper.map(album, Album.class);
        insertAlbum.setUid(userId);
        this.save(insertAlbum);
        return new ResultData();
    }

    @Override
    public ResultData multiUploadAlbum(List<AlbumUploadTo> albums, String userId) {
        List<Album> albumList = modelMapper.map(albums, new TypeToken<List<Album>>() {
        }.getType());
        albumList.forEach(album -> album.setUid(userId));
        this.saveBatch(albumList, 10);
        return new ResultData();
    }



}
