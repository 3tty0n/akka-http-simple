lazy val commonSettings = Seq(
  name := "akka-http-simple",
  scalaVersion := "2.12.0",
  libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-http" % "10.0.0"
  ),
  scalacOptions ++= Seq(
    "-deprecation",
    "-feature",
    "-unchecked"
  )
)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
