package kr.co.kh.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.kh.exception.InvalidTokenRequestException;
import kr.co.kh.service.CustomUserDetailsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Value("${app.jwt.header}")
    private String tokenRequestHeader;

    @Value("${app.jwt.header.prefix}")
    private String tokenRequestHeaderPrefix;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private JwtTokenValidator jwtTokenValidator;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);

            if (StringUtils.hasText(jwt) && jwtTokenValidator.validateToken(jwt)) {
                Long userId = jwtTokenProvider.getUserIdFromJWT(jwt);

                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails, jwt, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            filterChain.doFilter(request, response);  // 정상 처리 시 필터 체인 계속 진행

        } catch (InvalidTokenRequestException ex) {
            log.error("Invalid token: {}", ex.getMessage());

            // 예외 발생 시 응답 직접 작성하고 체인 종료
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json;charset=UTF-8");

            ObjectMapper mapper = new ObjectMapper();
            Map<String, String> errorResponse = Map.of(
                    "error", "invalid_token",
                    "message", ex.getMessage()
            );

            String json = mapper.writeValueAsString(errorResponse);
            response.getWriter().write(json);
            response.getWriter().flush();

        } catch (Exception ex) {
            // 예상치 못한 예외 처리 (로깅만, 필요시 다른 응답 처리 추가)
            log.error("Failed to set user authentication in security context: ", ex);
            filterChain.doFilter(request, response);  // 계속 진행하거나 필요시 에러 응답 처리
        }
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(tokenRequestHeader);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(tokenRequestHeaderPrefix)) {
            log.info("Extracted Token: {}", bearerToken);
            return bearerToken.replace(tokenRequestHeaderPrefix, "");
        }
        return null;
    }
}

