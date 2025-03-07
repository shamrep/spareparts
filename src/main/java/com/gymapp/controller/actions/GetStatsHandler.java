package com.gymapp.controller.actions;

import com.gymapp.controller.Request;
import com.gymapp.controller.Response;

public class GetStatsHandler implements Handler {

    @Override
    public void handle(Request request, Response response) {

        response.setStatusCode(Response.SC_OK).message("STATISTICS").build();

    }

}
