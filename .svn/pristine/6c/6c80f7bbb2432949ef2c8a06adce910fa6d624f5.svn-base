package com.yixin.demo.http;

import okhttp3.*;
import org.junit.Test;

import java.io.IOException;

/**
 * http框架  OkHttp
 *
 * @author yaojie
 * @Description: TODO
 * @date 2018/9/299:27
 */
public class OkhttpTest {

    static   OkHttpClient client = new OkHttpClient();

    public static String GET(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {

            return response.body().string();
        }
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Test
    public  void  get() throws IOException {


//        String response = GET("https://raw.github.com/square/okhttp/master/README.md");
//        System.out.println(response);


        String json="{\"orderIds\":[\"800009\"],\"appId\":\"taochepay\",\"transId\":\"TC20180929120401681628\",\"orderCount\":\"1\",\"reqTime\":\"20180929120401\"}";
        RequestBody body = RequestBody.create(JSON, json);



        String url="http://192.168.145.117:8082/alixapi/api/taoche/queryOrderLoanInfo";
        final Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        final Call call = client.newCall(request);

        Response response = call.execute();

        String result;
        if(response.isSuccessful()){
            result = response.body().string();
        }else{
            throw new IOException("Unexpected code " + response);
        }

        System.out.println("result" + result);



    }
}
