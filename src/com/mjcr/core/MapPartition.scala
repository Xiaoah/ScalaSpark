package com.mjcr.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import scala.collection.mutable.ListBuffer

object MapPartition {
  def main(args : Array[String]){
    val conf = new SparkConf()
      .setAppName("app")
      .setMaster("local")
     
    val sc = new SparkContext(conf)
    
    val rdd = sc.makeRDD(1 to 10, 3)
    
    /**
     * mapPartitions遍历单位是一个partition，会将一个partition的数据量全部加载到一个集合里面
     */
    val mapPartitionRDD = rdd.mapPartitions(iterator =>{
      val list = new ListBuffer[Int]()
      while(iterator.hasNext){
        val num = iterator.next()
        list .+= (num+100)
      }
      list.iterator
    }, false)
     
    val resultArr = mapPartitionRDD.collect()
    resultArr.foreach(println)
    
    /**
     * mapPartionWithIndex 遍历单位也是一个partition，但是会同时返回分区号
     */
    val mapPartitionWithIndexRDD = rdd.mapPartitionsWithIndex((index,iterator) =>{
      val list = new ListBuffer[Int]()
      while(iterator.hasNext){
        val num = iterator.next()
        println("partitionId:"+index+ " value:"+num)
        list+=num
      }
      list.iterator
    }, false).collect()
    
    /**
     * filter
     */
    rdd.filter(x => x!=2).foreach(println)
    
    sc.stop()
  }
}