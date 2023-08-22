package com.flowengine.server.exceptions;



import com.flowengine.common.utils.CommonResult;
import com.flowengine.common.utils.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Log _logger = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResult error(Exception e) {

        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String uri = attributes.getRequest().getRequestURI();
        _logger.error("uri=" + uri + ";", e);
        return CommonResult.fail(e.getMessage());
    }

    @ExceptionHandler(SQLException.class)
    @ResponseBody
    public CommonResult error(SQLException e) {
        _logger.error("", e);
        return CommonResult.fail();
    }

    @ExceptionHandler(FlowException.class)
    @ResponseBody
    public CommonResult error(FlowException e) {
        _logger.error("", e);
        return CommonResult.fail();
    }
}