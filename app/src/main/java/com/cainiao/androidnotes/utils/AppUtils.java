package com.cainiao.androidnotes.utils;

/**
 * @author liangtao
 * @date 2018/8/31 18:04
 * @describe
 */
public class AppUtils {
    /**
     * 将字符串中间用星号隐藏
     * @param start
     *            从哪一位开始隐藏 1为初始位
     * @param end
     *            最后剩几位
     */
    public static String hideString(String str, int start, int end) {
        if(start<0 || end<0){
            return str;
        }else if ((start + end) > str.length()) {
            return str;
        } else {
            int length = str.length();
            String strStart = str.substring(0, start);
            String strMid = str.substring(start, length - end);
            String strEnd = str.substring(start + strMid.length(), length);
            StringBuffer s = new StringBuffer();
            for (int i = 0; i < strMid.length(); i++) {
                s.append("*");
            }
            return strStart + s.toString() + strEnd;
        }
    }
}
