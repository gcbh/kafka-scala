package org.gcbh.scala_kafka.client.producer

import org.apache.kafka.clients.producer.KafkaProducer

trait Producer[K, V] {
  def prod: KafkaProducer[K, V]
}
