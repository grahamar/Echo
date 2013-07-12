package models

import java.util.Date
import scalikejdbc._
import SQLInterpolation._
import play.api.libs.json._

case class Feedback (
    id : Int,
    receiver : Int,
    rating : Int,
    comment : String,
    originalsender : Int,
    createdby : Int,
    created : Date
)

object Feedback extends SQLSyntaxSupport[Feedback] with Writes[Feedback] {

    val a = syntax("a")

    def apply(a : SyntaxProvider[Feedback])(rs : WrappedResultSet) : Feedback = apply(a.resultName)(rs)
    def apply(a : ResultName[Feedback])(rs : WrappedResultSet) : Feedback = new Feedback(
        id = rs.int(a.id),
        receiver = rs.int(a.receiver),
        rating = rs.int(a.rating),
        comment = rs.string(a.comment),
        originalsender = rs.int(a.originalsender),
        createdby = rs.int(a.createdby),
        created = rs.date(a.created)
    )

    private val auto = AutoSession

    def findById(id : Int)(implicit s : DBSession = auto) : Option[Feedback] = withSQL {
        select.from(Feedback as a).where.eq(a.id, id)
    }.map(Feedback(a)).single.apply()

    def findAll()(implicit s : DBSession = auto) : Seq[Feedback] = withSQL {
        select.from(Feedback as a)
    }.map(Feedback(a)).list.apply()

    def findAllForReceiver(account : Option[Account])(implicit s : DBSession = auto) : Seq[Feedback] = withSQL {
        select.from(Feedback as a).where.eq(a.receiver, account.get.id)
    }.map(Feedback(a)).list.apply()
    
    def create(feedback : Feedback)(implicit s : DBSession = auto) {
        withSQL {
            import feedback._
            insert.into(Feedback).values(id, receiver, rating, comment, originalsender, createdby, created)
        }.update.apply()
    }
    
    implicit def writes(f : Feedback) : JsValue = {
        Json.obj(
	    	"id" -> f.id,
	    	"receiver" -> f.receiver,
	    	"rating" -> f.rating,
	    	"comment" -> f.comment,
	    	"originalSender" -> f.originalsender,
	    	"createdBy" -> f.createdby,
	    	"created" -> f.created
	    )
    }
    
};