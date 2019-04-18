package com.yixin.alixjob.utils.http;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.http.client.ClientProtocolException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OKHttpUtils {

    private static final Logger logger = LoggerFactory.getLogger(OKHttpUtils.class);
    private static final MediaType MediaType_JSON  = MediaType.parse("application/json; charset=utf-8");

    private static OkHttpClient okHttpClient = null;

    private OKHttpUtils() {
    }

    public static OkHttpClient getInstance() {
        if (okHttpClient == null) {
            synchronized (OKHttpUtils.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()//构建器
                            .connectTimeout(15, TimeUnit.SECONDS)//连接超时
                            .writeTimeout(20, TimeUnit.SECONDS)//写入超时
                            .readTimeout(20, TimeUnit.SECONDS)//读取超时
                            .addInterceptor(new CommonParamsInterceptor())//拦截器
                            .build();
                }
            }
        }
        return okHttpClient;
    }



    /**
     * @param url
     * @param params 请求中的参数
     * @return HttpResult
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static HttpResult doPostJSON(String url, Map<String, Object> params) {
        String paramsJson = JSONObject.toJSONString(params);
        return doPostJSON(url,paramsJson);
    }

    /**
     * @param url
     * @param jsonParams 请求中的参数
     * @return HttpResult
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static HttpResult doPostJSON(String url, String jsonParams)  {

        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getInstance();
        RequestBody body = RequestBody.create(MediaType_JSON, jsonParams);
        //创建Request
        Request request = new Request.Builder().url(url).post(body).build();
        Call call = okHttpClient.newCall(request);
        logger.info("doPostJson请求url{},请求报文:{}", url, jsonParams);

        Response response = null;
        try {
            response = call.execute();
            if (response.isSuccessful()) {
                String respData = response.body().string();
                logger.info("doPostJson请求url{},doPostJson请求返回码:{},返回报文:{}", url, response.code(), respData);
                return new HttpResult(response.code(), respData);
            } else {
                logger.error("doPostJson请求url{}失败！返回状态码:{}", url, response.code());
                return new HttpResult(8888, null);
            }
        } catch (Exception e) {
            logger.error("doPost请求url:"+url+",失败！", e);
            return new HttpResult(9999, null);
        }finally {
            if (null != response) {
                response.close();
            }
        }
    }



    public static void doGet(String oldUrl, Callback callback) {

        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getInstance();
        //创建Request
        Request request = new Request.Builder().url(oldUrl).build();
        //得到Call对象
        Call call = okHttpClient.newCall(request);
        //执行异步请求
        call.enqueue(callback);

    }


    public static void doPost(String url, Map<String, String> params, Callback callback) {

        //创建OkHttpClient请求对象
        OkHttpClient okHttpClient = getInstance();
        //3.x版本post请求换成FormBody 封装键值对参数

        FormBody.Builder builder = new FormBody.Builder();
        //遍历集合
        for (String key : params.keySet()) {
            builder.add(key, params.get(key));
        }
        //创建Request
        Request request = new Request.Builder().url(url).post(builder.build()).build();
        Call call = okHttpClient.newCall(request);
        //异步请求
        call.enqueue(callback);

    }

    /**
     * 公共参数拦截器
     */
    private static class CommonParamsInterceptor implements Interceptor{

        //拦截的方法
        @Override
        public Response intercept(Chain chain) throws IOException {

            //获取到请求
            Request request = chain.request();
            //获取请求的方式
            String method = request.method();
            //获取请求的路径
            String oldUrl = request.url().toString();

            logger.info(">>>>>>>okhttp请求："+request.toString());

            Response response = chain.proceed(request);
            return response;
        }
    }

} 