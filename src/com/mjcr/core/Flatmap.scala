package com.mjcr.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext


object Flatmap {
  def main(args:Array[String]):Unit={
    
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("app")
    
    val sc = new SparkContext(conf)
    val rdd = sc.parallelize(Array(
        "hello,spark",
        "hello,jay chou"), 1)
    
    rdd.map(x => x.split(",")).foreach(println)
    
    val flatMapRDD = rdd.flatMap(x => x.split(","))
    flatMapRDD.foreach(println)
    
    sc.stop()
  }
}