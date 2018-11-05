package com.web;

import com.dao.SsmOrderMapper;
import com.dao.SsmPetMapper;
import com.entity.ApiResponse;
import com.entity.SsmOrder;
import com.entity.SsmPet;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/")
public class OrderController {
    @Autowired
    private SsmOrderMapper ssmOrderMapper;

    @RequestMapping(method = RequestMethod.GET, value = "/store/inventory")
    @ResponseBody
    public ApiResponse selectInventoriesByStatus() {
        Map<String,Integer> data=ssmOrderMapper.selectInventoriesByStatus();
        JSONObject jsonMap = JSONObject.fromObject(data);
        return new ApiResponse(200, "succeed", jsonMap.toString());
    }
    @RequestMapping(method = RequestMethod.POST, value = "/store/order")
    @ResponseBody
    public ApiResponse insertOrder(SsmOrder ssmOrder) {
        if(ssmOrderMapper.insert(ssmOrder)==0){
            return new ApiResponse(400, "error", "订单无效");
        }
        return new ApiResponse(200, "succeed","成功运行");
    }
    @RequestMapping(method = RequestMethod.GET, value = "/store/order/{orderId}")
    @ResponseBody
    public ApiResponse insertOrder(@PathVariable("orderId") int orderId) {
        SsmOrder ssmOrder=null;
        try{
            ssmOrder=ssmOrderMapper.selectByPrimaryKey(orderId);
            if(ssmOrder==null){
                return new ApiResponse(404, "error","订单未找到");
            }
        }catch (Exception e){
            return new ApiResponse(400, "error","提供的ID无效");
        }
        JSONObject jsonObject = JSONObject.fromObject(ssmOrder);
        return new ApiResponse(200, "succeed",jsonObject.toString());
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/store/order/{orderId}")
    @ResponseBody
    public ApiResponse delOrder(@PathVariable("orderId") int orderId) {
        SsmOrder ssmOrder=null;
        try{
            ssmOrder=ssmOrderMapper.selectByPrimaryKey(orderId);
            if(ssmOrder==null){
                return new ApiResponse(404, "error","订单未找到");
            }
        }catch (Exception e){
            return new ApiResponse(400, "error","提供的ID无效");
        }
        ssmOrderMapper.deleteByPrimaryKey(orderId);
        JSONObject jsonObject = JSONObject.fromObject(ssmOrder);
        return new ApiResponse(200, "succeed","成功运行");
    }
}
