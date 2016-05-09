package ua.skillsup.practice.hibernate.model;

import java.math.BigDecimal;

public class Data {

    public Data() {    }

    public static BigDecimal MIN_BID_STEP;

    public static void setMIN_BID_STEP(BigDecimal minBidStep) {
        MIN_BID_STEP = minBidStep;
    }

}
