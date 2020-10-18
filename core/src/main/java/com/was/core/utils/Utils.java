package com.was.core.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/3/16.
 * <p>
 * 常用工具类
 */
public class Utils {

    /**
     * StirngBuilder 拼接字符串
     *
     * @param strings
     * @return
     */
    public static String toStringBuilder(String... strings) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String str : strings) {
            if (!TextUtils.isEmpty(str))
                stringBuilder.append(str);
        }
        return stringBuilder.toString();
    }


    /**
     * 判断多个字符串
     * 是否有一个参数为空
     *
     * @param strs
     * @return
     */
    public static boolean isEmptyFromArray(String... strs) {
        for (String str : strs) {
            if (TextUtils.isEmpty(str))
                return true;
        }
        return false;
    }


    /**
     * 获取TextView 字符串
     *
     * @param tv
     * @return
     */
    public static String getTextViewText(TextView tv) {
        return tv.getText().toString();
    }

    /**
     * 判断list 是否为空
     *
     * @param list
     * @return
     */
    public static <T> boolean isEmptyList(List<T> list) {
        return list == null || list.isEmpty();
    }


    /**
     * 数组转化ArrayList
     *
     * @param array
     * @param <T>
     * @return
     */
    public static <T> List<T> arrayTransformList(T[] array) {
        List<T> list = new ArrayList<>();
        Collections.addAll(list, array);
        return list;
    }

    /**
     * 资源数组转型成集合
     *
     * @param context
     * @param resId
     * @return
     */
    public static List<String> resArrayTransformList(Context context, int resId) {
        return arrayTransformList(context.getResources().getStringArray(resId));
    }


    /**
     * 得到拼接后的详细地址
     *
     * @param delimiter
     * @param hint
     * @param addressNames
     * @return
     */
    public static String showFormatAddress(String delimiter, String hint, String... addressNames) {

        if (TextUtils.isEmpty(delimiter)) {
            delimiter = "-";
        }

        if (TextUtils.isEmpty(hint)) {
            hint = "未知地区";
        }

        if (addressNames == null || addressNames.length == 0) {
            return hint;
        }

        StringBuilder sb = new StringBuilder();
        for (String addressName : addressNames) {
            if (!TextUtils.isEmpty(addressName)) {
                sb.append(addressName)
                        .append(delimiter);
            }
        }

        String address = sb.toString();

        if (TextUtils.isEmpty(address))
            return hint;

        //检测拼接地址后面时候 有隔离符号
        if (!TextUtils.isEmpty(delimiter)) {
            if (address.endsWith(delimiter)) {
                return address.substring(0, address.length() - delimiter.length());
            }
        }
        return address;
    }


    /**
     * 设置textView 空的占位符
     *
     * @param value
     * @param placeHolder
     */
    public static String setEmptyPlaceholder(String value, String placeHolder) {
        return !TextUtils.isEmpty(value) ? value : placeHolder;
    }


    /**
     * 设置textView 空的占位符
     *
     * @param tv
     * @param value
     */
    public static void setTextViewPlaceholder(TextView tv, String value) {
        setTextViewPlaceholder(tv, value);
    }

    /**
     * 设置textView 空的占位符
     *
     * @param tv
     * @param value
     */
    public static void setTextViewPlaceholder(TextView tv, String value, String placeHolder) {
        tv.setText(TextUtils.isEmpty(value) ? placeHolder : value);
    }

    /**
     * editText光标移动到最后
     *
     * @param ed
     */
    public static void edCursorMoveEnd(EditText ed) {
        ed.setSelection(ed.getText().length());
    }


    /**
     * 展示格式化的  金钱  万元
     *
     * @param price
     * @return
     */
    public static String showFormatPrice(long price) {
        if (price <= 0)
            return "0元";

        if (price < 10000) {// 小于10000元
            return toStringBuilder(String.valueOf(price), "元");
        }

        BigDecimal format = new BigDecimal(10000);
        BigDecimal priceBigDecimal = new BigDecimal(price);
        String s1 = priceBigDecimal.divide(format, 2, BigDecimal.ROUND_HALF_UP).toString();
        if (Float.valueOf(s1) >= 10000.00) {// 大于1亿
            BigDecimal priceBigDecima2 = new BigDecimal(s1);
            float f2 = priceBigDecima2.divide(format, 2, BigDecimal.ROUND_HALF_UP).floatValue();
            return toStringBuilder(String.valueOf(f2), "亿");
        }
        return toStringBuilder(String.valueOf(s1), "万元");// 大于1万   小于1亿
    }


    /**
     * 展示  html格式
     *
     * @param text
     * @return
     */
    public static Spanned showHtmlText(String text) {
        return Html.fromHtml(text);
    }


    /**
     * 验证是否是手机格式
     * 移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
     * 联通：130、131、132、152、155、156、185、186 电信：133、153、180、189、（1349卫通）
     * 总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
     *
     * @param mobile
     * @return
     */
    public static boolean isMobileNO(String mobile) {
        String telRegex = "[1][3456789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        return TextUtils.isEmpty(mobile) ? false : mobile.matches(telRegex);
    }


    /**
     * 拨打电话
     *
     * @param context
     * @param phoneNumber
     */

    public static void callPhone(Context context, String phoneNumber) {
        try {
            Method getITelephony = TelephonyManager.class.getDeclaredMethod("getITelephony", (Class[]) null);
            getITelephony.setAccessible(true);
            TelephonyManager tManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            Object iTelephony = getITelephony.invoke(tManager, (Object[]) null);
            Method dial = iTelephony.getClass().getDeclaredMethod("dial", String.class);
            dial.invoke(iTelephony, phoneNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 匹配是否是int
     *
     * @param matcherStr
     * @return
     */
    public static boolean matcherInt(String matcherStr) {
        if (TextUtils.isEmpty(matcherStr)) {
            return false;
        }
        Pattern p = Pattern.compile("[0-9]*");
        Matcher matcher = p.matcher(matcherStr);
        return matcher.matches();
    }

    /**
     * 关闭键盘
     *
     * @param context
     */
    public static void closeKeyboard(Activity context) {
        Window window = context.getWindow();
        if (window.getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(window.getDecorView().getWindowToken(), 0);
        }
    }


    /**
     * 防止连续点击跳转两个页面
     */
    public static long lastClickTime;

    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 800) {
            return true;
        }
        lastClickTime = time;
        return false;
    }

    /**
     * 防止连续点击跳转两个页面
     */
    public static long lastClickTime2;

    public static boolean isFastDoubleClick2() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 800) {
            return true;
        }
        lastClickTime2 = time;
        return false;
    }


    /**
     * textView 文本为空就toast提示语句
     *
     * @param textView
     * @param warning
     * @return
     */
    public static boolean toastTextEmpty(TextView textView, CharSequence warning) {
        String text = getTextViewText(textView);
        return toastTextEmpty(text, warning);
    }


    /**
     * text 为空就toast提示语句
     *
     * @param text
     * @param warning
     * @return
     */
    public static boolean toastTextEmpty(CharSequence text, CharSequence warning) {
        boolean empty = TextUtils.isEmpty(text);
        if (empty) {
            ToastUtils.showShort(warning);
        }
        return empty;
    }


    /**
     * t 为空就toast提示语句
     *
     * @param t
     * @param warning
     * @param <T>
     * @return
     */
    public static <T> boolean toastNull(T t, CharSequence warning) {
        boolean b;
        if (b = isNull(t)) {
            ToastUtils.showShort(warning);
        }
        return b;
    }


    /**
     * 多参数检测是否为空
     *
     * @param ts
     * @param <T>
     */
    public static <T> boolean isNulls(T... ts) {
        for (T t : ts) {
            if (isNull(t))
                return true;
        }
        return false;
    }

    /**
     * 检查 对象时候为空
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> boolean isNull(T t) {
        return t == null;
    }


    /**
     * 检查 对象是异常
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> void checkNullException(T t) {
        if (t == null)
            throw new NullPointerException(t.getClass().getName() + " ==NULL  ");
    }

    /**
     * 检查 手机版本是否大于   23  6.0
     * 申请权限的时候使用
     *
     * @return
     */
    public static boolean isVersionM() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    /**
     * 检查 手机版本是否大于  24  7.0
     * 申请权限的时候使用
     *
     * @return
     */
    public static boolean isVersionN() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }



//    /**
//     * 显示 webView
//     *
//     * @param webView
//     * @param webContent
//     */
//    public static void showWebView(WebView webView, String webContent) {
//        if (webView == null || TextUtils.isEmpty(webContent)) {
//            return;
//        }
//
//        webView.getSettings().setDefaultTextEncodingName("utf-8");// 避免中文乱码
//        webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
//        WebSettings settings = webView.getSettings();
//        settings.setJavaScriptEnabled(true);
//        settings.setNeedInitialFocus(false);
//        settings.setSupportZoom(true);
//        settings.setLoadWithOverviewMode(true);//适应屏幕
//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        settings.setLoadsImagesAutomatically(true);//自动加载图片
//        settings.setCacheMode(WebSettings.LOAD_DEFAULT
//                | WebSettings.LOAD_CACHE_ELSE_NETWORK);
//
//        Document parse = Jsoup.parse(webContent);
//        Elements elementsByTag = parse.getElementsByTag("img");
//
//        if (elementsByTag.size() != 0) {
//            for (Element ele : elementsByTag) {
//                ele.attr("width", "100%");
//                ele.attr("height", "auto");
//            }
//        }
//
//        String newHtmlContent = parse.toString();
//        webView.loadDataWithBaseURL(null, newHtmlContent, "text/html", "utf-8", null);
//    }
}
