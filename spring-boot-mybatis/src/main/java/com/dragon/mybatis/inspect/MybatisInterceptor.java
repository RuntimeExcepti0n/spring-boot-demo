package com.dragon.mybatis.inspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.*;
import org.apache.ibatis.plugin.*;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;
import java.util.Properties;

/**
 * code by me
 * <p>
 * Data:2017/8/10 Time:11:28
 * User:lbh
 */
@Intercepts
        ({
                @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        })
@Component
public class MybatisInterceptor implements Interceptor {
    private static Log logger = LogFactory.getLog(MybatisInterceptor.class);

//    @Value("${fdn.open}")
    private boolean fdnOpen = true;

    static int MAPPED_STATEMENT_INDEX = 0;// 这是对应上面的args的序号
    static int PARAMETER_INDEX = 1;
    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        if(!fdnOpen){
            return invocation.proceed();
        }
        final Object[] queryArgs = invocation.getArgs();
        final MappedStatement mappedStatement = (MappedStatement) queryArgs[MAPPED_STATEMENT_INDEX];
        final Object parameter = queryArgs[PARAMETER_INDEX];
        //sql类型
        SqlCommandType sqlCommandType = mappedStatement.getSqlCommandType();
        if (SqlCommandType.INSERT.equals(sqlCommandType)) {
            //插入操作时，自动插入env
//            Field fieldCreate = object.getClass().getDeclaredField("createdDate");
            Field[] declaredFields = parameter.getClass().getSuperclass().getDeclaredFields();
            for (Field field : declaredFields){
                System.out.println(field.getName());
            }
            Field fieldCreate = parameter.getClass().getSuperclass().getDeclaredField("createdDate");
            fieldCreate.setAccessible(true);
            fieldCreate.set(parameter, new Date());
        }
        BoundSql boundSql = mappedStatement.getBoundSql(parameter);

        String sql = boundSql.getSql();
        if(!sql.contains("created_date")){
            sql = sql.substring(0,sql.indexOf(")"))+",created_date"+sql.substring(sql.indexOf(")"),sql.lastIndexOf(")"))+",?)";
        }
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
//        ParameterMapping parameterMapping = ParameterMapping.Builder(mappedStatement.getConfiguration(),"createdDate", JdbcType.TIMESTAMP.getClass())

        ParameterMapping.Builder builder = new ParameterMapping.Builder(mappedStatement.getConfiguration(),"createdDate",java.util.Date.class);
        ParameterMapping build = builder.build();
        parameterMappings.add(build);


        // 重新new一个查询语句对像
        BoundSql newBoundSql = new BoundSql(mappedStatement.getConfiguration(), sql, parameterMappings, boundSql.getParameterObject());
        // 把新的查询放到statement里
        MappedStatement newMs = copyFromMappedStatement(mappedStatement, new BoundSqlSqlSource(newBoundSql));
//        for (ParameterMapping mapping : parameterMappings) {
//            String prop = mapping.getProperty();
//            if (boundSql.hasAdditionalParameter(prop)) {
//                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
//            }
//        }
        queryArgs[MAPPED_STATEMENT_INDEX] = newMs;
        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {

    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, SqlSource newSqlSource) {
        MappedStatement.Builder builder = new MappedStatement.Builder(ms.getConfiguration(), ms.getId(), newSqlSource, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        if (ms.getKeyProperties() != null && ms.getKeyProperties().length > 0) {
            builder.keyProperty(ms.getKeyProperties()[0]);
        }
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    public static class BoundSqlSqlSource implements SqlSource {
        private BoundSql boundSql;
        public BoundSqlSqlSource(BoundSql boundSql) {
            this.boundSql = boundSql;
        }
        public BoundSql getBoundSql(Object parameterObject) {
            return boundSql;
        }
    }
}