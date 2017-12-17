package org.gcbh.scala_kafka.client.consumer

import java.util.regex.Pattern

import org.apache.kafka.clients.consumer._
import org.apache.kafka.common.{Metric, MetricName, PartitionInfo, TopicPartition}

import scala.concurrent.duration.TimeUnit

trait ConsumerProxy[K, V] {
  def assignment: Set[TopicPartition]
  def subscription: Set[String]
  def subscribe(topics: Iterable[String], listener: ConsumerRebalanceListener): Unit
  def subscribe(topics: Iterable[String]): Unit
  def subscribe(pattern: Pattern, listener: ConsumerRebalanceListener): Unit
  def subscribe(pattern: Pattern): Unit
  def unsubscribe(): Unit
  def assign(partitions: Iterable[TopicPartition]): Unit
  def poll(timeout: Long): ConsumerRecord[K, V]
  def commitSync(): Unit
  def commitSync(offsets: Map[TopicPartition, OffsetAndMetadata]): Unit
  def commitAsync(): Unit
  def commitAsync(callback: OffsetCommitCallback): Unit
  def commitAsync(offsets: Map[TopicPartition, OffsetAndMetadata], callback: OffsetCommitCallback): Unit
  def seek(partition: TopicPartition, offset: Long): Unit
  def seekToBeginning(partitions: Iterable[TopicPartition]): Unit
  def seekToEnd(partitions: Iterable[TopicPartition]): Unit
  def position(partition: TopicPartition): Long
  def committed(partition: TopicPartition): OffsetAndMetadata
  def metrics(): Map[MetricName, Metric]
  def partitionsFor(topic: String): List[PartitionInfo]
  def listTopics(): Map[String, List[PartitionInfo]]
  def pause(partitions: Iterable[TopicPartition]): Unit
  def resume(partitions: Iterable[TopicPartition]): Unit
  def paused(): Set[TopicPartition]
  def offsetsForTimes(timestampsToSearch: Map[TopicPartition, Long]): Map[TopicPartition, OffsetAndTimestamp]
  def beginningOffsets(partitions: Iterable[TopicPartition]): Map[TopicPartition, Long]
  def endOffsets(partitions: Iterable[TopicPartition]): Map[TopicPartition, Long]
  def close(): Unit
  def close(timeout: Long, timeUnit: TimeUnit): Unit
  def wakeUp(): Unit


}
