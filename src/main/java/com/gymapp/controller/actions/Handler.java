package com.gymapp.controller.actions;


import com.gymapp.controller.Request;
import com.gymapp.controller.Response;

public interface Handler {
    void handle(Request request, Response response);
}
