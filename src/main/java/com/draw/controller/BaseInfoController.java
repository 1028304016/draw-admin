package com.draw.controller;

import com.alibaba.fastjson.JSONObject;
import com.draw.entity.BaseInfo;
import com.draw.service.BaseInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @program: duty-admin->DutyController
 **/
@Slf4j
@RestController
public class BaseInfoController {

    @Autowired
    private BaseInfoService baseInfoService;

    /**
     *开始抽签
     * @param drawName
     * @return
     */
    @GetMapping("/lottery")
    public String lottery(@RequestParam("drawName") String drawName) {
        try {
            JSONObject jsonObject = baseInfoService.startLottery(drawName);
            return  jsonObject.toString();
        } catch (Exception e) {
            log.error(e.toString());
            return "操作有误";
        }
    }

    @GetMapping("/getDrawName")
    public String getInitData() {
        try {
            JSONObject jsonObject = baseInfoService.getInitData();
            return  jsonObject.toString();
        } catch (Exception e) {
            log.error(e.toString());
            return "操作有误";
        }
    }

    @GetMapping("/addBaseInfo")
    public String addBaseInfo(@RequestBody BaseInfo baseInfo) {
        try {
            JSONObject jsonObject = baseInfoService.addBaseInfo(baseInfo);
            return  jsonObject.toString();
        } catch (Exception e) {
            log.error(e.toString());
            return "操作有误";
        }
    }

    @GetMapping("/getBaseInfoList")
    public String getBaseInfoList() {
        try {
            JSONObject jsonObject = baseInfoService.getBaseInfoList();
            return  jsonObject.toString();
        } catch (Exception e) {
            log.error(e.toString());
            return "操作有误";
        }
    }

    @GetMapping("/getBaseInfo/{id}")
    public String getBaseInfo(@PathVariable("id") Integer id) {
        try {
            JSONObject jsonObject = baseInfoService.getBaseInfo(id);
            return  jsonObject.toString();
        } catch (Exception e) {
            log.error(e.toString());
            return "操作有误";
        }
    }

    /*
     *新增一个问题
     */
    @PostMapping("/addOne")
    public BaseInfo addOne(@RequestBody BaseInfo baseInfo){
        return baseInfoService.addone(baseInfo);
    }

    /*
     *修改一个问题
     */
    @PutMapping("/updateOne/{id}")
    public BaseInfo updateOne(@PathVariable Long id,@RequestBody BaseInfo baseInfo){
        return baseInfoService.updateOne(id,baseInfo);
    }

    /*
     *删除一个问题
     */
    @DeleteMapping("/deleteOne/{id}")
    public void deleteOne(@PathVariable Integer id){
        baseInfoService.deleteOne(id);
    }
}
