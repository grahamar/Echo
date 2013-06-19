package controllers

import play.api.data._
import play.api.data.Forms._
import play.api.templates._
import models._
import views._
import play.api.mvc._
import play.api.mvc.Results._
import jp.t2v.lab.play2.auth._
import play.api.Play._
import play.api.cache.Cache
import reflect.classTag
import jp.t2v.lab.play2.stackc.{ RequestWithAttributes, RequestAttributeKey, StackableController }

object Application extends Controller with LoginLogout with AuthConfigImpl {

    val loginForm = Form {
        mapping("email" -> email, "password" -> text)(Account.authenticate)(_.map(u => (u.email, "")))
            .verifying("Invalid email or password", result => result.isDefined)
    }

    def login = Action { implicit request =>
        println("start login")
        Ok(html.login(loginForm))
    }

    def logout = Action { implicit request =>
        gotoLogoutSucceeded.flashing(
            "success" -> "You've been logged out"
        )
    }

    def authenticate = Action { implicit request =>
        println("start act")
        loginForm.bindFromRequest.fold(
            formWithErrors => { println("form eror"); BadRequest(html.login(formWithErrors)) },
            user => gotoLoginSucceeded(user.get.id)
        )
    }

}
