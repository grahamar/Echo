package controllers

import play.api.mvc.Controller
import jp.t2v.lab.play2.auth.AuthElement
import models.NormalUser
import models.Administrator
import views._
import play.api.data._
import play.api.data.Forms._
import play.api.mvc._
import models.Account
import java.util.Date

object Feedback extends Feedback {}

trait Feedback extends Controller with Pjax with AuthElement with AuthConfigImpl {
    
    val title = "Feedback";
    val team = List("Graham", "Alison");
    
    val myFeedbackForm = Form {
        tuple("teamMembers" -> text, "fromDate" -> text, "toDate" -> text)
    }

    def index = StackAction(AuthorityKey -> NormalUser) { implicit request =>
        Ok(html.feedback.index(title, team))
    }
    
    def myFeedback = Action { implicit request =>
        myFeedbackForm.bindFromRequest.fold(
            formWithErrors => { 
                println(formWithErrors);
                Results.BadRequest;
            }, data => updateMyFeedback(data)
        )
    }
    
    def updateMyFeedback(data : (String, String, String)) : Result = {
        println("form eror");
        Results.Ok;
    }

}