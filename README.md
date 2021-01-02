# Java Bouncing Ball Simulation
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/christopher-boustros/Java-Bouncing-Ball-Simulation)

A Java applet program that simulates multiple bouncing balls with simulation parameters using the ACM graphics library. This program was made as part of a course assignment for ECSE 202 Introduction to Software Development in fall 2018 at McGill University. 

![Alt text](/Screenshot1.PNG?raw=true "Screenshot1")
![Alt text](/Screenshot2.PNG?raw=true "Screenshot2")

## How to run the program

### Requirements

- Eclipse IDE for Java Developers
- JDK 8
- JRE 1.8

### Running the program with Eclipse

Clone this repository with `git clone https://github.com/christopher-boustros/Java-Bouncing-Ball-Simulation.git`, or alternatively, download and extract the ZIP archive.

Open the Eclipse IDE and go to `Window > Preferences` to open the Preferences window.

In the Preferences window, go to `Java > Installed JREs` and make sure Eclipse is using JRE 1.8. If not, click *Add...*, choose *Standard VM*, and set *JRE home* to the directory where JRE 1.8 is installed. Then, go to `Java > Compiler` and set the *compiler compliance level* to 1.8. Finally, click *Apply and Close*.

Go to `File > New > Java Project` to create a new Java project.

Select the option labelled *Use an execution environment JRE* and select *JavaSE-1.8* as the JRE.

Uncheck the box labelled *Use default location* and set the location of the project to the root directory of this repository. The name of the project should match the name of that directory. 

Click *Next* and then click on the *Libraries* tab. Make sure the *acm.jar* file is included in the build path. If not, add it by clicking *Add JARs...* and selecting it from the *lib* directory.

Click *Finish* to finish creating the Java project.

In the Package Explorer window, expand the `src` directory and expand the `project` package. Then, right click on the `bSim.java` file and click `Run As > Java Applet`.

## License

The `lib/acm.jar` package file is released under the ACM Java Task Force Software Licensing Agreement (see the `lib/LICENSE` file for more details).

The source files in the `src/project` directory are released under the [MIT License](https://opensource.org/licenses/MIT) (see the `src/project/LICENSE` file for more details).

The source files in the `src/assets` directory do not contain a license.
