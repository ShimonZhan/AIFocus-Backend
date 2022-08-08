package org.cmyk.aifocus.utils;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: ZhanX
 * @Date: 2021-03-31 15:20:53
 */
@Slf4j
public class QuestionListener<T extends ExcelExtra,V extends IService<T>> extends AnalysisEventListener<T> {
    /**
     * 每隔5条存储数据库，实际使用中可以3000条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 3000;
    private List<T> list = new ArrayList<>();

    private V service;

    private String teacherId;

    public QuestionListener(V service, String teacherId) {
        this.service = service;
        this.teacherId = teacherId;
    }

    /**
     * 这个每一条数据解析都会来调用
     **/
    @Override
    public void invoke(T question, AnalysisContext analysisContext) {
        question.updateTeacherId(teacherId);
        list.add(question);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (list.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
    }

    private void saveData() {
        service.saveBatch(list);
//        list.forEach(e->log.info(e.toString()));
//        log.info(" ");
    }
}
