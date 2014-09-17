package com.fantasy.framework.lucene.dao.mybatis;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;

import com.fantasy.framework.lucene.backend.EntityChangedListener;
import com.fantasy.framework.lucene.backend.IndexChecker;

/**
 * 
 * @功能描述  还有问题，不推荐使用
 * @author 李茂峰
 * @since 2013-1-30 下午12:58:37
 * @version 1.0
 */
@Deprecated
public class EntityChangedInterceptor implements Interceptor {

	static int MAPPED_STATEMENT_INDEX = 0;
	static int PARAMETER_INDEX = 1;

	private Map<Class<?>, EntityChangedListener> entityChangeds = new HashMap<Class<?>, EntityChangedListener>();

	private EntityChangedListener getEntityChangedListener(Class<?> clazz) {
		if (!entityChangeds.containsKey(clazz)) {
			entityChangeds.put(clazz, new EntityChangedListener(clazz));
		}
		return entityChangeds.get(clazz);
	}

	public Object intercept(Invocation invocation) throws Throwable {
		Object[] queryArgs = invocation.getArgs();
		MappedStatement ms = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
		Object entity = invocation.getArgs()[PARAMETER_INDEX];
		Class<? extends Object> clazz = entity.getClass();
		try {
			return invocation.proceed();
		} finally {
			if (SqlCommandType.INSERT.equals(ms.getSqlCommandType())) {
				if (IndexChecker.hasIndexed(clazz))
					getEntityChangedListener(clazz).entityInsert(entity);
			} else if (SqlCommandType.UPDATE.equals(ms.getSqlCommandType())) {
				if (!IndexChecker.hasIndexed(clazz))
					getEntityChangedListener(clazz).entityUpdate(entity);
			} else if (SqlCommandType.DELETE.equals(ms.getSqlCommandType())) {

			}
		}
	}

	public Object plugin(Object target) {
		return Plugin.wrap(target, this);
	}

	public void setProperties(Properties properties) {
	}

}
