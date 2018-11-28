import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class DESDemo {
    // DES加密算法,key的大小必须是8个字节
    public static void main(String[] args) throws Exception {
        String input = "frank";
        String key = "12345678";
        // 指定获取Cipher的算法,如果没有指定分组密码模式和填充模式,ECB/PKCS5Padding就是默认值
        // CBC模式,必须指定初始向量,初始向量中密钥的长度必须是8个字节
        // NoPadding模式,原文的长度必须是8个字节的整倍数
//        String transformation = "DES";
        String transformation = "DES/CBC/PKCS5Padding";
        // 指定获取密钥的算法
        String algorithm = "DES";
        String encryptDES = DESEncrypt(input, key, transformation, algorithm);
        System.out.println("加密:" + encryptDES);
        String s = DESDecrypt(encryptDES, key, transformation, algorithm);
        System.out.println("解密:" + s);
    }

    /**
     *
     * @param input  明文
     * @param key   密钥(DES,密钥的长度必须是8个字节)
     * @param transformation  获取Cipher对象的算法
     * @param algorithm   获取密钥的算法
     * @return  返回密文
     * @throws Exception
     */
    private static String DESEncrypt(String input, String key, String transformation, String algorithm) throws Exception {
        // 1,获取Cipher对象
        Cipher cipher = Cipher.getInstance(transformation);
        // 指定密钥规则
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), algorithm);
        // 2.初始化向量的秘钥长度需要根据算法而定,des 8个字节长度  aes 16个字节长度
        //这里为了方便,统一使用传来的秘钥
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, sks, iv);
//        cipher.init(Cipher.ENCRYPT_MODE, sks);
        // 3. 加密
        byte[] bytes = cipher.doFinal(input.getBytes());
        // 对数据进行Base64编码
        String encode = Base64.encode(bytes);
        return encode;
    }


    private static String DESDecrypt(String input, String key, String transformation, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), algorithm);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, sks, iv);
//         cipher.init(Cipher.DECRYPT_MODE, sks);
        byte[] bytes = cipher.doFinal(Base64.decode(input));
        return new String(bytes);
    }
}
