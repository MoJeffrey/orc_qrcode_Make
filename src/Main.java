public class Main {
    public static void main(String[] args) {
        String hexString = "FFFF0000"; // 十六进制字符串
        long longValue = Long.parseLong(hexString, 16);
        int intValue = (int) longValue; // 将 long 值转换为 int 类型
        System.out.println("转换后的整数值为：" + intValue);
    }

}