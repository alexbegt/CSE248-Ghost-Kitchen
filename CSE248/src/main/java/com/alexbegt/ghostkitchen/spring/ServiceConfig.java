package com.alexbegt.ghostkitchen.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"com.alexbegt.ghostkitchen.service"})
public class ServiceConfig {
}
