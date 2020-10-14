package com.thread;

import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/**
 * @author xqw
 * @description:
 * @date 2020/8/25
 */
public class CopyFile {
    public static void copy(String from, String b) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        List<String> unfinished = Arrays.asList("2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        List<String> finished = Collections.singletonList("1");
        copy(new LinkedBlockingDeque<>(finished), new LinkedBlockingDeque<>(unfinished));
    }

    volatile static boolean stop = false;

    public static void copy(BlockingDeque<String> finished, BlockingDeque<String> unfinished) throws InterruptedException {
        String from = null, to = null;

        while (!stop) {
            from = finished.poll(1, TimeUnit.SECONDS);
            to = unfinished.poll(1, TimeUnit.SECONDS);
            if (from != null && to != null) {
                String finalFrom = from;
                String finalTo = to;
                new Thread(() -> {
                    copy(finalFrom, finalTo);
                    finished.add(finalFrom);
                    finished.add(finalTo);
                    System.out.println("finished:" + finalFrom + "->" + finalTo);
                    if (finished.size() == finished.size() + unfinished.size()) {
                        stop = true;
                    }
                }).start();
            }
        }
    }
}
