package com.kroger.cache.poc.serializer;

import java.io.IOException;

import com.kroger.cache.poc.model.Data;
import com.hazelcast.nio.ObjectDataInput;
import com.hazelcast.nio.ObjectDataOutput;
import com.hazelcast.nio.serialization.StreamSerializer;

public class DataSerializer implements StreamSerializer<Data> {
    @Override
    public void write(ObjectDataOutput out, Data object) throws IOException {
        out.writeInt(object.getId());
        out.writeString(object.getMessage());
    }

    @Override
    public Data read(ObjectDataInput in) throws IOException {
        return Data.builder()
            .id(in.readInt())
            .message(in.readString())
            .build();
    }

    @Override
    public int getTypeId() {
        return 1;
    }
}
