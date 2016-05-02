package org.mariusconstantin.jobsqueue.utils;

import android.support.annotation.NonNull;

import java.util.Random;

/**
 * Created by MConstantin on 4/22/2016.
 */
public class TestUtils {

    private final static String RAND_GEN_CHARS = "abcdefghijklmnoprstuvwxyz0123456789";

    @NonNull
    public static String generateRandomString(int length) {
        final StringBuilder builder = new StringBuilder();
        final Random random = new Random();
        final int randLimit = RAND_GEN_CHARS.length() - 1;
        int counter = 0;
        while (counter < length) {
            builder.append(RAND_GEN_CHARS.charAt(random.nextInt(randLimit)));
            counter++;
        }
        return builder.toString();
    }
}
