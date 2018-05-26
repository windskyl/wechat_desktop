package com.rc.db.model;

/**
 * Created by song on 08/06/2017.
 */
public class CurrentUser extends BasicModel
{
    private String username;
    private String skey;
    private String sid;
    private String uin;
    private String passTicket;
    private int sex;
    private String nickName;
    private String signature;
    private String remarkName;
    private String headImgUrl;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getSkey()
    {
        return skey;
    }

    public void setSkey(String skey)
    {
        this.skey = skey;
    }

    public String getSid()
    {
        return sid;
    }

    public void setSid(String sid)
    {
        this.sid = sid;
    }

    public String getUin()
    {
        return uin;
    }

    public void setUin(String uin)
    {
        this.uin = uin;
    }

    public String getPassTicket()
    {
        return passTicket;
    }

    public void setPassTicket(String passTicket)
    {
        this.passTicket = passTicket;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getSignature()
    {
        return signature;
    }

    public void setSignature(String signature)
    {
        this.signature = signature;
    }

    public String getRemarkName()
    {
        return remarkName;
    }

    public void setRemarkName(String remarkName)
    {
        this.remarkName = remarkName;
    }

    public String getHeadImgUrl()
    {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl)
    {
        this.headImgUrl = headImgUrl;
    }
}
