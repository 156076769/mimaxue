import sun.misc.BASE64Decoder;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class Main2 {
    public static void main(String[] args) throws Exception {
        PrivateKey key = readPrivateKey(new File("D:\\work\\projects\\mimaxue\\mykeygen\\private_unencrypted.pkcs8"));
        key.getEncoded();
        Cipher decrypt= Cipher.getInstance("RSA");
        decrypt.init(Cipher.DECRYPT_MODE, key);
        byte[] encryptedMessage = Base64.getDecoder().decode("AI3pSC2J9vQuFcfljfQRk5Q1ebNoSbOePBgaPkbFkDD3E55qKhL47wK3OYCdxb6Am+WUsisAToDaJgHzOToU2dlbIwJPRkluPC+vlk5CB+BEb5RxSVMUFHYhhL77NvPSMl2SZLbYrFP1dMF230iv7xvYFQEalefi6bhxl1zmhTczysa3s7GSGrzOIoPLhb2JU4xmuYvuYxcWlsQ4YRSpO0eNk9mTFDz4xIGPA9Omo3Tmi0ZluWsK+bJNy/Ma1H+bE0JAB2PgUgVuatwyFfslVtPjKjAuLpZFGCORNO6gMTrU+PPb3eyW05vPio/N1H6wtlFS/SXTHTa/q9BdSkG9OrE=OduzTpFzG+P4qnQviTBkGUL9QIXSGubXFcmwH88HUtsSN+/qerv9s/Y54Axz4SutLD9A+wVGmDke+qnMQHwVqzz+4E2lWuJgJdXqyzJsYdve3ksYq+Jh0M0OYIO+wmCYJZSeuhc7zXH7JmculWr15x5Oc7RnSjjc81E95n+wz+TmP+gHy5pigXU2M5kqMWtOxVgUKcMhuVcibXgYfGIJ87OYq3lgw87X7hj06MlNfthmf/zPbTKsunjSoIiyJXs/CRj8pmKk7vKi9XaaO+W1jVEENyfxsC5p0sbSD8L0+iN8RaIh0cZrIfYkLcNN+pwp7Lz7Pr3f9xzs1D27bOaxkQ==");
        byte[] decryptedMessage=decrypt.doFinal(encryptedMessage);
        System.out.println(new String(decryptedMessage));
    }

    public static PrivateKey readPrivateKey(File keyFile) throws Exception {
        // read key bytes
        FileInputStream in = new FileInputStream(keyFile);
        byte[] keyBytes = new byte[in.available()];
        in.read(keyBytes);
        in.close();

        String privateKeyContent = new String(keyBytes, "UTF-8");
        privateKeyContent = privateKeyContent.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");

        KeyFactory kf = null;
        try {
            kf = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
            PrivateKey privKey = kf.generatePrivate(keySpecPKCS8);
            return privKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
}
