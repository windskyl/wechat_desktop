package com.rc.entity;

/**
 * 消息列表中显示的房间条目
 * Created by song on 24/03/2017.
 */

public class RoomItem implements Comparable<RoomItem>
{

    private String username;
    private String title;
    private long lastChatAt;
    private long unreadCount;
    private String lastMessage;
    private int contactFlag;
    private String headImageUrl;


    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
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

    public String getLastMessage()
    {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage)
    {
        this.lastMessage = lastMessage;
    }

    @Override
    public int compareTo(RoomItem o)
    {
        // 注意，不能强制转int, 两个时间相差太远时有可能溢出
        // 忽略结果为0的情况，两个item必有先后，没有相同
        if (this.contactFlag != o.contactFlag)
        {
            return o.contactFlag - this.contactFlag;
        }

        long ret = o.getLastChatAt() - this.getLastChatAt();
        return ret > 0 ? 1 : -1;
    }

    public int getContactFlag()
    {
        return contactFlag;
    }

    public void setContactFlag(int contactFlag)
    {
        this.contactFlag = contactFlag;
    }

    public String getHeadImageUrl()
    {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl)
    {
        this.headImageUrl = headImageUrl;
    }
}
