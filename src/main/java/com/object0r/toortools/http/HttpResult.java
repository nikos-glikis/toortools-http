package com.object0r.toortools.http;

import java.util.ArrayList;
import java.util.Vector;

public class HttpResult
{
    byte content[];
    String cookie;
    ArrayList<HttpHeader> headers = new ArrayList<HttpHeader>();
    private byte[] errorContent;
    Exception thrownException;


    public boolean isSuccessfull()
    {
        if (errorContent == null)
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    /**
     * Adds a header. (can be multiple Set-Cookie for example)
     */
    public void addHeader(String key, String value)
    {
        headers.add(new HttpHeader(key, value));
    }

    public HttpHeader getHeader(String key)
    {
        HttpHeader theHeader = null;
        for (HttpHeader httpHeader : headers)
        {
            if (httpHeader.getKey().equals(key))
            {
                theHeader = httpHeader;
                break;
            }
        }
        return theHeader;
    }

    public ArrayList<HttpHeader> getHeaders(String key)
    {
        ArrayList<HttpHeader> relevant = new ArrayList<HttpHeader>();

        for (HttpHeader header : headers)
        {
            if (header.getKey().equals(key))
            {
                relevant.add(header);
            }
        }
        return relevant;
    }

    /**
     * Returns A Vector containing all Cookies Values.
     *
     * @return
     */
    public Vector<String> getCookiesVector()
    {
        ArrayList<HttpHeader> httpHeaders = getHeaders("Set-Cookie");
        Vector<String> cookies = new Vector<String>();
        for (HttpHeader httpHeader : httpHeaders)
        {
            cookies.add(httpHeader.getValue());
        }
        return cookies;
    }

    /**
     * Returns cookies from HttpResult as browser is sending them.
     * //TODO clean these a bit.
     *
     * @return
     */
    public String getCookiesString()
    {
        ArrayList<HttpHeader> httpHeaders = getHeaders("Set-Cookie");
        String cookies = "";
        for (HttpHeader httpHeader : httpHeaders)
        {
            //cookies.add(httpHeader.getValue());
            String c = httpHeader.getValue();
            if (c.contains(";"))
            {
                c = c.substring(0, c.indexOf(";"));
            }

            cookies = cookies + c + "; ";
        }
        return cookies;
    }

    public ArrayList<HttpHeader> getHeaders()
    {
        return headers;
    }

    public byte[] getContent()
    {
        return content;
    }

    public String getContentAsString()
    {
        try
        {
            return new String(content, "UTF-8");
        }
        catch (Exception e)
        {
            System.out.println(e.toString());
            return new String(content);
        }
    }

    public String getContentAsString(String encoding) throws Exception
    {
        return new String(content, encoding);
    }

    public void setContent(byte[] content)
    {
        this.content = content;
    }

    public String getCookie()
    {
        return cookie;
    }

    public void setCookie(String cookie)
    {
        this.cookie = cookie;
    }

    public void setErrorContent(byte[] errorContent)
    {
        this.errorContent = errorContent;
    }

    public byte[] getErrorContent()
    {
        return errorContent;
    }

    public String getErrorContentAsString()
    {
        return new String(errorContent);
    }


    public Exception getThrownException()
    {
        return thrownException;
    }

    public void setThrownException(Exception thrownException)
    {
        this.thrownException = thrownException;
    }
}
