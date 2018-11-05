package com.web;

import com.dao.SsmOrderMapper;
import com.dao.SsmUserMapper;
import com.entity.ApiResponse;
import com.entity.SsmUser;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.rmi.server.ExportException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private SsmUserMapper ssmUserMapper;

    private SsmUser staticUser=null;

    @RequestMapping(value = "/index")
    public String goIndex(Model model) {
        return "index";
    }

    /**
     * 创建用户
     */
    @RequestMapping(method = RequestMethod.POST, value = "/user")
    @ResponseBody
    public ResponseEntity<ApiResponse> insertUser(SsmUser ssmUser) {
        //userName没问题,userName没别人使用
        System.out.println(ssmUser);
        if (findUserByUserName(ssmUser.getUserName()) == null && ssmUserMapper.selectByPrimaryKey(staticUser.getId()) != null) {
            return ResponseEntity.status(401).body(new ApiResponse(401, "error", "用户名已被使用"));
        }
        if(findUserByUserName(ssmUser.getUserName()) == null && ssmUserMapper.selectByPrimaryKey(staticUser.getId()) == null) {
            ssmUserMapper.insert(ssmUser);
        }
        return ResponseEntity.status(200).body(new ApiResponse(200, "succeed", "运行成功"));
    }

    /**
     * 批量添加，
     * 创建具有给定输入数组的用户列表
     * 没用使用的地方
     * ignore
     */
    @RequestMapping(method = RequestMethod.POST, value = "/user/createWithArray")
    @ResponseBody
    public ResponseEntity<ApiResponse> insertUserByArray(List<SsmUser> list) {
        for (SsmUser user : list) {
            ssmUserMapper.insert(user);
        }
        return ResponseEntity.status(200).body(new ApiResponse(200, "succeed", "运行成功"));
    }

    /**
     * 将用用户登录到系统
     */
    @RequestMapping(method = RequestMethod.GET, value = "/user/login")
    @ResponseBody
    public ResponseEntity<ApiResponse> login(String username, String password,HttpSession session) {
        SsmUser ssmUser = new SsmUser();
        ssmUser.setPassword(password);
        ssmUser.setUserName(username);
        ssmUser.setUserStatus(0);
        int id = ssmUserMapper.selectLogin(ssmUser);
        if (id == 0) {
            return ResponseEntity.status(400).body(new ApiResponse(400, "error", "提供的用户名/密码无效"));
        }
        ssmUser.setId(id);
        ssmUserMapper.updateStatus(ssmUser);
        session.setAttribute("id",id);
        return ResponseEntity.status(400).body(new ApiResponse(200, "succeed", "运行成功"));
    }

    /**
     * 注销当前登录的用户会话
     *
     * @param session
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/user/logout")
    @ResponseBody
    public ApiResponse insertUser(HttpSession session) {
        SsmUser ssmUser = new SsmUser();
        ssmUser.setId((Integer) session.getAttribute("id"));
        ssmUser.setUserStatus(0);
        ssmUserMapper.updateStatus(ssmUser);
        return new ApiResponse(200, "succeed", "运行成功");
    }

    /**
     * 按用户名获取用户
     *
     * @param username
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, value = "/user/{username}")
    @ResponseBody
    public ApiResponse selectUserByUserName(@PathVariable("username") String username) {
        ApiResponse apiResponse = null;
        apiResponse = findUserByUserName(username);
        if (apiResponse == null) {
            apiResponse = new ApiResponse(200, "succeed", "运行成功");
        }
        return apiResponse;
    }

    /**
     * 更新用户
     *
     * @param username
     * @param ssmUser
     * @return
     */
    @RequestMapping(method = RequestMethod.PUT, value = "/user/{username}")
    @ResponseBody
    public ApiResponse updateUser(@PathVariable("username") String username, SsmUser ssmUser) {
        ApiResponse apiResponse = null;
        apiResponse = findUserByUserName(username);
        if (apiResponse == null) {
            apiResponse = new ApiResponse(200, "succeed", "运行成功");
        }
        ssmUser.setUserName(username);
        ssmUserMapper.updateByPrimaryKey(ssmUser);
        return apiResponse;
    }

    /**
     * 删除用户
     *
     * @param username
     * @return
     */
    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{username}")
    @ResponseBody
    public ApiResponse delUser(@PathVariable("username") String username) {
        ApiResponse apiResponse = null;
        apiResponse = findUserByUserName(username);
        if (apiResponse == null) {
            apiResponse = new ApiResponse(200, "succeed", "运行成功");
        }
        ssmUserMapper.deleteByPrimaryKey(staticUser.getId());
        return apiResponse;
    }

    /**
     * 通过用户名查找用户的统一调用方法
     */
    public ApiResponse findUserByUserName(String username) {
        ApiResponse apiResponse = null;
        try {
            staticUser = ssmUserMapper.selectByUserName(username);
            if (staticUser == null) {
                apiResponse = new ApiResponse(404, "error", "找不到用户");
            }
        } catch (Exception e) {
            e.printStackTrace();
            apiResponse = new ApiResponse(400, "error", "提供用户名无效");
        }
        return apiResponse;
    }
}
