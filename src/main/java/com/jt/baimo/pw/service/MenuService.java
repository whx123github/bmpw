package com.jt.baimo.pw.service;

import com.jt.baimo.pw.model.Menu;
import com.jt.baimo.pw.vo.ResultData;

public interface MenuService {
    ResultData queryMenu(Integer type);
}
