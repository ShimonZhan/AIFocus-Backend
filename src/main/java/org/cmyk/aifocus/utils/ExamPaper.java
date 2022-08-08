package org.cmyk.aifocus.utils;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.cmyk.aifocus.entity.*;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExamPaper implements Serializable {
    private List<ExamPaperBlank> blanks;
    private List<ExamPaperSingle> singles;
    private List<ExamPaperMultiple> multiples;
    private List<ExamPaperSubjective> subjectives;
    private List<ExamPaperJudge> judges;
}
