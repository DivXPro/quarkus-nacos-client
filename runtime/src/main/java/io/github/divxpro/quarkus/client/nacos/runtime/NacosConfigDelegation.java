package io.github.divxpro.quarkus.client.nacos.runtime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class NacosConfigDelegation {
    private final NacosConfig config;

    public NacosConfigDelegation(NacosConfig config) {
        this.config = config;
    }

    public String dataId() {
        return config.dataId().orElseThrow();
    }

    public String group() {
        return config.group().orElse("DEFAULT_GROUP");
    }

    public Optional<String> namespace() {
        return config.namespace();
    }

    public String serverAddr() {
        return config.serverAddr().orElseThrow();
    }

    public Optional<String> username() {
        return config.username();
    }

    public Optional<String> password() {
        return config.password();
    }

    public ConfigFileFormat format() {
        return config.format().orElse(ConfigFileFormat.properties);
    }

    public String profile() {
        return config.profile().orElse(null);
    }

    public boolean enabled() {
        return config.serverAddr().isPresent();
    }

    public List<String> configIdListOrdered() {
        List<String> list = new ArrayList<>();
        list.add("application." + format());
        config.profile().ifPresent(it -> list.add("application-" + it + "." + format()));
        list.add(dataId() + "." + format());
        config.profile().ifPresent(it -> list.add(dataId() + "-" + it + "." + format()));
        return list;
    }
}
