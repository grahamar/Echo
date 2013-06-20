package controllers

import play.api.mvc.Controller
import jp.t2v.lab.play2.auth.AuthElement
import models.NormalUser
import models.Administrator
import views._

object Administration extends Administration {}

trait Administration extends Controller with Pjax with AuthElement with AuthConfigImpl {

    def index = StackAction(AuthorityKey -> NormalUser) { implicit request =>
        val title = "Administration"
        Ok(html.admin.index(title))
    }
}
