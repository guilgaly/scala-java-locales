val scalaJSVersion =
  Option(System.getenv("SCALAJS_VERSION")).getOrElse("0.6.18")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion)

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "1.1")

addSbtPlugin("org.scalastyle" % "scalastyle-sbt-plugin" % "0.8.0")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.0.0")

// Dependencies for custom tasks
libraryDependencies := {
  CrossVersion.partialVersion(scalaVersion.value) match {
    // if scala 2.11+ is used, add dependency on scala-xml module
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      libraryDependencies.value ++ Seq(
        "org.scala-lang.modules" %% "scala-xml" % "1.0.5")
    case _ =>
      libraryDependencies.value
  }
}

libraryDependencies ++= Seq(
  "com.eed3si9n" %% "treehugger" % "0.4.1"
)
