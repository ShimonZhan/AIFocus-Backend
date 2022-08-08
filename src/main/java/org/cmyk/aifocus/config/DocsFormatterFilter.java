package org.cmyk.aifocus.config;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;

public class DocsFormatterFilter implements Filter {
    @Override
    public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain)
            throws IOException, ServletException {
        ByteResponseWrapper byteResponseWrapper = new ByteResponseWrapper((HttpServletResponse) response);
        ByteRequestWrapper byteRequestWrapper = new ByteRequestWrapper((HttpServletRequest) request);

        chain.doFilter(byteRequestWrapper, byteResponseWrapper);

        String jsonResponse = new String(byteResponseWrapper.getBytes(), response.getCharacterEncoding());

        response.getOutputStream().write((new com.google.gson.JsonParser().parse(jsonResponse).getAsString())
                .getBytes(response.getCharacterEncoding()));
    }

    @Override
    public void destroy() {

    }

    static class ByteResponseWrapper extends HttpServletResponseWrapper {

        private final PrintWriter writer;
        private final ByteOutputStream output;

        public ByteResponseWrapper(HttpServletResponse response) {
            super(response);
            output = new ByteOutputStream();
            writer = new PrintWriter(output);
        }

        public byte[] getBytes() {
            writer.flush();
            return output.getBytes();
        }

        @Override
        public PrintWriter getWriter() {
            return writer;
        }

        @Override
        public ServletOutputStream getOutputStream() {
            return output;
        }
    }

    static class ByteRequestWrapper extends HttpServletRequestWrapper {

        byte[] requestBytes = null;
        private ByteInputStream byteInputStream;


        public ByteRequestWrapper(HttpServletRequest request) throws IOException {
            super(request);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            InputStream inputStream = request.getInputStream();

            byte[] buffer = new byte[4096];
            int read;
            while ((read = inputStream.read(buffer)) != -1) {
                baos.write(buffer, 0, read);
            }

            replaceRequestPayload(baos.toByteArray());
        }

        @Override
        public BufferedReader getReader() {
            return new BufferedReader(new InputStreamReader(getInputStream()));
        }

        @Override
        public ServletInputStream getInputStream() {
            return byteInputStream;
        }

        public void replaceRequestPayload(byte[] newPayload) {
            requestBytes = newPayload;
            byteInputStream = new ByteInputStream(new ByteArrayInputStream(requestBytes));
        }
    }

    static class ByteOutputStream extends ServletOutputStream {

        private final ByteArrayOutputStream bos = new ByteArrayOutputStream();

        @Override
        public void write(int b) {
            bos.write(b);
        }

        public byte[] getBytes() {
            return bos.toByteArray();
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }

    static class ByteInputStream extends ServletInputStream {

        private final InputStream inputStream;

        public ByteInputStream(final InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public int read() throws IOException {
            return inputStream.read();
        }

        @Override
        public boolean isFinished() {
            return false;
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }
    }
}
