package com.flowengine.server.core;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.List;
import java.util.Map;

/**
 * @Description:
 * @author yangzl 2019-11-06
 * @version 1.00.00
 * @history:
 */
public abstract class BaseAction extends Base {

    private final static Log _logger = LogFactory.getLog(BaseAction.class);

	/*@Autowired
	@Qualifier("reverseDependencyInjectionHospitalFactoryImp")
	protected BaseReverseDependencyInjectionHospitalFactory _factory;*/


    @SuppressWarnings("rawtypes")
    public void clearList(List list){
        list.clear();
        list = null;
    }

    public void clearListMap(List<Map<String, Object>> list){
        for(Map<String, Object> map : list){
            clearMap(map);
        }
        clearList(list);
    }
}

