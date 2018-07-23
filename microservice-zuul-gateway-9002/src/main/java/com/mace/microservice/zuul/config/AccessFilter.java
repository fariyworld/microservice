package com.mace.microservice.zuul.config;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * description: zuul 拦截请求，确认访问权限
 * <br />
 * Created by mace on 16:39 2018/7/4.
 */
@Slf4j
public class AccessFilter extends ZuulFilter {



    /**
     * description: 过滤器的类型，它决定过滤器在请求的哪个生命周期中执行。这里定义为pre,代表会在请求被路由之前执行
     *
     *      per：路由之前，如实现认证、记录调试信息等
     *      routing：路由时
     *      post：路由后，比如添加HTTP Header
     *      error：发生错误时调用
     *
     * <br /><br />
     * create by mace on 2018/7/4 16:39.
     * @param
     * @return: java.lang.String
     */
    @Override
    public String filterType() {
        return "pre";
    }


    /**
     * description: 过滤器的执行顺序。当请求在一个阶段中存在多个过滤器时，需要根据该方法返回的值来依次执行
     *
     *              值越小,优先级越高
     * <br /><br />
     * create by mace on 2018/7/4 16:40.
     * @param
     * @return: int
     */
    @Override
    public int filterOrder() {
        return 0;
    }


    /**
     * description: 判断该过滤器是否需要被执行。这里我们直接返回了true,
     *
     *              因此该过滤器对所有请求都会生效。实际运用中我们可以利用该函数来指定过滤器的有效范围
     *
     * <br /><br />
     * create by mace on 2018/7/4 16:40.
     * @param
     * @return: boolean
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }


    /**
     * description: 过滤器的具体逻辑。这里我们通过ctx.setSendZuulResponse(false)令zuul过滤该请求，
     *              不对其进行路由，然后通过ctx.setResponseStatusCode(401)设置了其返回的错误码，
     *              当然也可以进 一步优化我们的返回，比如，通过ctx.setResponseBody(body)对返回的body内容进行编辑等
     * <br /><br />
     * create by mace on 2018/7/4 16:40.
     * @param
     * @return: java.lang.Object
     */
    @Override
    public Object run() {

        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        log.info("send {} request to {}", request.getMethod(), request.getRequestURL().toString());
        String accessToken = request.getParameter("accessToken");
        if (accessToken == null || StringUtils.isBlank(accessToken)) {
            log.warn("accessToken is empty");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.set("isSuccess", false);
            try {
                ctx.getResponse().getWriter().write("{\"result\":\"9002 : accessToken is empty\"}");
            } catch (Exception e) {

            }
            return null;
        }else{
            ctx.setSendZuulResponse(true);// 对该请求进行路由
            ctx.setResponseStatusCode(200);
            ctx.set("isSuccess", true);// 设值，可以在多个过滤器时使用
            log.info("9002 : access is ok");
            return null;
        }

    }
}
