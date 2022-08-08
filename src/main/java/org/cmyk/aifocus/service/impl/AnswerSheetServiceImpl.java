package org.cmyk.aifocus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.cmyk.aifocus.dao.AnswerSheetMapper;
import org.cmyk.aifocus.entity.AnswerSheet;
import org.cmyk.aifocus.service.AnswerSheetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class AnswerSheetServiceImpl extends ServiceImpl<AnswerSheetMapper, AnswerSheet> implements AnswerSheetService {

}
