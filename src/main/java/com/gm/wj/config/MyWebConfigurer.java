package com.gm.wj.config;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.web.servlet.config.annotation.*;

@SpringBootConfiguration
public class MyWebConfigurer implements WebMvcConfigurer {

    //    旧版本addInterceptors，新版用了shiro的拦截器，所以不用这个了
    //    @Override
    //    public void addInterceptors(InterceptorRegistry registry) {
    //        registry.addInterceptor(getLoginInterceptor())
    //                .addPathPatterns("/**")
    //                .excludePathPatterns("/index.html")
    //                .excludePathPatterns("/api/register")
    //                .excludePathPatterns("/api/login")
    //                .excludePathPatterns("/api/logout")
    //                .excludePathPatterns("/api/books");
    //    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //所有请求都允许跨域，使用这种配置方法就不能在 interceptor 中再配置 header 了
        registry.addMapping("/**")
                //允许任何域名使用
                .allowCredentials(true)
                //允许任何方法（post、get等）
                .allowedOrigins("http://localhost:8080")
                //允许任何请求头
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE")
                //带上cookie信息
                .allowedHeaders("*")
                //跨域允许时间
                .maxAge(3600);
                //在3600秒内不需要再发送预检验请求，可以缓存该结果
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/api/file/**").addResourceLocations("file:" + "d:/workspace/img/");
        //文件对外的访问路径为：http://localhost:8443/api/file/文件名，而文件实际存储在d:/workspace/img/文件名
    }

}
