package com.demo.system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.demo.base.BaseModel;
import com.demo.common.Constants;
import com.demo.utils.StrUtil;

/**
 * Created by Tomoya.
 * Copyright (c) 2016, All Rights Reserved.
 * http://jfinalbbs.com
 */
public class SysConfig extends BaseModel<SysConfig> {

    public final static SysConfig me = new SysConfig();

    /**
     * 根据key查询value,并放入缓存里
     *
     * @param key
     * @return
     */
    public String findByKey(String key) {
        /*if (!StrUtil.isBlank(key)) {
            Map<String, Object> map = findAll2Map();
            return map.get(key).toString();
        }*/
    	List<SysConfig> list = super.find("select * from sys_config");
    	Map<String, Object> map = new HashMap<String, Object>();
        for (SysConfig sc : list) {
            map.put(sc.getStr("key"), sc.getStr("value"));
        }
    	return map.get(key).toString();
    }

    public Map<String, Object> findAll2Map() {
        List<SysConfig> list = super.findByCache(
                Constants.SYSCONFIGCACHE,
                Constants.SYSCONFIGCACHEKEY,
                "select * from sys_config"
        );
        Map<String, Object> map = new HashMap<String, Object>();
        for (SysConfig sc : list) {
            map.put(sc.getStr("key"), sc.getStr("value"));
        }
        return map;
    }

    public void update(String key, String value) {
        if (!StrUtil.isBlank(value)) {
            SysConfig sysConfig = findFirst("select * from sys_config where `key` = ?", key);
            if (sysConfig != null) {
                sysConfig.set("value", value);
                sysConfig.update();
            }
        }
    }
}
