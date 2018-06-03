package com.rc.components;

import com.rc.adapter.ContactsItemViewHolder;
import com.rc.utils.EmojiUtil;
import com.rc.utils.FontUtil;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RCTextPane extends JTextPane
{
    private Pattern emojiPattern;
    private String emojiRegx;

    public RCTextPane(Component container)
    {
        emojiRegx = "<span class=\"emoji emoji([0-9a-z]{1,5})\">(</span>)*";
        emojiPattern = Pattern.compile(emojiRegx);// 懒惰匹配，最小匹配

        setOpaque(false);
        this.setFont(FontUtil.getDefaultFont(14));
        setEditable(false);

        processMouseEvent(container);
    }

    private void processMouseEvent(Component container)
    {
        addMouseListener(new MouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                for (MouseListener listener : container.getMouseListeners())
                {
                    listener.mouseClicked(e);
                }
            }

            @Override
            public void mousePressed(MouseEvent e)
            {
                for (MouseListener listener : container.getMouseListeners())
                {
                    listener.mousePressed(e);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e)
            {
                for (MouseListener listener : container.getMouseListeners())
                {
                    listener.mouseReleased(e);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                for (MouseListener listener : container.getMouseListeners())
                {
                    listener.mouseEntered(e);
                }
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                for (MouseListener listener : container.getMouseListeners())
                {
                    listener.mouseExited(e);
                }
            }
        });
    }

    @Override
    public void setText(String t)
    {
        insertEmoji(t);
    }

    private void insertEmoji(String src)
    {
        src = replaceEmoji(src);
        String targetText = src.replaceAll(":.+?:", "");
        super.setText(targetText);

        Document doc = getDocument();

        StringBuilder stringBuilder = new StringBuilder();

        char[] charArr = src.toCharArray();
        char ch;
        boolean emojiStart = false;
        int pos = -1;
        for (int i = 0; i < charArr.length; i++)
        {
            ch = charArr[i];
            if (ch == ':')
            {
                if (!emojiStart)
                {
                    emojiStart = true;
                    stringBuilder.append(ch);
                    pos = i;
                } else
                {
                    emojiStart = false;
                    stringBuilder.append(ch);

                    setCaretPosition(pos);


                    BufferedImage img = EmojiUtil.getEmoji(stringBuilder.toString());
                    if (img != null)
                    {
                        ImageIcon icon = new ImageIcon(img);
                        insertIcon(icon);

                        charArr = resetCharArr(new String(charArr), stringBuilder.toString());
                        i = pos;
                        stringBuilder.setLength(0);
                    } else
                    {
                        // 表情不存在，原样输出
                        try
                        {
                            doc.insertString(pos, stringBuilder.toString(), getCharacterAttributes());
                        } catch (BadLocationException e)
                        {
                            e.printStackTrace();
                        }
                        stringBuilder.setLength(0);

                    }
                }
            } else
            {
                if (emojiStart)
                {
                    stringBuilder.append(ch);
                }
            }

        }
    }

    private char[] resetCharArr(String src, String s)
    {
        String str = src.replaceFirst(s, "#");
        return str.toCharArray();
    }

    public String replaceEmoji(String src)
    {
        Matcher matcher = emojiPattern.matcher(src);
        List<String> emojis = new ArrayList<>();

        while (matcher.find())
        {
            emojis.add(matcher.group(1));
            src = src.replaceAll(emojiRegx, ":" + matcher.group(1) + ":");
        }

        return src;
    }
}
