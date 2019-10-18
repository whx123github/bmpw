package com.jt.baimo.pw.mapper;

import com.jt.baimo.pw.model.Dynamic;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jt.baimo.pw.vo.DynamicAllVo;
import com.jt.baimo.pw.vo.DynamicMyVo;
import com.jt.baimo.pw.vo.DynamicOthersVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 动态表 Mapper 接口
 * </p>
 *
 * @author Ms.WenJing
 * @since 2019-09-17
 */
public interface DynamicMapper extends BaseMapper<Dynamic> {

    List<DynamicAllVo> serviceAllList(@Param("lastId") Integer lastId, @Param("size") Integer size, @Param("uid") String uid, @Param("userId") String userId);
}
