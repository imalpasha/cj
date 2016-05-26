package com.fly.cj.api;

import retrofit.Endpoint;

public class ApiEndpoint implements Endpoint {

    @Override
    public String getUrl() {

        //-----------------CARI JODOH------------------//
        //return "http://192.168.0.156/fly/public/api/";
        //return "http://192.168.0.130/fly/public/api/";
        return "http://192.168.0.111/fly/public/api/";
    }

    @Override
    public String getName() {
        return "Production Endpoint";
    }
}

//http://sheetsu.com/apis/c4182617