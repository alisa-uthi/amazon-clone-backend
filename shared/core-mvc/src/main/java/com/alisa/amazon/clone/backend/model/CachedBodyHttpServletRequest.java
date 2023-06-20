package com.alisa.amazon.clone.backend.model;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import lombok.SneakyThrows;
import org.springframework.util.StreamUtils;

import java.io.*;
import java.nio.charset.StandardCharsets;

// To read HttpServletRequest multiple times
// https://www.baeldung.com/spring-reading-httpservletrequest-multiple-times
public class CachedBodyHttpServletRequest extends HttpServletRequestWrapper {

    private final byte[] cachedBody;

    public CachedBodyHttpServletRequest(HttpServletRequest request) throws IOException {
        super(request);
        InputStream requestInputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(requestInputStream);
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new CachedBodyServletInputStream(this.cachedBody);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(this.cachedBody);
        return new BufferedReader(new InputStreamReader(byteArrayInputStream));
    }

    public class CachedBodyServletInputStream extends ServletInputStream {

        private InputStream cachedBodyInputStream;

        public CachedBodyServletInputStream(byte[] cachedBody) throws IOException {
            this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
            this.minifyInputStreamFormat();
        }

        private void minifyInputStreamFormat() throws IOException {
            // Read it with BufferedReader
            BufferedReader br = new BufferedReader(new InputStreamReader(this.cachedBodyInputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line.replace("\r", "").replace("\n", "").replace("    ", ""));
            }
            this.cachedBodyInputStream = new ByteArrayInputStream(sb.toString().getBytes(StandardCharsets.UTF_8));
        }

        @Override
        public int read() throws IOException {
            return cachedBodyInputStream.read();
        }

        @SneakyThrows
        @Override
        public boolean isFinished() {
            return cachedBodyInputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener listener) {
            throw new RuntimeException("Not Implemented");
        }
    }
}