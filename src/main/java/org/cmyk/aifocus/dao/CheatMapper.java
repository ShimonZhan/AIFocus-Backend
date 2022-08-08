package org.cmyk.aifocus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Mapper;
import org.cmyk.aifocus.config.MybatisRedisCache;
import org.cmyk.aifocus.entity.Cheat;

@Mapper
@CacheNamespace(implementation = MybatisRedisCache.class, eviction = MybatisRedisCache.class)
public interface CheatMapper extends BaseMapper<Cheat> {
}
