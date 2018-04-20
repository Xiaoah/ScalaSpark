package com.mjcr.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import scala.collection.mutable.ListBuffer

object CombineByKeyOper {
  def main(args:Array[String]):Unit={
    val conf = new SparkConf()
      .setAppName("combinebykey")
      .setMaster("local")
    val sc = new SparkContext(conf)
    
    val rdd1 = sc.makeRDD(Array(("A",2),("B",1),("C",3),("D",6),("B",4)),2)
    rdd1.mapPartitionsWithIndex((index,iterator)=>{
      val list = new ListBuffer[(String,Int)]
      while(iterator.hasNext){
        val log = iterator.next()
        println("index:"+index+"\tvalue:"+log)
        list += log
      }
      list.iterator
    }, false).count()
  
    /*
     * 首先在每个分区内各自按照key来分组
     * 第一个函数：初始化，作用在每一个分区分一个分组里的第一个元素
     * 第二个函数：对每个分区中每个分组里面的元素按照传进去得集合函数进行聚合
     * 第三个函数：在reduce端进行大聚合
     */
    rdd1.combineByKey((x)=>x+"~", (x:String,y:Int)=>x+"@"+y, (x:String,y:String)=>x+"&"+y).foreach(println)
    sc.stop()
  }
}