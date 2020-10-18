package com.was.core.utils;

import android.util.Log;

/**
 * log 工具类
 */
public class L {

    private static boolean isDebug = true;// 是否需要打印bug，可以在application的onCreate函数里面初始化
    private static String TAG = "wasNeverLonely";

    public L() {
    }

    /**
     * 设置 log 是否输出
     *
     * @param isDebug
     */
    public static void setIsDebug(boolean isDebug) {
        L.isDebug = isDebug;
    }

    /**
     * 设置tag
     *
     * @param TAG
     */
    public static void setTAG(String TAG) {
        L.TAG = TAG;
    }

    public static String getTAG() {
        return TAG;
    }

    // 下面四个是默认tag的函数
    public static void i(String msg) {
        if (isDebug)
            Log.i(TAG, msg);
    }

    public static void d(String msg) {
        if (isDebug)
            Log.d(TAG, msg);
    }

    public static void e(String msg) {
        if (isDebug)
            Log.e(TAG, msg);
    }

    public static void v(String msg) {
        if (isDebug)
            Log.v(TAG, msg);
    }

    // 下面是传入自定义tag的函数
    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);
    }


//    public static void writeToFile(){
//
//
//    }
//
//
//    /**
//     * 将log信息写入文件中
//     *
//     * @param type
//     * @param tag
//     * @param msg
//     */
//    private static void writeToFile(char type, String tag, String msg) {
//
//        if (null == logPath) {
//            Log.e(TAG, "logPath == null ，未初始化LogToFile");
//            return;
//        }
//
//        String fileName = logPath + "/log_" + dateFormat.format(new Date()) + ".log";//log日志名，使用时间命名，保证不重复
//        String log = dateFormat.format(date) + " " + type + " " + tag + " " + msg + "\n";//log日志内容，可以自行定制
//
//        //如果父路径不存在
//        File file = new File(logPath);
//        if (!file.exists()) {
//            file.mkdirs();//创建父路径
//        }
//
//        FileOutputStream fos = null;//FileOutputStream会自动调用底层的close()方法，不用关闭
//        BufferedWriter bw = null;
//        try {
//
//            fos = new FileOutputStream(fileName, true);//这里的第二个参数代表追加还是覆盖，true为追加，flase为覆盖
//            bw = new BufferedWriter(new OutputStreamWriter(fos));
//            bw.write(log);
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if (bw != null) {
//                    bw.close();//关闭缓冲流
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }

}