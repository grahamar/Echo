import play.api._
import models._

object Global extends GlobalSettings {

    override def onStart(app : Application) {
        InitialData.setup();
    }

}

object InitialData {

    def setup() = {
        if (Account.findAll.isEmpty) {
            Seq(
                Account(1, "admin@redhogs.net", "secret", "System Administrator", Administrator),
                Account(2, "grhodes@informationmosaic.com", "secret", "Bob", NormalUser)
            ) foreach Account.create
        }
    }

}