package com.flowengine.server.backend.service.admin;
/**
 * @Description: token服务
 * @author yangzl 2019-11-29
 * @version 1.00.00
 * @history:
 */
public interface TokenService {

    /**
     * 根据用户主键获得token
     * @param userOpId
     * @return
     */
    public String getLoginToken(String userOpId);

    /**
     * 验证token是否有效
     * @param token
     * @return
     */
    public boolean verifyToken(String token);

    /**
     * 根据refresh_token重新获得并刷新token
     * @param refreshToken
     * @return
     */
    public String reAccessToken(String refreshToken);

}
