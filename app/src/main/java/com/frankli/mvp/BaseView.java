package com.frankli.mvp;

/**
 * Created by Justy on 2017/11/15.
 */

public interface BaseView<T> {
    void setPresenter(T presenter);
}
