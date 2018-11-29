# Java密码学相关知识梳理
## ASCII编码
* ASCII（American Standard Code for Information Interchange，美国信息交换标准代码）是基于拉丁字母的一套电脑编码系统，主要用于显示现代英语和其他西欧语言。。
* ![Logo](imgs/ascii.jpg)
* 实例代码:
```$java
public static void main(String[] args) {
        char a = 'A';
        int code = a;
        System.out.println(code);

        String s = "frank";
        char[] chars = s.toCharArray();
        for (char c : chars) {
            int num = c;
            System.out.println(num);
        }
}
```
## 凯撒加密
* 恺撒密码（Caesar cipher）是一种相传尤利乌斯·恺撒曾使用过的密码。恺撒于公元前100年左右诞生于古罗马，是一位著名的军事统帅。
* 恺撤密码是通过将明文中所使用的字母表按照一定的字数“平移”来进行加密的
* ![Logo](imgs/i002.jpg)
* 凯撒密码加解密公式
  
    - 加密
  
         ![](imgs/513169b7dcabfc4de6d4fcbc03e613434244e917.svg)
  
    - 解密
    
        ![](imgs/110911f42b858bdf1bec629ae41b5b88b00859e2.svg)
      
  
  - 凯撒密码中的加密三要素
    - 明文/密文
      - 明文: 图表上部分的数据
      - 密文: 图表下部分的数据
    - 秘钥
      - 按照上图秘钥为3
    - 算法
      - 加密: +3
      - 解密: -3
* 示例代码如下:
```$java
public static void main(String[] args) {
        String s = "frank";
        int key = 3;
        String encriptData = KaiserEncrypt(s, key);
        System.out.println("encriptData="+encriptData);
        String decriptData = KaiserDecrypt(encriptData, key);
        System.out.println("decriptData="+decriptData);
        /**
         * result as below:
         * encriptData=iudqn
         * decriptData=frank
         */
    }

    /**
     *
     * @param orignal:原文
     * @param key:秘钥
     * @return:返回值
     */
    private static String KaiserEncrypt(String orignal, int key) {
        char[] chars = orignal.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char aChar : chars) {
            // 获取字符的ASCII编码
            int asciiCode = aChar;
            // 偏移数据
            asciiCode += key;
            // 将偏移后的数据转为字符
            char result = (char) asciiCode;
            // 拼接数据
            sb.append(result);
        }
        return sb.toString();
    }

    /**
     *
     * @param encryptedData 密文
     * @param key 秘钥
     * @return
     */
    private static String KaiserDecrypt(String encryptedData, int key) {
        char[] chars = encryptedData.toCharArray();
        StringBuilder sb = new StringBuilder();
        for (char aChar : chars) {
            int asciiCode = aChar;
            asciiCode -= key;
            char result = (char) asciiCode;
            sb.append(result);
        }
        return sb.toString();
    }
```
## 频度分析法破解恺撒加密
- 将明文字母的出现频率与密文字母的频率相比较的过程
- 通过分析每个符号出现的频率而轻易地破译代换式密码
- 在每种语言中，冗长的文章中的字母表现出一种可对之进行分辨的频率。
- e是英语中最常用的字母，其出现频率为八分之一
- 由于破解太简单,在此不贴代码了,有兴趣的自己试试

## 对称加密
> 以分组为单位进行处理的密码算法称为**分组密码（blockcipher）**
![Logo](imgs/i003.png)
- 对称加密采用单钥密码系统的加密方法，同一个密钥可以同时用作信息的加密和解密，这种加密方法称为对称加密，也称为单密钥加密。
- 示例
  - 我们现在有一个原文3要发送给B
  - 设置密钥为108, 3 * 108 = 324, 将324作为密文发送给B
  - B拿到密文324后, 使用324/108 = 3 得到原文
- 常见对称加密算法
  - DES : Data Encryption Standard，即数据加密标准，是一种使用密钥加密的块算法，1977年被美国联邦政府的国家标准局确定为联邦资料处理标准（FIPS），并授权在非密级政府通信中使用，随后该算法在国际上广泛流传开来。
  - AES : Advanced Encryption Standard, 高级加密标准 .在密码学中又称Rijndael加密法，是美国联邦政府采用的一种区块加密标准。这个标准用来替代原先的DES，已经被多方分析且广为全世界所使用。
- 特点
  - 加密速度快, 可以加密大文件
  - 密文可逆, 一旦密钥文件泄漏, 就会导致数据暴露
  - 加密后编码表找不到对应字符, 出现乱码
  - 一般结合Base64使用 

- 分组密码的模式
    1. 按位异或
    
       - 第一步需要将数据转换为二进制
    
       - 按位异或操作符: ^
    
       - 两个标志位进行按位异或操作:
    
         - 相同为0, 不同为1
    
       - 举例:
    
         ```go
         1 0 0 0   ----> 8
         1 0 1 1   ----> 11
         -----------------------按位异或一次
         0 0 1 1   ---->  3
         1 0 1 1   ----> 11
         -----------------------按位异或两侧
         1 0 0 0   -----> 8
         =================================
         a = 8
         b = 11
         a 和 b按位异或1次 ==> 加密
         得到的结果再次和 b 按位异或 ===> 解密
         ```
    
    2. ECB - Electronic Code Book, 电子密码本模式
    
       - 特点: 简单, 效率高, 密文有规律, 容易被破解
       - 最后一个明文分组必须要填充
         - des/3des -> 最后一个分组填充满8字节
         - aes -> 最后一个分组填充满16字节
       - 不需要初始化向量
    
    3. CBC - Cipher Block Chaining, 密码块链模式
    
       - 特点: 密文没有规律, 经常使用的加密方式
       - 最后一个明文分组需要填充
         - des/3des -> 最后一个分组填充满8字节
         - aes -> 最后一个分组填充满16字节
       - 需要一个初始化向量 - 一个数组
         - 数组的长度: 与明文分组相等
         - 数据来源: 负责加密的人的提供的
         - 加解密使用的初始化向量值必须相同
    
    4. CFB - Cipher FeedBack, 密文反馈模式
    
       - 特点: 密文没有规律,  明文分组是和一个数据流进行的按位异或操作, 最终生成了密文
       - 需要一个初始化向量 - 一个数组
         - 数组的长度: 与明文分组相等
         - 数据来源: 负责加密的人的提供的
         - 加解密使用的初始化向量值必须相同
       - 不需要填充
    
    5. OFB - Output-Feedback, 输出反馈模式
    
       - 特点: 密文没有规律,  明文分组是和一个数据流进行的按位异或操作, 最终生成了密文
       - 需要一个初始化向量 - 一个数组
         - 数组的长度: 与明文分组相等
         - 数据来源: 负责加密的人的提供的
         - 加解密使用的初始化向量值必须相同
       - 不需要填充
    
    6. CTR - CounTeR, 计数器模式
    
       - 特点: 密文没有规律,  明文分组是和一个数据流进行的按位异或操作, 最终生成了密文
       - 不需要初始化向量
       - 不需要填充
    
    7. 最后一个明文分组的填充
    
       - 使用cbc, ecb需要填充
         - 要求: 
           - 明文分组中进行了填充, 然后加密
           - 解密密文得到明文, 需要把填充的字节删除
       - 使用 ofb, cfb, ctr不需要填充
    
    8. 初始化向量 - IV
    
       - ecb, ctr模式不需要初始化向量
       - cbc, ofc, cfb需要初始化向量
         - 初始化向量的长度
           - des/3des -> 8字节
           - aes -> 16字节
         - 加解密使用的初始化向量相同
         
 - DES加密示例代码如下:
    - DES加密,key的大小必须是8个字节
    - 如果没有指定分组密码模式和填充模式,ECB/PKCS5Padding就是默认值
    - 如果指定分组密码模式为CBC,必须指定初始向量,初始向量中密钥的长度必须是8个字节
    - 如果没有指定填充模式为NoPadding模式,原文的长度必须是8个字节的整倍数
 ```$xslt
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
```
  - AES加密示例代码如下(AES和DES加密高度类似)
     - AES加密,key的大小必须是16个字节
     - 如果没有指定分组密码模式和填充模式,ECB/PKCS5Padding就是默认值
     - 如果没有指定分组密码模式为CBC,必须指定初始向量,初始向量中密钥的长度必须是16个字节
     - 如果没有指定填充模式为NoPadding模式,原文的长度必须是16个字节的整倍数
```$xslt
public class AESDemo {
    // AES加密算法,key的大小必须是16个字节
    public static void main(String[] args) throws Exception {
        String input = "frank";
        String key = "1234567887654321";
        // 指定获取Cipher的算法,如果没有指定分组密码模式和填充模式,ECB/PKCS5Padding就是默认值
        // CBC模式,必须指定初始向量,初始向量中密钥的长度必须是16个字节
        // NoPadding模式,原文的长度必须是16个字节的整倍数
//        String transformation = "AES";
        String transformation = "AES/CBC/PKCS5Padding";
        // 指定获取密钥的算法
        String algorithm = "AES";
        String encryptAES = AESEncrypt(input, key, transformation, algorithm);
        System.out.println("加密:" + encryptAES);
        String s = AESDecrypt(encryptAES, key, transformation, algorithm);
        System.out.println("解密:" + s);
    }

    /**
     *
     * @param input  明文
     * @param key   密钥(AES,密钥的长度必须是16个字节)
     * @param transformation  获取Cipher对象的算法
     * @param algorithm   获取密钥的算法
     * @return  返回密文
     * @throws Exception
     */
    private static String AESEncrypt(String input, String key, String transformation, String algorithm) throws Exception {
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


    private static String AESDecrypt(String input, String key, String transformation, String algorithm) throws Exception {
        Cipher cipher = Cipher.getInstance(transformation);
        SecretKeySpec sks = new SecretKeySpec(key.getBytes(), algorithm);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, sks, iv);
//         cipher.init(Cipher.DECRYPT_MODE, sks);
        byte[] bytes = cipher.doFinal(Base64.decode(input));
        return new String(bytes);
    }
}

```

## 非对称加密

### 1.  对称加密的弊端

- 秘钥分发困难

- 可以通过非对称加密完成秘钥的分发

  > https
  >
  > Alice 和 Bob通信, Alice给bob发送数据, 使用对称加密的方式
  >
  > 1. 生成一个非对称的秘钥对, bob生成
  > 2. bob将公钥发送给alice
  > 3. alice生成一个用于对称加密的秘钥
  > 4. alice使用bob的公钥就对称加密的秘钥进行加密, 并且发送给bob
  > 5. bob使用私钥就数据解密, 得到对称加密的秘钥
  > 6. 通信的双方使用写好的秘钥进行对称加密数据加密

### 2. 非对称加密的秘钥

- 不存在秘钥分发困难的问题

#### 2.1 场景分析

数据对谁更重要, 谁就拿私钥

- 直观上看: 私钥比公钥长
- 使用第三方工具生成密钥对: 公钥文件xxx.pub xxx 

> 1. 通信流程, 信息加密  （A写数据, 发送给B, 信息只允许B读）
>
>    A: 公钥
>
>    B: 私钥
>
> 2. 登录认证 （客户端要登录, 连接服务器, 向服务器请求个人数据）
>
>    客户端:  私钥
>
>    服务器:  公钥
>
> 3. 数字签名（表明信息没有受到伪造，确实是信息拥有者发出来的，附在信息原文的后面）
>
>    - 发送信息的人:   私钥
>    - 收到信息的人:   公钥
>
> 4. 网银U盾
>
>    - 个人: 私钥
>    - 银行拿公钥

### 3. 使用RSA非对称加密通信流程

> 要求: Alice 给 bob发送数据, 保证数据信息只有bob能看到

### 4. 生成RSA的秘钥对
#### 4.1 一些概念

1. x509证书规范、pem、base64
   - pem编码规范 - 数据加密
   - base64 - 对数据编码, 可逆
     - 不管原始数据是什么, 将原始数据使用64个字符来替代
       - a-z  A-Z 0-9 + /
2. ASN.1抽象语法标记
3. PKCS1标准

### 5. 常见算法

> RSA

> ECC(java需要借助第三方库实现加密解密,我将在另外一个项目中用go实现)

###6 RSA示例代码如下
   - 注意:经测试在后端使用Cipher.getInstance(“RSA”)加密,在移动端获取解密的Cipher类时要使用Cipher.getInstance(“RSA/ECB/PKCS1Padding”)
   
   - 移动端核心代码
```$java
  //后端用私钥加密过的数据
    String eText = "ETLPedgx7vR9E9JGNIj4pLhvurcOM26oo4RgJhmeF5RvDXVdl3qQ+5H6hmUx3dV2K8jPxi5aKSVs1xnjuMgSfK32fjrqzYBULFaBCmnN1HbpcwYFNMA3enWiVwT3TAWFKA9ReJ2DWh0lkCzaHruOmcWCY3f2tjuEE9X9L0DN7m5R9iy2qgEEDPgfzImYYl8wltYdudryz2fQ7UNGdIUPc75EMqdvHEUrxIi5A7cM0BDGQI2aXD+39ijQCOVBtai/9ohF7YXtGmbsocPBKarhe8qpVIcvXza6fBbOWxBC6Z68uRGcljTVkhvNjWrEmRuu5pc3C41bx4OK9FD8kPgITg==";
    //后端给的公钥
    String pkey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAmpz9g5IX3ElRtXFo2+9nwD4+amqhEH4Rz1FI2cXeSiQeLPfdCoSsflLovdJ21NxvcKGw9IvmjWkLESCVU/pxDeP2UkVXFAjC2KhZvoQO4v0x4Yn3/55bAAQ9O3qoGatjPlDbzr1CEAi+ZA7NY1Oz2TtOSq8Odc7wc3Sq6U1gZBf87w5jq0GwQwgLrQjaVf5oTgKmavyf6g8Uq8U0QnktXCJpJUsSSZdeWTwAhtKk+MDkd5VRHIynLklOgeAhjG7xzEAad/Q32qLGcCwY+ySiZWLZ5q5uZAys4rj98LiwV6zLyk8CYYclUDUtBPLLXDRN8DUEe4uKAucFC4IlkrXQ0wIDAQAB";
    //获取公钥对象
    KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
    X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decode(pkey, Base64.DEFAULT));
    PublicKey publicKey = keyFactory.generatePublic(spec);
    //指定解密algorithm = "RSA/ECB/PKCS1Padding"; Cipher cipher = Cipher.getInstance(algorithm);
    algorithm = "RSA/ECB/PKCS1Padding";
    String decrypt = RSADemo.RSADecrypt(algorithm, publicKey, eText, 256);
    System.out.println("decript###" + decrypt + "###");
```
   
 - 后端代码  
```java
public class RSADemo {
    private static int MAX_ENCRIPT_SIZE = 200;

    public static void main(String[] args) throws Exception {
        String algorithm = "RSA";
        String input = "frank";
//        generateKeys(algorithm, "test.pri", "test.pub");

        PrivateKey privateKey = getPrivateKey("test.pri", algorithm);
        PublicKey publicKey = getPublicKey("test.pub", algorithm);

        String encryptData = RSAEncrypt(algorithm, privateKey, input, MAX_ENCRIPT_SIZE);
        System.out.println("encryptData=" + encryptData);
        String decryptData = RSADecrypt(algorithm, publicKey, encryptData, 256);
        System.out.println("decryptData=" + decryptData);

    }

    public static PrivateKey getPrivateKey(String priPath, String algorithm) throws Exception {
        String privateKeyString = FileUtils.readFileToString(new File(priPath), Charset.defaultCharset());
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(Base64.decode(privateKeyString));
        return keyFactory.generatePrivate(spec);
    }

    public static PublicKey getPublicKey(String pubPath, String algorithm) throws Exception {
        String publicKeyString = FileUtils.readFileToString(new File(pubPath), Charset.defaultCharset());
        KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(Base64.decode(publicKeyString));
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
        String privateKeyString = Base64.encode(privateKeyEncoded);
        String publicKeyString = Base64.encode(publicKeyEncoded);

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
        return Base64.encode(baos.toByteArray());
    }

    public static String RSADecrypt(String algorithm, Key key, String input, int maxEncryptSize) throws Exception {
        Cipher cipher = Cipher.getInstance(algorithm);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] data = Base64.decode(input);
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
```

> 由于篇幅有点长,我将在另外一篇介绍哈希函数和数字签名