package com.rc.frames;

import com.rc.app.Launcher;
import com.rc.components.*;
import com.rc.db.model.CurrentUser;
import com.rc.db.model.Room;
import com.rc.db.service.CurrentUserService;
import com.rc.db.service.RoomService;
import com.rc.listener.AbstractMouseListener;
import com.rc.panels.RoomsPanel;
import com.rc.tasks.HttpBytesGetTask;
import com.rc.tasks.HttpGetTask;
import com.rc.tasks.HttpPostTask;
import com.rc.utils.*;
import com.rc.web.DataPullHelper;
import com.rc.web.Urls;
import okhttp3.Headers;
import org.apache.ibatis.session.SqlSession;
import com.rc.tasks.HttpResponseListener;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Decoder;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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

    private RoomService roomService = Launcher.roomService;



    public LoginFrame()
    {
        initService();
        initComponents();
        initView();
        centerScreen();
        setListeners();

        showQrCode();
        listenQrCodeScan();
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
            public void onSuccess(Object body, Headers headers)
            {
                String res = body.toString();
                String retCode = res.substring(res.indexOf("<ret>") + 5, res.indexOf("</ret>"));

                if (!retCode.equals("0"))
                {
                    tipLabel.setText("登录失败");
                    return;
                }

                String skey = res.substring(res.indexOf("<skey>") + 6, res.indexOf("</skey>"));
                String wxsid = res.substring(res.indexOf("<wxsid>") + 7, res.indexOf("</wxsid>"));
                String wxuin = res.substring(res.indexOf("<wxuin>") + 7, res.indexOf("</wxuin>"));
                String pass_ticket = res.substring(res.indexOf("<pass_ticket>") + 13, res.indexOf("</pass_ticket>"));

                String wxloadtime = null;
                String mm_lang = null;
                String webwx_data_ticket = null;
                String webwxuvid = null;
                String webwx_auth_ticket = null;

                Map<String, List<String>> map = headers.toMultimap();
                for (Map.Entry<String, List<String>> entry : map.entrySet())
                {
                    String name = entry.getKey();
                    List<String> list = entry.getValue();
                    if (name.equals("set-cookie"))
                    {
                        System.out.println(name + ": " + list);
                        for (String head : list)
                        {
                            // head: wxuin=1023459521; Domain=wx.qq.com; Path=/; Expires=Sun, 27-May-2018 02:50:59 GMT
                            // val: wxuin=1023459521
                            String val = head.substring(0, head.indexOf(";"));
                            int eqPos = val.indexOf("=");
                            String key = val.substring(0, eqPos);
                            val = val.substring(eqPos + 1);
                            switch (key)
                            {
                                case "wxloadtime":
                                {
                                    wxloadtime = val;
                                    break;
                                }
                                case "mm_lang":
                                {
                                    mm_lang = val;
                                    break;
                                }
                                case "webwx_data_ticket":
                                {
                                    webwx_data_ticket = val;
                                    break;
                                }
                                case "webwxuvid":
                                {
                                    webwxuvid = val;
                                    break;
                                }
                                case "webwx_auth_ticket":
                                {
                                    webwx_auth_ticket = val;
                                    break;
                                }

                            }
                        }
                    }
                }

                wxinit(skey, wxsid, wxuin, pass_ticket, wxloadtime, mm_lang, webwx_data_ticket, webwxuvid, webwx_auth_ticket);
            }

            @Override
            public void onFailed()
            {

            }
        }).execute(redirectUri);
    }

    /**
     * 获取初始数据
     */
    private void wxinit(String skey, String wxsid, String wxuin, String pass_ticket,
                        String wxloadtime, String mm_lang, String webwx_data_ticket, String webwxuvid, String webwx_auth_ticket)
    {
        HttpPostTask task = new HttpPostTask(new HttpResponseListener<JSONObject>()
        {

            @Override
            public void onSuccess(JSONObject body, Headers headers)
            {
                saveCurrentUser(skey, wxsid, wxuin, pass_ticket, wxloadtime, mm_lang, webwx_data_ticket, webwxuvid,
                        webwx_auth_ticket, body);
                dispose();

                MainFrame frame = new MainFrame();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                //frame.setRoomListData(body.getJSONObject("ContactList"));

                // 解析初始的房间列表
                initRoomsData(body.getJSONArray("ContactList"));

                // 拉取其他数据
                DataPullHelper dataPullHelper = new DataPullHelper();
                dataPullHelper.pullContacts();
            }

            @Override
            public void onFailed()
            {

            }
        });

        task.setJson("{\"BaseRequest\":{\"Uin\":\"" + wxuin + "\",\"Sid\":\"" + wxsid + "\",\"Skey\":\"" + skey + "\",\"DeviceID\":\"e784910383813007\"}}");
        task.execute(Urls.WX_INIT + "?r=1679825476&pass_ticket=" + pass_ticket);
    }


    /**
     * 保存当前登录用户信息
     */
    private void saveCurrentUser(String skey, String wxsid, String wxuin, String pass_ticket,
                                 String wxloadtime, String mm_lang, String webwx_data_ticket, String webwxuvid,
                                 String webwx_auth_ticket, JSONObject initData)
    {
        CurrentUser user = new CurrentUser();
        user.setSkey(skey);
        user.setSid(wxsid);
        user.setUin(wxuin);
        user.setPassTicket(pass_ticket);

        JSONObject userInfo = initData.getJSONObject("User");
        user.setSex(userInfo.getInt("Sex"));
        user.setUsername(userInfo.getString("UserName"));
        user.setHeadImgUrl(userInfo.getString("HeadImgUrl"));
        user.setNickName(userInfo.getString("NickName"));
        user.setSignature(userInfo.getString("Signature"));
        user.setRemarkName(userInfo.getString("RemarkName"));

        user.setWxLoadTime(wxloadtime);
        user.setMmLang(mm_lang);
        user.setWebwxDataTicket(webwx_data_ticket);
        user.setWebwxuvid(webwxuvid);
        user.setWebwxAuthTicket(webwx_auth_ticket);

        currentUserService.deleteAll();
        currentUserService.insert(user);

        Launcher.currentUser = user;

        String cookie = "mm_lang=" + user.getMmLang() + "; " +
                "refreshTimes=3; " +
                "wxuin=" + user.getUin() + "; " +
                "wxloadtime=" + user.getWxLoadTime() + "; " +
                "webwxuvid=" + user.getWebwxuvid() + "; " +
                "webwx_auth_ticket=" + user.getWebwxAuthTicket() + "; " +
                "MM_WX_NOTIFY_STATE=1; " +
                "MM_WX_SOUND_STATE=1; " +
                "login_frequency=4; " +
                "last_wxuin=" + user.getUin() + "; " +
                "wxsid=" + user.getSid() + "; " +
                "webwx_data_ticket=" + user.getWebwxDataTicket();

        Launcher.Cookie = cookie;
    }

    /**
     * 解析登录后从服务器获取的初始房间列表
     *
     * @param contactList
     */
    private void initRoomsData(JSONArray contactList)
    {
        new Thread(() ->
        {
            roomService.deleteAll();
            for (Object item : contactList)
            {
                Room room = new Room();
                JSONObject obj = (JSONObject) item;
                room.setSex(obj.getInt("Sex"));
                room.setContactFlag(obj.getInt("ContactFlag"));
                room.setUsername(obj.getString("UserName"));
                room.setHeadImgUrl(obj.getString("HeadImgUrl"));
                room.setMemberCount(obj.getInt("MemberCount"));
                room.setCity(obj.getString("City"));
                room.setNickname(EmojiUtil.replaseEmoji(obj.getString("NickName")));
                room.setProvince(obj.getString("Province"));
                room.setSnsFlag(obj.getInt("SnsFlag"));
                room.setSignature(EmojiUtil.replaseEmoji(obj.getString("Signature")));
                room.setRemarkName(EmojiUtil.replaseEmoji(obj.getString("RemarkName")));
                roomService.insertOrUpdate(room);
            }

            if (RoomsPanel.getContext() != null)
            {
                RoomsPanel.getContext().notifyDataSetChanged(false);
            }
        }).start();
    }
}
