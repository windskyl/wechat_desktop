package com.rc.tasks;

import com.rc.utils.HttpUtil;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by song on 08/06/2017.
 */
public class HttpGetTask extends HttpTask
{
    public HttpGetTask()
    {
    }

    public HttpGetTask(HttpResponseListener listener)
    {
        super(listener);
    }

    @Override
    public void execute(String url)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                try(Response ret = HttpUtil.get(url, headers, requestParams))
                {
                    handleResponse(ret);
                }
                catch (IOException e)
                {
                    if (listener != null)
                    {
                        listener.onFailed();
                    }
                }


            }
        }).start();

    }
}
