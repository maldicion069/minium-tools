package minium.developer.project;

import static minium.developer.internal.webelements.SelectorGadgetWebModules.selectorGadgetModule;
import static minium.script.rhinojs.RhinoWebModules.rhinoModule;
import static minium.web.internal.WebModules.baseModule;
import static minium.web.internal.WebModules.combine;
import static minium.web.internal.WebModules.conditionalModule;
import static minium.web.internal.WebModules.debugModule;
import static minium.web.internal.WebModules.interactableModule;
import static minium.web.internal.WebModules.positionModule;
import minium.tools.fs.service.FileSystemService;
import minium.web.CoreWebElements.DefaultWebElements;
import minium.web.DelegatorWebDriver;
import minium.web.actions.Browser;
import minium.web.actions.WebDriverBrowser;
import minium.web.config.WebDriverFactory;
import minium.web.config.services.DriverServicesProperties;
import minium.web.internal.WebModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.web.context.WebApplicationContext;

@Configuration
@EnableConfigurationProperties
public class ProjectContextConfiguration {

    @Bean
    @Autowired
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public FileSystemService fileSystemService(ProjectProperties projectConfiguration) {
        return new FileSystemService(projectConfiguration.getResourcesDir());
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
    @Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
    public AbstractProjectContext projectContext(ProjectProperties projConfiguration) throws Exception {
        if (projConfiguration.isCucumberProject()) {
            return new CucumberProjectContext(projConfiguration.getDir());
        } else {
            return new RhinoProjectContext(projConfiguration.getDir());
        }
    }

    @Autowired
    @Bean
    public WebDriverFactory webDriverFactory(DriverServicesProperties driverServicesProperties) throws Exception {
        return new WebDriverFactory(driverServicesProperties);
    }

    @Autowired
    @Bean
    public DelegatorWebDriver delegatorWebDriver() {
        return new DelegatorWebDriver();
    }

    @Lazy
    @Autowired
    @Bean
    public Browser<DefaultWebElements> browser(DelegatorWebDriver delegatorWebDriver) {
        return new WebDriverBrowser<>(delegatorWebDriver, DefaultWebElements.class, createWebModule(delegatorWebDriver));
    }

    protected WebModule createWebModule(DelegatorWebDriver delegatorWebDriver) {
        return combine(baseModule(delegatorWebDriver), positionModule(), conditionalModule(), interactableModule(), rhinoModule(), debugModule(), selectorGadgetModule());
    }
}
