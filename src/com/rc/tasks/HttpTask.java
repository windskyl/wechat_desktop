package com.rc.tasks;

import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by song on 08/06/2017.
 */
public abstract  class HttpTask
{
    public HttpTask()
    {

    }

    public HttpTask(HttpResponseListener listener)
    {
        this.listener = listener;
    }

    protected HttpResponseListener listener;

    protected Map<String, String> headers = new HashMap<>();
    protected Map<String, String> requestParams = new HashMap<>();
    protected String url;

    public HttpTask addHeader(String name, String value)
    {
        headers.put(name, value);

        return this;
    }

    public HttpTask addRequestParam(String name, String value)
    {
        requestParams.put(name, value);
        return this;
    }

    public abstract void execute(String url);

    public void setListener(HttpResponseListener listener)
    {
        this.listener = listener;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    void handleResponse(Response response)
    {
        if (response != null)
        {
            String body = null;
            try
            {
                body = response.body().string();
            } catch (IOException e)
            {
                e.printStackTrace();
            }

            if (body.trim().startsWith("{"))
            {
                JSONObject retJson = new JSONObject(body);
                if (listener != null)
                {
                    listener.onSuccess(retJson, response.headers());
                }
            }
            else
            {
                listener.onSuccess(body, response.headers());
            }
        }
        else
        {
            listener.onFailed();
        }

        /*if (ret != null && !ret.isEmpty())
        {
            if (ret.trim().startsWith("{"))
            {
                JSONObject retJson = new JSONObject(ret);
                if (listener != null)
                {
                    listener.onSuccess(retJson);
                }
            }
            else
            {
                listener.onSuccess(ret);
            }
        }*/
    }
}
