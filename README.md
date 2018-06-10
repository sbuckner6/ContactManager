# ContactManager

A Java application written in the Play framework as part of a 24-hour coding challenge.

### Prerequisites

Ant

Play Framework 1.2.4

### Installing Play Framework

A step by step series of examples that tell you how to get a development env running

Download Play Framework 1.2.4 and unzip and cd into the new folder.

```
cd play-1.2.4
```

Now simply run:

```
ant
```

If you receive the following:

```
compile:
    [mkdir] Created dir: /home/simon/play-1.2.4/framework/classes
    [javac] /home/simon/play-1.2.4/framework/build.xml:67: warning: 'includeantruntime' was not set, defaulting to build.sysclasspath=last; set to false for repeatable builds
    [javac] Compiling 271 source files to /home/simon/play-1.2.4/framework/classes
    [javac] warning: [options] bootstrap class path not set in conjunction with -source 1.5
    [javac] warning: [options] source value 1.5 is obsolete and will be removed in a future release
    [javac] warning: [options] target value 1.5 is obsolete and will be removed in a future release
    [javac] warning: [options] To suppress warnings about obsolete options, use -Xlint:-options.
    [javac] /home/simon/play-1.2.4/framework/src/play/templates/GroovyTemplate.java:388: warning: '_' used as an identifier
    [javac]         public Class _(String className) throws Exception {
    [javac]                      ^
    [javac]   (use of '_' as an identifier might not be supported in releases after Java SE 8)
    [javac] /home/simon/play-1.2.4/framework/src/play/db/DBPlugin.java:287: error: ProxyDriver is not abstract and does not override abstract method getParentLogger() in Driver
    [javac]     public static class ProxyDriver implements Driver {
    [javac]                   ^
    [javac] Note: Some input files use or override a deprecated API.
    [javac] Note: Recompile with -Xlint:deprecation for details.
    [javac] Note: Some input files use unchecked or unsafe operations.
    [javac] Note: Recompile with -Xlint:unchecked for details.
    [javac] 1 error
    [javac] 5 warnings

BUILD FAILED
```

...run the following:

```
cd framework/src/play/db
```

...and open DBPlugin.java.

Add the following to the ProxyDriver class (and make sure java.sql.SQLFeatureNotSupportedException is imported as well):

```
@Override 
public java.util.logging.Logger getParentLogger() 
                throws SQLFeatureNotSupportedException { 
        // TODO Auto-generated method stub 
        return null; 
} 

```

Now try ant again. If it is successful, you can now navigate to the folder where you extracted ContactManager and run:

```
play run
```

or

```
play test
```