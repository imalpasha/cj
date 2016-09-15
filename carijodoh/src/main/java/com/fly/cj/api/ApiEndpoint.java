package com.fly.cj.api;

import retrofit.Endpoint;

public class ApiEndpoint implements Endpoint {

    @Override
    public String getUrl() {

        //-----------------CARI JODOH------------------//
        //return "http://192.168.0.156/fly/public/api/";
        //return "http://192.168.0.106/fly/public/api/";
        //return "http://192.168.0.123/fly/public/api/";
        //return "http://192.168.0.117/fly/public/api/";
        //return "http://192.168.0.148:8012/fly/public/api";
        return "http://carijodoh.me-tech.com.my/api";

    }

    @Override
    public String getName() {
        return "Production Endpoint";
    }
}

//http://sheetsu.com/apis/c4182617