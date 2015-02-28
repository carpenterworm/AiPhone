package net.onefree.aiphone.utils;

import java.math.BigDecimal;

/**
 * Created by maoah on 14/11/1.
 */
public class NumberUtils {

    public static float formatDecimals(float value, int decimals) {
        BigDecimal b = new BigDecimal(value);
        return b.setScale(decimals, BigDecimal.ROUND_DOWN).floatValue();
    }


}
