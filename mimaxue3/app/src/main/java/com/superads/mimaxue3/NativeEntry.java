package com.superads.mimaxue3;

public class NativeEntry {

    private static NativeEntry instance = new NativeEntry();

    private NativeEntry() {

    }

    public static NativeEntry get() {return instance;}

    public native byte[] aes1(String key, byte[] plain);

    public native void aes2();

}
