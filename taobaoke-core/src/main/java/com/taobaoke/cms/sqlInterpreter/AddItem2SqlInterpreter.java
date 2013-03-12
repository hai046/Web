package com.taobaoke.cms.sqlInterpreter;

import java.lang.annotation.Annotation;

import net.paoding.rose.jade.annotation.ShardBy;
import net.paoding.rose.jade.statement.Interpreter;
import net.paoding.rose.jade.statement.StatementRuntime;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.annotation.Order;

import com.taobaoke.cms.annotation.AddItem2Sql;

//按Spring语义规定，Order值越高该解释器越后执行
@Order(9001)
public class AddItem2SqlInterpreter implements Interpreter {

    private static final Log logger = LogFactory.getLog(AddItem2SqlInterpreter.class);

//    public SQLInterpreterResult interpret(DataSource dataSource, final String sql, Modifier modifier, Map<String, Object> parametersAsMap, final Object[] parametersAsArray) {
//        AddItem2Sql[] addAnno = modifier.getParameterAnnotations(AddItem2Sql.class);
//        int index = 0;
//        for( index=0; index < addAnno.length; index++ ){
//            if( addAnno[index] == null ) continue;
//            else break; 
//        }
//        if( index >= addAnno.length || addAnno[index] == null )
//            return null;
//        
//        int i = 0;
//        int rightIndex =  sql.indexOf('?');
//        while( i++ != index){
//            rightIndex = sql.indexOf('?', rightIndex+1);
//        }
//        
//        StringBuilder sb = new StringBuilder(sql.length());
//        sb.append(sql.substring(0, rightIndex)).append(parametersAsArray[index]).append(sql.substring(rightIndex + 1));
//        
//        if( logger.isDebugEnabled() ){
//            logger.debug("change sql [" + sql + "] 2 new sql [" + sb.toString() + "]"); 
//        }
//        
//        Object[] parametersAsArrayCopy = new Object[parametersAsArray.length - 1]; 
//        for( i=0; i < parametersAsArray.length; i++ ){
//            if( i < index  ) parametersAsArrayCopy[i] = parametersAsArray[i];
//            else if( i > index) parametersAsArrayCopy[i-1] = parametersAsArray[i]; 
//        }
//        return new RouterSQLInterpreterResult(sb.toString(), parametersAsArrayCopy);
//    }

    @Override
    public void interpret(StatementRuntime runtime) {
        
        Annotation[][] annotations = runtime.getMetaData().getMethod().getParameterAnnotations();
        
        int addItem2SqlIndex = -1;
        for (int index = 0; index < annotations.length; index++) {
            for (Annotation annotation : annotations[index]) {
                if (annotation instanceof AddItem2Sql) {
                    if (addItem2SqlIndex >= 0) {
                        throw new IllegalArgumentException("duplicated @" + ShardBy.class.getName());
                    }
                    addItem2SqlIndex = index;
                    break;
                }
            }
        }
        if( addItem2SqlIndex == -1 ){
            return ;
        }
        
        int i = 0;
        int rightIndex =  runtime.getSQL().indexOf('?');
        while( i++ != addItem2SqlIndex){
            rightIndex = runtime.getSQL().indexOf('?', rightIndex+1);
        }
        
        StringBuilder sb = new StringBuilder(runtime.getSQL().length());
        sb.append(runtime.getSQL().substring(0, rightIndex)).append(runtime.getArgs()[addItem2SqlIndex]).append(runtime.getSQL().substring(rightIndex + 1));
        
        if( logger.isDebugEnabled() ){
            logger.debug("change sql [" + runtime.getSQL() + "] 2 new sql [" + sb.toString() + "]"); 
        }
        
        Object[] parametersAsArrayCopy = new Object[runtime.getArgs().length - 1]; 
        for( i=0; i < runtime.getArgs().length; i++ ){
            if( i < addItem2SqlIndex  ) parametersAsArrayCopy[i] = runtime.getArgs()[i];
            else if( i > addItem2SqlIndex) parametersAsArrayCopy[i-1] = runtime.getArgs()[i]; 
        }
//        return new RouterSQLInterpreterResult(sb.toString(), parametersAsArrayCopy);
        runtime.setSQL(sb.toString());
        runtime.setArgs(parametersAsArrayCopy);
        return ;
        
    }

}
