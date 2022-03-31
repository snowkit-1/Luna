package com.snowkit.luna.util;

import java.util.Base64;

public class KeyUtil {

    public static byte[] AES_KEY;

    static {
        String aesKey = System.getenv("AES_KEY");
        if (aesKey == null) {
            aesKey = "8oTsnjfizt5kJfoYFMa05VHpJ9Q2YcATn5fG7GoqSgM=";
        }
        AES_KEY = Base64.getDecoder().decode(aesKey);
    }
}
