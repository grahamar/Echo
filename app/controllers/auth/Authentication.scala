package controllers.auth

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._
import views._
import models.User
import controllers.routes

object Authentication extends Controller {

    val loginForm = Form(
        tuple(
            "email" -> text,
            "password" -> text
        ) verifying ("Invalid email or password", result => result match {
                case (email, password) => User.authenticate(email, password).isDefined
            })
    )

    /**
     * Login page.
     */
    def login = Action { implicit request =>
        Ok(html.login(loginForm))
    }

    /**
     * Logout and clean the session.
     */
    def logout = Action {
        Redirect(routes.Authentication.login).withNewSession.flashing(
            "success" -> "You've been logged out"
        )
    }

    /**
     * Handle login form submission.
     */
    def authenticate = Action { implicit request =>
        loginForm.bindFromRequest.fold(
            formWithErrors => BadRequest(html.login(formWithErrors)),
            user => Redirect(routes.Secure.index()).withSession("email" -> user._1)
        )
    }

}