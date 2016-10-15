package com.huangshihe.game.awl.manage;

import com.huangshihe.game.awl.core.AwlIdentity;
import com.huangshihe.game.core.Game;
import com.huangshihe.game.core.GameUser;
import com.huangshihe.game.manage.AssignIdentity;

import java.util.Comparator;

/**
 * Created by Administrator on 2016/7/26.
 */
public class AssignAwlIdentity extends AssignIdentity {
    @Override
    public boolean assign(Game game) {
        if (game.getGamers().size() != game.getRequireGamerNum()) {
            throw new IllegalArgumentException("can't assign identity, game users not enough!");
        }
        // 获得随机数组
        int[] randomNums = getRandomNums(game.getRequireGamerNum());
        // 按顺序给gameUser赋予身份和随机编号
        for (int i = 0; i < randomNums.length; i++) {
            game.getGamers().get(i).setIdentity(new AwlIdentity(i));
            game.getGamers().get(i).setNum(randomNums[i]);
        }

        // TODO 优化获得每个身份的特殊信息
        for (GameUser gameUser : game.getGamers()) {
            // 梅林知道好人和坏人
            if (gameUser.getIdentity().getId() == AwlIdentity.AwlIdentityEnum.MEHRING.ordinal()) {
                StringBuilder builder = new StringBuilder("特殊信息：好人是：");
                // 获得好人列表
                game.getGamers().stream().filter(tempGameUser -> tempGameUser.getIdentity().getType() == AwlIdentity.GOOD_TYPE).
                        sorted(Comparator.comparing(GameUser::getNum)).forEach(temp -> builder.append(temp.getNum()).append(" "));
                builder.append("坏人是：");
                // 获得坏人列表
                game.getGamers().stream().filter(tempGameUser -> tempGameUser.getIdentity().getType() == AwlIdentity.BAD_TYPE).
                        sorted(Comparator.comparing(GameUser::getNum)).forEach(temp -> builder.append(temp.getNum()).append(" "));
                gameUser.setInfo(builder.toString());
            }
            // 派西维尔知道梅林和莫甘娜。
            else if (gameUser.getIdentity().getId() == AwlIdentity.AwlIdentityEnum.PERCIVALE.ordinal()) {
                StringBuilder builder = new StringBuilder("特殊信息：梅林和莫甘娜的编号（无序）为：");
                game.getGamers().stream().filter(tempGameUser ->
                        tempGameUser.getIdentity().getId() == AwlIdentity.AwlIdentityEnum.MEHRING.ordinal()
                                || tempGameUser.getIdentity().getId() == AwlIdentity.AwlIdentityEnum.MORGANA.ordinal()).
                        sorted(Comparator.comparing(GameUser::getNum)).forEach(temp -> builder.append(temp.getNum()).append(" "));
                gameUser.setInfo(builder.toString());

//                int meilin = game.getGamers().stream().filter(tempGameUser ->
//                        tempGameUser.getIdentity().getId() == AwlIdentity.AwlIdentityEnum.MEHRING.ordinal()).
//                        findFirst().orElse(null).getNum();
//                int mogana = game.getGamers().stream().filter(tempGameUser ->
//                        tempGameUser.getIdentity().getId() == AwlIdentity.AwlIdentityEnum.MORGANA.ordinal()).
//                        findFirst().orElse(null).getNum();
//                if (meilin > mogana) {
//                    gameUser.setInfo("特殊信息：梅林和莫甘娜的编号（无序）为：" + mogana + " " + meilin);
//                } else {
//                    gameUser.setInfo("特殊信息：梅林和莫甘娜的编号（无序）为：" + meilin + " " + mogana);
//                }
            }
            // 忠臣比较愚蠢，墨子都不晓得，哈的死。
            else if (gameUser.getIdentity().getId() == AwlIdentity.AwlIdentityEnum.LOYAL.ordinal()) {
                gameUser.setInfo("特殊信息：你还要墨子特殊信息，平民一个！");
            }
            // 莫甘娜知道刺客
            else if (gameUser.getIdentity().getId() == AwlIdentity.AwlIdentityEnum.MORGANA.ordinal()) {
                int as = game.getGamers().stream().filter(tempGameUser ->
                        tempGameUser.getIdentity().getId() == AwlIdentity.AwlIdentityEnum.ASSASSIN.ordinal()).
                        findFirst().orElse(null).getNum();
                gameUser.setInfo("特殊信息：您的狐朋狗友之刺客是：" + as);
            }
            // 刺客知道莫甘娜
            else if (gameUser.getIdentity().getId() == AwlIdentity.AwlIdentityEnum.ASSASSIN.ordinal()) {
                int mogana = game.getGamers().stream().filter(tempGameUser ->
                        tempGameUser.getIdentity().getId() == AwlIdentity.AwlIdentityEnum.MORGANA.ordinal()).
                        findFirst().orElse(null).getNum();
                gameUser.setInfo("特殊信息：您的狐朋狗友之莫甘娜是：" + mogana);
            } else {
                throw new IllegalArgumentException("get identity fail!");
            }
            // 激活玩家
            gameUser.setStatus(GameUser.LIVE);
        }
        return true;
    }
}
