package com.xbchen.demo.client;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxiaobing
 */
public class HttpClientDemo{




    public static String post(Map<String, Object> sParaTemp, String url) throws Exception{
        HttpClient httpClient = new HttpClient();

        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
        List<String> keys = new ArrayList<String>(sParaTemp.keySet());
        NameValuePair[] param = new NameValuePair[keys.size()+1];
        for (int i = 0; i < keys.size(); i++) {
            String name = keys.get(i);
            Object object = sParaTemp.get(name);
            String value = "";
            if (object != null) {
                value = sParaTemp.get(name).toString();
            }
            //添加参数
            param[i] = new NameValuePair(name, value);
            post.setParameter(param[i].getName(),param[i].getValue());
            //System.out.println(param[i].getName());
        }
        HttpMethod method = post;
        httpClient.executeMethod(method);
        String response = method.getResponseBodyAsString();
        post.releaseConnection();
        return response;
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> paramsMap =new HashMap<String,Object>();
        paramsMap.put("seq","123456");
        paramsMap.put("sign","5d7acb0ef7c6c61c5981b4fd972255981504d4b6fa186d93aafc48b6ada41ea4");
        //查询部门信息
        System.out.println(post(paramsMap,"http://p100.2bulu.com:1241/queryDepartmentInfos"));

        paramsMap.put("date","2018-05-04");
        paramsMap.put("userId","10595");
        paramsMap.put("currPageIndex",1);
        paramsMap.put("pageSize",5);
        String url2="http://p100.2bulu.com:1241/queryPosInfos";
        System.out.println(post(paramsMap,url2));
    }
}