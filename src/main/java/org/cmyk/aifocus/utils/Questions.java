package org.cmyk.aifocus.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.cmyk.aifocus.entity.*;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Questions implements Serializable {
    private List<QuestionBlank> questionBlanks;
    private List<QuestionSingle> questionSingles;
    private List<QuestionMultiple> questionMultiples;
    private List<QuestionSubjective> questionSubjectives;
    private List<QuestionJudge> questionJudges;
}
