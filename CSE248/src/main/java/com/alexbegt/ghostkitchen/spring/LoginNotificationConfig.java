package com.alexbegt.ghostkitchen.spring;

import com.alexbegt.ghostkitchen.geoip2.CityDatabaseReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua_parser.Parser;

import java.io.File;
import java.io.IOException;

@Configuration
public class LoginNotificationConfig {

  @Bean
  public Parser uaParser() throws IOException {
    return new Parser();
  }

  @Bean
  public CityDatabaseReader cityDatabaseReader() throws IOException {
    final File resource = new File(this.getClass().getClassLoader().getResource("maxmind/GeoLite2-City.mmdb").getFile());

    return new CityDatabaseReader.Builder(resource).build();
  }
}
