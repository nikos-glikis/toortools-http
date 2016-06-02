package com.object0r.toortools.http;

/**
 * @author Nikos Glikis
 * @deprecated - Use HttpHelper instead
 */
public class HTTP
{
    /**
     * Makes the actual http request.
     * @deprecated - Use HttpHelper.request() instead
     * @param httpRequestInformation - The object containing the http request parameters.
     * @return The http result.
     * @throws Exception
     */
    public static HttpResult request(HttpRequestInformation httpRequestInformation) throws Exception
    {
        return HttpHelper.request(httpRequestInformation);
    }
}
