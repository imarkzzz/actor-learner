package datastructure;

public class BigNumberSum {
    /**
     * 大数求和(位的方式相加)
     * @param a 大整数a
     * @param b 大整数b
     * @return
     */
    public static String getSumBitWise(String a, String b) {
        // 1. 把两个整数用数组逆序存储，数组长度等于较大整数位数+1
        int maxLen = a.length() > b.length() ? a.length() : b.length();
        int[] arrayA = new int[maxLen + 1];
        for (int i = 0; i < a.length(); i++) {
            arrayA[i] = a.charAt(a.length() - 1 -i) - '0';
        }
        int[] arrayB = new int[maxLen + 1];
        for (int i = 0; i < b.length(); i++) {
            arrayB[i] = b.charAt(b.length() - 1 -i) - '0';
        }
        // 2. 构建result数组，数组长度等于较长整数位数+1
        int[] result = new int[maxLen + 1];
        // 3. 遍历数组，按位相加
        for (int i = 0; i < result.length; i++) {
            int temp = result[i];
            temp += arrayA[i];
            temp += arrayB[i];
            // 判断是否进位
            if (temp >= 10) {
                temp -= 10;
                result[i + 1] = 1;
            }
            result[i] = temp;
        }
        // 4. 把result数组再次逆序转成String
        StringBuilder sb = new StringBuilder();
        // 是否找到大整数的最高有效位
        boolean findHighest = false;
        for (int i = result.length - 1; i >= 0; i--) {
            if (!findHighest) {
                if (result[i] == 0) {
                    continue;
                }
                findHighest = true;
            }
            sb.append(result[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        System.out.printf(getSumBitWise("134348394839", "37438473289144738247"));
    }
}
