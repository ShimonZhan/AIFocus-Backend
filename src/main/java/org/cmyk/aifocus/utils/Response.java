package org.cmyk.aifocus.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Response<T> implements Serializable {
    private Integer status;
    private String message;
    private T content;
}
