package com.mjcr.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Join {
  def main(args:Array[String]):Unit={
    
    val conf = new SparkConf()
      .setAppName("join")
      .setMaster("local")
     
    val sc = new SparkContext(conf)
    
    val rdd1 = sc.makeRDD(Array(
        Tuple2("a",1),
        Tuple2("b",2),
        Tuple2("b",5),
        Tuple2("c",3),
        Tuple2("d",4),
        Tuple2("f",43)
      ))
      
     val rdd2 = sc.makeRDD(Array(
        Tuple2("a",11),
        Tuple2("b",22),
        Tuple2("b",66),
        Tuple2("c",33),
        Tuple2("d",44),
        Tuple2("e",55)
     ))
     
     /*
      * join 是一个内连接
      */
    val joinResultRDD = rdd1.join(rdd2)
    
    /*
     * take(n) 以数组形式返回前n个元素
     */
    joinResultRDD.take(10).foreach(println)
    
    /*
     * first()取rdd第一个元素
     */
    val result = joinResultRDD.first()
    println("key: "+result._1+" value: "+result._2)
    
    /*
     * 左外连接
     */
    rdd1.leftOuterJoin(rdd2).take(10).foreach(println)
    
    
    sc.stop()
  }
}