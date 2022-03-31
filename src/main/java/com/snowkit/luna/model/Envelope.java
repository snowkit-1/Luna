package com.snowkit.luna.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class Envelope {

    private String encryptedData;

    private String iv;

    private String encryptedKey;
}
