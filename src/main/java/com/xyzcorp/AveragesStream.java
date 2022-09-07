package com.xyzcorp;

import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.errors.StreamsUncaughtExceptionHandler.StreamThreadExceptionResponse;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

public class AveragesStream {
//     public static void main(String[] args) {
//         Properties props = new Properties();
//         props.put(StreamsConfig.APPLICATION_ID_CONFIG,
//                 "order-averages-stream");
//         props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG,
//                 "localhost:9092");
//         props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG,
//                 Serdes.String().getClass());
//         props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG,
//                 Serdes.Integer().getClass());
//         props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

//         StreamsBuilder builder = new StreamsBuilder();

//         KTable<String, Integer> totalRevenueByState = builder.table("total-revenue-by-state",
//                 Consumed.with(Serdes.String(), Serdes.Integer())); // Key: State, Value: Amount

//         KTable<String, Long> numberOrdersByState = builder.table("number-orders-by-state",
//                 Consumed.with(Serdes.String(), Serdes.Long()));

//         KTable<String, Long> averagesTable = totalRevenueByState.leftJoin(numberOrdersByState,
//                 (revenue, total) -> revenue / total);

//         averagesTable
//                 .toStream()
//                 .peek((key, value) -> System.out.printf("Placing key: %s, value %d in order-averages-by-state\n", key,
//                         value))
//                 .to("order-averages-by-state", Produced.with(Serdes.String(), Serdes.Long()));

//         Topology topology = builder.build();
//         System.out.println(topology.describe());

//         KafkaStreams streams = new KafkaStreams(topology, props);

//         streams.setUncaughtExceptionHandler(exception -> {
//             exception.printStackTrace();
//             return StreamThreadExceptionResponse.SHUTDOWN_CLIENT;
//         });

//         streams.start();

//         Runtime.getRuntime().addShutdownHook(new Thread(streams::close));
//     }
}
