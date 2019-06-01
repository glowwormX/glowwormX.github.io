package com.example.io;

import java.io.UnsupportedEncodingException;

/**
 * @author 徐其伟
 * @Description:
 * @date 2019/5/30 21:48
 */
public class CodeTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        System.out.println(Integer.toBinaryString(Float.floatToIntBits(-0.125F)));
        System.out.println(Integer.toBinaryString(Float.floatToIntBits(-5F)));
        System.out.println(Integer.toBinaryString(Float.floatToIntBits(5F)));
        System.out.println(Integer.toBinaryString(Float.floatToIntBits(-3.125F)));

        char a = '好';
        String str = "g";
        byte[] bytes = str.getBytes();
        int byte_len = bytes.length;
        System.out.println(bytes + "字节长度：" + byte_len);

    }
}
