package bupin.com.tmpdemo;





import android.util.Base64;

import org.apache.commons.io.FileUtils;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.nio.charset.Charset;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class RSADemo {
    private static int MAX_ENCRIPT_SIZE = 245;

//    public static void main(String[] args) throws Exception {
//        String algorithm = "RSA";
//        String input = "frank";
////        generateKeys(algorithm, "test.pri", "test.pub");
//        PrivateKey privateKey = getPrivateKey("test.pri", algorithm);
//        PublicKey publicKey = getPublicKey("test.pub", algorithm);
//
//        String encryptData = RSAEncrypt(algorithm, privateKey, input, MAX_ENCRIPT_SIZE);
//        System.out.println("encryptData=" + encryptData);
//        String decryptData = RSADecrypt(algorithm, publicKey, encryptData, 256);
//        System.out.println("decryptData=" + decryptData);
//
//    }

    public static PrivateKey getPrivateKey(String priPath, String algorithm) throws Exception {
        String privateKeyString = FileUtils.readFileToString(new File(priPath), Charset.defaultCharset());
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString,Base64.DEFAULT));
        return keyFactory.generatePrivate(spec);
    }

    public static PublicKey getPublicKey(String pubPath, String algorithm) throws Exception {
        String publicKeyString = FileUtils.readFileToString(new File(pubPath), Charset.defaultCharset());
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decode(publicKeyString,Base64.DEFAULT));
        return keyFactory.generatePublic(spec);
    }

    public static void generateKeys(String algorithm, String priPath, String pubPath) throws Exception {
        // 获取密钥对生成器
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
        // 获取密钥对
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 获取公私钥
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();
        // 获取公私钥的字节数组
        byte[] privateKeyEncoded = privateKey.getEncoded();
        byte[] publicKeyEncoded = publicKey.getEncoded();
        // 对公私钥进行Base64的编码
        String privateKeyString = new String(Base64.encode(privateKeyEncoded,Base64.DEFAULT));
        String publicKeyString = new String(Base64.encode(publicKeyEncoded,Base64.DEFAULT));

        FileUtils.writeStringToFile(new File(priPath), privateKeyString, Charset.defaultCharset());
        FileUtils.writeStringToFile(new File(pubPath), publicKeyString, Charset.defaultCharset());
    }

    public static String RSAEncrypt(String algorithm, Key key, String input, int maxEncryptSize) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] data = input.getBytes();
        int total = data.length;
        int offset = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes;
        while (total - offset > 0) {
            if (total - offset >= maxEncryptSize) {
                bytes = cipher.doFinal(data, offset, maxEncryptSize);
                offset += maxEncryptSize;
            } else {
                bytes = cipher.doFinal(data, offset, total - offset);
                offset = total;
            }
            baos.write(bytes);
        }
        return new String( Base64.encode(baos.toByteArray(),Base64.DEFAULT));
    }

    public static String RSADecrypt(String algorithm, Key key, String input, int maxEncryptSize) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] data = Base64.decode(input,Base64.DEFAULT);
        int total = data.length;
        int offset = 0;
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] bytes;
        while (total - offset > 0) {
            if (total - offset >= maxEncryptSize) {
                bytes = cipher.doFinal(data, offset, maxEncryptSize);
                offset += maxEncryptSize;
            } else {
                bytes = cipher.doFinal(data, offset, total - offset);
                offset = total;
            }
            baos.write(bytes);
        }
        return baos.toString();
    }

}
