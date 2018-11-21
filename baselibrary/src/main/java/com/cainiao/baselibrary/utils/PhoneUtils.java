package com.cainiao.baselibrary.utils;

import android.util.Log;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneUtils {
    /**
     * 判断手机号是否符合规范
     * @param phoneNo 输入的手机号
     * @return
     */
    static String YD = "^[1]{1}(([3]{1}[4-9]{1})|([5]{1}[012789]{1})|([8]{1}[12378]{1})|([4]{1}[7]{1}))[0-9]{8}$";
    static String LT = "^[1]{1}(([3]{1}[0-2]{1})|([5]{1}[56]{1})|([8]{1}[56]{1}))[0-9]{8}$";
    static String DX = "^[1]{1}(([3]{1}[3]{1})|([5]{1}[3]{1})|([8]{1}[09]{1}))[0-9]{8}$";

    //校检手机号码
    public static boolean checkPhoneNumber(String phoneNumber) {
        String telRegex = "[1][34578]\\d{9}";
        return phoneNumber.matches(telRegex);

    }

    //验证密码   这个正则表示8到15位字符，必须包含有字母和数字
    public static final boolean isRightPwd(String pwd){
        //Pattern p  = Pattern.compile("^(?![^a-zA-Z]+$)(?!\\D+$)[0-9a-zA-Z]{8,16}$");
        Pattern p = Pattern.compile("^[a-zA-Z0-9_\\u4e00-\\u9fa5]+${2,6}");
        Matcher m = p.matcher(pwd);
        return m.matches();
    }

    String mobPhnNum;

    public PhoneUtils(String mobPhnNum) {
        this.mobPhnNum = mobPhnNum;
        Log.d("tool", mobPhnNum);
    }

    public int matchNum() {
        /**
         * 28. * flag = 1 YD 2 LT 3 DX 29.
         */
        int flag;//存储匹配结果
        // 判断手机号码是否是11位
        if (mobPhnNum.length() == 11) {
            // 判断手机号码是否符合中国移动的号码规则
            if (mobPhnNum.matches(YD)) {
                flag = 1;
            }
            // 判断手机号码是否符合中国联通的号码规则
            else if (mobPhnNum.matches(LT)) {
                flag = 2;
            }
            // 判断手机号码是否符合中国电信的号码规则
            else if (mobPhnNum.matches(DX)) {
                flag = 3;
            }
            // 都不适合，未知֪
            else {
                flag = 4;
            }
        }
        // 不是11位
        else {
            flag = 5;
        }
        Log.d("TelNumMatch", "flag" + flag);
        return flag;
    }

    //手机号码的有效性验证
    public static boolean isValidPhoneNumber(String number)
    {
        if(number.length()==11 && (number.matches(YD)||number.matches(LT)||number.matches(DX)))
        {
           return true;
        }
        return false;
    }

    //判断手机号码是否存在
    public static boolean isExistPhoneNumber(String number)
    {
        return false;
    }


    //是否是数字和字母
    public static boolean isMatchCharOrNumber(String str)
    {
        String patternString="^[\\d|a-z|A-Z]+$";
        return isMatcher(patternString,str);
    }

    //是否匹配
    public static boolean isMatcher(String patternString,String str)
    {
        boolean isValid=false;
        CharSequence inputStr =str ;
        Pattern pattern =Pattern.compile(patternString,Pattern.CASE_INSENSITIVE);
        Matcher matcher=pattern.matcher(inputStr);
        if(matcher.matches())
        {
            isValid =true;
        }
        return isValid;
    }

}
