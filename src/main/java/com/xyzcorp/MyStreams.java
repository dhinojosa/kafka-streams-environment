package com.xyzcorp;

import java.util.List;
import java.util.Properties;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.Produced;

public class MyStreams {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(StreamsConfig.APPLICATION_ID_CONFIG,
                "my-orders-stream");
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
                "localhost:9092");
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
                Serdes.String().getClass());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
                Serdes.Integer().getClass());

        StreamsBuilder builder = new StreamsBuilder();

        KStream<String, Integer> stream = builder.stream("my-orders"); // Key: State, Value: Amount

        List<String> northWestStates = List.of("WA", "OR", "ID");
        List<String> westernStates = List.of("CA", "NV", "AZ");

        // One branch
        stream.filter((key, value) -> northWestStates.contains(key))
                .peek((key, value) -> System.out.printf("Placing key: %s, value: %d in northwest-orders.\n", key,
                        value))
                .to("northwest-orders");

        //Two branches 
        stream.filter((key, value) -> westernStates.contains(key))
               .peek((key, value) ->
                 System.out.printf("Placing key: %s, value: %d in western-orders.\n", key, value))
               .to("western-orders");

   
        //Third branch
        stream.groupByKey()
              .reduce((total, next) -> total + next) 
              .toStream()
              .peek((key, value) ->
                  System.out.printf("Placing key: %s, value %d in total-revenue-by-state\n", key, value))
              .to("total-revenue-by-state",
                  Produced.with(Serdes.String(), Serdes.Integer()));

        //Fourth branch
        stream.groupByKey()
            .count()
            .toStream()
            .peek((key, value) ->
                System.out.printf("Placing key: %s, value %d in total-orders-by-state\n", key, value))
            .to("number-orders-by-state",
                Produced.with(Serdes.String(), Serdes.Long()));         


        Topology topology = builder.build();

        KafkaStreams streams = new KafkaStreams(topology, props);

        streams.setUncaughtExceptionHandler(exception -> {
            exception.printStackTrace();
            return StreamThreadExceptionResponse.SHUTDOWN_CLIENT;
        });

        streams.start();

        Runtime.getRuntime().addShutdownHook(new Thread(streams::close));

    }
}