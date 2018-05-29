package com.rc.web;

public class Urls
{
    /**获取UUID**/
    public static final String UUID = "https://login.weixin.qq.com/jslogin?appid=wx782c26e4c19acffb";

    /** 获取登录验证码 **/
    public static final String QR_CODE = "https://login.weixin.qq.com/qrcode/";

    /** 监听用户扫描登录二维 **/
    public static final String LISTEN_QR_SCAN = "https://login.wx.qq.com/cgi-bin/mmwebwx-bin/login?loginicon=true&tip=0&r=1702132715&uuid=";

    /** 初始化 **/
    public static final String WX_INIT = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxinit";

    /** 通讯录 **/
    public static final String CONTACTS = "https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxgetcontact?pass_ticket={0}&r=${1}&seq=0&skey=${2}";


    public static String fill(String src, Object... params)
    {
        String ret = "";
        for (int i = 0; i < params.length; i++)
        {
            ret = src.replaceAll("\\{" + i + "\\}", params[i].toString());
        }

        return ret;
    }
}
