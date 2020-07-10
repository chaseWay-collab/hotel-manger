package com.example.shiro;

import com.alibaba.fastjson.JSON;
import com.example.common.utils.Constants;
import com.example.common.utils.DataResult;
import com.example.common.utils.HttpContextUtils;
import com.example.exception.MyException;
import com.example.exception.code.BaseResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @ClassName: CustomAccessControlFilter
 * @Description: 自定义过滤器
 * @Author: [咖啡]
 * @Version: 1.0
 **/
@Slf4j
public class CustomAccessControlFilter extends AccessControlFilter {

    /**
     * 权限过滤
     * @param servletRequest
     * @param servletResponse
     * @param o
     * @return
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) {
        return false;
    }

    /**
     * 权限验证
     * @param servletRequest
     * @param servletResponse
     * @return
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        try {
            Subject subject = getSubject(servletRequest, servletResponse);
            //System.out.println("[CustomAccessControlFilter]onAccessDenied: " + subject.isAuthenticated() + "");
            //System.out.println("[CustomAccessControlFilter]onAccessDenied: " + HttpContextUtils.isAjaxRequest(request));
            log.info(request.getMethod());
            log.info(request.getRequestURL().toString());
            //从header中获取token
            String token = request.getHeader(Constants.ACCESS_TOKEN);
            //如果header中不存在token，则从参数中获取token
            if (StringUtils.isEmpty(token)) {
                token = request.getParameter(Constants.ACCESS_TOKEN);  // ACCESS_TOKEN = "authorization" 正常 token
            }
            if (StringUtils.isEmpty(token)) {
                throw new MyException(BaseResponseCode.TOKEN_ERROR);  // 登录凭证已过期
            }
            UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(token, token);
            getSubject(servletRequest, servletResponse).login(usernamePasswordToken);
        } catch (MyException exception) {
            if (HttpContextUtils.isAjaxRequest(request)) {
                customRsponse(exception.getMessageCode(), exception.getDetailMsg(), servletResponse);
            } else if (exception.getMessageCode() == BaseResponseCode.TOKEN_ERROR.getCode()) {
                servletRequest.getRequestDispatcher("/index/login").forward(servletRequest, servletResponse);
            } else if (exception.getMessageCode() == BaseResponseCode.UNAUTHORIZED_ERROR.getCode()) {  // 权限校验不通过
                servletRequest.getRequestDispatcher("/index/403").forward(servletRequest, servletResponse);
            } else {
                servletRequest.getRequestDispatcher("/index/500").forward(servletRequest, servletResponse);
            }
            return false;
        } catch (AuthenticationException e) {
            if (HttpContextUtils.isAjaxRequest(request)) {
                if (e.getCause() instanceof MyException) {
                    MyException exception = (MyException) e.getCause();
                    customRsponse(exception.getMessageCode(), exception.getDetailMsg(), servletResponse);
                } else {
                    customRsponse(BaseResponseCode.SYSTEM_BUSY.getCode(), BaseResponseCode.SYSTEM_BUSY.getMsg(), servletResponse);
                }
            } else {
                servletRequest.getRequestDispatcher("/index/403").forward(servletRequest, servletResponse);
            }
            return false;
        } catch (Exception e) {
            if (HttpContextUtils.isAjaxRequest(request)) {
                if (e.getCause() instanceof MyException) {
                    MyException exception = (MyException) e.getCause();
                    customRsponse(exception.getMessageCode(), exception.getDetailMsg(), servletResponse);
                } else {
                    customRsponse(BaseResponseCode.SYSTEM_BUSY.getCode(), BaseResponseCode.SYSTEM_BUSY.getMsg(), servletResponse);
                }
            } else {
                servletRequest.getRequestDispatcher("/index/500").forward(servletRequest, servletResponse);
            }
            return false;
        }
        return true;
    }

    /**
     * 自定义返回请求
     * @param code
     * @param msg
     * @param response
     */
    private void customRsponse(int code, String msg, ServletResponse response) {
        try {
            DataResult result = DataResult.getResult(code, msg);

            response.setContentType("application/json; charset=utf-8");
            response.setCharacterEncoding("UTF-8");

            String userJson = JSON.toJSONString(result);
            OutputStream out = response.getOutputStream();
            out.write(userJson.getBytes("UTF-8"));
            out.flush();
        } catch (IOException e) {
            log.error("eror={}", e);
        }
    }

}
