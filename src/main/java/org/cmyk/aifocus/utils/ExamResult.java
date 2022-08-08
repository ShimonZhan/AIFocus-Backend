package org.cmyk.aifocus.utils;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class ExamResult implements Serializable {

    private String studentId;

    private Integer state;

    private Integer blankScore;

    private Integer judgeScore;

    private Integer multipleScore;

    private Integer singleScore;

    private Integer subjectiveScore;

    private Integer totalScore;

    private List<ExamResultQuestion> questions;
}
