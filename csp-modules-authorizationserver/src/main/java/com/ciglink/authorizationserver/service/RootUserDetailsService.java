package com.ciglink.authorizationserver.service;

import com.ciglink.authorizationserver.domain.dto.RootUserInfoDto;
import com.ciglink.authorizationserver.enums.RootUserInfoEnum;

/**
 * @author WANGKairen
 * @since 2022-12-23 10:58:48
 **/
@FunctionalInterface
public interface RootUserDetailsService {

    default RootUserInfoDto loadRootUserByUsername(String username) {
        //if (!RootUserInfoEnum.ROOT_USERNAME.getCode().equals(username)) {
        //    throw new IllegalArgumentException("仅提供给root用户");
        //}
        return this.doLoadRootUser(username);
    }

    /**
     * Load user by username user details.
     *
     * @param username the username
     * @return the user details
     */
    RootUserInfoDto doLoadRootUser(String username);
}
