package com.cy.storejj.utils;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.AdminConfig;
import com.cy.storejj.exception.ErrorCodes;
import com.cy.storejj.exception.JsonException;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonOperation extends AdminConfig {
    public static Map<String, Object> encodeStr(String str){
        Map<String, Object> rs = new HashMap<String, Object>();
        //get salt
        String salt = UUID.randomUUID().toString();
        rs.put("salt", salt);
        //md5
        String str1 = DigestUtils.md5DigestAsHex((str+salt).getBytes());
        rs.put("newstr",str1);
        return rs;
    }

    public static String encodeStr(String str, String salt){
        String str1 = DigestUtils.md5DigestAsHex((str+salt).getBytes());
        return str1;
    }

    public static boolean checkId(Integer id){
        if(id == null) return false;
        if(id.toString().isEmpty()) return false;
        if(id < 1)return false;
        return true;
    }

    public static boolean checkEmail(String email){
        if(email == null || email.isEmpty())return false;
        String regEx1 = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
        Pattern p;
        Matcher m;
        p = Pattern.compile(regEx1);
        m = p.matcher(email);
        if (m.matches())
            return true;
        else
            return false;
    }

    //上传文件
    public static JSONObject uploadFile(MultipartFile file, String myFileName){
        JSONObject rs = new JSONObject();
        if(file == null)throw JsonException.newInstance(ErrorCodes.FILE_NOT_EXSIT);

        String fileName = file.getOriginalFilename();
        String newFileName = myFileName;
        if(StringUtils.isBlank(myFileName)){
            newFileName = System.currentTimeMillis()+ "_"+fileName ;
        }

        String savePath = baseSavePath;

        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        int size = (int)file.getSize();
        size = (int)Math.ceil(size/1024);
        if(size <= 0)throw JsonException.newInstance(ErrorCodes.FILE_UPLOAD_FAILED);
        String destDir = savePath + newFileName;
        File dest  = new File(destDir);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest);
            rs = success("上传成功");
            rs.put("size", size);
            rs.put("filename", fileName);
            rs.put("realname", newFileName);
            rs.put("ext", ext);
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
           throw JsonException.newInstance(ErrorCodes.FILE_UPLOAD_FAILED);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw JsonException.newInstance(ErrorCodes.FILE_WRITE_FAILED);
        }
        return rs;
    }

    public static JSONObject uploadFile(MultipartFile file){
        return uploadFile(file, null);
    }

    //查看图片
    public static void getImage(String filename,
                                HttpServletRequest request,
                                HttpServletResponse response)throws IOException {

        if (StringUtils.isNotBlank(filename)) {
            FileInputStream is = null;
            String path = baseSavePath+filename;


            File file = new File(path);
            try {
                is = new FileInputStream(file);
                int i = is.available();
                byte data[] = new byte[i];
                is.read(data);
                is.close();
                response.setContentType("image/jpeg");
                OutputStream toClient = response.getOutputStream();
                toClient.write(data);
                toClient.close();
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
        }
    }

    public static JSONObject removeFile(String fileName){
        JSONObject rs = new JSONObject();
        if(fileName == null || fileName.isEmpty()) throw JsonException.newInstance(ErrorCodes.PARAM_NOT_EMPTY);

        String path = baseSavePath+fileName;

        File file = new File(path);
        if(file.exists() && file.isFile()){
            if(file.delete()){
                return success();
            }else {
                throw JsonException.newInstance(ErrorCodes.DATA_OP_FAILED);
            }
        }else{
            throw JsonException.newInstance(ErrorCodes.FILE_NOT_EXSIT);
        }
    }

    public static JSONObject success(Integer id){
        JSONObject rs = new JSONObject();
        rs.put("retCode", 0);
        rs.put("retMsg", "操作成功");
        rs.put("id", id);
        return  rs;
    }
    public static JSONObject success(){
        JSONObject rs = new JSONObject();
        rs.put("retCode", 0);
        rs.put("retMsg", "操作成功");
        return  rs;
    }

    public static JSONObject success(Object obj){
        JSONObject rs = new JSONObject();
        rs.put("retCode", 0);
        rs.put("retMsg", "操作成功");

        Map<String, Object> data = BeanMap.create(obj);
        rs.putAll(data);
        return rs;
    }

    public static JSONObject success(String msg){
        JSONObject rs = new JSONObject();
        rs.put("retCode", 0);
        rs.put("retMsg", msg);
        return  rs;
    }

    public static JSONObject fail(int errCode){
        JSONObject rs = new JSONObject();
        rs.put("retCode", errCode);
        rs.put("retMsg", "操作失败");
        return  rs;
    }

    public static JSONObject fail(){
        return JsonException.newInstance(ErrorCodes.SERVER_IS_WRONG).toJson();
    }

    public static JSONObject fail(String msg){
        JSONObject rs =  JsonException.newInstance(ErrorCodes.CUSTOM_ERROR).toJson();
        rs.put("retMsg", msg);
        return rs;
    }

    public static JSONObject obj2Json(Object object){
        JSONObject rs = new JSONObject();

        Map<String, Object> data = BeanMap.create(object);
        rs.putAll(data);
        return rs;
    }


    public static String setUrlParam(String url, String key, String val){
        if (!StringUtils.isNotBlank(url) && !StringUtils.isNotBlank(key)) return url;
        String local = url;
        String paramStr = "";
        if(url.contains("?")){
            paramStr = url.substring(url.indexOf("?")+1);
            local = url.substring(0, url.indexOf("?"));
        }

        Map<String, Object> paramMap = new HashMap<>();
        if(StringUtils.isNotBlank(paramStr)){
            //拆解参数
            String[] paramArr = paramStr.split("&");
            for(String param : paramArr ){
                int index = param.indexOf("=");
                String k = param.substring(0, index);
                String v = param.substring(index+1);
                paramMap.put(k, v);
            }
        }

        //hashmap 元素唯一
        paramMap.put(key, val);

        // 拼接参数
        String paramStr1 = "";
        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            paramStr1 += entry.getKey()+"="+entry.getValue()+"&";
        }
        paramStr1 = paramStr1.substring(0, paramStr1.length()-1);
        String newUrl = local+"?"+paramStr1;
        return newUrl;
    }

    //手机号脱敏 13198765432 --> 131****5432
    public static String maskMobile(String originMobile){
        //不能为空
        if(originMobile == null || originMobile.isEmpty() || originMobile.length() <3){
            return originMobile;
        }
        String newMobile = originMobile;
        String prefix = "";
        if(originMobile.indexOf('+')>-1){ //包含 +XX-前缀
            int preBot = originMobile.indexOf('-')+1;
            prefix = originMobile.substring(0, preBot);
            newMobile = originMobile.substring(preBot, originMobile.length());
        }
        int length = newMobile.length();
        if(length < 3){
            return originMobile;
        }
        String start = "";
        String end = "";
        int mask = 0;
        if(length <6){
            start = newMobile.substring(0,1);
            end = newMobile.substring(length-1, length);
            mask = length-2;
        }else {
            int maskLen = length -4;
            int s = maskLen/2;
            int e = maskLen - s;
            start = newMobile.substring(0, s);
            end = newMobile.substring(length-e, length);
            mask = 4;
        }

        //拼接
        newMobile = prefix+start;
        for (int i=0; i<mask; i++){
            newMobile += "*";
        }
        newMobile += end;
        return newMobile;
    }

    // 邮箱脱敏：dailm@126.com-->dai**@126.com
    public static String maskEmail(String email){

        if (StringUtils.isBlank(email)) {
            return email;
        }

        int lastPos = email.lastIndexOf("@");
        if (lastPos == -1) {
            return email;
        }

        if (lastPos > 3) {
            StringBuilder star = new StringBuilder();
            for (int i = 3; i < lastPos; i++) {
                star.append("*");
            }

            return email.substring(0, 3) + star.toString() + email.substring(lastPos);
        }

        return email;
    }

    // 姓名脱敏
    public static String maskName(String name){
        if(StringUtils.isBlank(name))
            return name;
        int length = name.length();
        int show = 1;
        if(length>3){
            show = 2;
        }
        name = name.substring(0,show);
        for (int i=0; i<length-show;i++){
            name += "*";
        }

        return name;
    }

    //生成随机字符串
    public static String getRandomStr(int length, String pre){
        if(StringUtils.isBlank(pre)){
            pre = "";
        }
        length = length - pre.length();
        return pre + RandomStringUtils.randomAlphanumeric(length);
    }

    public static String getRandomStr(int length){
        return getRandomStr(length, null);
    }

}
