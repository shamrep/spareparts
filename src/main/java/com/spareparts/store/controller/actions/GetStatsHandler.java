package com.spareparts.store.controller.actions;

import com.spareparts.store.controller.Request;
import com.spareparts.store.controller.Response;

public class GetStatsHandler implements Handler {

    @Override
    public void handle(Request request, Response response) {

        response.setStatusCode(Response.SC_OK).message("STATISTICS").build();

    }

}
