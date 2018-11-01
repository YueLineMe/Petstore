package com.web;

import com.dao.SsmOrderMapper;
import com.dao.SsmUserMapper;
import com.entity.ApiResponse;
import com.entity.SsmUser;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.rmi.server.ExportException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/")
public class UserController {
    @Autowired
    private SsmUserMapper ssmUserMapper;

    @RequestMapping(method = RequestMethod.POST, value = "/user")
    @ResponseBody
    public ApiResponse insertUser(SsmUser ssmUser) {
        ssmUserMapper.insert(ssmUser);
        return new ApiResponse(200, "succeed", "运行成功");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/user/createWithArray")
    @ResponseBody
    public ApiResponse insertUserByArray(List<SsmUser> list) {
        for (SsmUser user : list) {
            ssmUserMapper.insert(user);
        }
        return new ApiResponse(200, "succeed", "运行成功");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/login")
    @ResponseBody
    public ApiResponse insertUser(String username, String password) {
        SsmUser ssmUser = new SsmUser();
        ssmUser.setPassword(password);
        ssmUser.setUserName(username);
        ssmUser.setUserStatus(0);
        int id = ssmUserMapper.selectLogin(ssmUser);
        if (id == 0) {
            return new ApiResponse(400, "error", "提供的用户名/密码无效");
        }
        ssmUser.setId(id);
        ssmUserMapper.updateStatus(ssmUser);
        return new ApiResponse(200, "succeed", "运行成功");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/logout")
    @ResponseBody
    public ApiResponse insertUser(HttpSession session) {
        SsmUser ssmUser = new SsmUser();
        ssmUser.setId((Integer) session.getAttribute("id"));
        ssmUser.setUserStatus(0);
        ssmUserMapper.updateStatus(ssmUser);
        return new ApiResponse(200, "succeed", "运行成功");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/user/{username}")
    @ResponseBody
    public ApiResponse selectUserByUserName(@PathVariable("username") String username) {
        SsmUser ssmUser = null;
        try {
            ssmUser = ssmUserMapper.selectByUserName(username);
            if (ssmUser == null) {
                return new ApiResponse(404, "error", "找不到用户");
            }
        } catch (Exception e) {
            return new ApiResponse(400, "error", "提供用户名无效");
        }
        return new ApiResponse(200, "succeed", "运行成功");
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/user/{username}")
    @ResponseBody
    public ApiResponse updateUser(@PathVariable("username") String username, SsmUser ssmUser) {
        try {
            if (ssmUserMapper.selectByUserName(username) == null) {
                return new ApiResponse(404, "error", "找不到用户");
            }
        } catch (Exception e) {
            return new ApiResponse(400, "error", "提供用户名无效");
        }
        ssmUser.setUserName(username);
        ssmUserMapper.updateByPrimaryKey(ssmUser);
        return new ApiResponse(200, "succeed", "运行成功");
    }
    @RequestMapping(method = RequestMethod.DELETE, value = "/user/{username}")
    @ResponseBody
    public ApiResponse delUser(@PathVariable("username") String username) {
        SsmUser ssmUser = null;
        try {
            ssmUser = ssmUserMapper.selectByUserName(username);
            if (ssmUser == null) {
                return new ApiResponse(404, "error", "找不到用户");
            }
        } catch (Exception e) {
            return new ApiResponse(400, "error", "提供用户名无效");
        }
        ssmUserMapper.deleteByPrimaryKey(ssmUser.getId());
        return new ApiResponse(200, "succeed", "运行成功");
    }
}
