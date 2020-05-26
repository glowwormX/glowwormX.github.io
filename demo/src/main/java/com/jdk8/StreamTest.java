package com.jdk8;

import com.itextpdf.io.util.ArrayUtil;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * @author xqw
 * @Description:
 * @date 2020/4/30 15:24
 */
public class StreamTest {
    public static void main(String[] args) {
        List<Integer> a = new ArrayList<>();
        a.add(1);
        a.add(2);
        List<Integer> b = new ArrayList<>();
        b.add(3);
        b.add(4);
        List<Integer> figures = Stream.of(a, b)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        figures.forEach(System.out::println);

        //降维
        int[][] bi = {{1, 2}, {2, 3}, {2, 5}};
        int[] ints1 = Arrays.stream(bi).flatMapToInt(Arrays::stream).toArray();

        int[][] ints2 = IntStream.rangeClosed(0, ints1.length - 1)
                .filter(i -> i % 2 == 0)
                .mapToObj(i -> Arrays.copyOfRange(ints1, i, i + 2)).toArray(int[][]::new);

        try {
            assert ints2.length == 0;
        } catch (AssertionError e) {
            e.printStackTrace();
        }
        System.out.println(ints2.length);
//        of(Arrays.asList(1,2,3,4,5));
    }

    public static <E> Stream<List<E>> of(List<E> list) {
        Stream<List<E>> prefixes = IntStream.rangeClosed(1, list.size())
                .mapToObj(end -> list.subList(0, end));
        Stream<List<E>> suffixes = prefixes.flatMap(ls ->
                IntStream.range(0, list.size())
                        .mapToObj(start -> list.subList(start, list.size()))
        );
        return Stream.concat(Stream.of(Collections.emptyList()), suffixes);

    }


    private static <E> Stream<List<E>> prefixes(List<E> list) {

        return IntStream.rangeClosed(1, list.size())

                .mapToObj(end -> list.subList(0, end));

    }


    private static <E> Stream<List<E>> suffixes(List<E> list) {

        return IntStream.range(0, list.size())

                .mapToObj(start -> list.subList(start, list.size()));

    }
}
