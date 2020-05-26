package com.enums;

import java.util.Map;
import java.util.Optional;
import java.util.function.ToIntBiFunction;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toMap;

/**
 * @author xqw
 * @Description:
 * @date 2020/5/7 11:01
 */
public enum PayrollDay {
    MONDAY("星期一"), TUESDAY("星期二"), WEDNESDAY("星期三"), THURSDAY("星期四"), FRIDAY("星期五"),
    SATURDAY("星期六", PayType.WEEKEND), SUNDAY("星期日", PayType.WEEKEND);

    // Implementing a fromString method on an enum type
    private static final Map<String, PayrollDay> stringToEnum =
            Stream.of(values()).collect(
                    toMap(PayrollDay::getDesc, e -> e));

    // Returns Operation for string, if any
    public static Optional<PayrollDay> fromString(String desc) {
        return Optional.ofNullable(stringToEnum.get(desc));
    }

    private final PayType payType;
    private final String desc;

    public String getDesc() {
        return desc;
    }

    PayrollDay(String desc, PayType payType) {
        this.desc = desc;
        this.payType = payType;
    }

    PayrollDay(String desc) {
        this(desc, PayType.WEEKDAY);
    }  // Default


    int pay(int minutesWorked, int payRate) {
        return payType.pay(minutesWorked, payRate);
    }


    // The strategy enum type
    private enum PayType {
        WEEKDAY((minsWorked, payRate) -> minsWorked <= PayType.MINS_PER_SHIFT ? 0 :
                (minsWorked - PayType.MINS_PER_SHIFT) * payRate / 2
        ),
        WEEKEND((minsWorked, payRate) -> minsWorked * payRate / 2);

        PayType(ToIntBiFunction<Integer, Integer> overtimePay) {
            this.overtimePay = overtimePay;
        }

        private ToIntBiFunction<Integer, Integer> overtimePay;
        //        abstract int overtimePay(int mins, int payRate);
        private static final int MINS_PER_SHIFT = 8 * 60;


        int pay(int minsWorked, int payRate) {
            int basePay = minsWorked * payRate;
            return basePay + overtimePay.applyAsInt(minsWorked, payRate);
        }
    }

    public static void main(String[] args) {
        System.out.println(PayrollDay.valueOf("星期一"));
        System.out.println(PayrollDay.fromString("星期一"));
    }

}
