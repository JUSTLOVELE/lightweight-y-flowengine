package com.flowengine.server.env;



import com.flowengine.common.utils.CommonResult;
import com.flowengine.common.utils.SQLException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    private final static Log _logger = LogFactory.getLog(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public CommonResult error(Exception e) {
        _logger.error("", e);
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