package com.gymapp.controller.actions;

import com.gymapp.controller.Request;
import com.gymapp.controller.Response;

public class GetClientMembershipHandler implements Handler {
    @Override
    public void handle(Request request, Response response) {
        System.out.println("client membership method.");
    }
}
