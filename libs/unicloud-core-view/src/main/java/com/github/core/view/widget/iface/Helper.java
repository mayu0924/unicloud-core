package com.github.core.view.widget.iface;

import com.github.core.view.widget.helper.BaseHelper;

public interface Helper<T extends BaseHelper> {
    T getHelper();
}