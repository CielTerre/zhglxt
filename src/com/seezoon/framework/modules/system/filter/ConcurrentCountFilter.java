package com.seezoon.framework.modules.system.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by zengqy on 2018/7/17.
 */
public class ConcurrentCountFilter implements Filter {
    private static AtomicInteger count = new AtomicInteger(0);

    /**
     * 获取当前并发数
     * @author ZYB
     * @date Dec 3, 2014 9:51:37 AM<BR/>
     * @return
     */
    public static int get(){
        return count.get();
    }

    /**
     * 增加并发数量
     * @author ZYB
     * @date Dec 3, 2014 9:51:32 AM<BR/>
     * @return
     */
    public static int increase(){
        return count.incrementAndGet();
    }

    /**
     * 减少并发数量
     * @author ZYB
     * @date Dec 3, 2014 9:51:22 AM<BR/>
     * @return
     */
    public static int decrement(){
        return count.decrementAndGet();
    }

    /**
     * 最大并发允许数量, 负数表示不限制
     */
    private int maxConcurrent = -1;

    /* (non-Javadoc)
     * @see javax.servlet.Filter#init(javax.servlet.FilterConfig)
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String maxStr = filterConfig.getInitParameter("maxConcurrent");
        int num = -1;
        if(maxStr != null && !"".equals(maxStr)){
            try{
                num = Integer.parseInt(maxStr);
            }catch(NumberFormatException e){}
        }
        if(num >= 1){
            this.maxConcurrent = num;
        }else{
            this.maxConcurrent = -1;
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        try{
            int num = increase();//增加并发数量,并取得当前数量
          //  System.out.println("--------->"+num);
            if(maxConcurrent > 0){//检查是否限制最大并发数量, <=0表示不限制
                if(maxConcurrent >= num){//检查是否超过最大并发限制, 没超过则允许执行
                    chain.doFilter(request, response);
                }else{
                    //超过最大并发限制
                    HttpServletResponse res = (HttpServletResponse) response;
                    res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "达到最大并发数限制:" + this.maxConcurrent);
                }
            }else{//未限制最大并发数
                chain.doFilter(request, response);
            }
        }finally{//由于Servlet执行时可能会抛出异常,所以使用try-finally块来减少并发数非常必要.
            decrement();
        }
    }

    /* (non-Javadoc)
     * @see javax.servlet.Filter#destroy()
     */
    @Override
    public void destroy() {
    }

}
