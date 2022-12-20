package com.codestates.mainproject.oneyearfourcut.global.config.auth.filter;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//카카오로 로그인 요청을 보내기 전에 들어온 요청의 Host를 확인해서
//localhost:3000이면 다른 URI로 토큰값을 redirect해주기 위해 response객체에 커스텀 헤더를 추가
public class OAuth2LoginHandlingFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String host = request.getHeader("Host");
        if (host != null) { //테스트 코드에서 nullPointerException생겨서 한번 더 감싸줌
            if (host.equals("localhost:8080") || host.equals("localhost:3000")) {
                response.setHeader("OriginHost", host);
            }
        }
        filterChain.doFilter(request, response);
    }
}
