package io.github.divxpro.quarkus.client.nacos.deployment;

import io.github.divxpro.quarkus.client.nacos.runtime.NacosConfigSourceFactoryBuilder;
import io.quarkus.deployment.annotations.BuildProducer;
import io.quarkus.deployment.annotations.BuildStep;
import io.quarkus.deployment.builditem.ExtensionSslNativeSupportBuildItem;
import io.quarkus.deployment.builditem.FeatureBuildItem;
import io.quarkus.deployment.builditem.RunTimeConfigBuilderBuildItem;

class QuarkusNacosClientProcessor {

    private static final String FEATURE = "quarkus-nacos-client";

    @BuildStep
    FeatureBuildItem feature() {
        return new FeatureBuildItem(FEATURE);
    }

    @BuildStep
    void enableSsl(BuildProducer<ExtensionSslNativeSupportBuildItem> extensionSslNativeSupport) {
        extensionSslNativeSupport.produce(new ExtensionSslNativeSupportBuildItem(FEATURE));
    }

    @BuildStep
    void nacosConfigFactory(BuildProducer<RunTimeConfigBuilderBuildItem> runTimeConfigBuilder) {
        runTimeConfigBuilder.produce(new RunTimeConfigBuilderBuildItem(NacosConfigSourceFactoryBuilder.class.getName()));
    }
}
