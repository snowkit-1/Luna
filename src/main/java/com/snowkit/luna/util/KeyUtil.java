package com.snowkit.luna.util;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class KeyUtil {

    public static final String AES_KEY;

    public static final String PUBLIC_KEY;

    public static final String PRIVATE_KEY;

    public static final String JWT_SECRET_KEY;

    static {
        String aesKey = System.getenv("AES_KEY");
        if (aesKey == null) {
            aesKey = "8oTsnjfizt5kJfoYFMa05VHpJ9Q2YcATn5fG7GoqSgM=";
        }
        AES_KEY = aesKey;

        String publicKey = System.getenv("PUBLIC_KEY");
        if (publicKey == null) {
            publicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhRdd0BpiBkyexeByFwhh0AvvlsUIaVaum9gFN26KzdHy8iUz8Qg7vWhlDH6OSFzDtJQVyJ5P1f6f4kt/+GvgJB+r3aOfSwnu6S0XmC/EGhWWp7qY7zRx/nMVxS1blY0cO7ry/mGaG4nC4ZGCDOZhgTaWl8gnxaAClwehPk+GGLrWJyb6CE9VSlN5Mg6aU9G08Mphc8pbsf1SnuxlwAyUfm2evZ/gK8xgzJx86Zza/h1KuWXzFOi4v36zJepFcETh95YN9S2EOJSt7RdFaw3IAMz1REBcuPaoi22z1/ijTTpPGj+1YO5aGiT6BvMinS8xG5AvDz8ooPpJ3xWxOMNpoQIDAQAB";
        }
        PUBLIC_KEY = publicKey;

        String privateKey = System.getenv("PRIVATE_KEY");
        if (privateKey == null) {
            privateKey = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCFF13QGmIGTJ7F4HIXCGHQC++WxQhpVq6b2AU3borN0fLyJTPxCDu9aGUMfo5IXMO0lBXInk/V/p/iS3/4a+AkH6vdo59LCe7pLReYL8QaFZanupjvNHH+cxXFLVuVjRw7uvL+YZobicLhkYIM5mGBNpaXyCfFoAKXB6E+T4YYutYnJvoIT1VKU3kyDppT0bTwymFzylux/VKe7GXADJR+bZ69n+ArzGDMnHzpnNr+HUq5ZfMU6Li/frMl6kVwROH3lg31LYQ4lK3tF0VrDcgAzPVEQFy49qiLbbPX+KNNOk8aP7Vg7loaJPoG8yKdLzEbkC8PPyig+knfFbE4w2mhAgMBAAECggEAbBtI1qs3iIpgRbdEtTTXztiDKuKtLHN+rZRQ2G/wS7MINmzFvjnD3NyZiAacqGbBiULoVudRTi/fyz8smat2i6E2R3iLAbb1224HRlU2Y0G5+FB3vYXKOrdcradkRxwyHMqEauD4/vlt1TAOhU/1T7H5rRymQb7xm6BU93o9IIQkMXXoN0QFpuerKbWe797KKHLQciTVooG+rI73GwvrgmgWX/Zq8fnZ7KFXXdl7JJpgR7zqDYMh6uUph0kMojmOowDTNGCPWQnECx/bpA0oz9yYOc0maPDf18foP5Cy0WyUefYAKyDlwEIg8lAs5tWcI+t39VsE0yv6v3WVcDAwSQKBgQDVmZBYvAXhRXVI0/2QFPhenZpgthkUapdPHI35WUNbxpsc46/a7t/M249hcL5T63ldmlBCzho6Cp7DnfS/713UyLUnTrjqJRqYVYdkXDzWou46U9ddJBehMLVUPS4/WGmnlX9hJjIvNs8gy4vd4T1/LhiJuIorF3rkLKqf9dky7wKBgQCfgp+dYLvEbg/zoiD21FKA6DjWY4I/dnPKXiZGkv6gjS2r8t0Hw/6I9xq57mB4cY1eNVNA/FRUuWZLvtiBMEZQErDln/vXNZ/vbINBuluBA9ID0l3l9X8HUBCURl6esTudnAixjmVo45/cuxhUFFl6LMFPXqzwdycExTNO3X7sbwKBgQC43dS5h9oK84OjyZ4uYofn0722MESaj/pvBQa3pZMPzl+XbFRaRt45vllk+yAvH6EF6zXF6/10+JYaNg6LQ89RB3kKES8nr39KEtRAfzhEh/VpVAEUYtV0s+RsiRsKximDWndJjLZoeKZh+ukN2xDPjwS7xZUH4KWC8Emoci5OzwKBgEadWDYeveFO7EbqLne3NTMWGxtmACJCOS6MkZtKteizt1VY9drbPJK7+hv32MNZWcEsEEhDrL25Of0sZsKCcY4/SArP0XjLp5shydXOP3YVD+NbPPBxxyOgoUZTBM5yHbc/fWXFaUWPB5V7SmDmhVod+BJAEoV96kslVfqC+g6HAoGAWk9ZRrnAHM8Dony55hNwMps03BF0prPUHYLdM0/p/SDljmSmjJUvRvYZUuiLWIhcb7neIwlNoz7O7zTqSy30QMDWMvNtNZXp/4+fWneVPinsFI7cpLTBUs7AlktN4dpQlMCH4oqgBAoZCdCsk4BjjWVn9h0lSEhx+yFN+20K6Lg=";
        }
        PRIVATE_KEY = privateKey;

        String jwtSecretKey = System.getenv("JWT_SECRET_KEY");
        if (jwtSecretKey == null) {
//            throw new RuntimeException("Please set JWT_SECRET_KEY environment variable.");
            jwtSecretKey = "66d0HC5jKk7BNLry9r825lKLBFskG/FC6t+dunhXsT4=";
        }
        JWT_SECRET_KEY = jwtSecretKey;
    }
}
