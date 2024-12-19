package com.example.handlingformsubmission.generateQRcode;
@Configuration
public class AdditionalResourceWebConfiguration  implements WebMvcConfigurer {

        @Override
        public void addResourceHandlers(final ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/upload/**").addResourceLocations("file://" + System.getProperty("user.dir") + "/src/main/upload/");
        }
    }
