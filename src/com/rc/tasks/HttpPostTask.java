package com.rc.tasks;

import com.rc.utils.HttpUtil;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Created by song on 08/06/2017.
 */
public class HttpPostTask extends HttpTask
{

    public HttpPostTask()
    {
    }

    public HttpPostTask(HttpResponseListener listener)
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
                try(Response response = HttpUtil.post(url, headers, requestParams))
                {
                    handleResponse(response);
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
