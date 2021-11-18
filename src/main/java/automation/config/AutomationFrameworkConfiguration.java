package automation.config;

import automation.drivers.strategies.DriverStrategy;
import automation.drivers.strategies.DriverStrategyImplementer;
import automation.utils.ConfigurationProperties;
import org.openqa.selenium.WebDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("automation")
public class AutomationFrameworkConfiguration {
    @Bean
    public WebDriver instantiateDriver(ConfigurationProperties config) {
        String browser = config.getBrowser();
        DriverStrategy strategy = DriverStrategyImplementer.chooseStrategy(browser);
        return strategy.setStrategy();
    }

    public AutomationFrameworkConfiguration() {
    }
}
