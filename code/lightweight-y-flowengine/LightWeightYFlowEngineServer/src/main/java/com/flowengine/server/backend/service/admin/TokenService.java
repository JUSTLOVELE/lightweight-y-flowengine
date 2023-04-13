package com.flowengine.server.backend.service.admin;

import com.flowengine.common.utils.entity.PublicUserEntity;

/**
 * @Description: token服务
 * @author yangzl 2019-11-29
 * @version 1.00.00
 * @history:
 */
public interface TokenService {

    /**
     * 根据用户主键获得token
     * @param user
     */
    public void getLoginToken(PublicUserEntity user);

    /**
     * 根据用户主键获得token
     * @param userOpId
     */
    public void getLoginToken(String userOpId);

    /**
     * 验证accessToken是否有效
     * @param accessToken
     * @return
     */
    public boolean verifyToken(String accessToken);

    /**
     * 根据refresh_token重新获得并刷新token
     * @param token
     * @return
     */
    public String reRefreshToken(String token);

}
