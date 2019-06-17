package com.jdk8;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 徐其伟
 * @Description:
 * @date 19-6-14 下午1:38
 */
public class jdk8 {
    public static void main(String[] args) {
        Integer[][][] ll = {{{1,2},{2,3},{2,5}}};

        Map<Integer, Long> collect = Arrays.stream(ll)
                .flatMap(x1 -> Arrays.stream(x1).flatMap(x2 -> Arrays.stream(x2)))
                .filter(x -> x % 2 == 0)
                .collect(Collectors.groupingBy(Integer::intValue, Collectors.counting()));


        int portNumber = 1337;
        Runnable r = () -> System.out.println(portNumber);
//        portNumber = 31337;
    }
}
