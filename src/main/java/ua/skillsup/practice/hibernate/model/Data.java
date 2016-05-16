package ua.skillsup.practice.hibernate.model;

import java.math.BigDecimal;

public class Data {

    public Data() {    }

    public static final long ID_FOR_ERROR_DTO = -1L;

    //Эти константы проинжектит спринг:
    public static BigDecimal MIN_BID_STEP;
    public static int DURATION_MIN;
    public static int DURATION_MAX;

    public static void setMIN_BID_STEP(BigDecimal minBidStep) {
        MIN_BID_STEP = minBidStep;
    }

    public static void setDURATION_MIN(int durationMin) {
        DURATION_MIN = durationMin;
    }

    public static void setDURATION_MAX(int durationMax) {
        DURATION_MAX = durationMax;
    }
}
