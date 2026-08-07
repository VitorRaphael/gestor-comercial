package com.vitorraphael.gestor_comercial.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.vitorraphael.gestor_comercial.security.AutenticacaoInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final AutenticacaoInterceptor autenticacaoInterceptor;

    public WebConfig(AutenticacaoInterceptor autenticacaoInterceptor) {
        this.autenticacaoInterceptor = autenticacaoInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(autenticacaoInterceptor)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/login");
    }

    // Spring não resolve index.html automaticamente para subpastas de
    // recursos estáticos (só faz isso na raiz "/"), então /desktop e
    // /desktop/ precisam de um redirect explícito para o console abrir.
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addRedirectViewController("/desktop", "/desktop/index.html");
        registry.addRedirectViewController("/desktop/", "/desktop/index.html");
    }
}
