package com.kroger.cache.poc.config;

import java.util.Arrays;

import com.kroger.cache.poc.model.Data;
import com.kroger.cache.poc.serializer.DataSerializer;
import com.hazelcast.config.Config;
import com.hazelcast.config.SerializerConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HazelCastConfig {

    private Logger logger = LoggerFactory.getLogger(HazelCastConfig.class);

    @Bean
    public Config getHazelcastConfig() {
        logger.info(String.format("Within hazelcast config, current environment: "));
        Config config = new Config();
        config.getSerializationConfig().addSerializerConfig(
            new SerializerConfig()
                .setImplementation(new DataSerializer())
                .setTypeClass(Data.class)
        );
        config.getNetworkConfig().getJoin().getMulticastConfig().setEnabled(false);
        config.getNetworkConfig().getRestApiConfig().setEnabled(true);
        config.getNetworkConfig().getRestApiConfig().enableAllGroups();
        config.getNetworkConfig().getJoin().getTcpIpConfig().setEnabled(true);
        config.getNetworkConfig().getJoin().getTcpIpConfig()
                .setMembers(Arrays.asList("127.0.0.1"));
        logger.info("Completed tcpip hazelcast config.");
        return config;
    }

    @Bean
    public HazelcastInstance getHazelcastInstance(Config config) {
        return Hazelcast.newHazelcastInstance(config);
    }
}