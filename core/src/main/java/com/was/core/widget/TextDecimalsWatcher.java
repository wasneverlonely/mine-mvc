package com.was.core.widget;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.was.core.utils.L;
import com.was.core.utils.ToastUtils;

/**
 * 输入editText 监听
 */
public class TextDecimalsWatcher implements TextWatcher {

    private String maxHint = "小数点后面最多可以输入%d位";

    private EditText editText;
    private int decimals = 2; //小数点位数


    public TextDecimalsWatcher(EditText editText) {
        this.editText = editText;
    }

    public TextDecimalsWatcher(EditText editText, int decimals) {
        this.editText = editText;
        this.decimals = decimals;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        L.e("  s  " + s + "   start  " + start + "   count  " + count + "   after  " + after);
    }

    boolean isReduce = false;

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        L.e("  s  " + s + "   start  " + start + "   before  " + before + "   count  " + count);
        isReduce = count == 0;
    }

    @Override
    public void afterTextChanged(Editable editable) {
        decimalsWatcher(editable);
        L.e("     toString  " + editable.toString());
    }


    /**
     * 小数监听
     *
     * @param editable
     */
    public void decimalsWatcher(Editable editable) {
        if (isReduce) {
            return;
        }
        String temp = editable.toString();

        // 获取. 的位置
        int posDot = temp.indexOf(".");
        if (posDot == 0 && temp.length() == 1) {
            editable.insert(0, "0");
            return;
        }

        int posZero = temp.indexOf("0");
        if (posZero == 0 && temp.length() == 1) {
            editable.insert(1, ".");
            return;
        }

        int index = editText.getSelectionStart();//获取光标位置
        if (posDot != -1 && temp.length() - posDot - 1 > decimals) {//如果包含小数点
            ToastUtils.showShort(String.format(maxHint, decimals));
            editable.delete(index - 1, index);//删除光标前的字符
            return;
        }
    }
}
