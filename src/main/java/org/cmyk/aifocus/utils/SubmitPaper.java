package org.cmyk.aifocus.utils;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class SubmitPaper implements Serializable {
    private String examPaperId;

    private Integer questionType;

    private String answer;
}
