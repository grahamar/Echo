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
                Account(1, "admin", "secret", "System Administrator", Administrator),
                Account(2, "grhodes", "secret", "Bob", NormalUser)
            ) foreach Account.create
        }
        if(Feedback.findAll.isEmpty) {
            Seq(
                Feedback(1, 1, 3, "Test comment", 2, 2, new Date()),
                Feedback(2, 1, 2, "Test comment 1", 2, 2, new Date()),
                Feedback(3, 1, 1, "Test comment 2", 2, 2, new Date()),
                Feedback(4, 1, 3, "Test comment 3", 2, 2, new Date()),
                Feedback(5, 1, 3, "Test comment 4", 2, 2, new Date()),
                Feedback(6, 1, 2, "Test comment 5", 2, 2, new Date()),
                Feedback(7, 1, 2, "Test comment 6", 2, 2, new Date()),
                Feedback(8, 1, 2, "Test comment 7", 2, 2, new Date()),
                Feedback(9, 1, 2, "Test comment 8", 2, 2, new Date()),
                Feedback(10, 1, 1, "Test comment 9", 2, 2, new Date()),
                Feedback(11, 1, 1, "Test comment 10", 2, 2, new Date()),
                Feedback(12, 1, 1, "Test comment 11", 2, 2, new Date()),
                Feedback(13, 1, 3, "Test comment 12", 2, 2, new Date())
            ) foreach Feedback.create
        }
    }

}