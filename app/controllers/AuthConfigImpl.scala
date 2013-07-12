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

trait AuthConfigImpl extends AuthConfig {

    type Id = Int

    type User = Account

    type Authority = Permission

    val idTag = classTag[Id]

    val sessionTimeoutInSeconds = 3600

    def resolveUser(id : Id) = Account.findById(id)

    def logoutSucceeded(request : RequestHeader) = Redirect(routes.Application.login)

	def loginSucceeded(request: RequestHeader): Result = {
	  val uri = request.session.get("access_uri").getOrElse(routes.Dashboard.index.url.toString)
	  Redirect(uri).withSession(request.session - "access_uri")
	}
    
    def authorizationFailed(request : RequestHeader) = Forbidden("no permission")
    
    def authenticationFailed(request: RequestHeader): Result = Redirect(routes.Application.login).withSession("access_uri" -> request.uri)
	
    def authorize(user : User, authority : Authority) = (user.permission, authority) match {
        case (Administrator, _)       => true
        case (NormalUser, NormalUser) => true
        case _                        => false
    }

}