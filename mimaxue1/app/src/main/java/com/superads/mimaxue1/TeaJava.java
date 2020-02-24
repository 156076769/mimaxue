package com.superads.mimaxue1;

import com.superads.mimaxue1.ref1.edu.cedarville.cs.crypto.TinyE;
import com.superads.mimaxue1.ref1.edu.cedarville.cs.crypto.Tools;

public class TeaJava {
    private static TeaJava instance = new TeaJava();

    static TeaJava get() {
        return instance;
    }

    // String _key = "a56babcd00000000ffffffffabcdef01";
    // String _iv = "deadbeefcafefeed";
    public byte[] encrypt(String _key, String _iv, TinyE.Mode mode, byte[] _in) {
        Integer[] key = Tools.convertFromHexStringToInts(_key);
        Integer[] iv = null;
        if (!mode.equals(TinyE.Mode.ECB)) {
            iv = Tools.convertFromHexStringToInts(_iv);
            validateLength(iv, 2, "IV");
        }
        Integer[] input = Tools.convertFromBytesToInts(_in);
        TinyE cipher = new TinyE();
        Integer[] output = null;
        System.out.println("performing encryption");
        output = cipher.encrypt(input, key, mode, iv);
        validateLength(output, input.length, "output");
        return Tools.convertFromIntsToBytes(output);
    }

    public byte[] decrypt(String _key, String _iv, TinyE.Mode mode, byte[] _in) {
        Integer[] key = Tools.convertFromHexStringToInts(_key);
        Integer[] iv = null;
        if (!mode.equals(TinyE.Mode.ECB)) {
            iv = Tools.convertFromHexStringToInts(_iv);
            validateLength(iv, 2, "IV");
        }
        Integer[] input = Tools.convertFromBytesToInts(_in);
        TinyE cipher = new TinyE();
        Integer[] output = null;
        System.out.println("performing decryption");
        output = cipher.decrypt(input, key, mode, iv);
        validateLength(output, input.length, "output");
        return Tools.convertFromIntsToBytes(output);
    }

    private static void validateLength(Integer[] ints, int len, String id) {
        if (ints == null) {
            System.out.println(id + " is null");
            System.exit(1);
        } else if (ints.length != len) {
            System.out.println(id + " is wrong length: " + ints.length);
            System.exit(1);
        }
    }
}
