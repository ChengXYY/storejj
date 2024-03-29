package com.cy.storejj.utils;

import com.alibaba.fastjson.JSONObject;
import com.cy.storejj.config.CommonConfig;
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
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonOperation extends CommonConfig {
    public Map<String, Object> encodeStr(String str){
        Map<String, Object> rs = new HashMap<String, Object>();
        //get salt
        String salt = UUID.randomUUID().toString();
        rs.put("salt", salt);
        //md5
        String str1 = DigestUtils.md5DigestAsHex((str+salt).getBytes());
        rs.put("newstr",str1);
        return rs;
    }

    public String encodeStr(String str, String salt){
        String str1 = DigestUtils.md5DigestAsHex((str+salt).getBytes());
        return str1;
    }

    public boolean checkId(Integer id){
        if(id == null) return false;
        if(id.toString().isEmpty()) return false;
        if(id < 1)return false;
        return true;
    }

    public boolean checkEmail(String email){
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
    public JSONObject uploadFile(MultipartFile file, String myFileName){
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

    public JSONObject uploadFile(MultipartFile file){
        return uploadFile(file, null);
    }

    //查看图片
    public  void getImage(String filename,
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

    public  JSONObject removeFile(String fileName){
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

    public JSONObject success(Integer id){
        JSONObject rs = new JSONObject();
        rs.put("retCode", 0);
        rs.put("retMsg", "操作成功");
        rs.put("id", id);
        return  rs;
    }
    public JSONObject success(){
        JSONObject rs = new JSONObject();
        rs.put("retCode", 0);
        rs.put("retMsg", "操作成功");
        return  rs;
    }

    public JSONObject success(Object obj){
        JSONObject rs = new JSONObject();
        rs.put("retCode", 0);
        rs.put("retMsg", "操作成功");

        Map<String, Object> data = BeanMap.create(obj);
        rs.putAll(data);
        return rs;
    }

    public JSONObject success(String msg){
        JSONObject rs = new JSONObject();
        rs.put("retCode", 0);
        rs.put("retMsg", msg);
        return  rs;
    }

    public JSONObject fail(int errCode){
        JSONObject rs = new JSONObject();
        rs.put("retCode", errCode);
        rs.put("retMsg", "操作失败");
        return  rs;
    }

    public JSONObject fail(){
        return JsonException.newInstance(ErrorCodes.SERVER_IS_WRONG).toJson();
    }

    public JSONObject fail(String msg){
        JSONObject rs =  JsonException.newInstance(ErrorCodes.CUSTOM_ERROR).toJson();
        rs.put("retMsg", msg);
        return rs;
    }

    public JSONObject obj2Json(Object object){
        JSONObject rs = new JSONObject();

        Map<String, Object> data = BeanMap.create(object);
        rs.putAll(data);
        return rs;
    }


    public String setUrlParam(String url, String key, String val){
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
    public String maskMobile(String originMobile){
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
    public String maskEmail(String email){

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
    public String maskName(String name){
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
    public String getRandomStr(int length, String pre){
        if(StringUtils.isBlank(pre)){
            pre = "";
        }
        length = length - pre.length();
        return pre + RandomStringUtils.randomAlphanumeric(length);
    }

    public String getRandomStr(int length){
        return getRandomStr(length, null);
    }

    //提取 html img标签的 src值
    public List<String> getImgSrc(String content){
        List<String> list = new ArrayList();
        //目前img标签标示有3种表达式
        //<img alt="" src="1.jpg"/> <img alt="" src="1.jpg"></img> <img alt="" src="1.jpg">
        //开始匹配content中的<img />标签
        Pattern p_img = Pattern.compile("<(img|IMG)(.*?)(/>|></img>|>)");
        Matcher m_img = p_img.matcher(content);
        boolean result_img = m_img.find();
        if (result_img) {
            while (result_img) {
                //获取到匹配的<img />标签中的内容
                String str_img = m_img.group(2);
                //开始匹配<img />标签中的src
                Pattern p_src = Pattern.compile("(src|SRC)=(\"|\')(.*?)(\"|\')");
                Matcher m_src = p_src.matcher(str_img);
                if (m_src.find()) {
                    String str_src = m_src.group(3);
                    list.add(str_src);
                }
                //匹配content中是否存在下一个<img />标签，有则继续以上步骤匹配<img />标签中的src
                result_img = m_img.find();
            }
        }
        return list;
    }

    //提取 html 纯文本内容
    public String getText(String strHtml) {
        String txtcontent = strHtml.replaceAll("</?[^>]+>", ""); //剔出<html>的标签
        txtcontent = txtcontent.replaceAll("<a>\\s*|\t|\r|\n</a>", "");//去除字符串中的空格,回车,换行符,制表符
        return txtcontent;
    }

    //获取ip地址
    public  String getIpAddress(HttpServletRequest request) {
        String ipAddress = null;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1")) {
                    // 根据网卡取本机配置的IP
                    InetAddress inet = null;
                    try {
                        inet = InetAddress.getLocalHost();
                    } catch (UnknownHostException e) {
                        e.printStackTrace();
                    }
                    ipAddress = inet.getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length()
                // = 15
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress="";
        }
        // ipAddress = this.getRequest().getRemoteAddr();

        return ipAddress;
    }


}
