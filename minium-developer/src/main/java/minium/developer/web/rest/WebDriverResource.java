package minium.developer.web.rest;

import minium.web.DelegatorWebDriver;
import minium.web.config.WebDriverFactory;
import minium.web.config.WebDriverProperties;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/app/rest/webdrivers")
public class WebDriverResource {

	@Autowired
	protected WebDriverFactory webDriverFactory;

	@Autowired
	protected DelegatorWebDriver delegatorWebDriver;

	@RequestMapping(value = "/isLaunched", method = RequestMethod.GET, produces = "text/plain; charset=utf-8")
	@ResponseBody
	public ResponseEntity<String> isLaunched() {
		boolean webDriver = delegatorWebDriver.isValid();
		if (!webDriver) {
			return new ResponseEntity<String>("No Webdriver", HttpStatus.PRECONDITION_FAILED);
		} else {
			return new ResponseEntity<String>("Ok", HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	@ResponseBody
	public void create(@RequestBody WebDriverProperties webDriverProperties) {
		WebDriver webDriver = webDriverFactory.create(webDriverProperties);
		delegatorWebDriver.setDelegate(webDriver);
	}
}
