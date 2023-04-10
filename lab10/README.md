# Task

In completion of next tasks for output result make new directory and file using File class.

* Read text of Java-program and if word length larger than two characters then do uppercase all characters from this word.
* Also need in additional make own Exception class. Provide exception handling, which out of memory, file not exists by entered path, the absence or incorrectness of the required entry in file, invalid number value (out of boundary) and others.

# My decision

As I understand, in any cases need decompile class files from java program. We have two cases:
 1. program running using package manager such as gradle, maven or ant;
 2. program running by .jar file.

Then we need a class `Loader` that automatically loads classes from running program. In this point we need library "Decompiler API" from `procyon` for decompile java class files.
 Gradle package:
 ```groovy
 implementation 'org.bitbucket.mstrobel:procyon-compilertools:0.6.0'
 ```

## Files

Output files must be placed in directory `/output` and in this directory must be two subdirectories: `/decompiled` and `/reformatted`. As you see, in `/decompiled` directory will be place decompiled classes with path as sources packages (e.g. net.test.mypackage.MyClass -> `/output/decomiled/net/test/mypackage/MyClass.java`). In second directory `/reformatted` will be place classes which reformats by rule from Task.

## Classes

* RunningProgramClassLoader - loads classes from running program.
* ClassDecompiler – decompile classes and write to specific directory.
* Formatter – formats files by specific rules by lambda expression (functional interface).
* Output enum – have a two types: DECOMPILED and REFORMATTED. And we can get from they paths.

## Exception classes

* FileDeletionException (Exception) - upon attempt deleting a file (if it's exists!) and result are false.
* FileCreationException (Exception) - upon attempt creating a file.
* FormatException (Exception) - upon formatting appears exception.
* FormatRuntimeException (RuntimeException) - upon formatting appears uncaught exception.
* ClassDecompileException (RuntimeException) - upon decompiling class gets some exceptions.
* ClassLoaderException (Exception) - upon loading classes from class paths.

## Current completion

✅ Completed
