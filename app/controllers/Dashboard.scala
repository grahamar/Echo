package controllers

import play.api.mvc.Controller
import jp.t2v.lab.play2.auth.AuthElement
import models.NormalUser
import models.Administrator
import views._

object Dashboard extends Dashboard {}

trait Dashboard extends Controller with Pjax with AuthElement with AuthConfigImpl {

    def index = StackAction(AuthorityKey -> NormalUser) { implicit request =>
        val title = "Dashboard"
        Ok(html.dashboard.index(title))
    }

}
