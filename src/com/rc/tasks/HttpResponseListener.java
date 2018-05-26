package com.rc.tasks;


import okhttp3.Headers;

/**
 * Created by song on 08/06/2017.
 */
public interface HttpResponseListener<T extends Object>
{
    void onSuccess(T body, Headers headers);

    void onFailed();
}
