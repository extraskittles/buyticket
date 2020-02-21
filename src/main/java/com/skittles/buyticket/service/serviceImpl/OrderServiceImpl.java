package com.skittles.buyticket.service.serviceImpl;

import com.skittles.buyticket.detailMapper.OrderDetailMapper;
import com.skittles.buyticket.detailMapper.SceneDetailMapper;
import com.skittles.buyticket.detailModel.OrderDetail;
import com.skittles.buyticket.detailModel.SceneDetail;
import com.skittles.buyticket.mapper.HallMapper;
import com.skittles.buyticket.mapper.SceneMapper;
import com.skittles.buyticket.mapper.TicketOrderMapper;
import com.skittles.buyticket.mapper.UserMapper;
import com.skittles.buyticket.model.Hall;
import com.skittles.buyticket.model.Scene;
import com.skittles.buyticket.model.TicketOrder;
import com.skittles.buyticket.model.User;
import com.skittles.buyticket.param.ConfirmOrderParam;
import com.skittles.buyticket.param.ConfirmOrderParam2;
import com.skittles.buyticket.service.OrderService;
import com.skittles.buyticket.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
@Autowired
UserMapper userMapper;
@Autowired
    TicketOrderMapper orderMapper;
@Autowired
    OrderDetailMapper orderDetailMapper;
@Autowired
    SceneDetailMapper sceneDetailMapper;
@Autowired
    HallMapper hallMapper;
@Autowired
    SceneMapper sceneMapper;
//  扣取一定用户积分来获得电影票

    @Override
    public Map<String,Boolean> pay(int orderId, HttpServletRequest request) {
        Map map =new HashMap();
        //先判断用户积分是否够积分支付
        int userId = HttpUtils.getIdByRequest(request);
        User user = userMapper.selectByPrimaryKey(userId);
        Integer points = user.getPoints();
        TicketOrder order = orderMapper.selectByPrimaryKey(orderId);
        double payPrice = order.getPayPrice();
        map.put("noPoints",false);
        if(points/10<payPrice){
            map.put("noPoints",true);
            return map;
        }
       //扣取订单所需要的积分
        points=(int)(points-payPrice*10);
        user.setPoints(points);
        int count1 = userMapper.updateByPrimaryKeySelective(user);
        //生成唯一码并修改订单状态
        order.setStatus("已支付");
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        uuidString.replaceAll("-","");
        order.setUuid(uuidString);
        int count2 = orderMapper.updateByPrimaryKeySelective(order);
        if(count1>0&&count2>0){
            map.put("success",true);
            map.put("data",order);
        }
        return map;
    }
//确认订单
    @Override
    public Map<String, Object> confirmOrder(ConfirmOrderParam confirmOrderParam, int id) {
        Map<String, Object> map = new HashMap<>();
        ConfirmOrderParam2 confirmOrderParam2 = new ConfirmOrderParam2();
        int sceneId = confirmOrderParam.getSceneId();
        //判断票是否还在
        String numbersString = confirmOrderParam.getSitNumbers();
        String[] sitNumbers = numbersString.split(",");
        SceneDetail sceneDetail = sceneDetailMapper.selectSceneDetailById(sceneId);
        String leftSitString = sceneDetail.getLeftSit();
        if(leftSitString==null){
            Integer sitNumber = sceneDetail.getSitNumber();
            List<Integer> sitNumberString =new ArrayList<>();
            for(int i=1;i<sitNumber;i++){
                sitNumberString.add(i);
            }
            leftSitString = StringUtils.join(sitNumberString, ",");
        }
        String[] leftSits = leftSitString.split(",");
        int broketime = 0;
        for (String sitNumber : sitNumbers) {
            for (String leftSit : leftSits) {
                if (sitNumber.equals(leftSit)) {
                    broketime++;
                    break;
                }
            }
        }
        if (broketime != sitNumbers.length) {
            map.put("defaultTicket", true);
        }
        //计算价格
        double price = sceneDetail.getPrice();
        double payPrice = sitNumbers.length * price;
        //生成订单
        confirmOrderParam2.setPayPrice(payPrice);
        TicketOrder order = new TicketOrder();
        order.setUserId(id);
        order.setCinemaId(sceneDetail.getCinemaId());
        order.setSceneId(sceneDetail.getId());
        order.setStatus("生成订单未付款");
        order.setSitNumbers(numbersString);
        order.setPayPrice(payPrice);
        int count = orderMapper.insertSelective(order);
        Integer orderId = order.getId();
        map.put("count", count);
        //减少数据库scene表的电影票数量
        List<String> arr = new ArrayList(Arrays.asList(leftSits));
        for (String sitNumber : sitNumbers) {
            String a = sitNumber;
            arr.remove(a);
        }
        String join = StringUtils.join(arr, ",");
        Scene scene = new Scene();
        scene.setId(sceneId);
        scene.setLeftSit(join);
        sceneMapper.updateByPrimaryKeySelective(scene);
        //将应付价格
        OrderDetail orderDetail = orderDetailMapper.selectOrderDetailById(orderId);
        map.put("orderDetail",orderDetail);
        return map;
    }

    @Override
    public boolean cancelOrder(int orderId, int userId) {
        //判断订单是否支付，支付无法取消
        OrderDetail order = orderDetailMapper.selectOrderDetailById(orderId);
        if(order==null){
            return false;
        }
        if("已支付".equals(order.getStatus())){
            return false;
        }
        //确认订单是否属于本用户
        if(order!=null&&userId==order.getUserId()){
            //增加回座位号
            String sitNumbers = order.getSitNumbers();
            Integer sceneId = order.getSceneId();
            Scene scene = sceneMapper.selectByPrimaryKey(sceneId);
            String leftSit = scene.getLeftSit();
            String newLeftSit=leftSit+","+sitNumbers;
            scene.setLeftSit(newLeftSit);
            sceneMapper.updateByPrimaryKeySelective(scene);
            //删除订单
            orderMapper.deleteByPrimaryKey(orderId);


            return true;
        }else {
            return false;
        }
    }
}
