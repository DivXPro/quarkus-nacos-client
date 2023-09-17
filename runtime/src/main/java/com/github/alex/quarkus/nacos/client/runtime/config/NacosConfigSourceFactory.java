package com.github.alex.quarkus.nacos.client.runtime.config;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.github.alex.quarkus.nacos.client.runtime.NacosConfig;
import com.github.alex.quarkus.nacos.client.runtime.NacosConfigDelegation;
import io.smallrye.config.ConfigSourceContext;
import io.smallrye.config.ConfigSourceFactory;
import org.eclipse.microprofile.config.spi.ConfigSource;
import org.jboss.logging.Logger;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

class NacosConfigSourceFactory implements ConfigSourceFactory.ConfigurableConfigSourceFactory<NacosConfig> {
    private static final Logger log = Logger.getLogger(NacosConfigSourceFactory.class);

    @Override
    public Iterable<ConfigSource> getConfigSources(ConfigSourceContext configSourceContext, NacosConfig nacosConfig) {
        NacosConfigDelegation config = new NacosConfigDelegation(nacosConfig);
        if (!config.enabled()) {
            return Collections.emptyList();
        } else {
            log.info("============== nacos is enabled =============");
        }
        List<ConfigSource> configSources = new ArrayList<>(1);
        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, config.serverAddr());
        properties.setProperty(PropertyKeyConst.NAMESPACE, config.namespace().orElse(""));
        config.username().ifPresent(it -> properties.setProperty(PropertyKeyConst.USERNAME, it));
        config.password().ifPresent(it -> properties.setProperty(PropertyKeyConst.PASSWORD, it));
        try {
            ConfigService configService = NacosFactory.createConfigService(properties);
            configSources.add(new NacosConfigSource(configService, config));
        } catch (NacosException e) {
            log.error(e.getMessage(), e);
        }

        return configSources;
    }
}