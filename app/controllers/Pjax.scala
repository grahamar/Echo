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

trait Pjax extends StackableController {
    self : Controller with AuthElement with AuthConfigImpl =>

    type Template = String => Html => Html

    case object TemplateKey extends RequestAttributeKey[Template]

    abstract override def proceed[A](req : RequestWithAttributes[A])(f : RequestWithAttributes[A] => Result) : Result = {
        val template : Template = if (req.headers.keys("X-Pjax")) html.pjaxTemplate.apply else html.fullTemplate.apply(loggedIn(req))
        super.proceed(req.set(TemplateKey, template))(f)
    }

    implicit def template[A](implicit req : RequestWithAttributes[A]) : Template = req.get(TemplateKey).get

}