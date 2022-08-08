package org.cmyk.aifocus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.cmyk.aifocus.config.MybatisRedisCache;
import org.cmyk.aifocus.entity.Exam;
import org.cmyk.aifocus.utils.StudentStateInExam;

import java.util.List;

@Mapper
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
public interface ExamMapper extends BaseMapper<Exam> {

    @Select("select b.* from exam_mark_record a,exam b where a.student_id=#{studentId} and b.id=a.exam_id and b.status!=0 order by b.create_time desc")
    Page<Exam> selectStudentExams(Page<Exam> page, @Param("studentId") String studentId);

    @Select("select a.* from exam a, exam_mark_record b where b.student_id=#{studentId} and a.id=b.exam_id and a.status=2")
    List<Exam> selectExamsInExamStatus(@Param("studentId") String studentId);

    @Select("select b.id 'student_id', b.name 'student_name',b.is_online,a.is_submit,a.state from exam_mark_record a, user b where a.exam_id=#{examId} and a.student_id=b.id")
    List<StudentStateInExam> selectStudentsInExam(@Param("examId") String examId);
}
