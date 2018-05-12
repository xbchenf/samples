package com.xbchen.demo.client;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by chenxiaobing
 */
public class HttpClientDemo1 {



    public static String get(String url) throws Exception{
            GetMethod getMethod = new GetMethod(url);
            getMethod.setRequestHeader("Content-Type","application/x-www-form-urlencoded;charset=utf-8");
            HttpClient httpClient = new HttpClient();
            httpClient.executeMethod(getMethod);
            String response = getMethod.getResponseBodyAsString();
            getMethod.releaseConnection();
            return response;
    }

    public static String post(Object obj, String url) throws Exception{
        HttpClient httpClient = new HttpClient();
        PostMethod post = new PostMethod(url);
        post.setRequestHeader("Content-Type", "application/json; charset=utf8");
        post.setRequestBody(JsonUtil.obj2String(obj));
        HttpMethod method = post;
        httpClient.executeMethod(method);
        String response = method.getResponseBodyAsString();
        post.releaseConnection();
        return response;
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> paramsMap1 =new HashMap<String,Object>();
        paramsMap1.put("name","xiaobing");
        System.out.println("POST请求：..."+post(paramsMap1,"http://localhost:8080/SpringBootRestApi/api/user"));
       // System.out.println("GET请求：..."+get("http://localhost:8080/SpringBootRestApi/api/user/123?name=helloworld"));
    }
}