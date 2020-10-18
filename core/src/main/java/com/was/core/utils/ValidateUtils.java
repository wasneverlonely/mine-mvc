package com.was.core.utils;

import android.text.TextUtils;

import com.was.core.R;

import java.util.regex.Pattern;


/**
 * 验证工具类
 */
public class ValidateUtils {

    /**
     * 验证是否是手机格式
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     *
     * @param mobile
     * @return
     */
    public static boolean isPhoneNumber(String mobile) {
        String telRegex = "[1][3456789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return TextUtils.isEmpty(mobile) ? false : mobile.matches(telRegex);
    }


    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 检测手机号 是否正确
     *
     * @param phoneNumber
     * @return
     */
    public static boolean checkPhoneNumber(String phoneNumber) {
        if (TextUtils.isEmpty(phoneNumber) || !isPhoneNumber(phoneNumber)) {
            ToastUtils.showShort(R.string.str_phone_number_error);
            return false;
        }
        return true;
    }

    /**
     * 检测验证是否正确
     *
     * @param verificationCode
     * @return
     */
    public static boolean checkVerificationCode(String verificationCode, boolean isNumeric) {

        boolean empty = TextUtils.isEmpty(verificationCode);

        if (empty) {
            ToastUtils.showShort(R.string.str_verification_code_no_empty);
            return false;
        }

        if (isNumeric && !isNumeric(verificationCode)) {
            ToastUtils.showShort(R.string.str_verification_code_error);
            return false;
        }
        return true;
    }


    /**
     * @param password
     * @param minLength 最小字符  0
     * @return
     */
    public static boolean checkPassword(String password, int minLength) {
        if (TextUtils.isEmpty(password)) {
            ToastUtils.showShort(R.string.str_password_no_empty);
            return false;
        }

        String lengtgHint = "密码长度必须大于等于%d位!";
        if (minLength > 0 && password.length() < minLength) {
            String format = String.format(lengtgHint, minLength);
            ToastUtils.showShort(format);
            return false;
        }
        return true;
    }


    /**
     * 密码是否一致
     *
     * @param password
     * @param confirmPassword
     * @param minLength
     * @return
     */
    public static boolean checkPasswordAgreement(String password, String confirmPassword, int minLength) {

        if (!checkPassword(password, minLength) || !checkPassword(confirmPassword, minLength)) {
            return false;
        }

        if (!password.equals(confirmPassword)) {
            ToastUtils.showShort(R.string.str_password_unlike);
            return false;
        }
        return true;
    }
}
