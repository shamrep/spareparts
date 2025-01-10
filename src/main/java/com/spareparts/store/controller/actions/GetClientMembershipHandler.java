package com.spareparts.store.controller.actions;

import com.spareparts.store.controller.Request;
import com.spareparts.store.controller.Response;

public class GetClientMembershipHandler implements Handler {
    @Override
    public void handle(Request request, Response response) {
        System.out.println("client membership method.");
    }
}
