package com.dvb.forum.util;

import jakarta.servlet.http.HttpServletRequest;

import java.util.StringTokenizer;

public final class CommonUtil {

    private CommonUtil() {

    }

    public static String retrieveIpAddress(HttpServletRequest httpServletRequest) {
        String xForwardedForHeader = httpServletRequest.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return httpServletRequest.getRemoteAddr();
        } else {
            return new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
        }
    }

}
