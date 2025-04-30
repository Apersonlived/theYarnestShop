package com.theYarnestShop.filter;

import java.io.IOException;

import com.theYarnestShop.util.SessionUtil;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter(asyncSupported = true, urlPatterns = "/*")
public class AuthenticationFilter implements Filter {
		private static final String LOGIN = "/Login";
		private static final String REGISTER = "/Register";
		private static final String LANDING = "/Landing";
		private static final String PROFILE = "/Profile";
		private static final String ROOT = "/";

		@Override
		public void init(FilterConfig filterConfig) throws ServletException {
			// Initialization logic
		}

		@Override
		public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
				throws IOException, ServletException {

			// Cast the request and response to HttpServletRequest and HttpServletResponse
			HttpServletRequest req = (HttpServletRequest) request;
			HttpServletResponse res = (HttpServletResponse) response;

			// Get the requested URI
			String uri = req.getRequestURI();

			if (uri.endsWith(".png") || uri.endsWith(".jpg") || uri.endsWith(".css") 
					|| uri.endsWith(LANDING) || uri.endsWith(ROOT)|| 
					uri.endsWith("dashboard")|| uri.endsWith("update")) {
				chain.doFilter(request, response);
				return;
			}

			// Get the session and check for log in
			boolean isLoggedIn = SessionUtil.getAttribute(req, "username") != null;

			if (!isLoggedIn) {
				if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
					chain.doFilter(request, response);
				} else {
					res.sendRedirect(req.getContextPath() + LOGIN);
				}
				
			} else {
				if (uri.endsWith(LOGIN) || uri.endsWith(REGISTER)) {
					res.sendRedirect(req.getContextPath() + PROFILE);
				} else {
					chain.doFilter(request, response);
				}
			}
		}

		@Override
		public void destroy() {
			// Cleanup logic
		}
}
