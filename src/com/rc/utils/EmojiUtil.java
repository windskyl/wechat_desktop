package com.rc.utils;

import com.rc.app.Launcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by song on 2017/7/1.
 */
public class EmojiUtil
{

    private static final Map<String, Integer> EMOJI_POS;
    private static BufferedImage EMOJIS;
    private static Logger logger = LoggerFactory.getLogger(EmojiUtil.class);

    static
    {
        try
        {
            EMOJIS = ImageIO.read(Launcher.getContext().getClass().getResourceAsStream("/image/emojis.png"));
        } catch (IOException e)
        {
            e.printStackTrace();
        }

        EMOJI_POS = new HashMap<>();
        EMOJI_POS.put("1f412", 2520);
        EMOJI_POS.put("1f533", 10980);
        EMOJI_POS.put("1f414", 2640);
        EMOJI_POS.put("1f535", 10900);
        EMOJI_POS.put("1f532", 10900);
        EMOJI_POS.put("1f417", 2660);
        EMOJI_POS.put("1f419", 2340);
        EMOJI_POS.put("1f418", 2480);
        EMOJI_POS.put("1f539", 10980);
        EMOJI_POS.put("1f536", 10980);
        EMOJI_POS.put("1f537", 10980);
        EMOJI_POS.put("1f538", 10980);
        EMOJI_POS.put("1f64b", 11240);
        EMOJI_POS.put("1f64d", 3860);
        EMOJI_POS.put("1f64c", 3840);
        EMOJI_POS.put("1f52b", 5020);
        EMOJI_POS.put("2668", 7280);
        EMOJI_POS.put("1f40d", 2620);
        EMOJI_POS.put("1f64f", 3900);
        EMOJI_POS.put("1f40c", 9200);
        EMOJI_POS.put("1f64e", 3880);
        EMOJI_POS.put("2666", 9560);
        EMOJI_POS.put("2665", 9520);
        EMOJI_POS.put("1f40e", 6680);
        EMOJI_POS.put("1f52f", 5080);
        EMOJI_POS.put("1f52e", 5080);
        EMOJI_POS.put("2663", 9580);
        EMOJI_POS.put("1f420", 2420);
        EMOJI_POS.put("1f301", 9200);
        EMOJI_POS.put("1f422", 9200);
        EMOJI_POS.put("1f300", 100);
        EMOJI_POS.put("1f421", 7360);
        EMOJI_POS.put("1f646", 3780);
        EMOJI_POS.put("1f525", 5040);
        EMOJI_POS.put("1f645", 3760);
        EMOJI_POS.put("1f527", 9200);
        EMOJI_POS.put("1f526", 9200);
        EMOJI_POS.put("ae", 9920);
        EMOJI_POS.put("1f647", 3800);
        EMOJI_POS.put("1f529", 9200);
        EMOJI_POS.put("1f52a", 9200);
        EMOJI_POS.put("1f649", 9200);
        EMOJI_POS.put("1f648", 9200);
        EMOJI_POS.put("1f64a", 9200);
        EMOJI_POS.put("1f528", 4360);
        EMOJI_POS.put("1f63a", 3580);
        EMOJI_POS.put("1f63c", 3720);
        EMOJI_POS.put("1f63b", 3660);
        EMOJI_POS.put("1f63e", 3880);
        EMOJI_POS.put("1f51d", 10040);
        EMOJI_POS.put("1f63d", 3640);
        EMOJI_POS.put("1f51c", 9200);
        EMOJI_POS.put("1f51a", 9200);
        EMOJI_POS.put("1f51b", 9200);
        EMOJI_POS.put("1f51f", 9200);
        EMOJI_POS.put("2797", 9200);
        EMOJI_POS.put("1f4a0", 9200);
        EMOJI_POS.put("1f63f", 3680);
        EMOJI_POS.put("1f51e", 9860);
        EMOJI_POS.put("2796", 9200);
        EMOJI_POS.put("1f251", 9200);
        EMOJI_POS.put("2795", 9200);
        EMOJI_POS.put("1f530", 9660);
        EMOJI_POS.put("1f411", 2540);
        EMOJI_POS.put("1f531", 9680);
        EMOJI_POS.put("1f633", 3100);
        EMOJI_POS.put("1f512", 11140);
        EMOJI_POS.put("1f632", 2840);
        EMOJI_POS.put("1f511", 11160);
        EMOJI_POS.put("1f635", 3540);
        EMOJI_POS.put("1f514", 4980);
        EMOJI_POS.put("1f513", 11100);
        EMOJI_POS.put("1f637", 3080);
        EMOJI_POS.put("267f", 9640);
        EMOJI_POS.put("231b", 9200);
        EMOJI_POS.put("231a", 9200);
        EMOJI_POS.put("1f639", 3620);
        EMOJI_POS.put("1f638", 3720);
        EMOJI_POS.put("2693", 6920);
        EMOJI_POS.put("1f519", 11180);
        EMOJI_POS.put("1f62b", 3540);
        EMOJI_POS.put("1f50a", 7880);
        EMOJI_POS.put("1f62a", 3460);
        EMOJI_POS.put("1f62d", 3300);
        EMOJI_POS.put("1f50c", 9200);
        EMOJI_POS.put("1f50b", 9200);
        EMOJI_POS.put("1f50e", 11060);
        EMOJI_POS.put("1f50d", 11060);
        EMOJI_POS.put("267b", 9200);
        EMOJI_POS.put("1f640", 3860);
        EMOJI_POS.put("1f622", 3680);
        EMOJI_POS.put("1f621", 3880);
        EMOJI_POS.put("1f624", 3720);
        EMOJI_POS.put("1f623", 3540);
        EMOJI_POS.put("1f625", 3520);
        EMOJI_POS.put("1f628", 3320);
        EMOJI_POS.put("1f629", 3860);
        EMOJI_POS.put("2b05", 11180);
        EMOJI_POS.put("2b06", 9000);
        EMOJI_POS.put("2b07", 9020);
        EMOJI_POS.put("1f61a", 3060);
        EMOJI_POS.put("1f61c", 2980);
        EMOJI_POS.put("1f61e", 2860);
        EMOJI_POS.put("1f61d", 3000);
        EMOJI_POS.put("1f631", 3440);
        EMOJI_POS.put("1f510", 11140);
        EMOJI_POS.put("1f50f", 11140);
        EMOJI_POS.put("1f630", 2900);
        EMOJI_POS.put("1f613", 3500);
        EMOJI_POS.put("1f612", 2920);
        EMOJI_POS.put("1f614", 3860);
        EMOJI_POS.put("1f616", 10520);
        EMOJI_POS.put("1f618", 3640);
        EMOJI_POS.put("1f60b", 3220);
        EMOJI_POS.put("1f60a", 3220);
        EMOJI_POS.put("1f60d", 3660);
        EMOJI_POS.put("1f60c", 3380);
        EMOJI_POS.put("1f60f", 3480);
        EMOJI_POS.put("1f620", 2800);
        EMOJI_POS.put("1f602", 3620);
        EMOJI_POS.put("1f601", 3720);
        EMOJI_POS.put("1f604", 3260);
        EMOJI_POS.put("1f603", 3580);
        EMOJI_POS.put("1f606", 3380);
        EMOJI_POS.put("1f605", 3260);
        EMOJI_POS.put("2122", 9940);
        EMOJI_POS.put("1f609", 3560);
        EMOJI_POS.put("2b1b", 10900);
        EMOJI_POS.put("2b1c", 10980);
        EMOJI_POS.put("27a1", 9040);
        EMOJI_POS.put("2139", 9200);
        EMOJI_POS.put("1f379", 8860);
        EMOJI_POS.put("1f48b", 7900);
        EMOJI_POS.put("2b55", 9800);
        EMOJI_POS.put("1f48a", 5320);
        EMOJI_POS.put("1f48d", 7940);
        EMOJI_POS.put("1f48c", 11200);
        EMOJI_POS.put("1f48f", 7980);
        EMOJI_POS.put("1f48e", 7960);
        EMOJI_POS.put("2b50", 10720);
        EMOJI_POS.put("1f381", 6080);
        EMOJI_POS.put("1f380", 5420);
        EMOJI_POS.put("1f383", 5740);
        EMOJI_POS.put("1f382", 5460);
        EMOJI_POS.put("1f385", 5500);
        EMOJI_POS.put("1f384", 5480);
        EMOJI_POS.put("1f387", 5700);
        EMOJI_POS.put("1f386", 5540);
        EMOJI_POS.put("1f389", 5580);
        EMOJI_POS.put("1f388", 5560);
        EMOJI_POS.put("1f369", 9200);
        EMOJI_POS.put("1f36a", 9200);
        EMOJI_POS.put("1f36b", 9200);
        EMOJI_POS.put("1f36c", 9200);
        EMOJI_POS.put("1f36d", 9200);
        EMOJI_POS.put("1f36e", 9200);
        EMOJI_POS.put("1f36f", 9200);
        EMOJI_POS.put("1f355", 9200);
        EMOJI_POS.put("1f356", 9200);
        EMOJI_POS.put("1f357", 9200);
        EMOJI_POS.put("1f360", 9200);
        EMOJI_POS.put("1f364", 9200);
        EMOJI_POS.put("1f365", 9200);
        EMOJI_POS.put("1f368", 9200);
        EMOJI_POS.put("1f489", 5300);
        EMOJI_POS.put("26a1", 80);
        EMOJI_POS.put("26a0", 9700);
        EMOJI_POS.put("1f47c", 1900);
        EMOJI_POS.put("1f35b", 8580);
        EMOJI_POS.put("1f23a", 10260);
        EMOJI_POS.put("1f47b", 1880);
        EMOJI_POS.put("1f35a", 8540);
        EMOJI_POS.put("1f47e", 1940);
        EMOJI_POS.put("1f35d", 8560);
        EMOJI_POS.put("1f47d", 1920);
        EMOJI_POS.put("1f35c", 8400);
        EMOJI_POS.put("1f35f", 8480);
        EMOJI_POS.put("1f47f", 1960);
        EMOJI_POS.put("1f35e", 8420);
        EMOJI_POS.put("1f491", 8020);
        EMOJI_POS.put("1f370", 8380);
        EMOJI_POS.put("1f490", 8000);
        EMOJI_POS.put("1f372", 8660);
        EMOJI_POS.put("1f493", 9480);
        EMOJI_POS.put("1f492", 8040);
        EMOJI_POS.put("1f371", 8640);
        EMOJI_POS.put("1f250", 10320);
        EMOJI_POS.put("1f374", 8700);
        EMOJI_POS.put("1f373", 8440);
        EMOJI_POS.put("1f494", 9280);
        EMOJI_POS.put("1f376", 8800);
        EMOJI_POS.put("1f497", 9340);
        EMOJI_POS.put("1f375", 8780);
        EMOJI_POS.put("1f496", 9480);
        EMOJI_POS.put("1f495", 9480);
        EMOJI_POS.put("1f378", 8860);
        EMOJI_POS.put("1f499", 9380);
        EMOJI_POS.put("1f377", 8860);
        EMOJI_POS.put("1f498", 9360);
        EMOJI_POS.put("1f479", 9200);
        EMOJI_POS.put("1f47a", 9200);
        EMOJI_POS.put("1f358", 8520);
        EMOJI_POS.put("1f237", 10180);
        EMOJI_POS.put("1f478", 1860);
        EMOJI_POS.put("1f236", 10140);
        EMOJI_POS.put("1f239", 10220);
        EMOJI_POS.put("1f359", 8360);
        EMOJI_POS.put("1f238", 10200);
        EMOJI_POS.put("1f34a", 1260);
        EMOJI_POS.put("1f46b", 1660);
        EMOJI_POS.put("1f46a", 9200);
        EMOJI_POS.put("1f34e", 1360);
        EMOJI_POS.put("1f46f", 1700);
        EMOJI_POS.put("1f46e", 1680);
        EMOJI_POS.put("1f22f", 10240);
        EMOJI_POS.put("1f34f", 1360);
        EMOJI_POS.put("2049", 9200);
        EMOJI_POS.put("203c", 9200);
        EMOJI_POS.put("1f480", 1980);
        EMOJI_POS.put("1f482", 2020);
        EMOJI_POS.put("1f361", 8500);
        EMOJI_POS.put("1f481", 2000);
        EMOJI_POS.put("1f484", 1480);
        EMOJI_POS.put("1f363", 8620);
        EMOJI_POS.put("1f483", 2040);
        EMOJI_POS.put("1f362", 8600);
        EMOJI_POS.put("1f486", 1520);
        EMOJI_POS.put("1f485", 1500);
        EMOJI_POS.put("1f488", 1560);
        EMOJI_POS.put("1f004", 7560);
        EMOJI_POS.put("1f367", 8680);
        EMOJI_POS.put("1f487", 1540);
        EMOJI_POS.put("1f366", 8460);
        EMOJI_POS.put("1f468", 1620);
        EMOJI_POS.put("26c5", 260);
        EMOJI_POS.put("1f346", 1340);
        EMOJI_POS.put("1f467", 1600);
        EMOJI_POS.put("26c4", 60);
        EMOJI_POS.put("1f349", 1300);
        EMOJI_POS.put("1f469", 1640);
        EMOJI_POS.put("1f69a", 7100);
        EMOJI_POS.put("1f45a", 4620);
        EMOJI_POS.put("1f33b", 1200);
        EMOJI_POS.put("1f45c", 4960);
        EMOJI_POS.put("1f21a", 10160);
        EMOJI_POS.put("1f33a", 1100);
        EMOJI_POS.put("1f45b", 9200);
        EMOJI_POS.put("3030", 9200);
        EMOJI_POS.put("27b0", 9200);
        EMOJI_POS.put("27bf", 9200);
        EMOJI_POS.put("1f33c", 1200);
        EMOJI_POS.put("1f45d", 9200);
        EMOJI_POS.put("1f33f", 1220);
        EMOJI_POS.put("1f33e", 1180);
        EMOJI_POS.put("1f45f", 4400);
        EMOJI_POS.put("1f45e", 4400);
        EMOJI_POS.put("26ab", 10640);
        EMOJI_POS.put("26aa", 10640);
        EMOJI_POS.put("1f534", 10640);
        EMOJI_POS.put("1f471", 1720);
        EMOJI_POS.put("1f470", 9200);
        EMOJI_POS.put("1f352", 9200);
        EMOJI_POS.put("1f34c", 9200);
        EMOJI_POS.put("1f473", 1760);
        EMOJI_POS.put("1f351", 9200);
        EMOJI_POS.put("1f348", 9200);
        EMOJI_POS.put("1f347", 9200);
        EMOJI_POS.put("1f34d", 9200);
        EMOJI_POS.put("1f472", 1740);
        EMOJI_POS.put("1f475", 1800);
        EMOJI_POS.put("1f354", 8340);
        EMOJI_POS.put("1f233", 10100);
        EMOJI_POS.put("1f353", 1280);
        EMOJI_POS.put("1f474", 1780);
        EMOJI_POS.put("1f232", 9200);
        EMOJI_POS.put("1f477", 1840);
        EMOJI_POS.put("1f235", 10120);
        EMOJI_POS.put("1f476", 1820);
        EMOJI_POS.put("1f234", 9200);
        EMOJI_POS.put("1f457", 4560);
        EMOJI_POS.put("1f699", 6860);
        EMOJI_POS.put("25b6", 9080);
        EMOJI_POS.put("1f335", 1160);
        EMOJI_POS.put("1f456", 9200);
        EMOJI_POS.put("1f338", 1020);
        EMOJI_POS.put("1f459", 4600);
        EMOJI_POS.put("1f337", 960);
        EMOJI_POS.put("1f458", 4580);
        EMOJI_POS.put("26d4", 9720);
        EMOJI_POS.put("1f339", 1040);
        EMOJI_POS.put("2199", 8940);
        EMOJI_POS.put("2198", 8980);
        EMOJI_POS.put("2197", 8960);
        EMOJI_POS.put("2196", 8920);
        EMOJI_POS.put("2195", 9200);
        EMOJI_POS.put("2194", 9200);
        EMOJI_POS.put("1f44b", 11420);
        EMOJI_POS.put("1f68c", 6880);
        EMOJI_POS.put("1f44a", 11280);
        EMOJI_POS.put("1f68f", 6900);
        EMOJI_POS.put("1f44d", 11300);
        EMOJI_POS.put("1f44c", 11460);
        EMOJI_POS.put("26be", 6480);
        EMOJI_POS.put("1f44f", 11440);
        EMOJI_POS.put("26bd", 6540);
        EMOJI_POS.put("1f44e", 11480);
        EMOJI_POS.put("1f460", 4420);
        EMOJI_POS.put("1f341", 1000);
        EMOJI_POS.put("1f462", 4460);
        EMOJI_POS.put("1f340", 1220);
        EMOJI_POS.put("1f461", 4440);
        EMOJI_POS.put("1f343", 1080);
        EMOJI_POS.put("1f464", 9200);
        EMOJI_POS.put("1f342", 1060);
        EMOJI_POS.put("1f463", 6460);
        EMOJI_POS.put("1f345", 1320);
        EMOJI_POS.put("1f466", 1580);
        EMOJI_POS.put("1f344", 9200);
        EMOJI_POS.put("1f330", 9200);
        EMOJI_POS.put("1f33d", 9200);
        EMOJI_POS.put("1f446", 11340);
        EMOJI_POS.put("1f445", 3000);
        EMOJI_POS.put("1f448", 11380);
        EMOJI_POS.put("1f689", 7000);
        EMOJI_POS.put("1f447", 11360);
        EMOJI_POS.put("1f449", 11400);
        EMOJI_POS.put("25c0", 9100);
        EMOJI_POS.put("3299", 10280);
        EMOJI_POS.put("3297", 10300);
        EMOJI_POS.put("1f55b", 600);
        EMOJI_POS.put("1f43a", 2560);
        EMOJI_POS.put("1f55a", 580);
        EMOJI_POS.put("1f31b", 360);
        EMOJI_POS.put("1f43b", 2260);
        EMOJI_POS.put("1f43e", 6460);
        EMOJI_POS.put("26ce", 880);
        EMOJI_POS.put("1f43d", 2780);
        EMOJI_POS.put("1f31f", 10740);
        EMOJI_POS.put("25ab", 10980);
        EMOJI_POS.put("25aa", 10900);
        EMOJI_POS.put("1f691", 7140);
        EMOJI_POS.put("303d", 7760);
        EMOJI_POS.put("1f451", 4500);
        EMOJI_POS.put("1f693", 7260);
        EMOJI_POS.put("1f692", 7120);
        EMOJI_POS.put("1f450", 11500);
        EMOJI_POS.put("1f453", 9200);
        EMOJI_POS.put("1f695", 7060);
        EMOJI_POS.put("1f331", 1220);
        EMOJI_POS.put("1f452", 4540);
        EMOJI_POS.put("1f334", 1140);
        EMOJI_POS.put("1f455", 4620);
        EMOJI_POS.put("1f697", 6840);
        EMOJI_POS.put("1f454", 4520);
        EMOJI_POS.put("1f556", 500);
        EMOJI_POS.put("1f435", 2320);
        EMOJI_POS.put("1f555", 480);
        EMOJI_POS.put("1f434", 2200);
        EMOJI_POS.put("1f558", 540);
        EMOJI_POS.put("1f437", 2780);
        EMOJI_POS.put("1f315", 9200);
        EMOJI_POS.put("1f557", 520);
        EMOJI_POS.put("1f436", 2720);
        EMOJI_POS.put("1f439", 2440);
        EMOJI_POS.put("26f5", 6960);
        EMOJI_POS.put("1f559", 620);
        EMOJI_POS.put("1f438", 2700);
        EMOJI_POS.put("26f3", 6500);
        EMOJI_POS.put("1f319", 360);
        EMOJI_POS.put("1f314", 360);
        EMOJI_POS.put("1f313", 360);
        EMOJI_POS.put("26f2", 4160);
        EMOJI_POS.put("1f30a", 900);
        EMOJI_POS.put("1f42b", 2680);
        EMOJI_POS.put("1f30c", 920);
        EMOJI_POS.put("1f42d", 2280);
        EMOJI_POS.put("1f30b", 9200);
        EMOJI_POS.put("1f42c", 2380);
        EMOJI_POS.put("1f42f", 2240);
        EMOJI_POS.put("1f42e", 2580);
        EMOJI_POS.put("1f680", 7020);
        EMOJI_POS.put("1f440", 1380);
        EMOJI_POS.put("1f442", 1400);
        EMOJI_POS.put("1f684", 6800);
        EMOJI_POS.put("1f320", 9200);
        EMOJI_POS.put("1f683", 6740);
        EMOJI_POS.put("1f444", 1440);
        EMOJI_POS.put("1f202", 10080);
        EMOJI_POS.put("1f443", 1420);
        EMOJI_POS.put("1f685", 6820);
        EMOJI_POS.put("1f201", 10060);
        EMOJI_POS.put("1f303", 920);
        EMOJI_POS.put("1f302", 120);
        EMOJI_POS.put("1f423", 2140);
        EMOJI_POS.put("1f305", 180);
        EMOJI_POS.put("1f426", 2400);
        EMOJI_POS.put("1f304", 160);
        EMOJI_POS.put("1f425", 2140);
        EMOJI_POS.put("1f424", 2140);
        EMOJI_POS.put("1f307", 220);
        EMOJI_POS.put("1f428", 2500);
        EMOJI_POS.put("1f306", 200);
        EMOJI_POS.put("1f427", 2160);
        EMOJI_POS.put("1f309", 920);
        EMOJI_POS.put("24c2", 6780);
        EMOJI_POS.put("1f687", 6780);
        EMOJI_POS.put("1f308", 240);
        EMOJI_POS.put("1f429", 2720);
        EMOJI_POS.put("26fa", 7300);
        EMOJI_POS.put("1f41a", 2360);
        EMOJI_POS.put("1f41b", 2460);
        EMOJI_POS.put("1f41e", 9200);
        EMOJI_POS.put("1f41d", 9200);
        EMOJI_POS.put("1f41c", 9200);
        EMOJI_POS.put("1f41f", 7360);
        EMOJI_POS.put("26ea", 4140);
        EMOJI_POS.put("1f550", 380);
        EMOJI_POS.put("1f552", 420);
        EMOJI_POS.put("1f431", 2080);
        EMOJI_POS.put("1f551", 400);
        EMOJI_POS.put("1f430", 2600);
        EMOJI_POS.put("1f554", 460);
        EMOJI_POS.put("1f433", 2300);
        EMOJI_POS.put("1f311", 9200);
        EMOJI_POS.put("1f30f", 9200);
        EMOJI_POS.put("1f553", 440);
        EMOJI_POS.put("1f432", 9200);
        EMOJI_POS.put("1f43c", 9200);
        EMOJI_POS.put("1f4ac", 9200);
        EMOJI_POS.put("1f4ab", 10520);
        EMOJI_POS.put("1f3a0", 9200);
        EMOJI_POS.put("1f4c0", 7820);
        EMOJI_POS.put("1f3a2", 7340);
        EMOJI_POS.put("1f3a1", 7320);
        EMOJI_POS.put("1f4c5", 9200);
        EMOJI_POS.put("1f4c1", 9200);
        EMOJI_POS.put("1f4c2", 9200);
        EMOJI_POS.put("1f3a4", 7380);
        EMOJI_POS.put("1f4c4", 6440);
        EMOJI_POS.put("1f4c3", 6440);
        EMOJI_POS.put("1f3a3", 7360);
        EMOJI_POS.put("1f4c7", 6420);
        EMOJI_POS.put("1f3a6", 7420);
        EMOJI_POS.put("1f4c6", 9200);
        EMOJI_POS.put("1f3a5", 7400);
        EMOJI_POS.put("1f4c9", 9200);
        EMOJI_POS.put("1f3a8", 7460);
        EMOJI_POS.put("26fd", 7180);
        EMOJI_POS.put("1f3a7", 7440);
        EMOJI_POS.put("1f3a9", 7540);
        EMOJI_POS.put("1f4bb", 6120);
        EMOJI_POS.put("1f4ba", 6100);
        EMOJI_POS.put("1f19a", 9960);
        EMOJI_POS.put("25fb", 10980);
        EMOJI_POS.put("1f4b0", 4720);
        EMOJI_POS.put("1f4b2", 4720);
        EMOJI_POS.put("1f4b1", 4660);
        EMOJI_POS.put("1f4b4", 9200);
        EMOJI_POS.put("1f4b3", 9200);
        EMOJI_POS.put("1f4b5", 4720);
        EMOJI_POS.put("1f4b8", 9200);
        EMOJI_POS.put("1f4b9", 6380);
        EMOJI_POS.put("1f4aa", 10500);
        EMOJI_POS.put("1f18e", 5380);
        EMOJI_POS.put("1f4a1", 10360);
        EMOJI_POS.put("1f4a3", 10400);
        EMOJI_POS.put("1f4a2", 10380);
        EMOJI_POS.put("1f4a5", 9200);
        EMOJI_POS.put("1f4a4", 10420);
        EMOJI_POS.put("1f4a7", 10460);
        EMOJI_POS.put("1f4a6", 10460);
        EMOJI_POS.put("25fe", 10900);
        EMOJI_POS.put("1f4a9", 5000);
        EMOJI_POS.put("25fd", 10980);
        EMOJI_POS.put("1f4a8", 10480);
        EMOJI_POS.put("25fc", 10900);
        EMOJI_POS.put("1f6bb", 5240);
        EMOJI_POS.put("1f6ba", 9760);
        EMOJI_POS.put("23e9", 9120);
        EMOJI_POS.put("1f6bd", 5260);
        EMOJI_POS.put("1f6bc", 9780);
        EMOJI_POS.put("1f6be", 5280);
        EMOJI_POS.put("1f17f", 7200);
        EMOJI_POS.put("1f17e", 5400);
        EMOJI_POS.put("1f192", 10020);
        EMOJI_POS.put("1f194", 11020);
        EMOJI_POS.put("1f193", 9200);
        EMOJI_POS.put("1f196", 9200);
        EMOJI_POS.put("1f195", 9980);
        EMOJI_POS.put("1f198", 9200);
        EMOJI_POS.put("1f197", 9880);
        EMOJI_POS.put("1f199", 10000);
        EMOJI_POS.put("1f6ac", 9600);
        EMOJI_POS.put("1f6ad", 9620);
        EMOJI_POS.put("23ec", 9200);
        EMOJI_POS.put("1f53a", 9200);
        EMOJI_POS.put("1f53b", 9200);
        EMOJI_POS.put("1f53c", 9200);
        EMOJI_POS.put("1f53d", 9200);
        EMOJI_POS.put("23eb", 9200);
        EMOJI_POS.put("21aa", 9200);
        EMOJI_POS.put("21a9", 9200);
        EMOJI_POS.put("1f4ae", 9200);
        EMOJI_POS.put("1f4af", 9200);
        EMOJI_POS.put("1f503", 9200);
        EMOJI_POS.put("23ea", 9140);
        EMOJI_POS.put("1f6c0", 5220);
        EMOJI_POS.put("23f3", 9200);
        EMOJI_POS.put("23f0", 620);
        EMOJI_POS.put("1f6b2", 6980);
        EMOJI_POS.put("1f170", 5340);
        EMOJI_POS.put("1f6b6", 7080);
        EMOJI_POS.put("1f171", 5360);
        EMOJI_POS.put("1f6b9", 9740);
        EMOJI_POS.put("1f38c", 5520);
        EMOJI_POS.put("1f38b", 9200);
        EMOJI_POS.put("1f38a", 9200);
        EMOJI_POS.put("1f38e", 5620);
        EMOJI_POS.put("1f38d", 5600);
        EMOJI_POS.put("1f38f", 5680);
        EMOJI_POS.put("1f6a2", 6920);
        EMOJI_POS.put("1f6a5", 7220);
        EMOJI_POS.put("1f6a4", 7040);
        EMOJI_POS.put("1f6a7", 9720);
        EMOJI_POS.put("1f6a9", 9200);
        EMOJI_POS.put("1f6a8", 7260);
        EMOJI_POS.put("1f49a", 9400);
        EMOJI_POS.put("1f37b", 8840);
        EMOJI_POS.put("1f49c", 9440);
        EMOJI_POS.put("1f37a", 8760);
        EMOJI_POS.put("1f49b", 9420);
        EMOJI_POS.put("1f49e", 9480);
        EMOJI_POS.put("1f49d", 9460);
        EMOJI_POS.put("1f49f", 9500);
        EMOJI_POS.put("1f390", 5720);
        EMOJI_POS.put("1f392", 5660);
        EMOJI_POS.put("1f391", 5760);
        EMOJI_POS.put("1f393", 5640);
        EMOJI_POS.put("2935", 8980);
        EMOJI_POS.put("2934", 8960);
        EMOJI_POS.put("2702", 6220);
        EMOJI_POS.put("2709", 11200);
        EMOJI_POS.put("1f4e8", 11200);
        EMOJI_POS.put("1f4e9", 11200);
        EMOJI_POS.put("2708", 6940);
        EMOJI_POS.put("2705", 9200);
        EMOJI_POS.put("2002", 9200);
        EMOJI_POS.put("2003", 9200);
        EMOJI_POS.put("2005", 9200);
        EMOJI_POS.put("270b", 11240);
        EMOJI_POS.put("270a", 11220);
        EMOJI_POS.put("270f", 6440);
        EMOJI_POS.put("270c", 11260);
        EMOJI_POS.put("2712", 9200);
        EMOJI_POS.put("1f520", 9200);
        EMOJI_POS.put("1f521", 9200);
        EMOJI_POS.put("1f522", 9200);
        EMOJI_POS.put("1f523", 9200);
        EMOJI_POS.put("1f524", 9200);
        EMOJI_POS.put("2716", 10340);
        EMOJI_POS.put("2714", 9200);
        EMOJI_POS.put("1f6aa", 9200);
        EMOJI_POS.put("1f6ab", 9200);
        EMOJI_POS.put("1f191", 9200);
        EMOJI_POS.put("2601", 20);
        EMOJI_POS.put("2600", 260);
        EMOJI_POS.put("2728", 11000);
        EMOJI_POS.put("1f3f0", 4220);
        EMOJI_POS.put("260e", 5800);
        EMOJI_POS.put("1f4de", 5800);
        EMOJI_POS.put("2614", 40);
        EMOJI_POS.put("2734", 10560);
        EMOJI_POS.put("2733", 10580);
        EMOJI_POS.put("2611", 9200);
        EMOJI_POS.put("1f516", 9200);
        EMOJI_POS.put("1f517", 9200);
        EMOJI_POS.put("1f518", 9200);
        EMOJI_POS.put("1f3eb", 4120);
        EMOJI_POS.put("1f3ea", 4100);
        EMOJI_POS.put("1f3ed", 4240);
        EMOJI_POS.put("1f3ec", 4180);
        EMOJI_POS.put("1f3ef", 4200);
        EMOJI_POS.put("1f3ee", 8800);
        EMOJI_POS.put("2615", 8720);
        EMOJI_POS.put("261d", 11320);
        EMOJI_POS.put("1f3e2", 3960);
        EMOJI_POS.put("1f3e1", 3940);
        EMOJI_POS.put("1f3e0", 3940);
        EMOJI_POS.put("1f3e3", 3980);
        EMOJI_POS.put("1f3e6", 4020);
        EMOJI_POS.put("1f3e5", 4000);
        EMOJI_POS.put("1f3e8", 4060);
        EMOJI_POS.put("1f3e7", 4040);
        EMOJI_POS.put("2744", 9200);
        EMOJI_POS.put("1f3e9", 4080);
        EMOJI_POS.put("1f4fb", 7840);
        EMOJI_POS.put("1f4fa", 7780);
        EMOJI_POS.put("1f4fc", 7860);
        EMOJI_POS.put("2747", 11000);
        EMOJI_POS.put("1f4df", 9200);
        EMOJI_POS.put("1f3be", 6520);
        EMOJI_POS.put("1f5ff", 9200);
        EMOJI_POS.put("1f5fe", 9200);
        EMOJI_POS.put("1f3bd", 9200);
        EMOJI_POS.put("274e", 10340);
        EMOJI_POS.put("274c", 10340);
        EMOJI_POS.put("1f3bf", 6560);
        EMOJI_POS.put("1f4f0", 9200);
        EMOJI_POS.put("1f4f2", 5840);
        EMOJI_POS.put("1f4f1", 5820);
        EMOJI_POS.put("1f4f4", 8320);
        EMOJI_POS.put("1f4f3", 8300);
        EMOJI_POS.put("1f4f6", 8280);
        EMOJI_POS.put("2757", 9160);
        EMOJI_POS.put("1f4f7", 4940);
        EMOJI_POS.put("2755", 9220);
        EMOJI_POS.put("1f4f9", 7400);
        EMOJI_POS.put("2754", 9200);
        EMOJI_POS.put("2753", 9180);
        EMOJI_POS.put("1f4eb", 5980);
        EMOJI_POS.put("1f4ea", 5980);
        EMOJI_POS.put("1f3ca", 6720);
        EMOJI_POS.put("1f4ee", 6000);
        EMOJI_POS.put("1f4ce", 9200);
        EMOJI_POS.put("1f3ad", 7540);
        EMOJI_POS.put("1f4cd", 9200);
        EMOJI_POS.put("1f3ac", 7520);
        EMOJI_POS.put("1f3af", 7580);
        EMOJI_POS.put("1f3ae", 9200);
        EMOJI_POS.put("263a", 3240);
        EMOJI_POS.put("2650", 800);
        EMOJI_POS.put("1f4e1", 6060);
        EMOJI_POS.put("1f3c0", 6580);
        EMOJI_POS.put("1f4e0", 5880);
        EMOJI_POS.put("1f4e3", 6040);
        EMOJI_POS.put("1f3c2", 9200);
        EMOJI_POS.put("1f4e2", 6020);
        EMOJI_POS.put("1f3c1", 6600);
        EMOJI_POS.put("1f4e5", 9200);
        EMOJI_POS.put("1f4e4", 9200);
        EMOJI_POS.put("1f3c4", 6640);
        EMOJI_POS.put("1f3c3", 6620);
        EMOJI_POS.put("1f4e7", 11200);
        EMOJI_POS.put("1f3c6", 6660);
        EMOJI_POS.put("1f4e6", 6080);
        EMOJI_POS.put("1f3c8", 6700);
        EMOJI_POS.put("2764", 9240);
        EMOJI_POS.put("1f5fc", 4320);
        EMOJI_POS.put("1f3ba", 7720);
        EMOJI_POS.put("1f5fb", 4300);
        EMOJI_POS.put("1f4da", 6420);
        EMOJI_POS.put("1f4d3", 6420);
        EMOJI_POS.put("1f4d4", 6420);
        EMOJI_POS.put("1f4d5", 6420);
        EMOJI_POS.put("1f4d6", 6420);
        EMOJI_POS.put("1f4d7", 6420);
        EMOJI_POS.put("1f4d8", 6420);
        EMOJI_POS.put("1f4d9", 6420);
        EMOJI_POS.put("2649", 660);
        EMOJI_POS.put("1f4dd", 6440);
        EMOJI_POS.put("1f3bc", 7740);
        EMOJI_POS.put("2648", 640);
        EMOJI_POS.put("1f5fd", 4340);
        EMOJI_POS.put("1f4dc", 9200);
        EMOJI_POS.put("1f4db", 9200);
        EMOJI_POS.put("1f3bb", 9200);
        EMOJI_POS.put("264f", 780);
        EMOJI_POS.put("1f4bc", 6160);
        EMOJI_POS.put("264e", 760);
        EMOJI_POS.put("1f4bf", 7800);
        EMOJI_POS.put("264d", 740);
        EMOJI_POS.put("1f4be", 6200);
        EMOJI_POS.put("1f4bd", 6200);
        EMOJI_POS.put("264c", 720);
        EMOJI_POS.put("264b", 700);
        EMOJI_POS.put("264a", 680);
        EMOJI_POS.put("2660", 9540);
        EMOJI_POS.put("1f4d0", 9200);
        EMOJI_POS.put("1f4cf", 9200);
        EMOJI_POS.put("1f4d2", 6420);
        EMOJI_POS.put("1f3b1", 7620);
        EMOJI_POS.put("1f4d1", 6440);
        EMOJI_POS.put("1f3b0", 7600);
        EMOJI_POS.put("1f3b5", 7640);
        EMOJI_POS.put("1f3b4", 9200);
        EMOJI_POS.put("1f0cf", 9200);
        EMOJI_POS.put("1f3b2", 9200);
        EMOJI_POS.put("1f3b3", 9200);
        EMOJI_POS.put("1f3b7", 7680);
        EMOJI_POS.put("1f3b6", 7740);
        EMOJI_POS.put("1f3b9", 9200);
        EMOJI_POS.put("2653", 860);
        EMOJI_POS.put("1f3b8", 7700);
        EMOJI_POS.put("2652", 840);
        EMOJI_POS.put("2651", 820);
        EMOJI_POS.put("a9", 9900);
        EMOJI_POS.put("1f4ca", 6380);
        EMOJI_POS.put("1f4c8", 6380);
        EMOJI_POS.put("1f4cc", 9200);
        EMOJI_POS.put("1f3ab", 7500);
        EMOJI_POS.put("1f4cb", 6440);
        EMOJI_POS.put("1f3aa", 9200);
    }

    /**
     * 获取Emoji表情
     *
     * @param code emoji代码，形式如 {@code :dog:}
     * @return
     */
    public static ImageIcon getEmoji(Object context, String code)
    {
        String iconPath = "/emoji/" + code.subSequence(1, code.length() - 1) + ".png";
        URL url = context.getClass().getResource(iconPath);
        return url == null ? null : new ImageIcon(url);
    }

    /**
     * 判断给定的emoji代码是否可识别
     *
     * @param code
     * @return
     */
    public static boolean isRecognizableEmoji(Object context, String code)
    {
        return getEmoji(context, code) != null;
    }


    /**
     * 提取emoji表情
     *
     * @param src 形如:<span class="emoji emoji1f63b">
     * @return
     */
    public static List<String> extractEmojiClass(String src)
    {
        String regx = "emoji([0-9a-z]{5})";
        Pattern pattern = Pattern.compile(regx);
        Matcher matcher = pattern.matcher(src);
        List<String> emojis = new ArrayList<>();

        while (matcher.find())
        {
            emojis.add(matcher.group(1));
        }

        return emojis;
    }


    /**
     * 获取emoji表情, 形式如  :a9:
     * @param name
     * @return
     */
    public static BufferedImage getEmoji(String name)
    {
        Integer  pos= EMOJI_POS.get(name.substring(1, name.length() - 1));
        if (pos == null)
        {
            logger.error("无法获取emoji:" + name);
            return null;
        }

        BufferedImage dest = EMOJIS.getSubimage(0, pos, 20, 20);
        return dest;
    }


    public static void main(String[] args) throws IOException
    {
        /*  .emoji1f52e,
            .emoji1f52f {
            background-position: 0 -5080px;
            }*/

        /*Map<String, String> map = replaceEmoji2("");


        map.forEach((k,v) -> {
            System.out.println("\"" +k+"\"," + v);
        });*/

        BufferedImage images = ImageIO.read(new FileInputStream("C:\\Users\\song\\IdeaProjects\\rocketchat_desktop\\res\\image\\emojis.png"));
        BufferedImage dest = images.getSubimage(0, 9900, 20, 20);
        ImageIO.write(dest, "png", new File("C:\\Users\\song\\Desktop\\aaa.png"));
    }
}
