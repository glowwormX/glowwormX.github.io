package com.jdk8;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 徐其伟
 * @Description:
 * @date 19-6-14 下午1:38
 */
public class Jdk8 {
    public static void main(String[] args) {
        test1();
        List<Integer> list = new ArrayList<>(Arrays.asList(1,2,3,4));
        List<Integer> res = list.stream().filter(x -> x % 2 == 0).collect(Collectors.toList());
        new C().hello();
    }

    private static void test1() {
        Integer[][][] ll = {{{1,2},{2,3},{2,5}}};

        Map<Integer, Long> collect = Arrays.stream(ll)
                .flatMap(x1 -> Arrays.stream(x1).flatMap(x2 -> Arrays.stream(x2)))
                .filter(x -> x % 2 == 0)
                .collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));
        System.out.println(collect);
        LocalDate date1 = LocalDate.of(2014, 3, 18);
        LocalDate date2 = date1.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY));
    }

    public interface P{
        default void hello() {
            System.out.println("P");
        }
    }

    public interface A extends P{
        default void hello() {
            System.out.println("A");
        }
    }
    public interface B extends P{
        default void hello() {
            System.out.println("B");
        }
    }
    public static class C implements A, B{
        @Override
        public void hello() {

        }
    }
}
