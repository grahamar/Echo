package controllers

import play.api.mvc.Controller
import jp.t2v.lab.play2.auth.AuthElement
import models.NormalUser
import models.Administrator
import views._

object SuggestionBox extends SuggestionBox {}

trait SuggestionBox extends Controller with Pjax with AuthElement with AuthConfigImpl {

    def index = StackAction(AuthorityKey -> NormalUser) { implicit request =>
        val title = "Suggestion Box"
        Ok(html.feedback.index(title))
    }
}
