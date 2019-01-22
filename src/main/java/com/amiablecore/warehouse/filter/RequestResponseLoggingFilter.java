package com.pravin.test;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class RequestResponseLoggingFilter implements Filter {

	private final static Logger logger = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);

	Set<String> loginToken = new HashSet<String>();

	@Autowired
	private Encoder encode;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		logger.info("Starting a transaction for req : {}", req.getRequestURI());
		String header = req.getHeader("name");
		if (req.getRequestURI().contains("/rest/user/")) {
			ServletInputStream inputStream = req.getInputStream();
			byte[] byteArray = IOUtils.toByteArray(inputStream);
			// response.

			String encoded = null;
			logger.info("Payload : {}", new String(byteArray));
			try {
				JSONObject obj = new JSONObject(new String(byteArray));
				String userId = (String) obj.get("userId");
				encoded = encode.encodeLogindetails(userId);
			} catch (Exception e) {
				logger.error("", e);
			}

			HttpServletResponse res = (HttpServletResponse) response;
			res.addHeader("accesstoken", encoded);

		}
		if (req.getRequestURI().contains("/rest/logout/")) {

			logger.info("Logout done : ");

		}

		logger.info("Name Header : {}", header);
		chain.doFilter(request, response);
		logger.info("Committing a transaction for req : {}", req.getRequestURI());
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}
}
