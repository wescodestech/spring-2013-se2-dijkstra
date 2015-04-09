# Overview #
This project is designed to meet the requirements set forth by Dr. Rex Page at the University of Oklahoma for Software Engineering II in the school of Computer Science.  The outcome is to develop team skills and a plan for action in developing larger scale applications coupled with strict program requirements.  The ACL2 language is one of such requirements, while the languages that are implemented, the development process and the team load delegation are all discretionary.  The program must be meaningful as well has have a specific purpose.  Lines of Code requirements range from 2500 to 3000 lines (approximately 500 LOC per team member).

## Team Members ##
  * Wesley Howell
  * Adam Ghodratnama
  * Matthew Crist

# The Program #
The program that team Dijkstra decided to write was an ACL2 Email Client/Server System.  We chose this since there are many things that we must overcome in order to integrate ACL2 into a networking system.  The program is fully dependent upon ACL2 to do the legwork on the local system, but the networking is delegated to the Operating Systems.  The OS of choice for this application is any POSIX based system (typically UNIX).  It has been found to run completely on Mac OS and Ubuntu specifically given a certain set of requirements:

  * [ACL2 v6.1](http://www.cs.utexas.edu/~moore/acl2/)
  * [Java SDK 1.7](http://www.oracle.com/technetwork/java/javase/downloads/index.html)
  * [Dr Racket with Dracula](http://racket-lang.org/) (used for theorem testing not script invocation)

The application itself is divided into two parts:

  * [The Client](https://code.google.com/p/spring-2013-se2-dijkstra/wiki/Client)
  * [The Server](https://code.google.com/p/spring-2013-se2-dijkstra/wiki/Server)

_You may view the components of each subsystem by viewing the wiki or following the links above_