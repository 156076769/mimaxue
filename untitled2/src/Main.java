//public class Main {

//    public static void main(String[] args) {
//        System.out.println("Hello World!");
//        new Main().findPrimeLessParam(100000000);
//    }
//
//    public void findPrimeLessParam(int param) {
//        for (int i = 10000000; i < param; i++) {
//            if (isPrime(i)) {
//                System.out.println(i);
//            }
//        }
//    }
//
//    private boolean isPrime(int n) {
//        if (n < 2)
//            return false;
//        for (int i = 2; i <= Math.sqrt(n); i++)
//            if (n % i == 0)
//                return false;
//        return true;
//    }

//}

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Base64;
import java.util.List;

/**
 * @author Rafael M. Pestano
 */
public class Main {

    public static BigInteger new_n;
    public static BigInteger new_e;
    public static BigInteger new_d;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String a1 = "    00:b8:1a:0f:ad:12:22:aa:bb:eb:e9:60:5a:66:be:\n" +
                "    d7:f1:cb:c6:ff:a3:62:df:4b:5d:48:18:8b:60:d3:\n" +
                "    0d:24:ee:ab:57:98:5e:72:d7:f9:1f:ab:df:28:38:\n" +
                "    99:d7:64:af:b1:0b:b5:49:eb:32:c5:f9:25:29:1c:\n" +
                "    d7:56:2d:80:05:af:22:a6:76:76:b0:b7:cc:9b:04:\n" +
                "    b4:83:d6:54:88:a7:98:0c:84:d1:39:df:a3:2a:90:\n" +
                "    40:99:b0:4e:7b:d7:4c:c4:16:7c:37:4f:4f:75:44:\n" +
                "    96:a8:f1:8e:ec:9c:d1:ee:c3:aa:21:fc:7d:51:75:\n" +
                "    32:4c:12:b6:76:c3:32:42:b0:04:23:bd:01:7b:f7:\n" +
                "    ff:a5:f6:66:01:b0:7c:fa:22:19:fc:e1:93:ef:b3:\n" +
                "    a5:9c:3b:cf:6c:a2:d7:95:66:a6:ef:b5:e1:9e:43:\n" +
                "    d5:69:bf:b1:73:4f:83:6c:5b:c5:a1:3d:56:0b:dd:\n" +
                "    c1:d8:8e:13:25:bb:19:38:e4:f5:f0:d2:fe:ac:8e:\n" +
                "    aa:18:a1:8d:1f:56:90:14:67:b2:fb:86:d9:3a:8c:\n" +
                "    d8:55:38:bc:50:15:50:cd:37:aa:a1:2e:ba:c2:0c:\n" +
                "    b7:42:78:fe:b2:96:d1:b1:fb:ca:0a:38:53:c5:60:\n" +
                "    e5:bb:43:76:69:df:5b:ba:f4:46:1e:c0:fc:1e:42:\n" +
                "    9a:73";
        a1 = a1.replace("00:", "");
        new_n = new BigInteger(Utils.myTrim(a1), 16);
        System.out.println(new_n.toString(2));
        new_e = new BigInteger("65537", 10);
        System.out.println(new_e.toString(16));

        String a2 = "28:f1:6a:d1:13:ba:6f:fc:11:10:3a:e4:7f:fb:2b:\n" +
                "    6d:53:e3:72:d0:f4:59:32:9a:91:41:1c:26:31:69:\n" +
                "    b7:ef:f8:5d:27:be:c1:8d:b4:92:cd:97:78:8b:75:\n" +
                "    f3:48:2a:26:96:b2:ff:b8:75:f7:3f:5c:7b:53:35:\n" +
                "    b4:ad:b3:ce:0f:d0:05:f3:4c:9c:2a:94:2f:59:91:\n" +
                "    87:cc:6f:ca:60:73:59:3d:64:86:99:6c:e1:37:69:\n" +
                "    96:84:76:3f:e6:76:e5:19:17:10:f9:eb:72:ea:09:\n" +
                "    13:93:7d:34:b3:ff:a0:39:15:aa:2f:4b:f3:84:bb:\n" +
                "    e5:dd:37:85:1c:0e:1e:74:44:32:40:8b:2f:55:42:\n" +
                "    1d:74:cd:40:42:9d:af:25:ae:a7:7e:36:a0:7e:ae:\n" +
                "    6f:24:61:16:2f:72:3c:10:a7:6b:68:82:63:7c:63:\n" +
                "    24:a3:e3:23:84:87:67:ae:7b:f4:f2:a2:a6:4a:6d:\n" +
                "    9e:b8:4b:3e:63:37:8d:c2:4a:15:80:65:02:23:25:\n" +
                "    bd:43:d7:3e:e9:ad:51:37:4f:81:d7:af:b6:eb:3f:\n" +
                "    8e:5f:30:7a:c0:a7:a3:73:ef:aa:1b:25:71:b2:bb:\n" +
                "    c0:65:08:0c:5f:b9:dd:90:01:93:00:f2:2e:41:bf:\n" +
                "    78:3c:9e:c2:9e:8f:15:21:30:61:62:13:51:e5:8a:\n" +
                "    09";
        new_d = new BigInteger(Utils.myTrim(a2), 16);
        System.out.println(new_d.toString(16));

        PrimeExample.test(17);
        //BigInteger x = new BigInteger("57099d7574efefb20de7bf3066ebd8a7", 16); //mintegral
        BigInteger x = new BigInteger("35894562752016259689151502540913447503526083241413", 16); //mintegral
        System.out.println(x.toString(2));
        System.out.println(x.isProbablePrime(10000));
        BigInteger p;
        BigInteger q;
        BigInteger e;
        final String message;
        boolean isFile = false;
        if (args.length != 4) {//at leat four parametter should be given
            p = new BigInteger("5700734181645378434561188374130529072194886062117");
            System.out.println(p.toString(2));
            BigInteger my1 = new BigInteger("C019875F29313037411501A0005952497A02A15E780D82602381C2274AD7FB7B", 16);
            System.out.println(my1.toString(2));
            q = new BigInteger("35894562752016259689151502540913447503526083241413");
            e = new BigInteger("33445843524692047286771520482406772494816708076993");
            //message = "This is a test";
//            isFile = true;
//            message = "D:\\work\\projects\\mimaxue\\my_json_request.txt";
            isFile = false;
            //message = "a56babcd00000000ffffffffabcdef01";
            message = "testkey123456789";

            //below are also valid primes
//          p = new BigInteger("101");
//          q = new BigInteger("113");
//          e = new BigInteger("3533");
        } else {
            p = new BigInteger(args[0]);
            q = new BigInteger(args[1]);
            e = new BigInteger(args[2]);
            if (args[3].contains("-f")) {
                isFile = true;
                message = args[3].substring(2);
            } else {
                message = args[3];
            }
        }


        long t1 = System.currentTimeMillis();
        System.out.println("t1: " + t1);
        RSA RSA = new RSAImpl(p, q, e);
        System.out.println(RSA);

        List<BigInteger> encryption;
        List<BigInteger> signed;
        List<BigInteger> decimalMessage;
        if (isFile) {
            encryption = RSA.encryptFile(message);
            //signed = RSA.signFile(message);
            //decimalMessage = RSA.fileToDecimal(message);
        } else {
            encryption = RSA.encryptMessage(message);
            //signed = RSA.signMessage(message);
            //decimalMessage = RSA.messageToDecimal(message);
        }

        new File("D:\\work\\projects\\mimaxue\\untitled2\\my_json_request_enc.txt").delete();
        try (FileOutputStream fos = new FileOutputStream("D:\\work\\projects\\mimaxue\\untitled2\\my_json_request_enc.txt")) {
            for(BigInteger e1 : encryption) {
                fos.write((Base64.getEncoder().encodeToString(e1.toByteArray()) + ",").getBytes());
            }
            //fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        long t2 = System.currentTimeMillis();
        System.out.println("t2-t1: " + (t2-t1));

        List<BigInteger> decrypt = RSA.decrypt(encryption);

        new File("D:\\work\\projects\\mimaxue\\untitled2\\my_json_request_dec.txt").delete();
        try (FileOutputStream fos = new FileOutputStream("D:\\work\\projects\\mimaxue\\untitled2\\my_json_request_dec.txt")) {
            for(BigInteger e1 : decrypt) {
                fos.write(e1.toByteArray());
            }
            //fos.close(); There is no more need for this line since you had created the instance of "fos" inside the try. And this will automatically close the OutputStream
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        long t3 = System.currentTimeMillis();
        System.out.println("t3-t2: " + (t3-t2));
//
//        //List<BigInteger> verify = RSA.verify(signed);
//        System.out.println();
        //System.out.println("message(plain text)   = " + Utils.bigIntegerToString(decimalMessage));
        //System.out.println("message(decimal)      = " + Utils.bigIntegerSum(decimalMessage));
        //System.out.println("encripted(decimal)    = " + Utils.bigIntegerSum(encryption));
        //System.out.println("decrypted(plain text) = " + Utils.bigIntegerToString(decrypt));
        //System.out.println("decrypted(decimal)    = " + Utils.bigIntegerSum(decrypt));
        //System.out.println("signed(decimal)       = " + Utils.bigIntegerSum(signed));
        //System.out.println("verified(plain text)  = " + Utils.bigIntegerToString(verify));
        //System.out.println("verified(decimal)     = " + Utils.bigIntegerSum(verify));
    }
}

/*
t1: 1565170509072
#p, A+aNwwEovdFpz8AyQ3V3tlkPQFAl
#q, GI9f3HaT+kD2MDaEzD1tO6pnESXF
#n, X8yJYpUvHDSwBVpSaEAKqKNg4tyMIosfo7zpzbW+XMXTh3XU0z2IBXk=
#phi, X8yJYpUvHDSwBVpSaEAKqKNg4txvrJ2ALAAxu1W+Zg7D1JDiz8c2j5A=
#e, FuJzflBnerMdsRkOHX7hUVFGjyXB
#d, VcP2NUKk84Mxa5sw0tgk4d5cPQ4aYdRgWNBXyg9HjTYrvMN2J09ZGpE=

p                     = 5700734181645378434561188374130529072194886062117
q                     = 35894562752016259689151502540913447503526083241413
e                     = 33445843524692047286771520482406772494816708076993
private               = 183193943982723541083656360380592796925228591717543963697284925059702232695599866544519541421578897
modulus               = 204625360815634094995873000754145818613880478081621272344332984978247528769851193693851726624851321
t2-t1: 3760
t3-t2: 6558


Process finished with exit code 0
 */
