package com.rc.frames;

import com.rc.app.Launcher;
import com.rc.components.*;
import com.rc.db.model.CurrentUser;
import com.rc.db.service.CurrentUserService;
import com.rc.listener.AbstractMouseListener;
import com.rc.utils.*;
import org.apache.ibatis.session.SqlSession;
import org.json.JSONObject;
import com.rc.tasks.HttpPostTask;
import com.rc.tasks.HttpResponseListener;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 * Created by song on 08/06/2017.
 */
public class LoginFrame extends JFrame
{
    private static final int windowWidth = 280;
    private static final int windowHeight = 400;

    private static final int QR_WIDTH = 210;

    private JPanel controlPanel;
    private JLabel closeLabel;
    private JLabel titleLabel;

    private JLabel imageLabel; // 显示二维码
    private JLabel tipLabel; // 显示提示信息

    private static Point origin = new Point();

    private SqlSession sqlSession;
    private CurrentUserService currentUserService;


    public LoginFrame()
    {
        initService();
        initComponents();
        initView();
        centerScreen();
        setListeners();
    }

    public LoginFrame(String username)
    {
        this();
    }

    private void initService()
    {
        sqlSession = DbUtils.getSqlSession();
        currentUserService = new CurrentUserService(sqlSession);
    }


    private void initComponents()
    {
        Dimension windowSize = new Dimension(windowWidth, windowHeight);
        setMinimumSize(windowSize);
        setMaximumSize(windowSize);


        controlPanel = new JPanel();
        //controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        controlPanel.setLayout(new BorderLayout());
        //controlPanel.setBounds(0,5, windowWidth, 30);

        closeLabel = new JLabel();
        closeLabel.setIcon(IconUtil.getIcon(this, "/image/close.png"));
        closeLabel.setHorizontalAlignment(JLabel.CENTER);
        //closeLabel.setPreferredSize(new Dimension(30,30));
        closeLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));

        titleLabel = new RCTextLabel("微信", Colors.FONT_GRAY_DARKER);


        imageLabel = new JLabel();
        imageLabel.setPreferredSize(new Dimension(QR_WIDTH, QR_WIDTH));
        imageLabel.setIcon(getQrCode());

        tipLabel = new RCTextLabel("请使用微信扫一扫以登录");
        tipLabel.setForeground(Colors.FONT_GRAY_DARKER);

        setIconImage(IconUtil.getIcon(this, "/image/ic_launcher.png").getImage());
    }

    private ImageIcon getQrCode()
    {
        try
        {
            // 获取UUID
            String ret = HttpUtil.get(Urls.UUID);
            String uuid = parseUUID(ret);
            ImageIcon qrCode = new ImageIcon(new URL(Urls.QR_CODE + uuid));
            qrCode.setImage(qrCode.getImage().getScaledInstance(QR_WIDTH, QR_WIDTH, Image.SCALE_SMOOTH));
            return qrCode;

        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解析UUID
     *
     * @param ret window.QRLogin.code = 200; window.QRLogin.uuid = "oeApJlhU2A==";
     * @return
     */
    private String parseUUID(String ret)
    {
        if (ret.contains("200"))
        {
            return ret.substring(ret.indexOf("\"") + 1, ret.length() - 2);
        }
        return null;
    }

    private void initView()
    {
        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new LineBorder(Colors.LIGHT_GRAY));
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBackground(Colors.WINDOW_BACKGROUND);

        controlPanel.add(titleLabel, BorderLayout.WEST);
        controlPanel.add(closeLabel, BorderLayout.EAST);

        if (OSUtil.getOsType() != OSUtil.Mac_OS)
        {
            setUndecorated(true);
            contentPanel.add(controlPanel, new GBC(0, 0).setFill(GBC.BOTH).setWeight(1, 1).setInsets(8, 8, 5, 8));
        }


        add(contentPanel);
        contentPanel.add(imageLabel, new GBC(0, 1).setFill(GBC.BOTH).setWeight(1, 10).setInsets(25, 35, 0, 35));
        contentPanel.add(tipLabel, new GBC(0, 2).setFill(GBC.BOTH).setWeight(1, 20).setInsets(0, 35, 25, 35));


    }

    /**
     * 使窗口在屏幕中央显示
     */
    private void centerScreen()
    {
        Toolkit tk = Toolkit.getDefaultToolkit();
        this.setLocation((tk.getScreenSize().width - windowWidth) / 2,
                (tk.getScreenSize().height - windowHeight) / 2);
    }

    private void setListeners()
    {
        closeLabel.addMouseListener(new AbstractMouseListener()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                System.exit(1);
                super.mouseClicked(e);
            }

            @Override
            public void mouseEntered(MouseEvent e)
            {
                closeLabel.setBackground(Colors.LIGHT_GRAY);
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e)
            {
                closeLabel.setBackground(Colors.WINDOW_BACKGROUND);
                super.mouseExited(e);
            }
        });

        if (OSUtil.getOsType() != OSUtil.Mac_OS)
        {
            addMouseListener(new MouseAdapter()
            {
                public void mousePressed(MouseEvent e)
                {
                    // 当鼠标按下的时候获得窗口当前的位置
                    origin.x = e.getX();
                    origin.y = e.getY();
                }
            });

            addMouseMotionListener(new MouseMotionAdapter()
            {
                public void mouseDragged(MouseEvent e)
                {
                    // 当鼠标拖动时获取窗口当前位置
                    Point p = LoginFrame.this.getLocation();
                    // 设置窗口的位置
                    LoginFrame.this.setLocation(p.x + e.getX() - origin.x, p.y + e.getY()
                            - origin.y);
                }
            });
        }
    }
}
