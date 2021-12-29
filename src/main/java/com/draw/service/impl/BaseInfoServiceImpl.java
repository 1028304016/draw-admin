package com.draw.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.draw.entity.*;
import com.draw.repository.BaseInfoRepository;
import com.draw.repository.DrawOpenRepository;
import com.draw.repository.HistoryRepository;
import com.draw.repository.TransitionRepository;
import com.draw.service.BaseInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Component
public class BaseInfoServiceImpl implements BaseInfoService {


    @Autowired
    private BaseInfoRepository baseInfoRepository;

    @Autowired
    private TransitionRepository transitionRepository;

    @Autowired
    private HistoryRepository historyRepository;

    @Autowired
    private DrawOpenRepository drawOpenRepository;


    @Override
    public BaseInfo addone(BaseInfo baseInfo) {
        return baseInfoRepository.save(baseInfo);
    }

    @Override
    public JSONObject startLottery(String drawName) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "操作成功");
        jsonObject.put("code", "200");
        jsonObject.put("success", true);
        if (StringUtils.isEmpty(drawName)) {
            jsonObject.put("msg", "操作失败");
            jsonObject.put("code", "500");
            jsonObject.put("success", false);
            return jsonObject;
        }

        //剩余未抽奖的内容
        List<String> dutyContents = new ArrayList<>();
        //抽中了什么内容
        String content = "";
        //查询今天的抽签历史
        Transition qTransition = new Transition();
        Example<Transition> example = Example.of(qTransition);
        qTransition.setCreateTime(getCurrentDate());
        List<Transition> transitionList = transitionRepository.findAll();
        //第一个人抽奖
        if (transitionList.isEmpty()) {
            dutyContents = baseInfoRepository.findAll().stream().map(BaseInfo::getContent).collect(Collectors.toList());
        } else {
            List<String> contentList = transitionList.stream().map(Transition::getContent).collect(Collectors.toList());
            //list 转化为带逗号的字符串
            String contents = contentList.stream().map(String::valueOf).collect(Collectors.joining(","));
            dutyContents = baseInfoRepository.selectListByContent(contentList);
        }
        if (!dutyContents.isEmpty()) {
            List<DrawOpen> all = drawOpenRepository.findAll();
            DrawOpen drawOpen = all.get(0);
            //坑人操作
            if(drawOpen.getOpen().equals("true")){
               for(int i=0;i<=Integer.parseInt(drawOpen.getNum());i++){
                   //随机获得数字
                   content = dutyContents.get(getRandom(dutyContents.size()));
                   if("张颖颖".equals(drawName) ||"张焕焕".equals(drawName)|"邢荟萃".equals(drawName)){
                       if("拖地1".equals(content)||"拖地2".equals(content)){
                            break;
                       }
                   }
               }
            }else{
                //随机获得数字
                content = dutyContents.get(getRandom(dutyContents.size()));
            }
        }

        //保存抽签记录
        Transition transition = new Transition();
        transition.setContent(content);
        transition.setName(drawName);
        transition.setCreateTime(getCurrentDate());
        transitionRepository.save(transition);

        //封装返回数据
        List<Transition> transitionList2 = transitionRepository.findAll();
        //今天已经抽过的人和内容的集合
        List<String> nameList = transitionList2.stream().map(Transition::getName).collect(Collectors.toList());
        //list 转化为带逗号的字符串
        String names = nameList.stream().map(String::valueOf).collect(Collectors.joining(","));
        //剩余未抽奖的人
        List<String> dutyNames = baseInfoRepository.selectListByName(nameList);
        Map<String, Object> map = new HashMap<>();
        if (dutyNames.isEmpty() || dutyNames.size() == 0) {
            //抽奖结束
            map.put("drawName", "");
            transitionList2.forEach(item -> {
                History history = new History();
                BeanUtils.copyProperties(item,history);
                historyRepository.save(history);
            });
            transitionRepository.deleteAll();
        } else {
            map.put("drawName", dutyNames.get(0));
        }
        map.put("array", transitionList2.toArray());
        jsonObject.put("data", map);
        return jsonObject;
    }

    @Override
    public JSONObject getInitData() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "操作成功");
        jsonObject.put("code", "200");
        jsonObject.put("success", true);
        //下一个抽签人
        String name = "";
        //历史记录
        Map<String, Object> map = new HashMap<>();
        Transition transition = new Transition();

        Example<Transition> example = Example.of(transition);
        transition.setCreateTime(getCurrentDate());
        List<Transition> transitionList = transitionRepository.findAll();
        if (transitionList.isEmpty()) {
            //今天还未抽过签
            List<BaseInfo> all = baseInfoRepository.findAll();
            name = all.get(0).getName();
        } else {
            //今天已经抽过的人和内容的集合
            List<String> nameList = transitionList.stream().map(Transition::getName).collect(Collectors.toList());
            //list 转化为带逗号的字符串
            String names = nameList.stream().map(String::valueOf).collect(Collectors.joining(","));
            //今天剩余未抽奖的人
            List<String> dutyNames = baseInfoRepository.selectListByName(nameList);
            if(!dutyNames.isEmpty()){
                name = dutyNames.get(0);
            }else{
                name = "";
            }
            map.put("array", transitionList.toArray());
        }
        map.put("drawName", name);
        jsonObject.put("data", map);
        return jsonObject;
    }

    @Override
    public JSONObject addBaseInfo(BaseInfo baseInfo) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "操作成功");
        jsonObject.put("code", "200");
        jsonObject.put("success", true);
        BaseInfo save = baseInfoRepository.save(baseInfo);
        System.out.println(save.getId());
        return jsonObject;
    }

    @Override
    public JSONObject getBaseInfoList() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "操作成功");
        jsonObject.put("code", "200");
        jsonObject.put("success", true);
        List<BaseInfo> baseInfoList = baseInfoRepository.findAll();
        jsonObject.put("data", baseInfoList);
        return jsonObject;
    }

    @Override
    public JSONObject getBaseInfo(Integer id) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "操作成功");
        jsonObject.put("code", "200");
        jsonObject.put("success", true);
        BaseInfo baseInfo = baseInfoRepository.getBaseInfo(id);

        jsonObject.put("data", baseInfo);
        return jsonObject;
    }

    //获取0-n之间的随机数
    private int getRandom(int n) {
        // 0<= random and random < n
        Random ran = new Random();
        int random = ran.nextInt(n);
        return random;
    }

    private Date getCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String format = "";
        format = simpleDateFormat.format(new Date());
        Date dateTime = null;
        try {
            dateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(format);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    @Override
    public BaseInfo updateOne(Long id,BaseInfo baseInfo) {
        BaseInfo baseInfo1=new BaseInfo();
        baseInfo1.setId(id);
        baseInfo1.setName(baseInfo.getName());
        baseInfo1.setContent(baseInfo.getContent());
        return baseInfoRepository.saveAndFlush(baseInfo1);
    }

    @Override
    public void deleteOne(Integer id) {
        baseInfoRepository.deleteOne(id);
    }

}
