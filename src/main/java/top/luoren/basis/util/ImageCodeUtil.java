package top.luoren.basis.util;

import top.luoren.basis.entity.ImageCode;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * @author luoren
 * @date 2019-05-06 11:18
 */
public class ImageCodeUtil {
    private static int width = 160;
    private static int height = 40;
    private static int codeCount = 5;
    private static int lineCount = 20;
    private static int expireIn=600;
    private static String code = null;
    private static Random random=new Random();

    public static ImageCode creatImage(int width, int height) {
        ImageCodeUtil.width = width;
        ImageCodeUtil.height = height;
        return creatImage();
    }

    public static ImageCode creatImage(int width, int height, int codeCount) {
        ImageCodeUtil.width = width;
        ImageCodeUtil.height = height;
        ImageCodeUtil.codeCount = codeCount;
        return creatImage();
    }

    public static ImageCode creatImage(int width, int height, int codeCount, int lineCount) {
        ImageCodeUtil.width = width;
        ImageCodeUtil.height = height;
        ImageCodeUtil.codeCount = codeCount;
        ImageCodeUtil.lineCount = lineCount;
        return creatImage();
    }

    /**
     * 生成图片
     * @return
     */
    private static ImageCode creatImage() {
        //字体的宽度
        int fontWidth = width / codeCount;
        //字体的高度
        int fontHeight = height - 5;
        int codeY = height - 8;

        // 图像buffer
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = buffImg.getGraphics();

        // 设置背景色
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);

        // 设置字体
        g.setFont(getFont(fontHeight));

        // 设置干扰线
        for (int i = 0; i < lineCount; i++) {
            int xs = random.nextInt(width);
            int ys = random.nextInt(height);
            int xe = xs + random.nextInt(width);
            int ye = ys + random.nextInt(height);
            g.setColor(getRandColor(1, 255));
            g.drawLine(xs, ys, xe, ye);
        }

        // 添加噪点,// 噪声率
        float yawpRate = 0.01f;
        int area = (int) (yawpRate * width * height);
        for (int i = 0; i < area; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);

            buffImg.setRGB(x, y, random.nextInt(255));
        }

        // 得到随机字符
        String str1 = randomStr(codeCount);
        ImageCodeUtil.code = str1;
        for (int i = 0; i < codeCount; i++) {
            String strRand = str1.substring(i, i + 1);
            g.setColor(getRandColor(1, 255));
            g.drawString(strRand, i * fontWidth + 3, codeY);
        }
        return new ImageCode(buffImg,code,expireIn);
    }

    /**
     * 得到随机字符
     * @param n
     * @return
     */
    private static String randomStr(int n) {
        String str1 = "ABCDEFGHIJKLMNPQRSTUVWXYZabcdefghijklmnpqrstuvwxyz123456789";
        StringBuilder sb = new StringBuilder();
        int len = str1.length() - 1;
        double r;
        for (int i = 0; i < n; i++) {
            r = (Math.random()) * len;
            sb.append(str1.charAt((int) r));
        }
        return sb.toString();
    }

    /**
     * 得到随机颜色
     * @param fc
     * @param bc
     * @return
     */
    private static Color getRandColor(int fc, int bc) {// 给定范围获得随机颜色
        if (fc > 255) {
            fc = 255;
        }
        if (bc > 255) {
            bc = 255;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    /**
     * 产生随机字体
     */
    private static Font getFont(int size) {
        Random random = new Random();
        Font[] font = new Font[5];
        font[0] = new Font("Ravie", Font.PLAIN, size);
        font[1] = new Font("Antique Olive Compact", Font.PLAIN, size);
        font[2] = new Font("Fixedsys", Font.PLAIN, size);
        font[3] = new Font("Wide Latin", Font.PLAIN, size);
        font[4] = new Font("Gill Sans Ultra Bold", Font.PLAIN, size);
        return font[random.nextInt(5)];
    }

}
