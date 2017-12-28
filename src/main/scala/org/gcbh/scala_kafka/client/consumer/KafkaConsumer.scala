package org.gcbh.scala_kafka.client.consumer

import java.util.Properties
import java.util.regex.Pattern

import org.apache.kafka.clients.consumer.{ConsumerRebalanceListener, ConsumerRecords, OffsetAndMetadata, OffsetAndTimestamp, OffsetCommitCallback, KafkaConsumer => JKafkaConsumer}
import org.apache.kafka.common.{Metric, MetricName, PartitionInfo, TopicPartition}

import scala.collection.JavaConverters._
import scala.concurrent.duration.TimeUnit

class KafkaConsumer[K, V](consumer: JKafkaConsumer[K, V])
  extends ConsumerProxy[K, V] {
  override def assignment: Set[TopicPartition] = consumer.assignment().asScala.toSet

  override def subscription: Set[String] = consumer.subscription().asScala.toSet

  override def subscribe(topics: Iterable[String], listener: ConsumerRebalanceListener): Unit =
    consumer.subscribe(topics.asJavaCollection, listener)

  override def subscribe(topics: Iterable[String]): Unit = consumer.subscribe(topics.asJavaCollection)

  override def subscribe(pattern: Pattern, listener: ConsumerRebalanceListener): Unit =
    consumer.subscribe(pattern, listener)

  override def subscribe(pattern: Pattern): Unit = consumer.subscribe(pattern)

  override def unsubscribe(): Unit = consumer.unsubscribe()

  override def assign(partitions: Iterable[TopicPartition]): Unit = consumer.assign(partitions.asJavaCollection)

  override def poll(timeout: Long): ConsumerRecords[K, V] = consumer.poll(timeout)

  override def commitSync(): Unit = consumer.commitSync()

  override def commitSync(offsets: Map[TopicPartition, OffsetAndMetadata]): Unit = consumer.commitSync(offsets.asJava)

  override def commitAsync(): Unit = consumer.commitAsync()

  override def commitAsync(callback: OffsetCommitCallback): Unit = consumer.commitAsync(callback)

  override def commitAsync(offsets: Map[TopicPartition, OffsetAndMetadata], callback: OffsetCommitCallback): Unit =
    consumer.commitAsync(offsets.asJava, callback)

  override def seek(partition: TopicPartition, offset: Long): Unit = consumer.seek(partition, offset)

  override def seekToBeginning(partitions: Iterable[TopicPartition]): Unit =
    consumer.seekToBeginning(partitions.asJavaCollection)

  override def seekToEnd(partitions: Iterable[TopicPartition]): Unit = consumer.seekToEnd(partitions.asJavaCollection)

  override def position(partition: TopicPartition): Long = consumer.position(partition)

  override def committed(partition: TopicPartition): OffsetAndMetadata = consumer.committed(partition)

  override def metrics(): Map[MetricName, Metric] = consumer.metrics().asScala.toMap

  override def partitionsFor(topic: String): List[PartitionInfo] = asScalaBuffer(consumer.partitionsFor(topic)).toList

  override def listTopics(): Map[String, List[PartitionInfo]] = {
    consumer.listTopics().asScala.map { t =>
      t._1 -> asScalaBuffer(t._2).toList
    }.toMap
  }

  override def pause(partitions: Iterable[TopicPartition]): Unit = consumer.pause(partitions.asJavaCollection)

  override def resume(partitions: Iterable[TopicPartition]): Unit = consumer.resume(partitions.asJavaCollection)

  override def paused(): Set[TopicPartition] = consumer.paused().asScala.toSet

  override def offsetsForTimes(timestampsToSearch: Map[TopicPartition, Long]): Map[TopicPartition, OffsetAndTimestamp] =
    consumer.offsetsForTimes {
      timestampsToSearch.map(t => t._1 -> t._2.asInstanceOf[java.lang.Long]).asJava
    }.asScala.toMap

  override def beginningOffsets(partitions: Iterable[TopicPartition]): Map[TopicPartition, Long] =
    consumer.beginningOffsets(partitions.asJavaCollection).asScala.map(t => t._1 -> t._2.asInstanceOf[Long]).toMap

  override def endOffsets(partitions: Iterable[TopicPartition]): Map[TopicPartition, Long] =
    consumer.endOffsets(partitions.asJavaCollection).asScala.map(t => t._1 -> t._2.asInstanceOf[Long]).toMap

  override def close(): Unit = consumer.close()

  override def close(timeout: Long, timeUnit: TimeUnit): Unit = consumer.close(timeout, timeUnit)

  override def wakeUp(): Unit = consumer.wakeup()
}

object KafkaConsumer {
  def apply[K, V](jKafkaConsumer: JKafkaConsumer[K, V]): KafkaConsumer[K, V] = new KafkaConsumer[K, V](jKafkaConsumer)

  def apply[K, V](properties: Properties) = apply(new JKafkaConsumer[K, V](properties))


}
