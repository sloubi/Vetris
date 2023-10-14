# Vetris
An implementation of Tetris in java

Vetris has a special piece, the V piece. It can't rotate.

![vetris](https://sloubi.eu/images/projects/img-20201115-5fb1265eba353.jpg)

## Packaging with JDK (with packr)
`java -jar packr-all-4.0.0.jar --platform windows64 --jdk jdk-17_windows-x64_bin.zip --useZgcIfSupportedOs --executable Vetris --classpath "target/Vetris-1.2.jar" --mainclass eu.sloubi.App --vmargs Xmx1G --resources "src/main/resources" --output build-win64`

