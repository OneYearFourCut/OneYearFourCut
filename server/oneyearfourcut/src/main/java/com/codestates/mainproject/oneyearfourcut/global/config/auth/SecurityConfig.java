package com.codestates.mainproject.oneyearfourcut.global.config.auth;

import com.codestates.mainproject.oneyearfourcut.domain.member.service.MemberService;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.filter.JwtVerificationFilter;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.handler.MemberAuthenticationEntryPoint;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.handler.OAuth2MemberSuccessHandler;
import com.codestates.mainproject.oneyearfourcut.global.config.auth.jwt.JwtTokenizer;
import com.codestates.mainproject.oneyearfourcut.domain.refreshToken.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2LoginAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String clientId;
    @Value("${spring.security.oauth2.client.registration.kakao.client-secret}")
    private String clientSecret;

    private final JwtTokenizer jwtTokenizer;
    private final MemberService memberService;
    private final RefreshTokenService refreshTokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().sameOrigin()
                .and()
                .csrf().disable()
                .cors(withDefaults())
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .formLogin().disable()
                .httpBasic().disable()
                .exceptionHandling()
                .authenticationEntryPoint(new MemberAuthenticationEntryPoint()) //인증이 실패할경우 생기는 에러를 처리하는 핸들러
//                .accessDeniedHandler(new MemberAccessDeniedHandler()) // 인증에 성공했지만 해당 리소스에 대한 권한이 없는 경우 호출 -> 현재 필요 없을듯?
                .and()
                .apply(new CustomFilterConfigurer())
                .and()
                .authorizeHttpRequests(authorize -> authorize
                                .antMatchers(HttpMethod.GET, "/galleries/**").permitAll()
                                .antMatchers(HttpMethod.GET, "/").permitAll()
                                .antMatchers(HttpMethod.GET, "/receive-token").permitAll()
                                .antMatchers(HttpMethod.GET, "/docs/index.html").permitAll()
                                .antMatchers(HttpMethod.GET, "/auth/refresh").permitAll()
                                .antMatchers("/h2/**").permitAll()
                                .antMatchers("/ws/stomp/**").hasRole("USER")
                                .antMatchers("/sub/**").hasRole("USER")
                                .antMatchers("/pub/**").hasRole("USER")
//                                .antMatchers("/ws/**").permitAll() // -> websocket test
//                        .antMatchers("/members/**").hasRole("USER")
//                        .antMatchers("/galleries/**").hasRole("USER")
//                        .antMatchers(HttpMethod.DELETE, "/galleries/**").hasRole("USER")

                                .anyRequest().hasRole("USER")
                )
                .oauth2Login(oauth2 -> oauth2
                        .successHandler(new OAuth2MemberSuccessHandler(jwtTokenizer, memberService, refreshTokenService)));
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOriginPatterns(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);
        configuration.addExposedHeader("Authorization");
        configuration.addExposedHeader("refresh");  //커스텀 헤더를 보이도록 하는 것

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();   //  CorsConfigurationSource 인터페이스의 구현 클래스인 UrlBasedCorsConfigurationSource 클래스의 객체를 생성한다.
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    public class CustomFilterConfigurer extends AbstractHttpConfigurer<CustomFilterConfigurer, HttpSecurity> {
        @Override
        public void configure(HttpSecurity builder) throws Exception {
            JwtVerificationFilter jwtVerificationFilter = new JwtVerificationFilter(jwtTokenizer);
            builder.addFilterAfter(jwtVerificationFilter, OAuth2LoginAuthenticationFilter.class);
        }
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        var clientRegistration = clientRegistration();

        return new InMemoryClientRegistrationRepository(clientRegistration);
    }

    private ClientRegistration clientRegistration() {
        return ThirdOAuth2Provider
                .KAKAO
                .getBuilder("kakao")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }
}
