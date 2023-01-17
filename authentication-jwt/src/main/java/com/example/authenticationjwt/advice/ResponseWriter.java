package com.example.authenticationjwt.advice;

import com.example.authenticationjwt.utils.JacksonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author WANGKairen
 * @since 2022-12-29 11:46:05
 **/
@Slf4j
public abstract class ResponseWriter {

    /**
     * Write.
     *
     * @param request  the request
     * @param response the response
     * @throws IOException the io exception
     */
    public void write(HttpServletRequest request, HttpServletResponse response) throws IOException {
        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }
        response.setStatus(HttpServletResponse.SC_OK);
        response.setCharacterEncoding("utf-8");
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        String resBody = null;
        try {
            resBody = JacksonUtil.obj2json(body(request));
        } catch (Exception e) {
            log.error("Rest body to json exception",e);
        }
        PrintWriter printWriter = response.getWriter();
        printWriter.print(resBody);
        printWriter.flush();
        printWriter.close();
    }

    /**
     * Msg string.
     *
     * @param request the request
     * @return the string
     */
    protected abstract Rest<?> body(HttpServletRequest request);
}
