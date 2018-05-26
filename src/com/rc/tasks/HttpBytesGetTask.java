package com.rc.tasks;

import com.rc.utils.HttpUtil;
import okhttp3.Response;

import java.io.IOException;

/**
 * Created by song on 2017/6/13.
 */
public class HttpBytesGetTask extends HttpTask
{
    public HttpBytesGetTask()
    {

    }

    public HttpBytesGetTask(HttpResponseListener listener)
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
                try(Response ret = HttpUtil.getBytes(url, headers, requestParams))
                {
                    if (listener != null)
                    {
                        listener.onSuccess(ret.body().bytes(),  ret.headers());
                    }
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
