package org.gcbh.scala_kafka.util

import java.util.concurrent.{Future => JavaFuture}

import com.twitter.util.{Future => TwitterFuture}

import scala.concurrent.{ExecutionContext, Future => ScalaFuture}
import scala.util.control.NonFatal

object JavaConverters {
  implicit class JFutures[T](f: JavaFuture[T]) {
    def toTwitter: TwitterFuture[T] = {
      try {
        TwitterFuture(f.get)
      } catch {
        case NonFatal(e) => TwitterFuture.exception(e)
      }
    }

    def toScala(implicit ec: ExecutionContext): ScalaFuture[T] = {
      try {
        ScalaFuture(f.get())
      } catch {
        case NonFatal(e) => ScalaFuture.failed(e)
      }
    }
  }
}
