public class KaiserDemo {
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
}
