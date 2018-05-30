package com.rc.web;

import com.rc.app.Launcher;
import com.rc.db.model.Contacts;
import com.rc.db.service.ContactsService;
import com.rc.frames.MainFrame;
import com.rc.panels.ContactsPanel;
import com.rc.tasks.HttpGetTask;
import com.rc.tasks.HttpResponseListener;
import okhttp3.Headers;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.util.logging.PlatformLogger;

import javax.swing.*;

/**
 * 专门从服务器拉取数据
 */

public class DataPullHelper
{
    private ContactsService contactsService = Launcher.contactsService;
    private Logger logger = LoggerFactory.getLogger(DataPullHelper.class);

    /**
     * 获取通讯录
     */
    public void pullContacts()
    {
        contactsService.deleteAll();
        String url = Urls.fill(Urls.CONTACTS, Launcher.currentUser.getPassTicket(),
                System.currentTimeMillis(), Launcher.currentUser.getSkey());

        new HttpGetTask(new HttpResponseListener<JSONObject>()
        {
            @Override
            public void onSuccess(JSONObject body, Headers headers)
            {
                logger.info("成功获取通讯录信息");
                int ret = body.getJSONObject("BaseResponse").getInt("Ret");
                if (ret != 0)
                {
                    JOptionPane.showMessageDialog(MainFrame.getContext(), "错误", "通讯录获取失败, 请重新登录", JOptionPane.WARNING_MESSAGE);
                }

                JSONArray memberList = body.getJSONArray("MemberList");
                for (Object obj : memberList)
                {
                    JSONObject member = (JSONObject) obj;
                    Contacts contacts = new Contacts();
                    contacts.setUsername(member.getString("UserName"));
                    contacts.setSex(member.getInt("Sex"));
                    contacts.setAttrStatus(member.getInt("AttrStatus"));
                    contacts.setContactFlag(member.getInt("ContactFlag"));
                    //contacts.setMemberList(member.getString("MemberList"));
                    contacts.setHeadImgUrl(member.getString("HeadImgUrl"));
                    contacts.setMemberCount(member.getInt("MemberCount"));
                    contacts.setCity(member.getString("City"));
                    contacts.setNickName(member.getString("NickName"));
                    contacts.setProvince(member.getString("Province"));
                    contacts.setSnsFlag(member.getInt("SnsFlag"));
                    contacts.setSignature(member.getString("Signature"));
                    contacts.setRemarkName(member.getString("RemarkName"));
                    contacts.setpYQuanPin(member.getString("PYQuanPin"));
                    contacts.setpYInitial(member.getString("PYInitial"));
                    contacts.setRemarkPYQuanPin(member.getString("RemarkPYQuanPin"));
                    contacts.setRemarkPYInitial(member.getString("RemarkPYInitial"));
                    contacts.setType(parseUserType(contacts.getUsername()));

                    contactsService.insert(contacts);
                }

                if (ContactsPanel.getContext() != null)
                {
                    ContactsPanel.getContext().notifyDataSetChanged();
                }

            }

            @Override
            public void onFailed()
            {
                JOptionPane.showMessageDialog(MainFrame.getContext(), "错误", "通讯录获取失败, 请重新登录", JOptionPane.WARNING_MESSAGE);
            }
        }).addHeader("Cookie", Launcher.Cookie).execute(url);
    }

    private String parseUserType(String username)
    {
        if (username.startsWith("@@"))
        {
            return "group";
        } else if (username.startsWith("@"))
        {
            return "user";
        }
        else
        {
            return "wx";
        }

    }
}