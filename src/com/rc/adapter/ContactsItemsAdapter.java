package com.rc.adapter;

import com.rc.app.Launcher;
import com.rc.components.Colors;
import com.rc.components.RCBorder;
import com.rc.entity.ContactsItem;
import com.rc.panels.RightPanel;
import com.rc.listener.AbstractMouseListener;
import com.rc.utils.AvatarLoadedListener;
import com.rc.utils.AvatarUtil;
import com.rc.utils.CharacterParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.List;

/**
 * Created by song on 17-5-30.
 */
public class ContactsItemsAdapter extends BaseAdapter<ContactsItemViewHolder>
{
    private List<ContactsItem> contactsItems;
    private List<ContactsItemViewHolder> viewHolders = new ArrayList<>();
    Map<Integer, String> positionMap = new TreeMap<>();
    private ContactsItemViewHolder selectedViewHolder;

    public ContactsItemsAdapter(List<ContactsItem> contactsItems)
    {
        this.contactsItems = contactsItems;

        if (contactsItems != null)
        {
            processData();
        }
    }

    @Override
    public int getCount()
    {
        return contactsItems.size();
    }

    @Override
    public ContactsItemViewHolder onCreateViewHolder(int viewType)
    {
        return new ContactsItemViewHolder();
    }

    @Override
    public HeaderViewHolder onCreateHeaderViewHolder(int viewType, int position)
    {
        for (int pos : positionMap.keySet())
        {
            if (pos == position)
            {
                String ch = positionMap.get(pos);

                return new ContactsHeaderViewHolder(ch.toUpperCase());
            }
        }

        return null;
    }

    @Override
    public void onBindHeaderViewHolder(HeaderViewHolder viewHolder, int position)
    {
        ContactsHeaderViewHolder holder = (ContactsHeaderViewHolder) viewHolder;
        holder.setPreferredSize(new Dimension(100, 25));
        holder.setBackground(Colors.DARKER);
        holder.setBorder(new RCBorder(RCBorder.BOTTOM));
        holder.setOpaque(true);

        holder.letterLabel = new JLabel();
        holder.letterLabel.setText(holder.getLetter());
        holder.letterLabel.setForeground(Colors.FONT_GRAY);

        holder.setLayout(new BorderLayout());
        holder.add(holder.letterLabel, BorderLayout.WEST);
    }

    @Override
    public void onBindViewHolder(ContactsItemViewHolder viewHolder, int position)
    {
        viewHolders.add(position, viewHolder);
        ContactsItem item = contactsItems.get(position);
        viewHolder.roomName.setText(item.getTitle());

        // 获取头像
        AvatarUtil.getOrLoadUserAvatarAsync(item.getUsername(), item.getHeadImageUrl(), Launcher.currentUser,
                new AvatarLoadedListener()
                {
                    @Override
                    public void onSuccess(Image image)
                    {
                        ImageIcon icon = new ImageIcon();
                        icon.setImage(image.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
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

        viewHolder.addMouseListener(new AbstractMouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                RightPanel.getContext().getUserInfoPanel().setUsername(item.getTitle());
                RightPanel.getContext().showPanel(RightPanel.USER_INFO);

                setBackground(viewHolder, Colors.ITEM_SELECTED);
                selectedViewHolder = viewHolder;

                for (ContactsItemViewHolder holder : viewHolders)
                {
                    if (holder != viewHolder)
                    {
                        setBackground(holder, Colors.DARK);
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
                if (selectedViewHolder != viewHolder)
                {
                    setBackground(viewHolder, Colors.DARK);
                }
            }
        });
    }

    private void setBackground(ContactsItemViewHolder holder, Color color)
    {
        holder.setBackground(color);
    }

    public void processData()
    {
        Collections.sort(contactsItems);
        //Collections.reverse(contactsItems);
        Collections.sort(contactsItems, new Comparator<ContactsItem>()
        {
            @Override
            public int compare(ContactsItem o1, ContactsItem o2)
            {

                String str = o2.getpYQuanPin().substring(0, 1).toUpperCase();
                char ch = str.charAt(0);
                if (ch < 'A' || ch > 'Z')
                {
                    return 1;
                }

                return 0;
            }
        });

        positionMap.clear();

        int index = 0;
        String lastChara = "";
        for (ContactsItem item : contactsItems)
        {
            String str = item.getpYQuanPin().substring(0, 1).toUpperCase();
            char ch = str.charAt(0);
            if (ch < 'A' || ch > 'Z')
            {
                str = "#";
            }

            if (!str.equals(lastChara))
            {
                lastChara = str;
                positionMap.put(index, str);
            }

            index++;
        }
    }
}