package com.mjcr.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object CogroupOper {
  def main(args:Array[String]):Unit = {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("CogroupOper")
      
    val sc = new SparkContext(conf)
    val rdd1 = sc.makeRDD(Array(("A",2),("B",1),("C",3),("B",2),("D",6)))
    val rdd2 = sc.makeRDD(Array(("A",2),("C",3),("B",2),("E",6)))
    
    /*
     * 全外连接
     */
    val result = rdd1.fullOuterJoin(rdd2)
    result.foreach(println)
    
    /*
     * cogroup : rdd内部先groupbykey，再fullouterjoin
     */
    rdd1.cogroup(rdd2).foreach(println)
    
    sc.stop()
  }
}