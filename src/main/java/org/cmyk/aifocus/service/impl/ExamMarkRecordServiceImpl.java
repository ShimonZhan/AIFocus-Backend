package org.cmyk.aifocus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cmyk.aifocus.dao.ExamMarkRecordMapper;
import org.cmyk.aifocus.entity.ExamMarkRecord;
import org.cmyk.aifocus.service.ExamMarkRecordService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class ExamMarkRecordServiceImpl extends ServiceImpl<ExamMarkRecordMapper, ExamMarkRecord> implements ExamMarkRecordService {
}
