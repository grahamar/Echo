package models

import org.mindrot.jbcrypt.BCrypt
import scalikejdbc._, SQLInterpolation._

case class Account(id : Int, username : String, password : String, name : String, permission : Permission)

object Account extends SQLSyntaxSupport[Account] {

    val a = syntax("a")

    def apply(a : SyntaxProvider[Account])(rs : WrappedResultSet) : Account = apply(a.resultName)(rs)
    def apply(a : ResultName[Account])(rs : WrappedResultSet) : Account = new Account(
        id = rs.int(a.id),
        username = rs.string(a.username),
        password = rs.string(a.password),
        name = rs.string(a.name),
        permission = Permission.valueOf(rs.string(a.permission))
    )

    private val auto = AutoSession

    def authenticate(username : String, password : String)(implicit s : DBSession = auto) : Option[Account] = {
        findByUsername(Option(username)).filter { account => BCrypt.checkpw(password, account.password) }
    }

    def findByUsername(username : Option[String])(implicit s : DBSession = auto) : Option[Account] = withSQL {
        select.from(Account as a).where.eq(a.username, username)
    }.map(Account(a)).single.apply()

    def findById(id : Int)(implicit s : DBSession = auto) : Option[Account] = withSQL {
        select.from(Account as a).where.eq(a.id, id)
    }.map(Account(a)).single.apply()

    def findAll()(implicit s : DBSession = auto) : Seq[Account] = withSQL {
        select.from(Account as a)
    }.map(Account(a)).list.apply()

    def create(account : Account)(implicit s : DBSession = auto) {
        withSQL {
            import account._
            val pass = BCrypt.hashpw(account.password, BCrypt.gensalt())
            insert.into(Account).values(id, username, pass, name, permission.toString)
        }.update.apply()
    }

}