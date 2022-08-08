package org.cmyk.aifocus.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginBack implements Serializable {
    private String name;
    private Integer userType;
    private String userId;
    private String avater;
}