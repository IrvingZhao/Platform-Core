package cn.irving.zhao.platform.core.mybatis;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

public interface CustomMapper<T> extends Mapper<T>, IdsMapper<T> {
}
