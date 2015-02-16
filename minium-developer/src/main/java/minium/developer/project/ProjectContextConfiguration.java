package minium.developer.project;

import java.io.File;

import minium.tools.fs.service.FileSystemService;
import minium.web.config.services.DriverServicesProperties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
public class ProjectContextConfiguration {

    @Bean
    @Autowired
    public FileSystemService fileSystemService(ProjectProperties projectConfiguration) {
        return new FileSystemService(new File(projectConfiguration.getDir(), "src/test/resources"));
    }

    @Bean
    @ConfigurationProperties(prefix = "minium.driverservices")
    public DriverServicesProperties driverServicesProperties() {
        return new DriverServicesProperties();
    }

    @Bean
    @ConfigurationProperties(prefix = "minium.project")
    public ProjectProperties projectConfiguration() {
        return new ProjectProperties();
    }

    @Autowired
    @Bean
    public CucumberProjectContext cucumberProjectContext(ProjectProperties projConfiguration) throws Exception {
        return new CucumberProjectContext(projConfiguration.getDir());
    }

}
