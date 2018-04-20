package com.mjcr.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object ZipOper {
  def main(args: Array[String]):Unit = {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("zipoper")
      
    val sc = new SparkContext(conf)
    val rdd1 = sc.parallelize(Array("a","b","c"))
    val rdd2 = sc.parallelize(Array(1,2,3))
    
    /*
     * 将两个非kv格式的rdd变成一个kv格式的rdd
     */
    val result = rdd1.zip(rdd2)
    
    /*
     * 将非kv格式的rdd变成一个kv格式的rdd
     */
    val result1 = rdd1.zipWithIndex() 
    result1.foreach(println)
    
    sc.stop()
  }
}