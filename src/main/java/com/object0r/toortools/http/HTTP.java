package com.object0r.toortools.http;

/**
 * @author Nikos Glikis
 * @deprecated - Use HttpHelper instead
 */
public class HTTP
{
    /**
     * Makes the actual http request.
     *
     * @param httpRequestInformation - The object containing the http request parameters.
     * @return The http result.
     * @throws Exception
     * @deprecated - Use HttpHelper.request() instead
     */
    public static HttpResult request(HttpRequestInformation httpRequestInformation) throws Exception
    {
        return HttpHelper.request(httpRequestInformation);
    }
}
