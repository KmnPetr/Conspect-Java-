package com.example.springrest.model;

import java.util.Random;

public enum Mood {
    HAPPY, SAD, ANGRY/*злой*/, WORRIED/*обеспокоенный*/, CALM/*спокойный*/;

    public static Mood getRandomMood() {
        int mood = new Random().nextInt(Mood.values().length);//nextInt() вернет случ.число от 0 до предложенного ему или от предложенного до предложенного числа
        return values()[mood];
    }
}
