package com.logicalguess.server

import javax.inject.Inject
import com.logicalguess.services.recommender.{RecommenderService, ALSRecommenderService}
import com.twitter.finatra.http.routing.HttpWarmup
import com.twitter.finatra.utils.Handler

class RecommenderWarmupHandler @Inject()(httpWarmup: HttpWarmup, recSvc: RecommenderService) extends Handler {
  override def handle() = {
    //recSvc.init
  }
}