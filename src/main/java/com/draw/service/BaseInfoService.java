package com.draw.service;

import com.alibaba.fastjson.JSONObject;
import com.draw.entity.BaseInfo;

public interface BaseInfoService {

    /**
     * 新增一个问题
     * @return
     */
    BaseInfo addone(BaseInfo BaseInfo);

    JSONObject startLottery(String drawName);

    JSONObject getInitData();

    JSONObject addBaseInfo(BaseInfo baseInfo);

    JSONObject getBaseInfoList();

    JSONObject getBaseInfo(Integer id);

    /**
     * 修改一个问题
     * @return
     */
    BaseInfo updateOne(Long id,BaseInfo baseInfo);

    /**
     * 删除一个问题
     * @return
     */
    void deleteOne(Integer id);
}
