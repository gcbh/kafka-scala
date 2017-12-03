package org.gcbh.scala_kafka.util

import com.twitter.util.{Return, Throw, Future => TwitterFuture, Promise => TwitterPromise, Try => TwitterTry}

import scala.concurrent.{ExecutionContext, Future => ScalaFuture, Promise => ScalaPromise}
import scala.language.implicitConversions
import scala.util.{Failure, Success, Try => ScalaTry}

object ImplicitConverters {
  implicit def toTwitter[T](f: ScalaFuture[T])(implicit ec: ExecutionContext): TwitterFuture[T] = {
    val promise = TwitterPromise[T]()
    f onComplete { promise update _ }
    promise
  }

 implicit def toScala[T](f: TwitterFuture[T]): ScalaFuture[T] = {
    val promise = ScalaPromise[T]()
    f respond { promise complete _ }
    promise.future
  }

  implicit def toTwitter[T](t: ScalaTry[T]): TwitterTry[T] = t match {
    case Success(s) => Return(s)
    case Failure(f) => Throw(f)
  }

  implicit def toScala[T](t: TwitterTry[T]): ScalaTry[T] = t match {
    case Return(r) => Success(r)
    case Throw(e) => Failure(e)
  }
}
