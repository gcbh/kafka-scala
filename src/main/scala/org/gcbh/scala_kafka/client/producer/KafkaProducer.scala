package org.gcbh.scala_kafka.client.producer

import java.util.Properties

import com.twitter.util.Future
import org.apache.kafka.clients.consumer.OffsetAndMetadata
import org.apache.kafka.clients.producer.{Callback, ProducerRecord, RecordMetadata, KafkaProducer => JKafkaProducer}
import org.apache.kafka.common.{Metric, MetricName, PartitionInfo, TopicPartition}
import org.gcbh.scala_kafka.util.JavaConverters._

import scala.collection.JavaConverters._
import scala.concurrent.duration.TimeUnit

case class KafkaProducer[K, V](producer: JKafkaProducer[K, V])
    extends ProducerProxy[K, V] {
  override def initTransactions(): Unit = producer.initTransactions()

  override def beginTransaction(): Unit = producer.beginTransaction()

  override def sendOffsetsToTransaction(offsets: Map[TopicPartition, OffsetAndMetadata], consumerGroupId: String): Unit = {
    producer.sendOffsetsToTransaction(mapAsJavaMap(offsets), consumerGroupId)
  }

  override def commitTransaction(): Unit = producer.commitTransaction()

  override def abortTransaction(): Unit = producer.abortTransaction()

  override def send(record: ProducerRecord[K, V]): Future[RecordMetadata] = producer.send(record).toTwitter

  override def send(record: ProducerRecord[K, V], callback: Callback): Future[RecordMetadata] = {
    producer.send(record, callback).toTwitter
  }

  override def flush(): Unit = producer.flush()

  override def partitionsFor(topic: String): List[PartitionInfo] = asScalaBuffer(producer.partitionsFor(topic)).toList

  override def metrics(): Map[MetricName, Metric] = mapAsScalaMap(producer.metrics()).toMap

  override def close(): Unit = producer.close()

  override def close(timeout: Long, timeUnit: TimeUnit): Unit = producer.close(timeout, timeUnit)
}

object KafkaProducer {
  def apply[K, V](jKafkaProducer: JKafkaProducer[K, V]): KafkaProducer[K, V] = new KafkaProducer(jKafkaProducer)

  def apply[K, V](properties: Properties) = apply(new JKafkaProducer[K, V](properties))

}
