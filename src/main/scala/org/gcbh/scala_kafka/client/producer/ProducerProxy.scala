package org.gcbh.scala_kafka.client.producer

import com.twitter.util.Future
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.clients.producer.{Callback, ProducerRecord, RecordMetadata}
import org.apache.kafka.common.{Metric, MetricName, PartitionInfo, TopicPartition}

import scala.concurrent.duration.TimeUnit

trait ProducerProxy[K, V] {
  def initTransactions(): Unit
  def beginTransaction(): Unit
  def sendOffsetsToTransaction(offsets: Map[TopicPartition, OffsetAndMetadata], consumerGroupId: String): Unit
  def commitTransaction(): Unit
  def abortTransaction(): Unit
  def send(record: ProducerRecord[K, V]): Future[RecordMetadata]
  def send(record: ProducerRecord[K, V], callback: Callback): Future[RecordMetadata]
  def flush(): Unit
  def partitionsFor(topic: String): List[PartitionInfo]
  def metrics(): Map[MetricName, Metric]
  def close(): Unit
  def close(timeout: Long, timeUnit: TimeUnit)
}
