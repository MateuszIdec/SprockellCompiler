# Sprockell Compiler

This software allows to compile custom programming language into machine code for the virtual CPU called Sprockell.

## Prerequisites

Make sure you have installed:

- Maven 3.8 or later.
- Java 11 or later.


## Compiling

In a terminal, run:

```
mvn compile
```

## Running

Specify the name of the file to compile in `input.txt` and the result file in `output.hs`  

In a terminal, run:
```
mvn exec:java "-Dexec.args = input.txt output.hs"
```

Alternatively, to run it with the default filenames as specified above, run:
```
mvn exec:java
```
