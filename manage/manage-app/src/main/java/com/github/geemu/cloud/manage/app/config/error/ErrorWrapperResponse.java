//package com.github.geemu.cloud.app.manage.config.error;
//
//import javax.servlet.ServletOutputStream;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpServletResponseWrapper;
//import java.io.IOException;
//import java.io.PrintWriter;
//
///**
// * HttpServletResponse包装器
// * @author 陈方明  cfmmail@sina.com
// * @since 2020-03-18 20:25
// */
//public class ErrorWrapperResponse extends HttpServletResponseWrapper {
//
//    /** http状态码 **/
//    private int status;
//    /** 提示信息 **/
//    private String message;
//    /** 是否有异常 **/
//    private boolean hasErrorToSend = Boolean.FALSE;
//
//    /**
//     * 构造方法
//     * @param response response
//     */
//    public ErrorWrapperResponse(HttpServletResponse response) {
//        super(response);
//    }
//
//    /**
//     * 此方法的默认行为是在包装的响应对象上调用sendError（int status）
//     * @param status http状态码
//     * @throws IOException IOException IO异常
//     */
//    @Override
//    public void sendError(int status) throws IOException {
//        sendError(status, null);
//    }
//
//    /**
//     * 此方法的默认行为是在包装的响应对象上调用sendError（int status）
//     * 不要调用super，因为容器可能会阻止我们自己处理错误
//     * @param status http状态码
//     * @param message 提示信息
//     * @throws IOException IO异常
//     */
//    @Override
//    public void sendError(int status, String message) throws IOException {
//        this.status = status;
//        this.message = message;
//        this.hasErrorToSend = Boolean.TRUE;
//    }
//
//    /**
//     * 如果没有错误，我们需要信任包装的响应
//     * 默认实现是调用{@link javax.servlet.http.HttpServletResponse#getStatus()}
//     * 在包装上 {@link javax.servlet.http.HttpServletResponse}.
//     */
//    @Override
//    public int getStatus() {
//        if (this.hasErrorToSend) {
//            return this.status;
//        }
//        return super.getStatus();
//    }
//
//    /**
//     * 该方法的默认行为是在包装的响应对象上调用flushBuffer()
//     */
//    @Override
//    public void flushBuffer() throws IOException {
//        sendErrorIfNecessary();
//        super.flushBuffer();
//    }
//
//    /**
//     * 必要时发送错误
//     * @throws IOException IO异常
//     */
//    private void sendErrorIfNecessary() throws IOException {
//        if (this.hasErrorToSend && !isCommitted()) {
//            HttpServletResponse httpServletResponse = (HttpServletResponse) getResponse();
//            httpServletResponse.sendError(this.status, this.message);
//        }
//    }
//
//    /**
//     * 获取提示信息
//     * @return 提示信息
//     */
//    public String getMessage() {
//        return this.message;
//    }
//
//    /**
//     * 获取是否有异常
//     * @return 是否有异常
//     */
//    public boolean hasErrorToSend() {
//        return this.hasErrorToSend;
//    }
//
//    /**
//     * 此方法的默认行为是在包装的响应对象上返回getWriter()
//     * @return PrintWriter
//     * @throws IOException IO异常
//     */
//    @Override
//    public PrintWriter getWriter() throws IOException {
//        sendErrorIfNecessary();
//        return super.getWriter();
//    }
//
//    /**
//     * 此方法的默认行为是在包装的响应对象上调用setContentLength（int len）
//     * @return ServletOutputStream
//     * @throws IOException IO异常
//     */
//    @Override
//    public ServletOutputStream getOutputStream() throws IOException {
//        sendErrorIfNecessary();
//        return super.getOutputStream();
//    }
//
//}
