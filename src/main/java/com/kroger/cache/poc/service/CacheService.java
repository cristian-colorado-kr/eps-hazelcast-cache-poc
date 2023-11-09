package com.kroger.cache.poc.service;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import lombok.RequiredArgsConstructor;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.kroger.cache.poc.model.Data;

@Service
@RequiredArgsConstructor
public class CacheService {
    private Logger logger = LoggerFactory.getLogger(CacheService.class);
    private final HazelcastInstance hazelcastInstance;
    public static final String CONTENT = "content";

    public Data put(String number, Data data) {
        ConcurrentMap<String, Data> map = hazelcastInstance.getMap(CONTENT);
        Data previous = map.putIfAbsent(number, data);
        if(previous == null) {
            logger.info("Inserting new data: %d - %s", data.id, data.message);
        }
        return data;
    }

    public Data get(String key) {
        ConcurrentMap<String, Data> map = hazelcastInstance.getMap(CONTENT);
        return map.get(key);
    }

    public List<Data> all() {
        ConcurrentMap<String, Data> map = hazelcastInstance.getMap(CONTENT);
        List<Data> result = new ArrayList<>(map.values());
        return result;
    }

    public List<Data> evict() {
        IMap<String, Data> map = hazelcastInstance.getMap(CONTENT);
        List<Data> result = new ArrayList<>(map.values());
        map.evictAll();
        return result;
    }
}
