package com.example.io;

import java.io.UnsupportedEncodingException;

/**
 * @author 徐其伟
 * @Description:
 * @date 2019/5/30 21:48
 */
public class CodeTest {
    public static void main(String[] args) throws UnsupportedEncodingException {
        char a = '好';
        String str = "g";
        byte[] bytes = str.getBytes();
        int byte_len = bytes.length;
        System.out.println(bytes + "字节长度：" + byte_len);

    }
}
