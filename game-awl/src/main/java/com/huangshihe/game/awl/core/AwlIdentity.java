package com.huangshihe.game.awl.core;

import com.huangshihe.game.core.Identity;

/**
 * Created by Administrator on 2016/7/24.
 */
public class AwlIdentity implements Identity {

    private int id;

    public AwlIdentity(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public int getType() {
        return AwlIdentityEnum.values()[id].getType();
    }

    @Override
    public String getName() {
//        // 不需要手动添加
//        if (id < 0 || id >= AwlIdentityEnum.values().length)
//            throw new IllegalArgumentException("length error!");
        return AwlIdentityEnum.values()[id].getName();
    }

    @Override
    public String getDescription() {
        return AwlIdentityEnum.values()[id].getDescription();
    }

    public enum AwlIdentityEnum {
        MEHRING {
            @Override
            public String getName() {
                return "梅林";
            }

            @Override
            public int getType() {
                return GOOD_TYPE;
            }

            @Override
            public String getDescription() {
                return "即：预言家。";
            }
        }, PERCIVALE {
            @Override
            public String getName() {
                return "派西维尔";
            }

            @Override
            public int getType() {
                return GOOD_TYPE;
            }

            @Override
            public String getDescription() {
                return "即：女巫。";
            }
        }, LOYAL {
            @Override
            public String getName() {
                return "忠臣";
            }

            @Override
            public int getType() {
                return GOOD_TYPE;
            }

            @Override
            public String getDescription() {
                return "即：平民。";
            }
        }, MORGANA {
            @Override
            public String getName() {
                return "莫甘娜";
            }

            @Override
            public int getType() {
                return BAD_TYPE;
            }

            @Override
            public String getDescription() {
                return "即：坏人头头。";
            }
        }, ASSASSIN {
            @Override
            public String getName() {
                return "刺客";
            }

            @Override
            public int getType() {
                return BAD_TYPE;
            }

            @Override
            public String getDescription() {
                return "即：刺客。";
            }
        };

        public abstract String getName();

        public abstract int getType();

        public abstract String getDescription();
    }

    /**
     * 好人
     */
    public final static int GOOD_TYPE = 1;

    /**
     * 坏人
     */
    public final static int BAD_TYPE = 2;
}
