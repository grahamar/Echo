import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

    val appName = "Echo"
    val appVersion = "1.0-SNAPSHOT"

    val appDependencies = Seq(
        // Add your project dependencies here,
        jdbc,
        anorm,
        "org.mindrot" % "jbcrypt" % "0.3m",
        "jp.t2v" %% "play2.auth" % "0.9",
        "jp.t2v" %% "play2.auth.test" % "0.9" % "test",
        "com.github.seratch" %% "scalikejdbc" % "[1.6,)",
        "com.github.seratch" %% "scalikejdbc-test" % "[1.6,)",
        "com.github.seratch" %% "scalikejdbc-play-plugin" % "[1.6,)",
        "com.github.seratch" %% "scalikejdbc-interpolation" % "[1.6,)"
    )

    val main = play.Project(appName, appVersion, appDependencies).settings(
        resolvers += "Typesafe releases" at "http://repo.typesafe.com/typesafe/releases/",
        resolvers += "Typesafe Repo" at "http://repo.typesafe.com/typesafe/repo/",
        resolvers += "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
        resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases",

        lessEntryPoints <<= baseDirectory(customLessEntryPoints)
    )

    // Only compile the bootstrap bootstrap.less file and any other *.less file in the stylesheets directory 
    def customLessEntryPoints(base : File) : PathFinder = (
        (base / "app" / "assets" / "stylesheets" / "bootstrap" * "bootstrap.less") +++
        (base / "app" / "assets" / "stylesheets" / "bootstrap" * "responsive.less") +++
        (base / "app" / "assets" / "stylesheets" * "*.less")
    )

}
