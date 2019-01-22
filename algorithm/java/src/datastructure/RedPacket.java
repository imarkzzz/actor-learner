package datastructure;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 模拟微信红包生成，以分为单位
 */
public class RedPacket {
    /**
     * 定义红包金额范围
     */
    private static final int MIN_MONEY = 1; // 生成红包最小值 1分
    private static final int MAX_MONEY = 200 * 100; // 生成红包最小值 200人民币

    /**
     * 定义红包状态
     */
    private static final int LESS = -1; // 小于最小值
    private static final int MORE = -2; // 大于最大值
    private static final int OK = 1; // 正常值

    /**
     * 最大的红包是平均值的TIMES 倍，防止某一次分配红包较大
     */
    private static final double TIMES = 2.1F;

    private int recursiveCount = 0;

    public List<Integer> splitRedPacket(int money, int count) {
        List<Integer> moneys = new LinkedList<>();

        // 金额检查，如果最大红包 * 个数 < 总金额；则需要调大最小红包 MAX_MONEY
        if (MAX_MONEY * count <= money) {
            System.out.println("请调大最小红包金额 MAX_MONEY=[" + MAX_MONEY + "]");
            return moneys;
        }

        // 计算出最大红包
        int max = (int) ((money / count) *TIMES);
        max = max > MAX_MONEY ? MAX_MONEY : max;

        for (int i = 0; i < count; i++) {
            // 随机获取红包
            int redPeacket = randomRedPacket(money, MIN_MONEY, max, count - i);
            moneys.add(redPeacket);
            // 总金额每次减少
            money -= redPeacket;
        }
        return moneys;
    }

    /**
     * 随机生成一个合法的红包
     * @param totalMoney 剩余总金额
     * @param minMoney 红包最小金额
     * @param maxMoney 红包最大金额
     * @param count 红包数量
     * @return
     */
    private int randomRedPacket(int totalMoney, int minMoney, int maxMoney, int count) {
        // 只有一个红包直接返回
        if (count == 1) {
            return totalMoney;
        }

        if (minMoney == maxMoney) {
            return minMoney;
        }

        // 如果最大金额大于剩余金额，则用剩余金额，因为这个 money 每分配一次都会减小
        maxMoney = maxMoney > totalMoney?totalMoney : maxMoney;

        // 在minMoney到maxMoney生成一个随机红包
        int redPacket = (int) (Math.random() * (maxMoney - minMoney) + minMoney);

        int lastMoney = totalMoney - redPacket;

        int status = checkMoney(lastMoney, count - 1);
        // 正常金额
        if (OK == status) {
            return redPacket;
        }
        if (LESS == status) {
            recursiveCount++;
            System.out.println("recursiveCount==" + recursiveCount);
            return randomRedPacket(totalMoney, minMoney, redPacket, count);
        }
        if (MORE == status) {
            recursiveCount++;
            System.out.println("recursiveCount===" + recursiveCount);
            return randomRedPacket(totalMoney, redPacket, maxMoney, count);
        }
        return redPacket;
    }

    /**
     * 检查剩余的金额的平均值是否在最小值和最大值这个范围内
     * @param lastMoney
     * @param count
     * @return
     */
    private int checkMoney(int lastMoney, int count) {
        double avg = lastMoney / count;
        if (avg < MIN_MONEY) {
            return LESS;
        }

        if (avg > MAX_MONEY) {
            return MORE;
        }
        return OK;
    }

    public static void main(String[] args) {
        RedPacket redPacket = new RedPacket();
        int count = 10000;
        int num = 10;
        List<Integer> redPacketsAVG = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            redPacketsAVG.add(0);
        }
        for (int i = 0; i < count; i++) {
            List<Integer> redPackets = redPacket.splitRedPacket(200, num);
            int idx = 0;
            for (Integer red : redPackets) {
                redPacketsAVG.set(idx, redPacketsAVG.get(idx) + red);
                idx++;
            }
        }
        // 分析微信红包的分布规律
        for (Integer red : redPacketsAVG) {
            System.out.println(1.0*red/count);
        }
    }
}
