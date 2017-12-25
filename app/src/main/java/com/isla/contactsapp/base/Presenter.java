package com.isla.contactsapp.base;

public interface Presenter<V> {

    void onViewAttached(V view);

    void onViewDetached();

    void onDestroyed();
}
