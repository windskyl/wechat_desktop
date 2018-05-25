package com.rc.components;

import com.rc.utils.FontUtil;

import javax.swing.*;
import java.awt.*;

/**
 * 统一样式的文本Label
 */
public class RCTextLabel extends JLabel
{
    public RCTextLabel()
    {
        this.setFont(FontUtil.getDefaultFont(14));
        this.setForeground(Colors.FONT_BLACK);
    }

    public RCTextLabel(String text)
    {
        this();
        this.setText(text);
        this.setHorizontalAlignment(CENTER);
    }

    public RCTextLabel(String text, Color frontColor)
    {
        super(text, CENTER);
        this.setFont(FontUtil.getDefaultFont(14));
        this.setForeground(frontColor);
    }


    public RCTextLabel(String text, int horizontalAlignment)
    {
        super(text, horizontalAlignment);
        this.setFont(FontUtil.getDefaultFont(14));
        this.setForeground(Colors.FONT_BLACK);
    }
}
