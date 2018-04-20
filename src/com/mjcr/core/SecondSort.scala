package com.mjcr.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext


object SecondSort {
  def main(args:Array[String]):Unit={
    val conf = new SparkConf().setAppName("secondsort").setMaster("local")
    val sc = new SparkContext(conf)
    val lines = sc.textFile("secondsortfile")
    
    val pairs = lines.map(x => (new SecondSortKey1(x.split(" ")(0).toInt,x.split(" ")(1).toInt),x))
    val sortedPairs = pairs.sortByKey(true)
    sortedPairs.map(_._2).foreach(println)
  }
}

class SecondSortKey1(val first:Int,val second:Int) extends Ordered[SecondSortKey1] with Serializable{
  def compare(that:SecondSortKey1): Int={
    if(this.first-that.first == 0)
      this.second - that.second
    else
      this.first - that.first
  }
}