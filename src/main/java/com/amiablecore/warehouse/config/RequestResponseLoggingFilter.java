package com.amiablecore.warehouse.config;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

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

	// Set<String> loginTokens = new HashSet<String>();
	Map<String, Date> tokensMap = new HashMap<>();

	@Autowired
	private Encoder encode;

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		logger.info("Starting a transaction for req : {}", req.getRequestURI());
		HttpServletResponse res = (HttpServletResponse) response;
		if (req.getRequestURI().contains("/rest/user/")) {
			ServletInputStream inputStream = req.getInputStream();
			byte[] byteArray = IOUtils.toByteArray(inputStream);

			String encoded = null;
			logger.info("Payload : {}", new String(byteArray));
			try {
				JSONObject obj = new JSONObject(new String(byteArray));
				String userId = (String) obj.get("userId");
				encoded = encode.encodeLogindetails(userId);
			} catch (Exception e) {
				logger.error("", e);
			}
			// loginTokens.add(encoded);
			tokensMap.put(encoded, new Date());
			
			res.addHeader("accesstoken", encoded);
			return;
		}
		if (req.getRequestURI().contains("/rest/logout/")) {
			res.addHeader("accesstoken", null);
			logger.info("Logout done : ");
		}
		String token = req.getHeader("accesstoken");

		if (tokensMap.get(token) == null) {
			res.sendError(403, "Token in Invalid");
			return;
		}
		Date sessionStartDate = tokensMap.get(token);
		Date currentDate = new Date();
		logger.info("Session Start Date {} ", sessionStartDate);
		logger.info("Session Current Date {} ", currentDate);

		long timeDiffrence = currentDate.getTime() - sessionStartDate.getTime();
		logger.info("Time Diffrence {} ", timeDiffrence);

		long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDiffrence);
		logger.info("Time Diffrence in Minutes {} ", minutes);
		if (minutes > 5) {
			tokensMap.remove(token);
			res.sendError(403, "Token Is Expired");
			return;
		}

		res.addHeader("accesstoken", token);
		logger.info("Token Found : {}", token);
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
