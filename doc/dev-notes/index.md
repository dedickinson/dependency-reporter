# Dev Notes

This directory contains a variety of notes made whilst investigating various aspects of the project. 

## Design thoughts

### Tech

* Main language: Groovy
* Testing: Spock
* Build tool: Gradle
* CLI Library: [JCommander](http://jcommander.org/)

### Approach

* Establish each "command" (e.g. search) as a [service](https://docs.oracle.com/javase/tutorial/ext/basics/spi.html)
    * Break the codebase into Gradle sub-projects 

### Similar or aligned systems

See also the various files in this directory.

* Maven plugins:
    * [RAT: Release Audit Tool](http://creadur.apache.org/rat/index.html)

* Gradle plugins:
    * 

## Other information

* A blog post from some early prodding [The sum of our component parts ](http://blog.duncan.dickinson.name/2015/10/the-sum-of-our-component-parts.html)
* (U.S.A.) [H.R.5793 - Cyber Supply Chain Management and Transparency Act of 2014](https://www.congress.gov/bill/113th-congress/house-bill/5793)
* [National Vulnerability Database](https://web.nvd.nist.gov/view/vuln/search)
