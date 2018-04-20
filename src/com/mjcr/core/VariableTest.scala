package com.mjcr.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext


object VariableTest {
  def main(args:Array[String]):Unit={
    val conf = new SparkConf().setMaster("local").setAppName("variable")
    val sc = new SparkContext(conf)
    
    /*
     * 广播变量
     */
    val rdd = sc.makeRDD(1 to 10,3)
    val i = 6
    val bc = sc.broadcast(i)
    rdd.filter({
     val b = bc.value
     x => x!=b
    }).foreach(println)
    
    //广播一个集合
    val nameRDD = sc.makeRDD(List("xah","zkf"))
    val blackList = List("zkf","jay","dss")
    val bcBlackList = sc.broadcast(blackList)
    nameRDD.filter(x => {
      val bl = bcBlackList.value
      !bl.contains(x)
    }).foreach(println)
    /*
     * 累加变量
     */
    val count = sc.accumulator(0)
    val lineRDD = sc.textFile("wc", 2)
    lineRDD.map(x=>{
      count.add(1)
      x
    }).count()
    
    println(count.value)
  }
}