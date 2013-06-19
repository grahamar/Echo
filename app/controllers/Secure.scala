package controllers

import play.api.mvc.Controller
import jp.t2v.lab.play2.auth.AuthElement
import models.NormalUser
import models.Administrator
import views._

object Secure extends Secure {}

trait Secure extends Controller with Pjax with AuthElement with AuthConfigImpl {

    def dashboard = StackAction(AuthorityKey -> NormalUser) { implicit request =>
        val title = "Dashboard"
        Ok(html.secure.dashboard(title))
    }

    def list = StackAction(AuthorityKey -> NormalUser) { implicit request =>
        val title = "all messages"
        Ok(html.secure.list(title))
    }

    def detail(id : Int) = StackAction(AuthorityKey -> NormalUser) { implicit request =>
        val title = "messages detail "
        Ok(html.secure.detail(title + id))
    }

    def write = StackAction(AuthorityKey -> Administrator) { implicit request =>
        val title = "write message"
        Ok(html.secure.write(title))
    }

}
