package com.dsmhack.igniter;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(Cucumber.class)
@CucumberOptions(
		plugin = {"pretty"},
		features = {"./src/test/resources/features"},
		tags = "not @ignore"
)
public class IgniterApplicationTests {

	@Test
	public void contextLoads() {
		assertTrue(true);
	}

}
