package com.example.demo.config;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.base.Strings;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpInterceptorLog implements HandlerInterceptor {

	/**
	 * Executed before actual handler is executed
	 **/
	@Override
	public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler)
			throws Exception {
		log.info("[preHandle][" + request + "]" + "[" + request.getMethod() + "]" + request.getRequestURI()
				+ getParameters(request));
		response.addHeader("tokenxD", "holiii");
		return true;
	}

	/**
	 * Executed before after handler is executed
	 **/
	@Override
	public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
			final ModelAndView modelAndView) throws Exception {
		log.info("[postHandle][" + request + "]");
		response.addHeader("tokenxD", "holiii");
	}

	/**
	 * Executed after complete request is finished
	 **/
	@Override
	public void afterCompletion(final HttpServletRequest request, final HttpServletResponse response,
			final Object handler, final Exception ex) throws Exception {
		log.info("HttpServletResponse {} ", response.getHeader("Content-Type"));
		response.setHeader("Content-Type", "application/json");
		if (ex != null)
			ex.printStackTrace();
		log.info("[afterCompletion][" + request + "][exception: " + ex + "]");
	}

	private String getParameters(final HttpServletRequest request) {
		final StringBuffer posted = new StringBuffer();
		final Enumeration<?> e = request.getParameterNames();
		if (e != null)
			posted.append("?");
		while (e != null && e.hasMoreElements()) {
			if (posted.length() > 1)
				posted.append("&");
			final String curr = (String) e.nextElement();
			posted.append(curr).append("=");
			if (curr.contains("password") || curr.contains("answer") || curr.contains("pwd")) {
				posted.append("*****");
			} else {
				posted.append(request.getParameter(curr));
			}
		}

		final String ip = request.getHeader("X-FORWARDED-FOR");
		final String ipAddr = (ip == null) ? getRemoteAddr(request) : ip;
		if (!Strings.isNullOrEmpty(ipAddr))
			posted.append("&_psip=" + ipAddr);
		return posted.toString();
	}

	private String getRemoteAddr(final HttpServletRequest request) {
		final String ipFromHeader = request.getHeader("X-FORWARDED-FOR");
		if (ipFromHeader != null && ipFromHeader.length() > 0) {
			log.debug("ip from proxy - X-FORWARDED-FOR : " + ipFromHeader);
			return ipFromHeader;
		}
		return request.getRemoteAddr();
	}

}
