package org.gcbh.scala_kafka

import java.util.Properties

import com.twitter.conversions.time._
import com.twitter.util.{Await, Future, Return, Throw}
import org.gcbh.scala_kafka.util.JavaConverters._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

object App {
  def main(args: Array[String]): Unit = {
    val topic = args(0)
    val output = args(1)

    val props: Properties = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props.put("acks", "all")
    props.put("retries", "0")
    props.put("batch.size", "16384")
    props.put("linger.ms", "1")
    props.put("buffer.memory", "33554432")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")

    val producer = new KafkaProducer[String, String](props)

    val response = producer.send(new ProducerRecord[String, String](topic, output, output))
    val result = response.toTwitter transform {
      case Return(r) => Future(r.timestamp())
      case Throw(e) => Future.exception(e)
    }
    Await.result(result, 10.seconds)
  }
}
