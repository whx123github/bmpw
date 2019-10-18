package com.jt.baimo.pw.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jt.baimo.pw.model.ListType;
import com.jt.baimo.pw.mapper.ListTypeMapper;
import com.jt.baimo.pw.service.ListTypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jt.baimo.pw.vo.ResultData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author Forever丶诺
 * @since 2019-09-20
 */
@Service
public class ListTypeServiceImpl extends ServiceImpl<ListTypeMapper, ListType> implements ListTypeService {


    @Override
    public ResultData getDataByType(Integer type) {
        List<ListType> result = this.list(new LambdaQueryWrapper<ListType>().eq(ListType::getType, type));
        return new ResultData().setData(result);
    }


}
