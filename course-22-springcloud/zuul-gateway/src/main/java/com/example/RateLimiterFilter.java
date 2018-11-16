package com.example;

import com.google.common.util.concurrent.RateLimiter;
import com.netflix.zuul.ZuulFilter;
import static org.springframework.cloud.netflix.zuul.filters.support.FilterConstants.PRE_TYPE;

/**
 * 限流
 */
public class RateLimiterFilter extends ZuulFilter {

    //guava的令牌桶算法
    private static final RateLimiter RATE_LIMITER = RateLimiter.create(100);

    @Override
    public String filterType() {
        return PRE_TYPE;
    }

    @Override
    public int filterOrder() {
        return -5;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run(){

        if(!RATE_LIMITER.tryAcquire()) {
            throw new RuntimeException("占坑失败~~");
        }

        System.out.println("-------->没限流");
        return null;
    }
}
