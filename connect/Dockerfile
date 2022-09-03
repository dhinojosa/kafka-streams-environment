FROM confluentinc/cp-kafka-connect-base

RUN   confluent-hub install --no-prompt mongodb/kafka-connect-mongodb:1.7.0 \
   && confluent-hub install --no-prompt debezium/debezium-connector-postgresql:1.9.3
