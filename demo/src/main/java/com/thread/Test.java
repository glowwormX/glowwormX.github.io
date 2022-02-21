package com.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * @Description:
 * @author xqw
 * @date 2022/2/21 20:45
 */
public class Test {
    public static void main(String[] args) {

        List<String> allPaymentList = new ArrayList<>();
        ThreadPoolExecutor executor = new ThreadPoolExecutor(16, 32, 60, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(0),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy());
       /* List<FutureTask<Boolean>> fts = new ArrayList<>();
        for (String p : allPaymentList) {
            Future<String> t = executor.submit(() -> {
                if(isEnable(p)) {
                    return p;
                }
                return null;
            });
            fts.add(t);
        }*/
        List<String> collect = allPaymentList.stream()
                .map(p -> executor.submit(() -> isEnable(p) ? p : null))
                .map(t -> {
                    try {
                        return t.get();
                    } catch (Exception e) {

                    }
                    return null;
                })
                .filter(p -> p != null)
                .collect(Collectors.toList());



    }

    private static Boolean isEnable(String p) {
        return true;
    }

}
