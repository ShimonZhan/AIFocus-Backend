package org.cmyk.aifocus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.cmyk.aifocus.config.MybatisRedisCache;
import org.cmyk.aifocus.entity.Clazz;
import org.cmyk.aifocus.entity.User;

@Mapper
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
public interface ClassMapper extends BaseMapper<Clazz> {
    @Select("select a.* from class a,user_class b where b.user_id=#{userId} and a.id=b.class_id order by a.create_time desc")
    Page<Clazz> selectUserClassPage(Page<Clazz> page, @Param("userId") String userId);

    @Select("select a.* from user a, user_class b where b.class_id=#{classId} and a.id=b.user_id and b.user_type=0")
    Page<User> selectStudentsFromClassPage(Page<User> page, @Param("classId") String classId);
}
