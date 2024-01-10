package com.gm.wj.error;


import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;


@Component
public class ErrorConfig implements ErrorPageRegistrar {
// ErrorConfig类实现了ErrorPageRegistrar接口，重写了registerErrorPages方法，将404错误重定向到index.html页面，前端都在index.html页面，所以这样做可以防止前端路由出现404错误。
    @Override
    public void registerErrorPages(ErrorPageRegistry registry) {
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/index.html");
        registry.addErrorPages(error404Page);
    }

}

