# Cucumber for Eclipse
Cucumber-JVM for Eclipse is a fork of the well known [Cucumber-JVM](https://github.com/cucumber/cucumber-jvm) project providing the capability to run SWTBot tests controlled by Cucumber Features files.

## Why is a separated project needed?
### Classloading in OSGI
Out-of-the-box, it is not possible to rund SWTBot Tests using pure Cucumber-JVM. The current implementation is intensively using the classloader mechanism of Java.
In contrast to Cucumber-JVM, SWTBot tests as well as Eclipse RCP Applications are based on OSGI modules. Each OSGI-Module has it's own classloader. Therefore, Cucumber-JVM ist not able to resolve glue code and feature resources.

### Bundling Cucumber as OSGI bundles was stopped
With Cucumber-JVM 3.0.0 the packaging of OSGI-bundles [has been stopped](https://github.com/cucumber/cucumber/issues/412).

## Building the project
### The short story
* checkout
* mvn clean install org.reficio:p2-maven-plugin:1.3.0:site -f pom_swtbot.xml
* cd cucumber-eclipse
* mvn clean verify

You can find the created p2-repository in: 
./cucumber-eclipse/releng/io.cucumber.eclipse.update/target/repository

## Running the example
### The short story
* cd ./examples/rcp-example-appl
* mvn clean verify
