package com.mycompany.servlets;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * Servlet Filter implementation class FiltreTransparent
 */
@WebFilter(filterName ="XssFilter", urlPatterns = {"/*"})
public class XSSFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public XSSFilter() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here

		// pass the request along the filter chain
		chain.doFilter(new XssRequestWrapper((HttpServletRequest) request), response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

	private static class XssRequestWrapper extends HttpServletRequestWrapper implements ServletRequest {

		public XssRequestWrapper(HttpServletRequest request) {
			super(request);
			// TODO Auto-generated constructor stub
		}

		private static final Pattern[] XSS_PATTERNS = { 
				Pattern.compile("<.*>"), //"<.*?>" avec le ? = on enlève que les balises. " | "<.*>" sans le ? = on enlève balises et texte dans les balises.
				Pattern.compile("&.*?;"),
				Pattern.compile("%[0-9a-fA-F]*") };

		@Override
		public String getParameter(String parameter) {
			return removeTags(super.getParameter(parameter));
		}
		
		
		@Override
		public String getHeader(String name) {
			return removeTags( super.getHeader(name) );
		}

		private String removeTags(String value) {
			// System.out.println("value1 " + value);
			if (value != null) {
				// On retire le code ASCII 0, au cas ou
				value = value.replaceAll("\0", "");

				// Supprime l'ensemble de tags et des entités existants
				for (Pattern pattern : XSS_PATTERNS) {
					value = pattern.matcher(value).replaceAll("");
				}

				// Au cas ou les caractères < et > ne seraient pas en nombres pairs
				value = value.replaceAll("<", "");
				value = value.replaceAll(">", "");
			}
			// System.out.println("value2 " + value);
			return value;
		}

	}

}