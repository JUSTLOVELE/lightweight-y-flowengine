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
    public void setLoginToken(PublicUserEntity user);

    /**
     * 根据用户主键获得token
     * @param userOpId
     */
    public void setLoginToken(String userOpId);

    /**
     * 生成并获取签名
     * @param timestamp
     * @param accessToken
     * @param refreshToken
     * @return
     */
    public String createAndGetSign(String timestamp, String accessToken, String refreshToken);

    /**
     * 验证accessToken是否有效
     * @param sign
     * @param user
     * @return
     */
    public boolean verifyToken(String sign, PublicUserEntity user);

    /**
     * 根据refresh_token重新获得并刷新token
     * @param accessToken
     * @param sign
     * @return
     */
    public String reRefreshToken(String accessToken, String sign);
}
