package com.codestates.mainproject.oneyearfourcut.global.config.auth.handler;

import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Member;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.MemberStatus;
import com.codestates.mainproject.oneyearfourcut.domain.member.entity.Role;
import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.JwtTokenizer;
import com.codestates.mainproject.oneyearfourcut.domain.refreshToken.entity.RefreshToken;
import com.codestates.mainproject.oneyearfourcut.domain.refreshToken.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class OAuth2MemberSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        var oAuth2User = (OAuth2User)authentication.getPrincipal();
        Map<String, Object> attributes = oAuth2User.getAttributes();

        // kakao는 kakao_account에 유저정보가 있다. (email)
        Map<String, Object> kakaoAccount = (Map<String, Object>)attributes.get("kakao_account");
        // kakao_account안에 또 profile이라는 JSON객체가 있다. (nickname, profile_image)
        Map<String, Object> kakaoProfile = (Map<String, Object>)kakaoAccount.get("profile");

        String email = (String) kakaoAccount.get("email");
        String nickname = (String) kakaoProfile.get("nickname");
        String profile = (String) kakaoProfile.get("profile_image_url");
        List<String> authorities = List.of("USER"); //일단 모든유저 역할 통일

        saveMember(email, nickname, profile);
        redirect(request, response, email, authorities);
    }

    private void saveMember(String email, String nickname, String profile) {
        Member member = Member.builder()
                .email(email)
                .nickname(nickname)
                .profile(profile)
                .role(Role.USER)
                .status(MemberStatus.ACTIVE)
                .build();
        memberService.createMember(member);
    }

    private void redirect(HttpServletRequest request, HttpServletResponse response, String username, List<String> authorities) throws IOException {
        Member member = memberService.findMemberByEmail(username);  //memberId를 accessToken에 담고, refreshToken을 저장하기위함
        String accessToken = delegateAccessToken(authorities, member);
        String refreshToken = delegateRefreshToken(member);

        String uri = createURI(accessToken, refreshToken).toString();
        getRedirectStrategy().sendRedirect(request, response, uri);
    }

    private String delegateAccessToken(List<String> authorities, Member member) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", member.getEmail());
        claims.put("roles", authorities);
        claims.put("id", member.getMemberId());

        String subject = member.getEmail(); //subject에 이메일을 저장

        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String accessToken = jwtTokenizer.generateAccessToken(claims, subject, base64EncodedSecretKey);

        return "Bearer " + accessToken;
    }

    private String delegateRefreshToken(Member member) {
        String subject = member.getEmail();
        String base64EncodedSecretKey = jwtTokenizer.encodeBase64SecretKey(jwtTokenizer.getSecretKey());

        String refreshToken = jwtTokenizer.generateRefreshToken(subject, base64EncodedSecretKey);

        boolean tokenExist = refreshTokenService.isTokenExist(subject);
        if (tokenExist) refreshTokenService.updateRefreshToken(subject, refreshToken);
        else refreshTokenService.saveRefreshToken(member, refreshToken);    //refresh token 생성과 함께 저장

        return refreshToken;
    }

    private URI createURI(String accessToken, String refreshToken) {
        MultiValueMap<String, String> queryParams = new LinkedMultiValueMap<>();
        queryParams.add("access_token", accessToken);
        queryParams.add("refresh_token", refreshToken);

        return UriComponentsBuilder
                .newInstance()
                .scheme("http")
                .host("localhost")
                .port(3000)
                .path("localStorage")
                .queryParams(queryParams)
                .build()
                .toUri();
    }

}