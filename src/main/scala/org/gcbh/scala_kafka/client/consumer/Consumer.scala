package org.gcbh.scala_kafka.client.consumer

import org.apache.kafka.clients.consumer.KafkaConsumer

trait Consumer[K, V] {
  def cons: KafkaConsumer[K, V]
}
