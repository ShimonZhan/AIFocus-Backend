package org.cmyk.aifocus.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
public class ExamResultQuestion implements Serializable {

    private String examPaperId;

    private Integer questionType;

    private String answer;

    private Integer score;
}
