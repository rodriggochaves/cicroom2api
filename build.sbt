val ScalatraVersion = "2.5.3"

organization := "com.github.rodriggochaves"

name := "cicroomapi"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.12.3"

resolvers += Classpaths.typesafeReleases

libraryDependencies ++= Seq(
  "org.scalatra" %% "scalatra" % ScalatraVersion,
  "org.scalatra" %% "scalatra-scalatest" % ScalatraVersion % "test",
  "org.scalatra" % "scalatra-json_2.12" % "2.5.3",
  "ch.qos.logback" % "logback-classic" % "1.1.5" % "runtime",
  "org.eclipse.jetty" % "jetty-webapp" % "9.2.15.v20160210" % "container",
  "javax.servlet" % "javax.servlet-api" % "3.1.0" % "provided",
  "com.typesafe.slick" %% "slick" % "3.2.0",
  "org.postgresql" % "postgresql" % "9.3-1100-jdbc4",
  "com.mchange" % "c3p0" % "0.9.5.2",
  "org.json4s" %% "json4s-jackson" % "3.5.3",
  "com.google.code.gson" % "gson" % "2.8.2",
  "com.typesafe.akka" %% "akka-actor" % "2.4.12",
  "net.databinder.dispatch" %% "dispatch-core" % "0.12.0",
)

enablePlugins(SbtTwirl)
enablePlugins(ScalatraPlugin)
