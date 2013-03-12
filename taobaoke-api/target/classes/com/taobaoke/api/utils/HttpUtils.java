package com.taobaoke.api.utils;

import java.io.IOException;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class HttpUtils {
    public static String getFileContentFromUrl(String url) throws Exception {
        HttpEntity httpEntity = getResponseEntity(url);

        return EntityUtils.toString(httpEntity);
    }

    private static HttpEntity getResponseEntity(String url) throws URISyntaxException, IOException, ClientProtocolException {
        DefaultHttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet();
        httpget.setURI(new java.net.URI(url));
        HttpResponse response = httpclient.execute(httpget);
        HttpEntity httpEntity = response.getEntity();
        return httpEntity;
    }
}
