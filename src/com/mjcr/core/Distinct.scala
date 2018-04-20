package com.mjcr.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Distinct {
  def main(args:Array[String]):Unit={
     val conf = new SparkConf()
      .setAppName("bykey")
      .setMaster("local")
     
    val sc = new SparkContext(conf)
    
    val rdd1 = sc.makeRDD(Array(
      Tuple2("A",1),
      Tuple2("B",2),
      Tuple2("B",2),
      Tuple2("C",5),
      Tuple2("D",6),
      Tuple2("D",6)
    ))
    
    /*
     * distinct 去重
     */
    rdd1.distinct().foreach(println)
    
    /*
     * 用groupByKey实现distint
     */
    var resultArr = rdd1.map(x=>(x,1)).groupByKey()
    resultArr.foreach(x=>println(x._1))
    
  }
}