package com.rc.utils;

import java.awt.*;

/**
 * 头像异步加载监听器
 */
public interface AvatarLoadedListener
{
    void onSuccess(Image image);

    void onFailed();
}
