package com.mjcr.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object OperaByKey {
  def main(args:Array[String]):Unit={
    val conf = new SparkConf()
      .setAppName("bykey")
      .setMaster("local")
     
    val sc = new SparkContext(conf)
    
    val rdd1 = sc.makeRDD(Array(
      Tuple2("A",1),
      Tuple2("B",2),
      Tuple2("B",3),
      Tuple2("C",5),
      Tuple2("D",6),
      Tuple2("D",0)
    ))
    
    /*
     * groupByKey  按key分组
     */
    val grdd = rdd1.groupByKey()
    grdd.foreach(element => println(element))
    
    /*
     * reduceByKey 按key分组，再根据函数聚合
     */
    val rrdd = rdd1.reduceByKey((x,y)=>x+y)
    rrdd.foreach(element => println(element))
  }
}