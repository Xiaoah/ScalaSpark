package com.mjcr.core

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext

object PipelineTest {
  def main(args:Array[String]):Unit={
    val conf = new SparkConf().setAppName("pipeline").setMaster("local");
    val sc = new SparkContext(conf)
    
    val rdd = sc.makeRDD(Array(1,2,3,4))
    rdd.map(x => {println("map"+x)
      x
      }).filter(x => {println("filter"+x)
      true
      }).count()
      
    sc.stop()
  }
  
}