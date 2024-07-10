package com.ykb.hmn.mig.common.transformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.ykb.harmoni.inf.core.service.intf.HmnServiceException;
import com.ykb.hmn.inf.core.qe.QueryExecutor;
import com.ykb.hmn.inf.core.service.AbstractService;
import com.ykb.hmn.mig.common.util.StringUtils;


public class QuerySupport extends AbstractService{

	private static QueryExecutor queryExecutor = QueryExecutor.getInstance();
	
	public static <T> List<T> query(String queryName, QueryTransformer<T> queryTransformer) throws HmnServiceException{

		List<T> result = new ArrayList<T>();
		List<Map<String,Object>> found = queryExecutor.list(queryName, queryTransformer.prepare());
		for(Iterator<Map<String,Object>> it = found.iterator();it.hasNext();){
			result.add(queryTransformer.convert(it.next()));
		}
		return result;
	}

	public static <T> T querySingleResult(String queryName, QueryTransformer<T> queryTransformer) throws HmnServiceException{		
		List<Map<String,Object>> found = queryExecutor.list(queryName, queryTransformer.prepare());
		for(Iterator<Map<String,Object>> it = found.iterator();it.hasNext();){
			return (T)queryTransformer.convert(it.next());
		}
		return null;
	}
	
	
	public static <T> T callProcedure(String procedureName,String packageName, ProcedureTransformer<T> transformer) throws HmnServiceException{	
		Map<String,Object> found = null;
		if(!StringUtils.hasText(packageName)){
			found = queryExecutor.executeStoredProcedure(procedureName, transformer.prepare());
		}else{
			found = queryExecutor.executeStoredProcedure(procedureName, packageName, transformer.prepare());
		}
		return transformer.convert(found);		
	}	
	
	public static <T> List<T> dynamicQuery(String queryName, DynamicQueryTransformer<T> queryTransformer) throws HmnServiceException{

		DynamicQueryTransformerDTO dto = queryTransformer.prepare();
		List<T> result = new ArrayList<T>();
		List<Map<String,Object>> found = queryExecutor.listWithDynamicQuery(queryName, dto.getMap(), dto.getFlagList());
		for(Iterator<Map<String,Object>> it = found.iterator();it.hasNext();){
			result.add(queryTransformer.convert(it.next()));
		}
		return result;
	}
	
	/**
	 * Used at data access level paging
	 * 
	 * @author U0T2156 Fatih Eser
	 */
	public static <T> List<T> dynamicQuery(String queryName, DynamicQueryTransformer<T> queryTransformer, Integer page) throws HmnServiceException{

		DynamicQueryTransformerDTO dto = queryTransformer.prepare();
		List<T> result = new ArrayList<T>();
		List<Map<String,Object>> found = queryExecutor.listWithDynamicQuery(queryName, dto.getMap(), dto.getFlagList(),page);
		for(Iterator<Map<String,Object>> it = found.iterator();it.hasNext();){
			result.add(queryTransformer.convert(it.next()));
		}
		return result;
	}
	 
	/**
	 * Used at data access level paging
	 * Returns row count of given query
	 * 
	 * @author U0T2156 Fatih Eser
	 */
	public static <T> Integer dynamicQueryCount(String queryName, DynamicQueryTransformer<T> queryTransformer) throws HmnServiceException{
		 DynamicQueryTransformerDTO dto = queryTransformer.prepare();
		 return queryExecutor.getDynamicQueryRowCount(queryName, dto.getMap(), dto.getFlagList());
	}
	
	public static <T> T dynamicQuerySingleResult(String queryName, DynamicQueryTransformer<T> queryTransformer) throws HmnServiceException{

		DynamicQueryTransformerDTO dto = queryTransformer.prepare();
		List<Map<String,Object>> found = queryExecutor.listWithDynamicQuery(queryName, dto.getMap(), dto.getFlagList());
		for(Iterator<Map<String,Object>> it = found.iterator();it.hasNext();){
			return queryTransformer.convert(it.next());
		}
		return null;//FIXME kontrol et
	}
	
	public static <T> List<T> query(String queryName, QueryTransformer<T> queryTransformer, Integer page) throws HmnServiceException{

        List<T> result = new ArrayList<T>();
        List<Map<String,Object>> found = queryExecutor.list(queryName, queryTransformer.prepare(), page);
        for(Iterator<Map<String,Object>> it = found.iterator();it.hasNext();){
            result.add(queryTransformer.convert(it.next()));
        }
        return result;
    }
        
    public static <T> Integer queryGetCount(String queryName, QueryTransformer<T> queryTransformer) throws HmnServiceException{    
        return queryExecutor.getNamedQueryRowCount(queryName, queryTransformer.prepare());
    }
}
