package com.draw.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.draw.entity.History;
import com.draw.repository.HistoryRepository;
import com.draw.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;

    @Override
    public JSONObject getHistoryList() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg","操作成功");
        jsonObject.put("code","200");
        jsonObject.put("success","true");
        List<History> all = historyRepository.findAllByOrder();
        jsonObject.put("data",all);
        return jsonObject;
    }
}
