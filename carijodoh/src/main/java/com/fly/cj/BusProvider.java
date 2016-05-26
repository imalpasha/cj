package com.fly.cj;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public final class BusProvider {

    private static final Bus mbus = new Bus(ThreadEnforcer.ANY);

    public static Bus getInstance(){
        return mbus;
    }

    private BusProvider(){

    }
}
