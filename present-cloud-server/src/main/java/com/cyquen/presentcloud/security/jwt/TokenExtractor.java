package com.cyquen.presentcloud.security.jwt;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface TokenExtractor {

    Authentication extract(HttpServletRequest request);
}
