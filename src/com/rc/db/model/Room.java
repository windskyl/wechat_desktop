package com.rc.db.model;

/**
 * Created by song on 09/06/2017.
 */

/**
 * Created by song on 10/03/2017.
 */

public class Room extends BasicModel implements Comparable<Room>
{

    private String username;
    private String type;
    private String nickname;
    private int memberCount;
    private long msgSum;
    private long lastChatAt;
    private long unreadCount;
    private long totalReadCount;
    private String lastMessage;
    private String headImgUrl;
    private String signature;
    private String remarkName;
    private String city;
    private String province;
    private int sex;
    private int attrStatus;
    private int contactFlag;
    private int snsFlag;

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public String getNickname()
    {
        return nickname;
    }

    public void setNickname(String nickname)
    {
        this.nickname = nickname;
    }

    public int getMemberCount()
    {
        return memberCount;
    }

    public void setMemberCount(int memberCount)
    {
        this.memberCount = memberCount;
    }

    public long getMsgSum()
    {
        return msgSum;
    }

    public void setMsgSum(long msgSum)
    {
        this.msgSum = msgSum;
    }

    public long getLastChatAt()
    {
        return lastChatAt;
    }

    public void setLastChatAt(long lastChatAt)
    {
        this.lastChatAt = lastChatAt;
    }

    public long getUnreadCount()
    {
        return unreadCount;
    }

    public void setUnreadCount(long unreadCount)
    {
        this.unreadCount = unreadCount;
    }

    public long getTotalReadCount()
    {
        return totalReadCount;
    }

    public void setTotalReadCount(long totalReadCount)
    {
        this.totalReadCount = totalReadCount;
    }

    public String getLastMessage()
    {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage)
    {
        this.lastMessage = lastMessage;
    }

    public String getHeadImgUrl()
    {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl)
    {
        this.headImgUrl = headImgUrl;
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

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getProvince()
    {
        return province;
    }

    public void setProvince(String province)
    {
        this.province = province;
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

    public int getSnsFlag()
    {
        return snsFlag;
    }

    public void setSnsFlag(int snsFlag)
    {
        this.snsFlag = snsFlag;
    }


    @Override
    public int compareTo(Room o)
    {
        if (this.getType().equals(o.getType()))
        {
            return (int)(this.getLastChatAt() - o.getLastChatAt());
        }
        else
        {
            return this.getType().compareTo(o.getType());
        }
    }
}


