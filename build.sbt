import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "DeepPokemon",
      scalaVersion := "2.12.1",
      version      := ""
    )),
    name := "deep-pokemon",
    crossTarget := file(System.getProperty("build.location")),
    mainClass in run in Compile := Some("DeepPokemon.WSClient")
  )

libraryDependencies ++= Seq(
  scalaTest % Test,
  "com.twitter" %% "util-core" % "6.41.0",
  "com.twitter" %% "finagle-http" % "6.42.0",
  "org.parboiled" %% "parboiled-scala" % "1.1.8",
  "io.spray" %%  "spray-json" % "1.3.3",
  "com.github.fommil" %% "spray-json-shapeless" % "1.3.0"
)

crossPaths := false

oneJarSettings

artifact in oneJar ~= { _.copy(
  extension = "jar",
  name = System.getProperty("build.jar.name")
)}
