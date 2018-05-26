package com.rc.frames;

import com.rc.components.*;
import com.rc.db.service.CurrentUserService;
import com.rc.listener.AbstractMouseListener;
import com.rc.tasks.HttpBytesGetTask;
import com.rc.tasks.HttpGetTask;
import com.rc.tasks.HttpPostTask;
import com.rc.utils.*;
import okhttp3.Headers;
import org.apache.ibatis.session.SqlSession;
import com.rc.tasks.HttpResponseListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

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
    private String uuid;

    private boolean qrScaned = false;
    private boolean loginConfirmed = false;

    Logger logger = LoggerFactory.getLogger(LoginFrame.class);


    public LoginFrame()
    {
        initService();
        initComponents();
        initView();
        centerScreen();
        setListeners();

        showQrCode();
        listenQrCodeScan();

        /*HttpPostTask task = new HttpPostTask();
        task.addHeader("Cookie", "pgv_pvi=464926720; pgv_pvid=3185718195; pac_uid=0_5ac5a2e58a764; pt2gguin=o0443948402; RK=LO4s1Vl0eb; ptcz=d8aa6c0642f21f4d5babc2838bafdadc74c775f46a211bcd11d30148d8e3ada6; mm_lang=zh_CN; webwxuvid=358e049e0960146d2e7dfa3e04dcf77f4310e1388d19033b66301f75aa6f8681d5ad7b30194b9c52c97684b831406676; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; refreshTimes=4; wxuin=1023459521; last_wxuin=1023459521; wxpluginkey=1527319220; wxsid=45Gogdl865DNw16r; wxloadtime=1527321108; webwx_data_ticket=gScnNWNDpLSQlnyA8/635klR; webwx_auth_ticket=CIsBENH0i4wBGoABvzhiENK+m9xZDJAqYIxf0TTpSJDPVMBkdqgacfpwcLIPE7OBQzIKyBucwmRih76w3SqxRxYZAiUlI/gpCh8SUTWE6KDsK40HWUUp6vY9CEjUm7mgFntnGt3fSitcxcGOh2UvA7duSyOJIk2er9ehPRVDht6+Fr3/tJ3JLAc3XeY=; login_frequency=2");
        task.addRequestParam("DeviceID", "e718414749325294");
        task.addRequestParam("Sid", "45Gogdl865DNw16r");
        task.addRequestParam("Skey", "@crypt_f1ee1c3b_f269d0a23c1b1f78a2a54839e7a3e617");
        task.addRequestParam("Uin", "1023459521");
        task.setListener(new HttpResponseListener()
        {
            @Override
            public void onSuccess(Object ret)
            {
                System.out.println(ret);
            }

            @Override
            public void onFailed()
            {

            }
        });

        task.execute("https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxinit?r=1687218564&lang=zh_CN&pass_ticket=sjlSwQvyod%252BshX6VV7zCErpbSLK41ojYYGmCIvsc9xjklG5JDKuPuC6bFKuJ6hLU");
        */
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

        tipLabel = new RCTextLabel("请使用微信扫一扫以登录");
        tipLabel.setForeground(Colors.FONT_GRAY_DARKER);

        setIconImage(IconUtil.getIcon(this, "/image/ic_launcher.png").getImage());
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

    /**
     * 获取登录二维码
     *
     * @return
     */
    private void showQrCode()
    {
        HttpGetTask task = new HttpGetTask();
        task.setListener(new HttpResponseListener()
        {
            @Override
            public void onSuccess(Object ret, Headers headers)
            {
                uuid = parseUUID(ret.toString());
                logger.info("UUID = {}", uuid);

                new HttpBytesGetTask(new HttpResponseListener()
                {
                    @Override
                    public void onSuccess(Object ret, Headers headers)
                    {
                        byte[] data = (byte[]) ret;
                        ImageIcon icon = new ImageIcon(data);
                        icon.setImage(icon.getImage().getScaledInstance(QR_WIDTH, QR_WIDTH, Image.SCALE_SMOOTH));
                        imageLabel.setIcon(icon);

                        listenQrCodeScan();
                    }

                    @Override
                    public void onFailed()
                    {
                        tipLabel.setText("无法获取登录二维码");
                    }
                }).execute(Urls.QR_CODE + uuid);
            }

            @Override
            public void onFailed()
            {
                tipLabel.setText("无法获取登录二维码");
            }
        });

        task.execute(Urls.UUID);
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


    /**
     * 监听用户是否扫描二维码
     */
    private void listenQrCodeScan()
    {
        // 监听用户是否扫描二维码
        new Thread(() ->
        {
            while (!qrScaned)
            {
                new HttpGetTask(new HttpResponseListener()
                {
                    @Override
                    public void onSuccess(Object ret, Headers headers)
                    {
                        String res = ret.toString();
                        if (res.trim().startsWith("window.code=201"))
                        {
                            tipLabel.setText("请在手机上确认登录");
                            qrScaned = true;

                            String avatarData = res.substring(res.indexOf("base64,") + 7, res.length() - 2);
                            showAvatar(avatarData);

                            listenLoginConfirm();
                        }
                    }

                    @Override
                    public void onFailed()
                    {

                    }
                }).execute(Urls.LISTEN_QR_SCAN + uuid + "&_=" + System.currentTimeMillis());

                try
                {
                    Thread.sleep(5000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    private void showAvatar(String avatarData)
    {
        BASE64Decoder decoder = new BASE64Decoder();
        try
        {
            byte[] data = decoder.decodeBuffer(avatarData);
            ImageIcon icon = new ImageIcon(data);
            icon.setImage(icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
            imageLabel.setIcon(icon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * 监听是否确认登录
     */
    private void listenLoginConfirm()
    {
        // 监听是否确认登录
        new Thread(() ->
        {
            while (!loginConfirmed)
            {
                new HttpGetTask(new HttpResponseListener()
                {
                    @Override
                    public void onSuccess(Object ret, Headers headers)
                    {
                        String res = ret.toString();
                        /*
                        window.code=200;
                        window.redirect_uri="https://wx.qq.com/cgi-bin/mmwebwx-bin/webwxnewloginpage?ticket=AeVvpDZKEOmOtJHZ8AHyYySO@qrticket_0&uuid=gYB5OTGKPQ==&lang=zh_CN&scan=1527307430";
                        */

                        if (res.trim().startsWith("window.code=200"))
                        {
                            tipLabel.setText("登录成功");
                            loginConfirmed = true;

                            String redirectUri = res.substring(res.indexOf("redirect_uri=") + 14, res.length() - 2);
                            redirectUri += "&fun=new";

                            doLogin(redirectUri);
                        }
                    }

                    @Override
                    public void onFailed()
                    {

                    }
                }).execute(Urls.LISTEN_QR_SCAN + uuid + "&_=" + System.currentTimeMillis());

                try
                {
                    Thread.sleep(5000);
                } catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * @param redirectUri
     */
    private void doLogin(String redirectUri)
    {
        new HttpGetTask(new HttpResponseListener()
        {
            @Override
            public void onSuccess(Object ret, Headers headers)
            {
                String res = ret.toString();

                String retCode = res.substring(res.indexOf("<ret>") + 5, res.indexOf("</ret>"));

                String skey = res.substring(res.indexOf("<skey>") + 6, res.indexOf("</skey>"));

                String wxsid = res.substring(res.indexOf("<wxsid>") + 7, res.indexOf("</wxsid>"));

                String wxuin = res.substring(res.indexOf("<wxuin>") + 7, res.indexOf("</wxuin>"));

                String pass_ticket = res.substring(res.indexOf("<pass_ticket>") + 13, res.indexOf("</pass_ticket>"));


                System.out.println(res);

                System.out.println("skey = " + skey);
                System.out.println("wxsid = " + wxsid);
                System.out.println("wxuin = " + wxuin);
                System.out.println("pass_ticket = " + pass_ticket);
                wxinit(skey, wxsid, wxuin, pass_ticket);

            }

            @Override
            public void onFailed()
            {

            }
        }).execute(redirectUri);
    }

    private void wxinit(String skey, String wxsid, String wxuin, String pass_ticket)
    {
        HttpPostTask task = new HttpPostTask(new HttpResponseListener()
        {

            @Override
            public void onSuccess(Object body, Headers headers)
            {
                System.out.println(body);
            }

            @Override
            public void onFailed()
            {

            }
        });


        task.addRequestParam("DeviceID", "e718414749325294");
        task.addRequestParam("Sid", wxsid);
        task.addRequestParam("Skey", skey);
        task.addRequestParam("Uin", wxuin);
        task.addHeader("Cookie", "pgv_pvi=464926720; pgv_pvid=3185718195; pac_uid=0_5ac5a2e58a764; pt2gguin=o0443948402; RK=LO4s1Vl0eb; ptcz=d8aa6c0642f21f4d5babc2838bafdadc74c775f46a211bcd11d30148d8e3ada6; mm_lang=zh_CN; webwxuvid=358e049e0960146d2e7dfa3e04dcf77f4310e1388d19033b66301f75aa6f8681d5ad7b30194b9c52c97684b831406676; MM_WX_NOTIFY_STATE=1; MM_WX_SOUND_STATE=1; refreshTimes=4; wxuin=1023459521; last_wxuin=1023459521; wxpluginkey=1527319220; wxsid=45Gogdl865DNw16r; wxloadtime=1527321108; webwx_data_ticket=gScnNWNDpLSQlnyA8/635klR; webwx_auth_ticket=CIsBENH0i4wBGoABvzhiENK+m9xZDJAqYIxf0TTpSJDPVMBkdqgacfpwcLIPE7OBQzIKyBucwmRih76w3SqxRxYZAiUlI/gpCh8SUTWE6KDsK40HWUUp6vY9CEjUm7mgFntnGt3fSitcxcGOh2UvA7duSyOJIk2er9ehPRVDht6+Fr3/tJ3JLAc3XeY=; login_frequency=2");

        //task.addRequestParam("pass_ticket", pass_ticket);
        //task.addRequestParam("lang", "zh_CN");

        //task.execute(Urls.WX_INIT + "?r=1686288494&lang=zh_CN&pass_ticket=" + pass_ticket);
    }
}
