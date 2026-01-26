package com.kdu.smartHome.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.kdu.smartHome.exception.UnauthorizedException;

import com.kdu.smartHome.service.CurrentUserService;

/**
 * Resolves the current user id from the security context.
 */

@Service
public class CurrentUserServiceImpl implements CurrentUserService {
    @Override
    public Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new UnauthorizedException("No authenticated user");
        }
        try {
            return Long.parseLong(authentication.getPrincipal().toString());
        } catch (NumberFormatException ex) {
            throw new UnauthorizedException("Invalid user id in token");
        }
    }
}
