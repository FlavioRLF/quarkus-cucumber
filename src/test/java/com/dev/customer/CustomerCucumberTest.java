package com.dev.customer;

import io.quarkiverse.cucumber.CucumberOptions;
import io.quarkiverse.cucumber.CucumberQuarkusTest;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.dev.customer"
)
public class CustomerCucumberTest extends CucumberQuarkusTest {
}