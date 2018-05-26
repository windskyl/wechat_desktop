package com.rc.tasks;

import com.rc.utils.HttpUtil;
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
                try
                {
                    String ret = HttpUtil.get(url, headers, requestParams);
                    if (ret != null && !ret.isEmpty())
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
