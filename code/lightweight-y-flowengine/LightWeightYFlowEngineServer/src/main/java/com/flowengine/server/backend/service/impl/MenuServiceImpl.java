package com.flowengine.server.backend.service.impl;


import com.flowengine.server.backend.dao.MenuDao;
import com.flowengine.server.backend.service.MenuService;
import com.flowengine.server.core.BaseService;
import com.flowengine.server.model.MenuVO;
import com.flowengine.server.model.UserCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Description:
 * @author yangzl 2019-12-03
 * @version 1.00.00
 * @history:
 */
@Service(value = "menuServiceImpl")
@Transactional(rollbackFor = Exception.class)
public class MenuServiceImpl extends BaseService implements MenuService {
	
	@Autowired
	private MenuDao _menuDao;

	@Override
	public MenuVO createMenuTree(UserCache user) {
		return _menuDao.createMenuTree(user);
	}

}
