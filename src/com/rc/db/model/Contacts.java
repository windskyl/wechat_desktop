package com.rc.db.model;


/**
 * Created by song on 09/03/2017.
 */

public class Contacts extends BasicModel
{
    private String username;
    private int sex;
    private int attrStatus;
    private int contactFlag;
    private String memberList;
    private String headImgUrl;
    private int memberCount;
    private String city;
    private String nickName;
    private String province;
    private int snsFlag;
    private String signature;
    private String remarkName;
    private String pYQuanPin;
    private String pYInitial;
    private String remarkPYQuanPin;
    private String remarkPYInitial;
    private String type;


    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public int getSex()
    {
        return sex;
    }

    public void setSex(int sex)
    {
        this.sex = sex;
    }

    public int getAttrStatus()
    {
        return attrStatus;
    }

    public void setAttrStatus(int attrStatus)
    {
        this.attrStatus = attrStatus;
    }

    public int getContactFlag()
    {
        return contactFlag;
    }

    public void setContactFlag(int contactFlag)
    {
        this.contactFlag = contactFlag;
    }

    public String getMemberList()
    {
        return memberList;
    }

    public void setMemberList(String memberList)
    {
        this.memberList = memberList;
    }

    public String getHeadImgUrl()
    {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl)
    {
        this.headImgUrl = headImgUrl;
    }

    public int getMemberCount()
    {
        return memberCount;
    }

    public void setMemberCount(int memberCount)
    {
        this.memberCount = memberCount;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getNickName()
    {
        return nickName;
    }

    public void setNickName(String nickName)
    {
        this.nickName = nickName;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
    }

    public int getSnsFlag()
    {
        return snsFlag;
    }

    public void setSnsFlag(int snsFlag)
    {
        this.snsFlag = snsFlag;
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

    public String getpYQuanPin()
    {
        return pYQuanPin;
    }

    public void setpYQuanPin(String pYQuanPin)
    {
        this.pYQuanPin = pYQuanPin;
    }

    public String getpYInitial()
    {
        return pYInitial;
    }

    public void setpYInitial(String pYInitial)
    {
        this.pYInitial = pYInitial;
    }

    public String getRemarkPYQuanPin()
    {
        return remarkPYQuanPin;
    }

    public void setRemarkPYQuanPin(String remarkPYQuanPin)
    {
        this.remarkPYQuanPin = remarkPYQuanPin;
    }

    public String getRemarkPYInitial()
    {
        return remarkPYInitial;
    }

    public void setRemarkPYInitial(String remarkPYInitial)
    {
        this.remarkPYInitial = remarkPYInitial;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }
}
