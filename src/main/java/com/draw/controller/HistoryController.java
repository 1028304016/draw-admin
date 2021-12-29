package com.draw.controller;

import com.alibaba.fastjson.JSONObject;
import com.draw.service.HistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController()
public class HistoryController {

    @Autowired
    private HistoryService historyService;

    //查看历史记录
    @GetMapping("/getHistoryList")
    public String getHistoryList(){

        try {
            JSONObject jsonObject = historyService.getHistoryList();
            return jsonObject.toString();
        } catch (Exception e) {
            log.error(e.toString());
            return  "操作有误";
        }
    }
}
