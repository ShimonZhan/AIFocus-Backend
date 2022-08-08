package org.cmyk.aifocus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.cmyk.aifocus.entity.MessageLog;

@Mapper
//@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
public interface MessageLogMapper extends BaseMapper<MessageLog> {
}
