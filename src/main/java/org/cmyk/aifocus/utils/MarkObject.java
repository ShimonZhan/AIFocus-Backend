package org.cmyk.aifocus.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.cmyk.aifocus.entity.AnswerSheet;

import java.io.Serializable;

@Data
@AllArgsConstructor
@Builder
public class MarkObject<T> implements Serializable {

    private AnswerSheet answerSheet;

    private T examPaper;
}
