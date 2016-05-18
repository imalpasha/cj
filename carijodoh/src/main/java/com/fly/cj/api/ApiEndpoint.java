package com.fly.cj.api;

import retrofit.Endpoint;

public class ApiEndpoint implements Endpoint {

    @Override
    public String getUrl() {
        //return "https://api.github.com";
       //return "http://api.github.com";
        //return "http://sheetsu.com";

        //return "http://fyapidev.me-tech.com.my/api";
        //return "http://fyapistage.me-tech.com.my/api";
        //return "http://fyapi.me-tech.com.my/api";
       // return "http://192.168.0.111:44447";

        //-----------------CARI JODOH------------------//
        //return "http://192.168.0.156/fly/public/api/";
        return "http://192.168.0.121/fly/public/api/";

    }

    @Override
    public String getName() {
        return "Production Endpoint";
    }
    //
}

//http://sheetsu.com/apis/c4182617