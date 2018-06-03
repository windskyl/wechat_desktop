package com.rc.adapter;

import com.rc.app.Launcher;
import com.rc.components.Colors;
import com.rc.db.model.CurrentUser;
import com.rc.db.model.Room;
import com.rc.db.service.CurrentUserService;
import com.rc.db.service.RoomService;
import com.rc.entity.RoomItem;
import com.rc.panels.ChatPanel;
import com.rc.listener.AbstractMouseListener;
import com.rc.utils.AvatarLoadedListener;
import com.rc.utils.AvatarUtil;
import com.rc.utils.TimeUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by song on 17-5-30.
 */
public class RoomItemsAdapter extends BaseAdapter<RoomItemViewHolder>
{
    private List<RoomItem> roomItems;
    private List<RoomItemViewHolder> viewHolders = new ArrayList<>();
    private RoomItemViewHolder selectedViewHolder; // 当前选中的viewHolder
    private RoomService roomService = Launcher.roomService;
    private CurrentUserService currentUserService = Launcher.currentUserService;
    private List<RoomItemViewHolder> topViewHolderList = new ArrayList<>();

    public RoomItemsAdapter(List<RoomItem> roomItems)
    {
        this.roomItems = roomItems;
    }

    @Override
    public int getCount()
    {
        return roomItems.size();
    }

    @Override
    public RoomItemViewHolder onCreateViewHolder(int viewType)
    {
        return new RoomItemViewHolder();
    }

    @Override
    public void onBindViewHolder(RoomItemViewHolder viewHolder, int position)
    {
        if (!viewHolders.contains(viewHolder))
        {
            viewHolders.add(viewHolder);
        }

        RoomItem item = roomItems.get(position);
        viewHolder.setTag(item.getUsername());

        viewHolder.roomName.setText(item.getTitle());

        // 获取头像
        AvatarUtil.getOrLoadUserAvatarAsync(item.getUsername(), item.getHeadImageUrl(), Launcher.currentUser,
                new AvatarLoadedListener()
        {
            @Override
            public void onSuccess(Image image)
            {
                ImageIcon icon = new ImageIcon();
                icon.setImage(image.getScaledInstance(40, 40, Image.SCALE_SMOOTH));
                viewHolder.avatar.setIcon(icon);
            }

            @Override
            public void onFailed()
            {
                ImageIcon icon = new ImageIcon();
                icon.setImage(AvatarUtil.getDefaultAvatar().getScaledInstance(40, 40, Image.SCALE_SMOOTH));
                viewHolder.avatar.setIcon(icon);
            }
        });


        // 消息
        viewHolder.brief.setText(item.getLastMessage());
        if (item.getLastMessage() != null && item.getLastMessage().length() > 15)
        {
            viewHolder.brief.setText(item.getLastMessage().substring(0, 15) + "...");
        }
        else
        {
            viewHolder.brief.setText(item.getLastMessage());
        }

        // 时间
        if (item.getLastChatAt() > 0)
        {
            viewHolder.time.setText(TimeUtil.diff(item.getLastChatAt()));
        }

        // 未读消息数
        if (item.getUnreadCount() > 0)
        {
            viewHolder.unreadCount.setVisible(true);
            viewHolder.unreadCount.setText(item.getUnreadCount() + "");
        }
        else
        {
            viewHolder.unreadCount.setVisible(false);
        }

        // 是否置顶
        if (item.getContactFlag() == 2051)
        {
            topViewHolderList.add(viewHolder);
            setBackground(viewHolder, Colors.ITEM_STICKED);
        }

        //viewHolder.unreadCount.setVisible(true);
        //viewHolder.unreadCount.setText(item.getUnreadCount() + "1");


        viewHolder.addMouseListener(new AbstractMouseListener()
        {
            @Override
            public void mouseReleased(MouseEvent e)
            {
                if (e.getButton() == MouseEvent.BUTTON1)
                {

                    if (selectedViewHolder != viewHolder)
                    {
                        // 进入房间
                        enterRoom(item.getUsername());

                        for (RoomItemViewHolder holder : viewHolders)
                        {
                            if (holder != viewHolder)
                            {
                                setBackground(holder, Colors.DARK);
                            }
                        }

                        //setBackground(viewHolder, Colors.ITEM_SELECTED);
                        selectedViewHolder = viewHolder;
                    }
                }
            }


            @Override
            public void mouseEntered(MouseEvent e)
            {
                if (selectedViewHolder != viewHolder)
                {
                    setBackground(viewHolder, Colors.ITEM_SELECTED_DARK);
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                if (selectedViewHolder != viewHolder && !topViewHolderList.contains(viewHolder))
                {
                    setBackground(viewHolder, Colors.DARK);
                }
            }
        });
    }

    private String[] getRoomMembers(String roomId)
    {
        Room room = roomService.findById(roomId);
        String members = "";//room.getMember();
        String[] memberArr = null;

        List<String> roomMembers = new ArrayList<>();
        if (members != null)
        {
            String[] userArr = members.split(",");
            for (int i = 0; i < userArr.length; i++)
            {
                if (!roomMembers.contains(userArr[i]))
                {
                    roomMembers.add(userArr[i]);
                }
            }
        }
        String creator = "";//room.getCreatorName();
        if (creator != null)
        {
            if (!roomMembers.equals(creator))
            {
                roomMembers.add(creator);
            }
        }

        memberArr = roomMembers.toArray(new String[]{});
        return memberArr;
    }

    public RoomItemViewHolder getSelectedViewHolder()
    {
        return selectedViewHolder;
    }

    private void setBackground(RoomItemViewHolder holder, Color color)
    {
        holder.setBackground(color);
        holder.nameBrief.setBackground(color);
        holder.timeUnread.setBackground(color);
    }

    private void enterRoom(String roomId)
    {
        // 加载房间消息
        ChatPanel.getContext().enterRoom(roomId);
    }

    public void restoreActiveItem()
    {
        if (selectedViewHolder != null)
        {
            setBackground(selectedViewHolder, Colors.DARK);
            selectedViewHolder = null;
        }
    }
}
