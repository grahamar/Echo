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
import play.api.libs.json.Json

object Feedback extends Feedback {}

trait Feedback extends Controller with Pjax with AuthElement with AuthConfigImpl {
    
    val title = "Feedback";
    val team = List("Graham", "Alison");
    
    val myFeedbackForm = Form {
        tuple("teamMembers" -> text, "fromDate" -> text, "toDate" -> text)
    }

    def index = StackAction(AuthorityKey -> NormalUser) { implicit request =>
        val subject = loggedIn;
        Ok(html.feedback.index(title, team, subject))
    }
    
    def myFeedback = StackAction(AuthorityKey -> NormalUser) { implicit request =>
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
    
    def feedback(username : String) = StackAction(AuthorityKey -> NormalUser) { implicit request =>
        println("feedback json request")
	    Ok(Json.toJson(models.Feedback.findAllForReceiver(Account.findByUsername(Option(username)))
           .map(f => Json.obj(
		    	"id" -> f.id,
		    	"receiver" -> f.receiver,
		    	"rating" -> f.rating,
		    	"comment" -> f.comment,
		    	"originalSender" -> f.originalsender,
		    	"createdBy" -> f.createdby,
		    	"created" -> f.created
		    ))))
	}

}