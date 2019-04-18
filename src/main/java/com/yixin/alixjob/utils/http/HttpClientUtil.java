package com.yixin.alixjob.utils.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @description: HttpClient工具类
 * @autor: calvin_chen
 * @createDate: 2017/12/8 15:29
 * @version: 1.0.0
 */
@Component
public class HttpClientUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    @Autowired(required = false)
    private CloseableHttpClient httpClient;

    @Autowired(required = false)
    private RequestConfig requestConfig;
    
    /**
     * 执行Get请求
     *
     * @param url
     * @return 请求到的内容
     * @throws URISyntaxException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public HttpResult doGet(String url) throws URISyntaxException, ClientProtocolException, IOException {
        return doGet(url, null);
    }

    /**
     *
     * @param url
     * @return
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public HttpResult doPost(String url) throws URISyntaxException, ClientProtocolException, IOException {
        return doPost(url, null);
    }

    /**
     * 执行Get请求
     *
     * @param url
     * @param params 请求中的参数
     * @return 请求到的内容
     * @throws URISyntaxException
     * @throws IOException
     * @throws ClientProtocolException
     */
    public HttpResult doGet(String url, Map<String, Object> params) throws URISyntaxException, ClientProtocolException, IOException {
        logger.info("doGet请求url:{},请求参数:{}", url, params);
        URI uri = null;
        if (null != params) {
            URIBuilder builder = new URIBuilder(url);
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                builder.addParameter(entry.getKey(), String.valueOf(entry.getValue()));
            }
            uri = builder.build();
        }
        HttpGet httpGet = null;
        if (null != uri) {
            httpGet = new HttpGet(uri);
        } else {
            httpGet = new HttpGet(url);
        }
        httpGet.setConfig(this.requestConfig);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpGet);
            if (200 == response.getStatusLine().getStatusCode()) {
                int respStatusCode = response.getStatusLine().getStatusCode();
                String respData = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info("doGet请求返回码:{},返回报文:{}", respStatusCode, respData);
                return new HttpResult(respStatusCode, respData);
            } else {
                logger.error("doGet请求失败！返回状态码:{}", response.getStatusLine().getStatusCode());
            }
        } catch(Exception e) {
            logger.error("doGet请求失败！", e);
            throw e;
        }finally {
            if (null != response) {
                response.close();
            }
        }
        return null;
    }

    /**
     * @param url
     * @param params 请求中的参数
     * @return 请求到的内容
     * @throws URISyntaxException
     * @throws ClientProtocolException
     * @throws IOException
     */
    public HttpResult doPost(String url, Map<String, Object> params) throws URISyntaxException, ClientProtocolException, IOException {
        logger.info("doPost请求url:{},请求参数:{}", url, params);
        List<NameValuePair> parameters = null;
        UrlEncodedFormEntity formEntity = null;
        if (params != null) {
            parameters = new ArrayList<NameValuePair>();
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                parameters.add(new BasicNameValuePair(entry.getKey(), String.valueOf(entry.getValue())));
            }
            formEntity = new UrlEncodedFormEntity(parameters,"UTF-8");
        }

        HttpPost httpPost = null;
        if (null != formEntity) {
            httpPost = new HttpPost(url);
            httpPost.setEntity(formEntity);
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
        } else {
            httpPost = new HttpPost(url);
            httpPost.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36");
        }
        httpPost.setConfig(this.requestConfig);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if (200 == response.getStatusLine().getStatusCode()) {
                int respStatusCode = response.getStatusLine().getStatusCode();
                String respData = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info("doPost请求返回码:{},返回报文:{}", respStatusCode, respData);
                return new HttpResult(respStatusCode, respData);
            } else {
                logger.error("doPost请求失败！返回状态码:{}", response.getStatusLine().getStatusCode());
            }
        } catch (Exception e) {
            logger.error("doPost请求失败！", e);
            throw e;
        }finally {
            if (null != response) {
                response.close();
            }
        }
        return null;
    }

    /**
     *
     * @param url
     * @param jsonParams 请求中的参数
     * @return 请求到的内容
     * @throws IOException
     */
    public HttpResult doPostJson(String url, String jsonParams) throws IOException {
        if(url!=null&&url.contains("fileserver/api/fileUpload/uploadFile")){//传送授权书文件输出报文太长，不打印日志
        	
        }else{
        	logger.info("doPostJson请求url:{},请求参数:{}", url, jsonParams);
        }
    	HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (StringUtils.isNotEmpty(jsonParams)) {
            StringEntity stringEntity = new StringEntity(jsonParams, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
        }
        httpPost.setHeader("Content-type", "application/json");
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if (200 == response.getStatusLine().getStatusCode()) {
                int respStatusCode = response.getStatusLine().getStatusCode();
                String respData = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info("doPostJson请求url:"+url+",返回码:{},返回报文:{}", respStatusCode, respData);
                return new HttpResult(respStatusCode, respData);
            } else {
                logger.error("doPostJson请求url:"+url+"请求失败！返回状态码:{}", response.getStatusLine().getStatusCode());
                return new HttpResult(8888, null);
            }
        } catch(Exception e) {
            logger.error("doPostJson请求url:"+url+"请求失败！", e);
            //throw e;
            return new HttpResult(9999, null);
        }finally {
            if (null != response) {
                response.close();
            }
        }
       ///return null;
    }
    
    public HttpResult doPostJsonToZhongrui(String url, String jsonParams,String appKey) throws IOException {
        if(url!=null&&url.contains("fileserver/api/fileUpload/uploadFile")){//传送授权书文件输出报文太长，不打印日志
        	
        }else{
        	logger.info("doPostJson请求url:{},请求参数:{}", url, jsonParams);
        }
    	HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (StringUtils.isNotEmpty(jsonParams)) {
            StringEntity stringEntity = new StringEntity(jsonParams, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
        }
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("appkey", appKey);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if (200 == response.getStatusLine().getStatusCode()) {
                int respStatusCode = response.getStatusLine().getStatusCode();
                String respData = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info("doPostJson请求url:"+url+",返回码:{},返回报文:{}", respStatusCode, respData);
                return new HttpResult(respStatusCode, respData);
            } else {
                logger.error("doPostJson请求url:"+url+"请求失败！返回状态码:{}", response.getStatusLine().getStatusCode());
                return new HttpResult(8888, null);
            }
        } catch(Exception e) {
            logger.error("doPostJson请求url:"+url+"请求失败！", e);
            //throw e;
            return new HttpResult(9999, null);
        }finally {
            if (null != response) {
                response.close();
            }
        }
       ///return null;
    }
    
    
    /**
    * ssp请求单独处理
    *
    *@author fengzhiwen
    * @param url
    * @param jsonParams 请求中的参数
    * @param type 请求类型
    * 
    * @return 请求到的内容
    * @throws IOException
    */
	public HttpResult doPostJsonSSP(String url, String jsonParams) throws IOException {
		if (url != null && url.contains("fileserver/api/fileUpload/uploadFile")) {// 传送授权书文件输出报文太长，不打印日志

		} else {
			logger.info("doPostJson请求url:{},请求参数:{}", url, jsonParams);
		}
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(requestConfig);
		if (StringUtils.isNotEmpty(jsonParams)) {
			StringEntity stringEntity = new StringEntity(jsonParams, ContentType.APPLICATION_JSON);
			httpPost.setEntity(stringEntity);
		}
		httpPost.setHeader("Content-type", "application/json");
		CloseableHttpResponse response = null;
		try {
			
			response = httpClient.execute(httpPost);
			int respStatusCode = response.getStatusLine().getStatusCode();
			if (200 == response.getStatusLine().getStatusCode()) {
				
				String respData = EntityUtils.toString(response.getEntity(), "UTF-8");
				logger.info("doPostJson请求url:" + url + ",返回码:{},返回报文:{}", respStatusCode, respData);
				return new HttpResult(respStatusCode, respData, jsonParams, url);
				
			} else {
				logger.error("doPostJson请求url:" + url + "请求失败！返回状态码:{}", response.getStatusLine().getStatusCode());
				return new HttpResult(respStatusCode, null, jsonParams, url);
				
			}
		} catch (Exception e) {
			logger.error("doPostJson请求url:" + url + "请求失败！", e);
			
			return new HttpResult(9999, String.valueOf(e.getCause()), jsonParams, url);
		} finally {
			if (null != response) {
				response.close();
			}
		}
		/// return null;
	}
	
    /**
     *
     * @param url
     * @param jsonParams 请求中的参数
     * @return 请求到的内容
     * @throws IOException
     */
    public HttpResult doPostString(String url, String params) throws IOException {
        logger.info("doPostString请求url:{},请求参数:{}", url, params);
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (StringUtils.isNotEmpty(params)) {
            StringEntity stringEntity = new StringEntity(params, "utf-8");
            httpPost.setHeader("Content-type", "application/json");
            httpPost.setEntity(stringEntity);
        }
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(httpPost);
            if (200 == response.getStatusLine().getStatusCode()) {
                int respStatusCode = response.getStatusLine().getStatusCode();
                String respData = EntityUtils.toString(response.getEntity(), "UTF-8");
                logger.info("doPostString请求返回码:{},返回报文:{}", respStatusCode, respData);
                return new HttpResult(respStatusCode, respData);
            } else {
                logger.error("doPostString请求失败！返回状态码:{}", response.getStatusLine().getStatusCode());
            }
        } catch(Exception e) {
            logger.error("doPostString请求失败！", e);
            throw e;
        }finally {
            if (null != response) {
                response.close();
            }
        }
        return null;
    }
}