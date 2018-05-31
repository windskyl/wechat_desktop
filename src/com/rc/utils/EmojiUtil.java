package com.rc.utils;

import javax.swing.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by song on 2017/7/1.
 */
public class EmojiUtil
{

    /**
     * 获取Emoji表情
     *
     * @param code emoji代码，形式如 {@code :dog:}
     * @return
     */
    public static ImageIcon getEmoji(Object context, String code)
    {
        String iconPath = "/emoji/" + code.subSequence(1, code.length() - 1) + ".png";
        URL url = context.getClass().getResource(iconPath);
        return url == null ? null : new ImageIcon(url);
    }

    /**
     * 判断给定的emoji代码是否可识别
     * @param code
     * @return
     */
    public static boolean isRecognizableEmoji(Object context, String code)
    {
        return getEmoji(context, code) != null;
    }


    /**
     * 提取emoji表情
     * @param src 形如:<span class="emoji emoji1f63b">
     * @return
     */
    public static List<String> extractEmojiClass(String src)
    {
        String regx = "emoji([0-9a-z]{5})";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(src);
        List<String> emojis = new ArrayList<>();

        while (matcher.find())
        {
            emojis.add(matcher.group(1));
        }

        return emojis;
    }

    public static String replaceEmoji(String src)
    {
        String regx = "<span class=\"emoji emoji([0-9a-z]{1,5})\">(</span>)*";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(src);
        List<String> emojis = new ArrayList<>();

        while (matcher.find())
        {
            emojis.add(matcher.group(1));
            src = src.replaceAll(regx, matcher.group(1));
        }

        return src;
    }


    public static void main(String[] args)
    {
        System.out.println(replaceEmoji("❣愛丽˃̶͈̀ε˂̶͈́豬̤̮<span class=\"emoji emoji2728\"></span>"));
    }
}
