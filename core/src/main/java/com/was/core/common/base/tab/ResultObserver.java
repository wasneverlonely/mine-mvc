package com.was.core.common.base.tab;

import java.util.Observer;

/**
 * 返回 是否通知observer
 */
public interface ResultObserver extends Observer {

    // 是否通知
    boolean isNotify();
}
