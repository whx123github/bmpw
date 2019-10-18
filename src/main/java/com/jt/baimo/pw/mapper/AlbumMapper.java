package com.jt.baimo.pw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.baimo.pw.model.Album;
import com.jt.baimo.pw.vo.UserAlbumVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 相册表 Mapper 接口
 * </p>
 *
 * @author Ms.WenJing
 * @since 2019-09-17
 */
public interface AlbumMapper extends BaseMapper<Album> {

    List<UserAlbumVo> selectUserAlbumList(@Param("uid")String uid, @Param("userId") String userId);
}
