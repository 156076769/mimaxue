package com.superads.mimaxue2;

public class NativeEntry {

    private static NativeEntry instance = new NativeEntry();

    private NativeEntry() {

    }

    public static NativeEntry get() {return instance;}

    public native String getAESEncrypt(String str);

    public native String getAESDecrypt(String str);
}
