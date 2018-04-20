package com.mjcr

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object Spark {
  def main(args : Array[String]) {
    
    /**
     * 创建spark运行参数对象，sparkconf对象设置运行模式、app名称、所需要的资源
     */
    val conf = new SparkConf()
      .setAppName("SparkApp")
      .setMaster("local")
      
    /**
     * 创建SparkContext的上下文对象，它是通往集群的唯一通道，负责任务分发、失败后重试
     */
    val sc = new SparkContext(conf)
    
    /**
     * makeRDD 第一个参数代表RDD中的元素，第二个参数代表RDD的分区数
     */
    val rdd = sc.makeRDD(1 to 10,3)
    
    /**
     * mapRDD[String]
     */
    val mapRDD = rdd.map {_+"~"}
    
    /**
     * 想要执行必须有action算子
     * collect会将集群中的计算结果返回给Driver端，慎用
     */
    val resultArr = mapRDD.collect()
    resultArr.foreach(println)
    
    /**
     * 释放资源
     */
    sc.stop()
  }
}