import java.math.BigInteger;

public class Main3 {
    public static void main(String[] args) throws Exception {
        String s1 = "dd:99:6a:3f:26:c2:fd:1d:80:92:f6:15:9e:90:\n" +
                "    61:05:9d:42:fe:b2:5c:3d:9f:ce:19:5d:c5:e3:0a:\n" +
                "    bd:88:12:55:e7:55:d6:fc:96:b0:3a:05:51:7c:85:\n" +
                "    e4:bb:c7:2f:3b:a5:6d:75:60:d7:6b:8e:7c:cf:3a:\n" +
                "    a0:a2:fc:84:f5:2b:c2:b2:3c:72:c0:0f:60:30:29:\n" +
                "    7e:f9:af:02:86:f2:bb:38:34:fc:88:0b:c5:06:31:\n" +
                "    ae:13:52:19:f9:cf:22:90:63:84:1e:6a:17:db:3e:\n" +
                "    c3:fd:dc:2f:98:76:8a:04:f9:32:a2:d0:79:f9:eb:\n" +
                "    a4:cb:b1:a6:02:6b:b7:1a:55";
        BigInteger p = new BigInteger(Utils.myTrim(s1), 16);

        String s2 = "d4:ae:75:28:f2:1e:73:0e:1b:cf:f8:71:d2:6b:\n" +
                "    bb:88:61:fe:33:76:d2:f4:73:77:1a:a3:96:a3:4c:\n" +
                "    34:2f:b5:34:e0:de:0f:40:d2:83:f0:7e:50:bd:1b:\n" +
                "    dc:cb:82:27:1f:2d:cf:57:3f:e0:c6:48:00:a8:27:\n" +
                "    ee:a5:e5:8f:59:91:cb:d8:f0:8c:17:fe:ff:22:9e:\n" +
                "    b8:b9:b3:3f:48:a9:87:a2:df:64:64:d9:cd:1d:3c:\n" +
                "    ae:16:70:50:eb:5e:94:b1:89:fb:f2:5e:08:a5:2c:\n" +
                "    c5:0c:26:1e:fb:80:83:34:d2:39:2b:ff:08:ff:bc:\n" +
                "    71:0d:60:77:f7:53:a5:b9:a7";

        BigInteger q = new BigInteger(Utils.myTrim(s2), 16);

        BigInteger n = p.multiply(q);
        System.out.println(n.toString(16));
    }


}
