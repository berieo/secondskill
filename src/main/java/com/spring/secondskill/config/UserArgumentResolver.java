package com.spring.secondskill.config;

import com.spring.secondskill.access.UserContext;
import com.spring.secondskill.domain.SecondsKillUser;
import com.spring.secondskill.service.SecondsKillUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/*
    从HttpRequest中解析出Cookie
 */

@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {


    @Autowired
    SecondsKillUserService secondsKillUserService;

    /*
        重写方法判断是否为所需类
     */
    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == SecondsKillUser.class;
    }

    /*
        重写resolveArgument方法
        通过token返回SecondsKillUser
     */
    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer,
                                  NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory)
            throws Exception {

        HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse httpServletResponse = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken = httpServletRequest.getParameter(SecondsKillUserService.COOKIE_NAME_TOKEN);
        String cookieToken = getCookieValue(httpServletRequest, SecondsKillUserService.COOKIE_NAME_TOKEN);

        if(StringUtils.isEmpty(paramToken) && StringUtils.isEmpty(cookieToken)){
            return null;
        }
        String token = StringUtils.isEmpty(paramToken) ? cookieToken : paramToken;
        return secondsKillUserService.getByToken(httpServletResponse, token);
    }

    /*
        从Request中获取cookie
     */
    private String getCookieValue(HttpServletRequest request, String cookiName) {
        Cookie[]  cookies = request.getCookies();
        System.out.println("here");
        if(cookies == null || cookies.length <= 0){
            return null;
        }
        for(Cookie cookie : cookies) {
            if(cookie.getName().equals(cookiName)) {
                return cookie.getValue();
            }
        }
        return null;
    }
//    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
//                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
//        return UserContext.getUser();
//    }
}
