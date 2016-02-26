package com.demo.valicode;

import java.io.Serializable;
import java.util.Date;

import com.demo.base.BaseModel;
import com.demo.utils.DateUtil;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class ValiCode extends BaseModel<ValiCode> implements Serializable {
    public final static ValiCode me = new ValiCode();

    //查询未过期的验证码
    public ValiCode findByCodeAndEmail(String code, String email, String type) {
        String nowTime = DateUtil.formatDateTime(new Date());
        return super.findFirst("select * from valicode v where v.status = 0 and v.code = ? and v.target = ? and v.expire_time > ? and v.type = ?", code, email, nowTime, type);
    }
}
