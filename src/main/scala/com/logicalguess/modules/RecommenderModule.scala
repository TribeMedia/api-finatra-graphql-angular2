package com.logicalguess.modules

import javax.inject.Singleton

import com.google.inject.{Inject, Provides}
import com.logicalguess.data.DataProvider
import com.logicalguess.data.movielens.{MovieLens_100k, MovieLens_1m}
import com.logicalguess.services._
import com.logicalguess.services.recommnder.{RecommenderService, ALSRecommenderService}
import com.twitter.inject.TwitterModule
import org.apache.spark.SparkContext

object RecommenderModule extends TwitterModule {
  flag("rec.count", 10, "number of recommendations to be returned")

  private val dataSet = flag("data.set", "100k", "1m or 100k")

  val MOVIE_LENS_1M = "1m"
  val MOVIE_LENS_100K = "100k"

  @Singleton
  @Provides
  def dataProvider(@Inject() sc: SparkContext): DataProvider = {
    println("------------------data provider init-------------------")
    dataSet() match {
      case MOVIE_LENS_1M => MovieLens_1m(sc, "src/main/resources/ml-1m")
      case MOVIE_LENS_100K => MovieLens_100k(sc, "src/main/resources/ml-100k")
      case _ => throw new IllegalArgumentException("unknown data set")
    }
  }

  @Singleton
  @Provides
  def recommenderProvider(@Inject() sc: SparkContext, dataProvider: DataProvider): RecommenderService = {
    new ALSRecommenderService(sc, dataProvider)
  }
}