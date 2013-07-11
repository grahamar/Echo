import play.api._
import models._
import java.util.Date

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
        if(Feedback.findAll.isEmpty) {
            Seq(
                Feedback(1, 2, 3, "Test comment", 1, 1, new Date())
            ) foreach Feedback.create
        }
    }

}