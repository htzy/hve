package com.huangshihe.game.manage;

import com.huangshihe.game.core.Game;

/**
 * Created by Administrator on 2016/7/25.
 */
public abstract class AssignIdentity {

    public abstract boolean assign(Game game);

    protected static int[] getRandomNums(int totalNum) {
        // 从0开始
        int[] res = new int[totalNum];
        double[] randomNums = new double[totalNum];
        // 生成totalNum个随机数
        for (int i = 0; i < randomNums.length; i++) {
            res[i] = i;
            randomNums[i] = Math.random();
        }
        // 换下标，不换值
        for (int i = 0; i < randomNums.length; i++) {
            for (int j = 0; j < i; j++) {
                if (randomNums[i] < randomNums[j]) {
                    int temp = res[i];
                    res[i] = res[j];
                    res[j] = temp;
                }
            }
        }
        return res;
    }
}
