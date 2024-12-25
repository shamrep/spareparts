package com.spareparts.store.controller;

import com.spareparts.store.controller.actions.Handler;


public interface Dispatcher {

    void dispatch(Request request, Response response);
}
