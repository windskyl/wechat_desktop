package com.rc.entity;

import com.rc.utils.CharacterParser;

/**
 * Created by song on 17-5-30.
 */
public class ContactsItem implements Comparable<ContactsItem>
{
    private String username;
    private String title;
    private String headImageUrl;
    private String type;
    private String pYQuanPin;

    public ContactsItem()
    {
    }

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

    public String getHeadImageUrl()
    {
        return headImageUrl;
    }

    public void setHeadImageUrl(String headImageUrl)
    {
        this.headImageUrl = headImageUrl;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    @Override
    public int compareTo(ContactsItem o)
    {
        String str = o.getpYQuanPin().substring(0, 1).toUpperCase();
        char ch = str.charAt(0);
        if (ch < 'A' || ch > 'Z')
        {
            return 1;
        }
        return this.getpYQuanPin().compareTo(o.getpYQuanPin());
    }

    public String getpYQuanPin()
    {
        return pYQuanPin;
    }

    public void setpYQuanPin(String pYQuanPin)
    {
        this.pYQuanPin = pYQuanPin;
    }
}
