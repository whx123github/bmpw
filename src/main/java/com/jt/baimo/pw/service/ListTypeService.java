package com.jt.baimo.pw.service;

import com.jt.baimo.pw.model.ListType;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jt.baimo.pw.vo.ResultData;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Forever丶诺
 * @since 2019-09-20
 */
public interface ListTypeService extends IService<ListType> {

    ResultData getDataByType(Integer type);
}
