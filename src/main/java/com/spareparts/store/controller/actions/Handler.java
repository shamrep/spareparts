package com.spareparts.store.controller.actions;


import com.spareparts.store.controller.Request;
import com.spareparts.store.controller.Response;

public interface Handler {
    void handle(Request request, Response response);
}
