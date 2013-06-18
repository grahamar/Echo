package controllers

import play.api.mvc.Controller
import models.User
import views._

object Secure extends Controller with Secured {

  /**
   * Display restricted area only if user is logged in.
   */
  def index = IsAuthenticated { username =>
    _ =>
      User.findByEmail(username).map { user =>
        Ok(
          html.secure(user)
        )
      }.getOrElse(Forbidden)
  }
    
}