# Cucumber for Eclipse
Cucumber4Eclipse is a fork of the well known [Cucumber-JVM](https://github.com/cucumber/cucumber-jvm) project providing the capability to run [SWTBot tests](https://www.eclipse.org/swtbot/) controlled by Cucumber Features files.

## Why is a separated project needed?
### Classloading in OSGI
Out-of-the-box, it is not possible to rund SWTBot Tests using pure Cucumber-JVM. The current implementation of Cucumber-JVM is intensively using the classloader mechanisms of Java.
In contrast to Cucumber-JVM, SWTBot tests as well as [Eclipse RCP](https://wiki.eclipse.org/Rich_Client_Platform) applications are based on OSGI modules. Each OSGI-Module has it's own classloader. Therefore, Cucumber-JVM ist not able to resolve glue code and feature resources when running within a OSGI environment.

### Bundling Cucumber as OSGI bundles was stopped
With Cucumber-JVM 3.0.0 the packaging of OSGI-bundles [has been stopped](https://github.com/cucumber/cucumber/issues/412).

## Building the project
Please use Maven 3.6.

### The short story
* checkout
* mvn clean install org.reficio:p2-maven-plugin:1.3.0:site -f pom_swtbot.xml
* cd cucumber-eclipse
* mvn clean verify

You can find the created p2-repository in: 
*./cucumber-eclipse/releng/io.cucumber.eclipse.update/target/repository*

### The long story
The build process runs over 2 stages:
As an intermediate step, the first stage creates OSGI bundles for the Maven modules (core, junit and java) provided as a [P2 repository](https://www.eclipse.org/equinox/p2/) (./distribuion-p2). These modules are the basis of Cucumber4Eclipse. In comparison to the original Cucumber-JVM project the modules are changed as less as possible to keep this project updatable for the future. This will easier the transfer of changes from the original Cucumber-JVM project.

In a second step, two OSGI fragments (core-eclipse, core-junit) are created patching the original OSGI bundles (core, junit).

The 2-step mechanism is unfortunately necessary because the first step is based on pure maven (pom-first-approach) and the second step is based on Tycho (manifest-first-approach).

Take a look to the provided Jenkinsfile. This implements the entire build process including the example.

## Running the example
### The short story
* cd ./examples/rcp-example-appl
* mvn clean verify

### The long story
The example is a RCP-appliaction implementing a calculator (similar to the calculator example of Cucumber-JVM).The tests are executed as GUI-Tests using SWTBot.
The tests are provided by a test bundle (com.avenqo.cucumber.example.appl.uitests) containing the glue code & features as usual for Cucumber.
One note regarding the runner: The runner class **cucumber.api.junitCucumber** is a patched variant. In addition to Cucumber-JVM the class provides the same screenshot functionality as *SWTBotJunit4ClassRunner*. This means a screenshot is created (in directory *screenshots*) when a test has failed.

## Current restrictions
* This projects supports Eclipse RCP E4 Version 4.9 and above as well as SWTBot 2.7 and above.
* Currently there is no support for Java8 Lamda expressions available. Contributions are welcome.
