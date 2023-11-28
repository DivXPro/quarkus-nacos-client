package io.github.divxpro.quarkus.nacos.client.test;

import org.junit.jupiter.api.extension.RegisterExtension;

import io.quarkus.test.QuarkusDevModeTest;

public class QuarkusNacosClientDevModeTest {

    // Start hot reload (DevMode) test with your extension loaded
    @RegisterExtension
    static final QuarkusDevModeTest devModeTest = new QuarkusDevModeTest()
            .withEmptyApplication();
}
