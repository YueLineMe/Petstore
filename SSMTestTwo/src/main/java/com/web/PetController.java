package com.web;

import com.dao.SsmPetMapper;
import com.entity.ApiResponse;
import com.entity.SsmPet;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
@RequestMapping("/")
public class PetController {
    @Autowired
    private SsmPetMapper ssmPetMapper;

    @RequestMapping(method = RequestMethod.POST, value = "/pet")
    @ResponseBody
    public ApiResponse AddPet(SsmPet pet) {
        if (ssmPetMapper.insert(pet) == 0) {
            return new ApiResponse(405, "error", "输入无效");
        }
        return new ApiResponse(200, "succeed", "添加成功");
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/pet")
    @ResponseBody
    public ApiResponse UpdatePet(SsmPet pet) {
        try {
            if (ssmPetMapper.selectByPrimaryKey(pet.getId()) == null) {
                return new ApiResponse(404, "error", "找不到宠物");
            }
        } catch (Exception e) {
            return new ApiResponse(400, "error", "提供ID无效");
        }
        if (ssmPetMapper.updateByPrimaryKey(pet) == 0) {
            return new ApiResponse(405, "error", "验证异常");
        }
        return new ApiResponse(200, "succeed", "更新成功");
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pet/findByStatus")
    @ResponseBody
    public ApiResponse findByStatus(String status) {
        if (Arrays.asList("placed", "approved", "delivered").contains(status)) {
            return new ApiResponse(400, "error", "状态值无效");
        }
        List<SsmPet> list = ssmPetMapper.findByStatus(status);
        JSONArray jsonArray = JSONArray.fromObject(list);
        return new ApiResponse(200, "succeed", jsonArray.toString());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/pet/{petId}")
    @ResponseBody
    public ApiResponse findByStatus(@PathVariable("petId") int petId) {
        SsmPet pet = null;
        try {
            pet = ssmPetMapper.selectByPrimaryKey(petId);
        } catch (Exception e) {
            return new ApiResponse(400, "error", "提供ID无效");
        }
        if (pet == null) {
            return new ApiResponse(404, "error", "找不到宠物");
        }
        JSONObject jsonObject = JSONObject.fromObject(pet);
        return new ApiResponse(200, "succeed", jsonObject.toString());
    }

    @RequestMapping(method = RequestMethod.POST, value = "/pet/{petId}")
    @ResponseBody
    public ApiResponse updatePetByForm(@PathVariable("petId") int petId, String name, String status) {
        SsmPet pet = new SsmPet();
        pet.setId(petId);
        pet.setName(name);
        pet.setStatus(status);
        if (ssmPetMapper.updatePetByForm(pet) == 0) {
            return new ApiResponse(405, "error", "输入无效");
        }
        return new ApiResponse(200, "succeed", "更新成功");
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/pet/{petId}")
    @ResponseBody
    public ApiResponse delPet(@PathVariable("petId") int petId) {
        try {
            if (ssmPetMapper.selectByPrimaryKey(petId) == null) {
                return new ApiResponse(404, "error", "找不到宠物");
            }
        } catch (Exception e) {
            return new ApiResponse(400, "error", "提供ID无效");
        }
        ssmPetMapper.deleteByPrimaryKey(petId);
        return new ApiResponse(200, "succeed", "删除成功");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/pet/{petId}/uploadImage")
    @ResponseBody
    public ApiResponse uploadImage(@PathVariable("petId") int petId, String photoUrls) {
        try {
            if (ssmPetMapper.selectByPrimaryKey(petId) == null) {
                return new ApiResponse(404, "error", "找不到宠物");
            }
        } catch (Exception e) {
            return new ApiResponse(400, "error", "提供ID无效");
        }
        //getName()
        //getSize()
        //contentType
        SsmPet ssmPet = new SsmPet();
        ssmPet.setId(petId);
        ssmPet.setPhotoUrls(photoUrls);
        ssmPetMapper.uploadImage(ssmPet);
        return new ApiResponse(200, "succeed", "更改成功");
    }

    @RequestMapping("/uploadImage")
    public String uploadImage(Model model, MultipartFile file, HttpServletRequest request) {
        if (file.isEmpty()) {
            model.addAttribute("error", "上传不能为空");
        }
        if (!file.getContentType().contains("image/")) {
            model.addAttribute("error", "只能上传图片");
        }
        if (file.getSize() > 1024 * 1024 * 5) {
            model.addAttribute("error", "图片大小不能超过5M");
        }
        Map<String,String> fileInfoMap =subFileName(file.getName());
        String name="";
        return "image";
    }

    public Map<String,String> subFileName(String fileName){
        Map<String,String> map= new HashMap<String,String>();
        map.put("prefix",fileName.substring(0,fileName.lastIndexOf('.')));
        map.put("suffix",fileName.substring(fileName.lastIndexOf('.')+1));
        return map;
    }

}
