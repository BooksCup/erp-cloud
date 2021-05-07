package com.bc.common.utils;

import com.bc.common.cons.Constant;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.*;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * http工具类
 *
 * @author zhou
 */
@Configuration
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static final String REQ_ENCODING_UTF8 = "utf-8";
    private static PoolingHttpClientConnectionManager httpClientConnectionManager;

    public HttpUtil() {
        httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        httpClientConnectionManager.setMaxTotal(100);
        httpClientConnectionManager.setDefaultMaxPerRoute(20);
    }

    /**
     * get请求
     *
     * @param url url
     * @return 返回值
     * @throws Exception 异常
     */
    public static String httpGet(String url) throws Exception {
        return httpGet(url, null);
    }

    /**
     * get请求
     *
     * @param url     url
     * @param headers 请求头
     * @return 返回值
     * @throws Exception 异常
     */
    public static String httpGet(String url, Header[] headers) throws Exception {
        HttpUriRequest uriRequest = new HttpGet(url);
        if (null != headers) {
            uriRequest.setHeaders(headers);
        }
        CloseableHttpClient httpClient = null;
        try {
            httpClient = declareSslHttpClient(url);
            CloseableHttpResponse httpResponse = httpClient.execute(uriRequest);
            HttpEntity httpEntity = httpResponse.getEntity();
            return EntityUtils.toString(httpEntity, REQ_ENCODING_UTF8);
        } catch (ClientProtocolException e) {
            logger.error(String.format("http请求失败，uri{%s},exception{%s}", new Object[]{url, e}));
        } catch (IOException e) {
            logger.error(String.format("IO Exception，uri{%s},exception{%s}", new Object[]{url, e}));
        } finally {
            if (null != httpClient) {
                httpClient.close();
            }
        }
        return null;
    }

    /**
     * post请求
     *
     * @param url     url
     * @param headers 请求头
     * @param params  请求参数
     * @return 返回值
     * @throws Exception 异常
     */
    public static String httpPost(String url, Header[] headers, String params) throws Exception {
        HttpPost post = new HttpPost(url);
        if (null != headers) {
            post.setHeaders(headers);
        }
        post.setEntity(new StringEntity(params, Charset.forName("utf-8")));
        HttpResponse httpResponse;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = declareSslHttpClient(url);
            httpResponse = httpClient.execute(post);
            HttpEntity httpEntity = httpResponse.getEntity();
            return EntityUtils.toString(httpEntity, REQ_ENCODING_UTF8);
        } catch (ClientProtocolException e) {
            logger.error(String.format("http请求失败，uri{%s},exception{%s}", new Object[]{url, e}));
        } catch (IOException e) {
            logger.error(String.format("IO Exception，uri{%s},exception{%s}", new Object[]{url, e}));
        } finally {
            if (null != httpClient) {
                httpClient.close();
            }
        }
        return null;
    }

    /**
     * delete请求
     *
     * @param url     url
     * @param headers 请求头
     * @return 返回值
     * @throws Exception 异常
     */
    public static String httpDelete(String url, Header[] headers) throws Exception {
        HttpDelete delete = new HttpDelete(url);
        if (null != headers) {
            delete.setHeaders(headers);
        }
        CloseableHttpClient httpClient = null;
        try {
            httpClient = declareSslHttpClient(url);
            CloseableHttpResponse httpResponse = httpClient.execute(delete);
            HttpEntity httpEntity = httpResponse.getEntity();
            return EntityUtils.toString(httpEntity, REQ_ENCODING_UTF8);
        } catch (ClientProtocolException e) {
            logger.error(String.format("http请求失败，uri{%s},exception{%s}", new Object[]{url, e}));
        } catch (IOException e) {
            logger.error(String.format("IO Exception，uri{%s},exception{%s}", new Object[]{url, e}));
        } finally {
            if (null != httpClient) {
                httpClient.close();
            }
        }
        return null;
    }

    /**
     * put请求
     *
     * @param url     url
     * @param headers 请求头
     * @param params  请求参数
     * @return 返回值
     * @throws Exception 异常
     */
    public static String httpPut(String url, Header[] headers, String params) throws Exception {
        HttpPut put = new HttpPut(url);
        if (null != headers) {
            put.setHeaders(headers);
        }
        // 设置传输编码格式
        if (!StringUtils.isEmpty(params)) {
            put.setEntity(new StringEntity(params, Charset.forName("utf-8")));
        }
        HttpResponse httpResponse;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = declareSslHttpClient(url);
            httpResponse = httpClient.execute(put);
            HttpEntity httpEntity = httpResponse.getEntity();
            return EntityUtils.toString(httpEntity, REQ_ENCODING_UTF8);
        } catch (ClientProtocolException e) {
            logger.error(String.format("http请求失败，uri{%s},exception{%s}", new Object[]{url, e}));
        } catch (IOException e) {
            logger.error(String.format("IO Exception，uri{%s},exception{%s}", new Object[]{url, e}));
        } finally {
            if (null != httpClient) {
                httpClient.close();
            }
        }
        return null;
    }

    /**
     * 发送字节流
     *
     * @param url     url
     * @param bytes   字节流
     * @param headers 请求头
     * @return 返回值
     * @throws IOException 异常
     */
    public static String httpPutBytes(String url, byte[] bytes, Header[] headers) throws IOException {
        HttpPut put = new HttpPut(url);
        if (null != headers) {
            put.setHeaders(headers);
        }
        // 设置传输编码格式
        if (bytes != null) {
            put.setEntity(new ByteArrayEntity(bytes));
        }
        HttpResponse httpResponse;
        CloseableHttpClient httpClient = null;
        try {
            httpClient = declareSslHttpClient(url);
            httpResponse = httpClient.execute(put);
            HttpEntity httpEntity = httpResponse.getEntity();
            return EntityUtils.toString(httpEntity, REQ_ENCODING_UTF8);
        } catch (ClientProtocolException e) {
            logger.error(String.format("http请求失败，uri{%s},exception{%s}", new Object[]{url, e}));
        } catch (IOException e) {
            logger.error(String.format("IO Exception，uri{%s},exception{%s}", new Object[]{url, e}));
        } finally {
            if (null != httpClient) {
                httpClient.close();
            }
        }
        return null;
    }

    /**
     * 声明支持SSL的HTTP客户端
     *
     * @param url url
     * @return 支持SSL的HTTP客户端
     */
    private static CloseableHttpClient declareSslHttpClient(String url) {
        if (url.startsWith(Constant.PROTOCOL_HTTPS_PREFIX)) {
            return sslClient();
        } else {
            return HttpClientBuilder.create().setConnectionManager(httpClientConnectionManager).build();
        }
    }

    /**
     * 设置支持SSL的HTTP客户端
     *
     * @return 支持SSL的HTTP客户端
     */
    private static CloseableHttpClient sslClient() {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] xcs, String str) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] xcs, String str) {
                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLConnectionSocketFactory sslConnectionSocketFactory = SSLConnectionSocketFactory.getSocketFactory();
            return HttpClients.custom().setSSLSocketFactory(sslConnectionSocketFactory).build();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (KeyManagementException e) {
            throw new RuntimeException(e);
        }
    }

}