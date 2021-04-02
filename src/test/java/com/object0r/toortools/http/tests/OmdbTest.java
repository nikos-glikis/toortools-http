package com.object0r.toortools.http.tests;


import com.object0r.toortools.http.HttpHelper;
import com.object0r.toortools.http.HttpRequestInformation;
import com.object0r.toortools.http.HttpResult;
import org.junit.Assert;
import org.junit.Test;
import org.w3c.dom.html.HTMLParagraphElement;

import java.util.concurrent.ExecutionException;

public class OmdbTest
{
    @Test
    public void testGet()
    {
        try
        {
            HttpRequestInformation httpRequestInformation = new HttpRequestInformation();

            httpRequestInformation.setMethodGet();
            httpRequestInformation.setUrl("http://www.omdbapi.com/");
            httpRequestInformation.setBody("?t=matrix&y=&plot=short&r=json");
            httpRequestInformation.setHeader("Content-Type", "application/json");
            HttpResult httpResult = HttpHelper.request(httpRequestInformation);


            Assert.assertEquals(true, httpResult.isSuccessfull());
            Assert.assertNotEquals(false, httpResult.getContent() != null);

            boolean contains = httpResult.getContentAsString().contains("Phillip Jarrett, Carrie-Anne Moss,");
            Assert.assertNotEquals(true, contains);

            contains = httpResult.getContentAsString().contains("\"Title\":\"Matrix\",\"Year\":\"1993");
            Assert.assertNotEquals(true, contains);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.assertEquals(true, false);
        }
    }

    @Test
    public void testPostJson()
    {
        try
        {

            HttpRequestInformation httpRequestInformation = new HttpRequestInformation();
            httpRequestInformation.setMethodPost();
            httpRequestInformation.setUrl("https://httpbin.org/post");
            httpRequestInformation.setHeader("Content-Type", "application/json");
            httpRequestInformation.setBody("{\n" +
                    "  \"name\": \"ToorTools\",\n" +
                    "  \"type\": \"test\"\n" +
                    "}");
            HttpResult httpResult = HttpHelper.request(httpRequestInformation);
            Assert.assertEquals(true, httpResult.isSuccessfull());

            boolean contains = httpResult.getContentAsString().contains("\"data\": \"{\\n  \\\"name\\\": \\\"ToorTools\\\",\\n  \\\"type\\\": \\\"test\\\"\\n}");
            Assert.assertEquals(true, contains);

            contains = httpResult.getContentAsString().contains("\"Content-Type\": \"application/json\",");
            Assert.assertEquals(true, contains);
            contains = httpResult.getContentAsString().contains("\"json\": {\n" +
                    "    \"name\": \"ToorTools\", \n" +
                    "    \"type\": \"test\"\n" +
                    "  },");
            Assert.assertEquals(true, contains);


        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.assertEquals(true, false);
        }
    }

    @Test
    public void testPostHttp()
    {
        try
        {

            HttpRequestInformation httpRequestInformation = new HttpRequestInformation();
            httpRequestInformation.setMethodPost();
            httpRequestInformation.setUrl("https://httpbin.org/post");
            //httpRequestInformation.setHeader("Content-Type", "application/json");
            httpRequestInformation.setBody("{\n" +
                    "  \"name\": \"ToorTools\",\n" +
                    "  \"type\": \"test\"\n" +
                    "}");
            HttpResult httpResult = HttpHelper.request(httpRequestInformation);
            Assert.assertEquals(true, httpResult.isSuccessfull());

            boolean contains = httpResult.getContentAsString().contains("\"form\": {\n" +
                    "    \"{\\n  \\\"name\\\": \\\"ToorTools\\\",\\n  \\\"type\\\": \\\"test\\\"\\n}\": \"\"\n" +
                    "  },");
            Assert.assertEquals(true, contains);

            contains = httpResult.getContentAsString().contains("\"Content-Type\": \"application/x-www-form-urlencoded\",");

            Assert.assertEquals(true, contains);

            contains = httpResult.getContentAsString().contains("\"json\": null, ");

            Assert.assertEquals(true, contains);
            //
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.assertEquals(true, false);
        }
    }

    @Test
    public void testUserAgent()
    {
        try
        {

            HttpRequestInformation httpRequestInformation = new HttpRequestInformation();
            httpRequestInformation.setMethodPost();
            httpRequestInformation.setUserAgent("MyUserAgent");
            httpRequestInformation.setUrl("https://httpbin.org/post");
            //httpRequestInformation.setHeader("Content-Type", "application/json");
            httpRequestInformation.setBody("{\n" +
                    "  \"name\": \"ToorTools\",\n" +
                    "  \"type\": \"test\"\n" +
                    "}");
            HttpResult httpResult = HttpHelper.request(httpRequestInformation);
            Assert.assertEquals(true, httpResult.isSuccessfull());
            boolean contains = httpResult.getContentAsString().contains(" \"User-Agent\": \"MyUserAgent\"");
            Assert.assertEquals(true, contains);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.assertEquals(true, false);
        }
    }

    @Test
    public void testSetHeader()
    {
        try
        {

            HttpRequestInformation httpRequestInformation = new HttpRequestInformation();
            httpRequestInformation.setMethodPost();
            httpRequestInformation.setHeader("Aloxa", "aloxavalue");

            httpRequestInformation.setUrl("https://httpbin.org/post");
            //httpRequestInformation.setHeader("Content-Type", "application/json");
            httpRequestInformation.setBody("{\n" +
                    "  \"name\": \"ToorTools\",\n" +
                    "  \"type\": \"test\"\n" +
                    "}");
            HttpResult httpResult = HttpHelper.request(httpRequestInformation);
            Assert.assertEquals(true, httpResult.isSuccessfull());
            //System.out.println(httpResult.getContentAsString());
            boolean contains = httpResult.getContentAsString().contains("\"headers\": {\n" +
                    "    \"Accept\": \"text/html, image/gif, image/jpeg, *; q=.2, */*; q=.2\", \n" +
                    "    \"Aloxa\": \"aloxavalue\", ");
            Assert.assertEquals(true, contains);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.assertEquals(true, false);
        }
    }

    @Test
    public void test404()
    {
        try
        {

            HttpRequestInformation httpRequestInformation = new HttpRequestInformation();
            httpRequestInformation.setMethodPost();
            httpRequestInformation.setThrowExceptions(false);

            httpRequestInformation.setUrl("https://example.com/randomha");

            HttpResult httpResult = HttpHelper.request(httpRequestInformation);
            Assert.assertEquals(httpResult.getResponseCode(),404);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.assertEquals(true, false);
        }
    }
    @Test
    public void test500()
    {
        try
        {

            HttpRequestInformation httpRequestInformation = new HttpRequestInformation();
            httpRequestInformation.setMethodGet();
            httpRequestInformation.setUserAgent("Mozilla/5.0 (X11; Ubuntu; Linux x86_64; rv:87.0) Gecko/20100101 Firefox/87.0");
            httpRequestInformation.setThrowExceptions(false);

            httpRequestInformation.setUrl("http://getstatuscode.com/500");

            HttpResult httpResult = HttpHelper.request(httpRequestInformation);
            Assert.assertEquals(httpResult.getResponseCode(),500);
            Assert.assertTrue(httpResult.getContentAsString().contains("A generic error message, given when an unexpected condition was encountered and no more specific message is suitable."));
        }
        catch (Exception e)
        {
            e.printStackTrace();
            Assert.assertEquals(true, false);
        }
    }
}
