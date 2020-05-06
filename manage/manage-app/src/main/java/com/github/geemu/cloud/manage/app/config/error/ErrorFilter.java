//package com.github.geemu.cloud.app.manage.config.error;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.boot.web.server.ErrorPage;
//import org.springframework.boot.web.server.ErrorPageRegistry;
//import org.springframework.core.Ordered;
//import org.springframework.core.annotation.Order;
//import org.springframework.util.ClassUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//import org.springframework.web.util.NestedServletException;
//
//import javax.servlet.Filter;
//import javax.servlet.FilterChain;
//import javax.servlet.FilterConfig;
//import javax.servlet.ServletException;
//import javax.servlet.ServletRequest;
//import javax.servlet.ServletResponse;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//import java.util.Collection;
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.Map;
//import java.util.Set;
//
///**
// * 异常处理过滤器
// * @author 陈方明  cfmmail@sina.com
// * @since 2020-03-18 20:18
// */
//@Slf4j
//@Order(Ordered.HIGHEST_PRECEDENCE)
//public class ErrorFilter implements Filter, ErrorPageRegistry {
//
//    // From RequestDispatcher but not referenced to remain compatible with Servlet 2.5
//
//    private static final String ERROR_EXCEPTION = "javax.servlet.error.exception";
//
//    private static final String ERROR_EXCEPTION_TYPE = "javax.servlet.error.exception_type";
//
//    private static final String ERROR_MESSAGE = "javax.servlet.error.message";
//
//    /**
//     * The name of the servlet attribute containing request URI.
//     */
//    public static final String ERROR_REQUEST_URI = "javax.servlet.error.request_uri";
//
//    private static final String ERROR_STATUS_CODE = "javax.servlet.error.status_code";
//
//    private static final Set<Class<?>> CLIENT_ABORT_EXCEPTIONS;
//
//    static {
//        Set<Class<?>> clientAbortExceptions = new HashSet<>();
//        addClassIfPresent(clientAbortExceptions, "org.apache.catalina.connector.ClientAbortException");
//        CLIENT_ABORT_EXCEPTIONS = Collections.unmodifiableSet(clientAbortExceptions);
//    }
//
//    private String global;
//
//    private final Map<Integer, String> statuses = new HashMap<>();
//
//    private final Map<Class<?>, String> exceptions = new HashMap<>();
//
//    private final OncePerRequestFilter delegate = new OncePerRequestFilter() {
//
//        @Override
//        protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
//            ErrorWrapperResponse wrapped = new ErrorWrapperResponse(response);
//            try {
//                chain.doFilter(request, wrapped);
//                if (wrapped.hasErrorToSend()) {
//                    handleErrorStatus(request, response, wrapped.getStatus(), wrapped.getMessage());
//                    response.flushBuffer();
//                } else if (!request.isAsyncStarted() && !response.isCommitted()) {
//                    response.flushBuffer();
//                }
//            } catch (Throwable ex) {
//                Throwable exceptionToHandle = ex;
//                if (ex instanceof NestedServletException) {
//                    exceptionToHandle = ((NestedServletException) ex).getRootCause();
//                }
//                handleException(request, response, wrapped, exceptionToHandle);
//                response.flushBuffer();
//            }
//        }
//
//        @Override
//        protected boolean shouldNotFilterAsyncDispatch() {
//            return false;
//        }
//
//    };
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//        this.delegate.init(filterConfig);
//    }
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
//        this.delegate.doFilter(request, response, chain);
//    }
//
//    private void handleErrorStatus(HttpServletRequest request, HttpServletResponse response, int status, String message) throws ServletException, IOException {
//        if (response.isCommitted()) {
//            handleCommittedResponse(request, null);
//            return;
//        }
//        String errorPath = getErrorPath(this.statuses, status);
//        if (errorPath == null) {
//            response.sendError(status, message);
//            return;
//        }
//        response.setStatus(status);
//        setErrorAttributes(request, status, message);
//        request.getRequestDispatcher(errorPath).forward(request, response);
//    }
//
//    private void handleException(HttpServletRequest request, HttpServletResponse response, ErrorWrapperResponse wrapped, Throwable ex) throws IOException, ServletException {
//        Class<?> type = ex.getClass();
//        String errorPath = getErrorPath(type);
//        if (errorPath == null) {
//            rethrow(ex);
//            return;
//        }
//        if (response.isCommitted()) {
//            handleCommittedResponse(request, ex);
//            return;
//        }
//        forwardToErrorPage(errorPath, request, wrapped, ex);
//    }
//
//    private void forwardToErrorPage(String path, HttpServletRequest request, HttpServletResponse response, Throwable ex) throws ServletException, IOException {
//        if (log.isErrorEnabled()) {
//            String message = "Forwarding to error page from request " + getDescription(request) + " due to exception [" + ex.getMessage() + "]";
//            log.error(message, ex);
//        }
//        setErrorAttributes(request, 500, ex.getMessage());
//        request.setAttribute(ERROR_EXCEPTION, ex);
//        request.setAttribute(ERROR_EXCEPTION_TYPE, ex.getClass());
//        response.reset();
//        response.setStatus(500);
//        request.getRequestDispatcher(path).forward(request, response);
//        request.removeAttribute(ERROR_EXCEPTION);
//        request.removeAttribute(ERROR_EXCEPTION_TYPE);
//    }
//
//    /**
//     * Return the description for the given request. By default this method will return a
//     * description based on the request {@code servletPath} and {@code pathInfo}.
//     * @param request the source request
//     * @return the description
//     * @since 1.5.0
//     */
//    protected String getDescription(HttpServletRequest request) {
//        String pathInfo = (request.getPathInfo() != null) ? request.getPathInfo() : "";
//        return "[" + request.getServletPath() + pathInfo + "]";
//    }
//
//    private void handleCommittedResponse(HttpServletRequest request, Throwable ex) {
//        if (isClientAbortException(ex)) {
//            return;
//        }
//        String message = "Cannot forward to error page for request " + getDescription(request)
//                + " as the response has already been"
//                + " committed. As a result, the response may have the wrong status"
//                + " code. If your application is running on WebSphere Application"
//                + " Server you may be able to resolve this problem by setting"
//                + " com.ibm.ws.webcontainer.invokeFlushAfterService to false";
//        if (ex == null) {
//            log.error(message);
//        } else {
//            // User might see the error page without all the data here but throwing the
//            // exception isn't going to help anyone (we'll log it to be on the safe side)
//            log.error(message, ex);
//        }
//    }
//
//    private boolean isClientAbortException(Throwable ex) {
//        if (ex == null) {
//            return false;
//        }
//        for (Class<?> candidate : CLIENT_ABORT_EXCEPTIONS) {
//            if (candidate.isInstance(ex)) {
//                return true;
//            }
//        }
//        return isClientAbortException(ex.getCause());
//    }
//
//    private String getErrorPath(Map<Integer, String> map, Integer status) {
//        if (map.containsKey(status)) {
//            return map.get(status);
//        }
//        return this.global;
//    }
//
//    private String getErrorPath(Class<?> type) {
//        while (type != Object.class) {
//            String path = this.exceptions.get(type);
//            if (path != null) {
//                return path;
//            }
//            type = type.getSuperclass();
//        }
//        return this.global;
//    }
//
//    private void setErrorAttributes(HttpServletRequest request, int status, String message) {
//        request.setAttribute(ERROR_STATUS_CODE, status);
//        request.setAttribute(ERROR_MESSAGE, message);
//        request.setAttribute(ERROR_REQUEST_URI, request.getRequestURI());
//    }
//
//    private void rethrow(Throwable ex) throws IOException, ServletException {
//        if (ex instanceof RuntimeException) {
//            throw (RuntimeException) ex;
//        }
//        if (ex instanceof Error) {
//            throw (Error) ex;
//        }
//        if (ex instanceof IOException) {
//            throw (IOException) ex;
//        }
//        if (ex instanceof ServletException) {
//            throw (ServletException) ex;
//        }
//        throw new IllegalStateException(ex);
//    }
//
//    @Override
//    public void addErrorPages(ErrorPage... errorPages) {
//        for (ErrorPage errorPage : errorPages) {
//            if (errorPage.isGlobal()) {
//                this.global = errorPage.getPath();
//            } else if (errorPage.getStatus() != null) {
//                this.statuses.put(errorPage.getStatus().value(), errorPage.getPath());
//            } else {
//                this.exceptions.put(errorPage.getException(), errorPage.getPath());
//            }
//        }
//    }
//
//    @Override
//    public void destroy() {
//    }
//
//    private static void addClassIfPresent(Collection<Class<?>> collection, String className) {
//        try {
//            collection.add(ClassUtils.forName(className, null));
//        } catch (Throwable ex) {
//        }
//    }
//
//}
