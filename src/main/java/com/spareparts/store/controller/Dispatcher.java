package com.spareparts.store.controller;

import com.spareparts.store.controller.actions.Handler;

public interface Dispatcher {
    Handler dispatch(String path, String requestMethod);
}
