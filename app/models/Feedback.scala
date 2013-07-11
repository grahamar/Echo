package models

import java.util.Date
import scalikejdbc._, SQLInterpolation._

case class Feedback (
    id : Int,
    receiver : Int,
    rating : Int,
    comment : String,
    originalSender : Int,
    createdBy : Int,
    created : Date
)

object Feedback extends SQLSyntaxSupport[Feedback] {

    val a = syntax("a")

    def apply(a : SyntaxProvider[Feedback])(rs : WrappedResultSet) : Feedback = apply(a.resultName)(rs)
    def apply(a : ResultName[Feedback])(rs : WrappedResultSet) : Feedback = new Feedback(
        id = rs.int(a.id),
        receiver = rs.int(a.receiver),
        rating = rs.int(a.rating),
        comment = rs.string(a.comment),
        originalSender = rs.int(a.originalSender),
        createdBy = rs.int(a.createdBy),
        created = rs.date(a.created)
    )

    private val auto = AutoSession

    def findById(id : Int)(implicit s : DBSession = auto) : Option[Feedback] = withSQL {
        select.from(Feedback as a).where.eq(a.id, id)
    }.map(Feedback(a)).single.apply()

    def findAll()(implicit s : DBSession = auto) : Seq[Feedback] = withSQL {
        select.from(Feedback as a)
    }.map(Feedback(a)).list.apply()

    def create(feedback : Feedback)(implicit s : DBSession = auto) {
        withSQL {
            import feedback._
            insert.into(Feedback).values(id, receiver, rating, comment, originalSender, createdBy, created)
        }.update.apply()
    }
    
};