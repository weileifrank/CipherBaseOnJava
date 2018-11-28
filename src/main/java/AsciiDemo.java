public class AsciiDemo {

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

}
