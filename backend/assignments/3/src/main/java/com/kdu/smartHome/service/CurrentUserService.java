package com.kdu.smartHome.service;

/**
 * Resolves the current user id from the security context.
 */

public interface CurrentUserService {
    /**
     * Returns the current authenticated user id.
     */
    Long getCurrentUserId();
}
