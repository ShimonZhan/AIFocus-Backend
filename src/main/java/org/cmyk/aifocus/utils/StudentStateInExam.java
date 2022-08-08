package org.cmyk.aifocus.utils;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class StudentStateInExam implements Serializable {

    private String studentId;

    private String studentName;

    private Integer state;

    private Boolean isSubmit;

    private Boolean isOnline;
}
