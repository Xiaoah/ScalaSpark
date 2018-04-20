package com.mjcr.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import scala.collection.mutable.ListBuffer

object CoalesceOperator {
  def main(args:Array[String]):Unit = {
    val conf = new SparkConf().setMaster("local").setAppName("coalesce")
    val sc = new SparkContext(conf)
    
    val list = List("baby1","baby2","baby3","baby4","baby5","baby6",
        "baby7","baby8","baby9","baby10","baby11","baby12")
        
    val angle = sc.makeRDD(list,6)
    angle.mapPartitionsWithIndex((index,iterator) => {
      val list = new ListBuffer[String]()
      while(iterator.hasNext){
        val baby = iterator.next()
        //println("partitionID:"+index+"value:"+baby)
       // list.:+(baby)
        list.+=("partitionID:"+index+"value:"+baby)
      }
      list.iterator
    }, false).count()
    
    //增大分区数
/*    
    angle.coalesce(12, true).mapPartitionsWithIndex((index,iterator) => {
      val list = new ListBuffer[String]()
      while(iterator.hasNext){
        val baby = iterator.next()
        println("partitionID:"+index+"value:"+baby)
        list.:+(baby)
      }
      list.iterator
    }, false).count()
    
*/
    //缩小分区数
    angle.coalesce(3, true).mapPartitionsWithIndex((index,iterator) => {
      val list = new ListBuffer[String]()
      while(iterator.hasNext){
        val baby = iterator.next()
        println("partitionID:"+index+"value:"+baby)
        list.+=(baby)
      }
      list.iterator
    }, false).count()
    
  //打印出改变分区后，新分区中的数据分别来自原来的哪些分区
    angle.mapPartitionsWithIndex((index,iterator) => {
      val list = new ListBuffer[String]()
      while(iterator.hasNext){
        val baby = iterator.next()
        //println("partitionID:"+index+"value:"+baby)
       // list.:+(baby)
        list.+=("partitionID:"+index+"value:"+baby)
      }
      list.iterator
    }, false).coalesce(3, false).mapPartitionsWithIndex((index,iterator) => {
      val list = new ListBuffer[String]()
      while(iterator.hasNext){
        val baby = iterator.next()
        println("partitionID:"+index+"value:"+baby)
        list.+=(baby)
      }
      list.iterator
    }, false).count()
    
  }
}