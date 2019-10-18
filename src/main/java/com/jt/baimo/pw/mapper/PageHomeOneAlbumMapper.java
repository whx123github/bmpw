package com.jt.baimo.pw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.baimo.pw.vo.AlbumTargetVo;
import com.jt.baimo.pw.vo.HomeOneAlbumTargetListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Forever丶诺
 * @data 2019/9/19 14:46
 */
public interface PageHomeOneAlbumMapper extends BaseMapper {
    List<HomeOneAlbumTargetListVo> getTargetList(@Param("targetId") String targetId, @Param("userId") String userId);
}
