/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.lnnd.configs;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.lnnd.formatters.GroupExpenseFormatter;
import com.lnnd.formatters.GroupMemberFormatter;
import com.lnnd.formatters.TypeTransactionFormatter;
import com.lnnd.formatters.UserFormatter;
import com.lnnd.service.GroupExpenseService;
import com.lnnd.service.GroupMemberService;
import com.lnnd.service.UserService;
import com.lnnd.validator.GroupExpenseDateValidator;
import com.lnnd.validator.GroupExpenseValidator;
import com.lnnd.validator.RegisterConfirmPasswordValidator;
import com.lnnd.validator.RegisterEmailValidator;
import com.lnnd.validator.RegisterFileValidator;
import com.lnnd.validator.RegisterUsernameValidate;
import com.lnnd.validator.RegisterValidator;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 *
 * @author ADMIN
 */
@Configuration
@EnableWebMvc
@EnableTransactionManagement
@ComponentScan(basePackages = {
    "com.lnnd.controllers",
    "com.lnnd.repository",
    "com.lnnd.service",
    "com.lnnd.validator"
})
public class WebAppContextConfig implements WebMvcConfigurer {

    @Autowired
    private UserService userDetailsService;

    @Autowired
    private GroupExpenseService grService;

    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

//    @Bean
//    public InternalResourceViewResolver internalResourceViewResolver(){
//        InternalResourceViewResolver r = new InternalResourceViewResolver();
//        r.setViewClass(JstlView.class);
//        r.setPrefix("/WEB-INF/pages/");
//        r.setSuffix(".jsp");
//        
//        return r;
//    }
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(new TypeTransactionFormatter());
        registry.addFormatter(new UserFormatter());
        registry.addFormatter(new GroupExpenseFormatter());
        registry.addFormatter(new GroupMemberFormatter());
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver
                = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("UTF-8");
        return resolver;
    }

    @Override
    public void addResourceHandlers(
            ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/css/**")
                .addResourceLocations("/resources/css/");
        registry.addResourceHandler("/img/**")
                .addResourceLocations("/resources/images/");
        registry.addResourceHandler("/js/**")
                .addResourceLocations("/resources/js/");
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource m = new ResourceBundleMessageSource();

        m.setBasename("messages");

        return m;
    }

    @Bean(name = "validator")
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean
                = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

    @Bean
    public RegisterValidator registerValidator() {
        Set<Validator> springValidators = new HashSet<Validator>();
        springValidators.add(new RegisterEmailValidator(userDetailsService));
        springValidators.add(new RegisterUsernameValidate(userDetailsService));
        springValidators.add(new RegisterConfirmPasswordValidator());
        springValidators.add(new RegisterFileValidator());

        RegisterValidator v = new RegisterValidator();
        v.setSpringValidator(springValidators);
        return v;
    }

    @Bean
    public GroupExpenseValidator groupExpenseValidator() {
        Set<Validator> springValidators = new HashSet<Validator>();
        springValidators.add(new GroupExpenseDateValidator(grService));

        GroupExpenseValidator v = new GroupExpenseValidator();
        v.setSpringValidator(springValidators);
        return v;
    }

    @Override
    public Validator getValidator() {
        return validator();
    }
}
