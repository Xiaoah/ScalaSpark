package com.mjcr.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object WordCount {
  def main(args:Array[String]):Unit = {
    
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("wordcount")
    
    val sc = new SparkContext(conf)
    
    sc.textFile("wc", 3)
      .flatMap(line => line.split(" "))              //.flatMap(_.split(" "))
      .map(word => (word,1))                         //.map((_,1))
      .reduceByKey((v1,v2) => v1 + v2)               //.reduceByKey(_+_)
      .map(x=> (x._2,x._1))                          
      .sortByKey(false)                              //.sortBy(x=>x._2,false)
      .map(x=>(x._2,x._1))
      .foreach(println)
      
     sc.stop()
  }
}