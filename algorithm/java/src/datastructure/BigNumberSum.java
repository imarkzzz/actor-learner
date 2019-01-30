package datastructure;

import java.math.BigInteger;

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

    /**
     * 分组大整数加法
     * @param a 大整数 a
     * @param b 大整数 b
     * @return
     */
    public static String getSumGroup(String a, String b) {
        // 1. 把两个整数用数组分组，数组长度等于较大整数位数+1
        int maxLen = a.length() > b.length() ? a.length() % 9 : b.length() % 9;
        int[] arrayA = new int[maxLen + 1];
        int k = maxLen;
        for (int i = a.length() - 1; i >= 0; i-=9) {
            StringBuilder sb = new StringBuilder();
            int j = i - 8;
            if (j<= 0) {
                j = 0;
            }
            for (;j <= i; j++) {
                sb.append(a.charAt(j));
            }
            arrayA[k] = Integer.parseInt(sb.toString());
            k-=1;
        }
        int[] arrayB = new int[maxLen + 1];
        k = maxLen;
        for (int i = b.length() - 1; i >= 0; i-=9) {
            StringBuilder sb = new StringBuilder();
            int j = i - 8;
            if (j<= 0) {
                j = 0;
            }
            for (;j <= i; j++) {
                sb.append(b.charAt(j));
            }
            arrayB[k] = Integer.parseInt(sb.toString());
            k-=1;
        }

        // 2. 构建result数组，数组长度等于分组数
        int[] result = new int[maxLen + 1];
        // 3. 遍历数组，按组
        for (int i = result.length - 1; i >= 0; i--) {
            int temp = result[i];
            temp += arrayA[maxLen - i];
            temp += arrayB[maxLen - i];
            // 判断是否进位
            if (temp >= 1000_000_000) {
                temp -= 1000_000_000;
                result[i + 1] = 1;
            }
            result[i] = temp;
        }
        // 4. 把result数组再次逆序转成String
        StringBuilder sb = new StringBuilder();
        for (int i = result.length - 1; i >= 0; i--) {
            sb.append(result[i]);
        }
        return sb.toString();
    }
    public static void main(String[] args) {
        System.out.println(getSumBitWise("134348394839", "37438473289144738247"));
        // 查看 int 类型最大的位数是10位数，第10位可以作为进位标记位(按位大数加法用到了int但是只算一位效率低)
//        System.out.println(Integer.MAX_VALUE);
        System.out.println(getSumGroup("134348394839", "37438473289144738247"));
        // 和BigInteger的结果进行比较，检验结果是否正确
        BigInteger bintA = new BigInteger("134348394839");
        BigInteger bintB = new BigInteger("37438473289144738247");
        BigInteger result = bintA.add(bintB);
        System.out.println(result);
    }
}
