package cn.machine.geek.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author: MachineGeek
 * @Description: Web服务器配置类
 * @Date: 2020/10/4
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    // 映射路径
    public static String URI;
    // 本地路径
    public static String PATH;

    @Value("${route-map.uri}")
    public static void setUri(String Uri) {
        URI = Uri;
    }

    @Value("${route-map.path}")
    public static void setPath(String path) {
        PATH = path;
    }

    /** @Author: MachineGeek
    * @Description: URI映射
    * @Date: 2020/10/4
    * @param registry
    * @Return void
    */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 文件系统路径映射
        registry.addResourceHandler(URI +"**")
                .addResourceLocations("file:"+ PATH);
        // 项目内的静态资源映射
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    /** @Author: MachineGeek
     * @Description: 跨域配置
     * @Date: 2020/10/4
     * @param registry
     * @Return void
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT")
                .maxAge(3600 * 24);
    }
}
