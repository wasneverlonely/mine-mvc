package com.was.core.common.base.tab;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

/**
 * 页面状态改变 观察者
 */
public class PageStateChangeObservable extends Observable {

    private Vector<Observer> obs; // 通知单个

    public PageStateChangeObservable() {
        obs = new Vector<>();
    }

    @Override
    public synchronized void addObserver(Observer o) {
        super.addObserver(o);
        if (!obs.contains(o)) {
            obs.addElement(o);
        }
    }

    @Override
    public void notifyObservers(Object arg) {
        setChanged(); //必须改变状态才能 通知
        super.notifyObservers(arg);
    }

    /**
     * 通知单个
     *
     * @param position
     * @param arg
     */
    public void notifyObserver(int position, Object arg) {
        setChanged();

        Object[] arrLocal;
        synchronized (this) {
            if (!hasChanged())
                return;
            arrLocal = obs.toArray();
            clearChanged();
        }
        ((Observer) arrLocal[position]).update(this, arg);
    }

    /**
     * @param position
     * @return
     */
    public Observer getObserver(int position) {
        if (obs != null || obs.isEmpty() || position > obs.size()) {
            return null;
        }
        return obs.get(position);
    }

    /**
     * 是否通知
     *
     * @param position
     * @return
     */
    public boolean isNotify(int position) {
        Observer observer = getObserver(position);
        if (observer != null && observer instanceof ResultObserver) {
            return ((ResultObserver) observer).isNotify();
        }
        return false;
    }
}
