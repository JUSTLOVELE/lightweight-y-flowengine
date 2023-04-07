package com.flowengine.server.backend.service.admin.impl;

import com.flowengine.common.utils.entity.PublicUserEntity;
import com.flowengine.server.backend.service.admin.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class TokenServiceImpl implements TokenService {


    @Override
    public String getLoginToken(PublicUserEntity user) {

        return null;
    }

    @Override
    public String getLoginToken(String userOpId) {
        return null;
    }

    @Override
    public boolean verifyToken(String token) {
        return false;
    }

    @Override
    public String reAccessToken(String refreshToken) {
        return null;
    }
}

