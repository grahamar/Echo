import play.api._
import models.User

object Global extends GlobalSettings {

    override def onStart(app : Application) {
        InitialData.setup();
    }

}

object InitialData {

    def setup() = {
        if (User.findAll.isEmpty) {
            Seq(
                User("admin@redhogs.net", "System Administrator", "secret")
            ).foreach(User.create);
        }
    }

}