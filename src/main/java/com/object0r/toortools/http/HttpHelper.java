package com.object0r.toortools.http;

import org.apache.commons.io.IOUtils;

import java.io.DataOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Nikos Glikis
 * <p>
 * Uses HTTP to make basic requests.
 */
public class HttpHelper
{
    public static HttpResult basicPostRequest(String url, String body) throws Exception
    {
        return basicPostRequest(url, body, "");
    }

    public static HttpResult basicPostRequest(String url, String body, String cookie) throws Exception
    {
        HttpRequestInformation httpRequestInformation = new HttpRequestInformation();
        httpRequestInformation.setUrl(url);
        if (cookie != null)
        {
            httpRequestInformation.setCookie(cookie);
        }
        httpRequestInformation.setMethodPost();
        httpRequestInformation.setBody(body);

        return HttpHelper.request(httpRequestInformation);
    }

    public static HttpResult basicGetRequest(String url) throws Exception
    {
        return basicGetRequest(url, null);
    }

    public static HttpResult basicGetRequest(String url, String cookie) throws Exception
    {
        HttpRequestInformation httpRequestInformation = new HttpRequestInformation();
        httpRequestInformation.setUrl(url);
        if (cookie != null)
        {
            httpRequestInformation.setCookie(cookie);
        }
        httpRequestInformation.setMethodGet();

        HttpResult httpResult = HttpHelper.request(httpRequestInformation);

        return httpResult;
    }


    public static HttpResult request(HttpRequestInformation httpRequestInformation) throws Exception
    {
        URL url;
        HttpURLConnection connection = null;
        InputStream is;
        HttpResult httpResult = new HttpResult();
        if (httpRequestInformation.getUrl() == null)
        {
            throw new Exception("Url is not set in HttpRequestInformation.");
        }
        if (httpRequestInformation.getProxy() == null)
        {
            throw new Exception("Proxy is null HttpRequestInformation.");
        }

        try
        {

            // Create connection
            url = new URL(httpRequestInformation.getUrl());
            connection = (HttpURLConnection) url.openConnection(httpRequestInformation.getProxy());

            Iterator<Map.Entry<String, String>> it = httpRequestInformation.getHeaders().entrySet().iterator();
            while (it.hasNext())
            {
                Map.Entry<String, String> pair = it.next();
                connection.setRequestProperty(pair.getKey(), pair.getValue());
                it.remove();
            }

            //TODO maybe put below in helper.
            connection.setRequestProperty("Content-Language", "en-US");

            if (httpRequestInformation.hasTimeOut())
            {
                connection.setReadTimeout(httpRequestInformation.getTimeoutSeconds() * 1000);
                connection.setConnectTimeout(httpRequestInformation.getTimeoutSeconds() * 1000);
            }

            if (httpRequestInformation.isMethodPost())
            {
                connection.setRequestMethod("POST");
                if (connection.getRequestProperty("Content-Type") == null)
                {
                    connection.setRequestProperty("Content-Type",
                            "application/x-www-form-urlencoded");
                }

                connection.setRequestProperty("Content-Length", ""
                        + Integer.toString(httpRequestInformation.getBody().getBytes().length));
                connection.setUseCaches(false);
                connection.setDoInput(true);
                connection.setDoOutput(true);
                // Send request
                DataOutputStream wr = new DataOutputStream(connection
                        .getOutputStream());
                wr.writeBytes(httpRequestInformation.getBody());
                wr.flush();
                wr.close();
            }
            else
            {
                connection.setRequestMethod(httpRequestInformation.getMethod());
            }

            if (httpRequestInformation.isFollowRedirects())
            {
                connection.setInstanceFollowRedirects(true);
            }
            else
            {
                connection.setInstanceFollowRedirects(false);
            }
            is = connection.getInputStream();
            byte[] bytes = IOUtils.toByteArray(is);


            httpResult.setContent(bytes);

            if (connection.getErrorStream() != null)
            {
                is = connection.getErrorStream();
                bytes = IOUtils.toByteArray(is);
                httpResult.setErrorContent(bytes);
                //System.out.println(new String(bytes));
            }


            Map<String, List<String>> map = connection.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : map.entrySet())
            {
                if (entry.getKey() != null)
                {
                    List<String> l = entry.getValue();
                    Iterator<String> iter = l.iterator();
                    for (String c : l)
                    {
                        httpResult.addHeader(entry.getKey(), c);
                        //cookies += c +"; ";
                    }
                }
            }
        }
        catch (Exception e)
        {
            if (connection.getErrorStream() != null)
            {
                is = connection.getErrorStream();
                byte[] bytes = IOUtils.toByteArray(is);
                httpResult.setErrorContent(bytes);
                //System.out.println(new String(bytes));
            }
            try
            {
                connection.disconnect();
            }
            catch (Exception ee)
            {

            }

            if (httpRequestInformation.isThrowExceptions())
            {
                throw e;
            }
            else
            {
                httpResult.setThrownException(e);
            }
        }
        finally
        {

            if (connection != null)
            {
                connection.disconnect();
            }
        }
        return httpResult;
    }
}
