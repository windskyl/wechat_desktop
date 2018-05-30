package com.rc.panels;

import com.rc.adapter.ContactsItemsAdapter;
import com.rc.app.Launcher;
import com.rc.components.Colors;
import com.rc.components.GBC;
import com.rc.components.RCListView;
import com.rc.db.model.Contacts;
import com.rc.db.service.ContactsService;
import com.rc.db.service.CurrentUserService;
import com.rc.entity.ContactsItem;
import com.rc.utils.AvatarUtil;
import okhttp3.Headers;
import org.apache.log4j.Logger;
import com.rc.tasks.HttpBytesGetTask;
import com.rc.tasks.HttpResponseListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by song on 17-5-30.
 *
 * <P>下图 #ContactsPanel# 对应的位置</P>
 *
 * 显示通讯录列表
 *
 * <P>推荐使用Menlo或Consolas字体</P>
 * ┌────────────────────────┬────────────────────────────────────────────────────────┐
 * │ ┌─────┐                │  Room Title                                         ≡  │
 * │ │     │ name         ≡ ├────────────────────────────────────────────────────────┤
 * │ └─────┘                │                                                        │
 * ├────────────────────────┤                     message time                       │
 * │    search              │  ┌──┐ ┌────────────┐                                   │
 * ├────────────────────────┤  └──┘ │  message   │                                   │
 * │  ▆    │    ▆   │   ▆   │       └────────────┘                                   │
 * ├────────────────────────┤                                                        │
 * │                        │                                                        │
 * │                        │                     message time                       │
 * │                        │                                    ┌────────────┐ ┌──┐ │
 * │                        │                                    │  message   │ └──┘ │
 * │                        │                                    └────────────┘      │
 * │     #ContactsPanel#    │                                                        │
 * │                        │                                                        │
 * │                        ├────────────────────────────────────────────────────────┤
 * │                        │  ▆   ▆   ▆                                             │
 * │                        │                                                        │
 * │                        │                                                        │
 * │                        │                                                ┌─────┐ │
 * │                        │                                                └─────┘ │
 * └────────────────────────┴────────────────────────────────────────────────────────┘
 */
public class ContactsPanel extends ParentAvailablePanel
{
    private static ContactsPanel context;

    private RCListView contactsListView;
    private List<ContactsItem> contactsItemList = new ArrayList<>();
    private ContactsService contactsService = Launcher.contactsService;
    private Logger logger = Logger.getLogger(this.getClass());
    private CurrentUserService currentUserService = Launcher.currentUserService;
    private String currentUsername;

    public ContactsPanel(JPanel parent)
    {
        super(parent);
        context = this;

        initComponents();
        initView();
        initData();
        contactsListView.setAdapter(new ContactsItemsAdapter(contactsItemList));
    }


    private void initComponents()
    {
        contactsListView = new RCListView();
    }

    private void initView()
    {
        setLayout(new GridBagLayout());
        contactsListView.setContentPanelBackground(Colors.DARK);
        add(contactsListView, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 1));
    }

    private void initData()
    {
        contactsItemList.clear();

        List<Contacts> contactsList = contactsService.findAll();
        for (Contacts contacts : contactsList)
        {
            String title = contacts.getNickName();
            if (contacts.getRemarkName() != null && contacts.getRemarkName().length() > 1)
            {
                title = contacts.getRemarkName();
            }
            ContactsItem item = new ContactsItem();
            item.setTitle(title);
            item.setpYQuanPin(contacts.getpYQuanPin());
            item.setHeadImageUrl(contacts.getHeadImgUrl());
            item.setType(contacts.getType());
            item.setUsername(contacts.getUsername());

            contactsItemList.add(item);
        }

        //Collections.sort(contactsItemList);
    }

    public void notifyDataSetChanged()
    {
        initData();
        ((ContactsItemsAdapter) contactsListView.getAdapter()).processData();
        contactsListView.notifyDataSetChanged(false);

        // 通讯录更新后，获取头像
        //getContactsUserAvatar();
    }

    public static ContactsPanel getContext()
    {
        return context;
    }

    /**
     * 获取通讯录中用户的头像
     */
    private void getContactsUserAvatar()
    {
        new Thread(new Runnable()
        {
            @Override
            public void run()
            {
                for (ContactsItem user : contactsItemList)
                {
                    if (!AvatarUtil.customAvatarExist(user.getTitle()))
                    {
                        final String username = user.getTitle();
                        //logger.debug("获取头像:" + username);
                        getUserAvatar(username, true);
                    }
                }

                // 自己的头像每次启动都去获取
                currentUsername = currentUserService.findAll().get(0).getUsername();
                //if (!AvatarUtil.customAvatarExist(currentUsername))
                {
                    getUserAvatar(currentUsername, true);
                }
            }
        }).start();

    }

    /**
     * 更新指定用户头像
     * @param username 用户名
     * @param hotRefresh 是否热更新，hotRefresh = true， 将刷新该用户的头像缓存
     */
    public void getUserAvatar(String username, boolean hotRefresh)
    {
        HttpBytesGetTask task = new HttpBytesGetTask();
        task.setListener(new HttpResponseListener<byte[]>()
        {
            @Override
            public void onSuccess(byte[] data, Headers headers)
            {
                processAvatarData(data, username);
                if (hotRefresh)
                {
                    //MyInfoPanel.getContext().reloadAvatar();
                    AvatarUtil.refreshUserAvatarCache(username);

                    if (username.equals(currentUsername))
                    {
                        MyInfoPanel.getContext().reloadAvatar();
                    }
                }

            }

            @Override
            public void onFailed()
            {
                System.out.println("头像获取失败：" + username);
            }
        });
        task.execute(Launcher.HOSTNAME + "/avatar/" + username);
    }

    /**
     * 处理头像数据
     * @param data
     * @param username
     */
    private void processAvatarData(byte[] data, String username)
    {
        if (data != null && data.length > 1024)
        {
            AvatarUtil.saveAvatar(data, username);
        }
        else
        {
            AvatarUtil.deleteCustomAvatar(username);
        }
    }

}
