package com.snowkit.luna.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.snowkit.luna.exception.NoContentException;
import com.snowkit.luna.model.ResponseMessage;
import com.snowkit.luna.util.ObjectMapperUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (NoContentException e) {
            this.setExceptionResponse(response, HttpStatus.NO_CONTENT, e.getMessage());
        } catch (IllegalArgumentException e) {
            this.setExceptionResponse(response, HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (AccessDeniedException e) {
            this.setExceptionResponse(response, HttpStatus.FORBIDDEN, e.getMessage());
        } catch (Exception e) {
            this.setExceptionResponse(response, HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private void setExceptionResponse(HttpServletResponse response, HttpStatus httpStatus, String message) {
        response.setStatus(httpStatus.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");

        ObjectMapper objectMapper = ObjectMapperUtil.getObjectMapper();
        ResponseMessage responseMessage = new ResponseMessage(message);
        try {
            String responseJson = objectMapper.writeValueAsString(responseMessage);
            response.getWriter().write(responseJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
