package com.snowkit.luna.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class Envelope {

    private String encData;

    private String iv;

    private String encKey;
}
