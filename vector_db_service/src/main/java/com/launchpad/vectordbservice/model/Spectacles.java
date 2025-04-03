package com.launchpad.vectordbservice.model;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Spectacles {
     private Integer leftEyePower;
    private Integer rightEyePower;
    private Boolean bifocal=false;
}
