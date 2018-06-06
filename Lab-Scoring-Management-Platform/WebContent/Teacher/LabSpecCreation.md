# How To Create Lab Specifications

The following document contains information on how to create your own lab specifications for grading your students!

## Submitting Lab Specifications
Lab specifications are in the format of a text file with the file extension ".txt" that is submitted from the Teacher's homepage. Please keep in mind that only text files ending in ".txt" will work.

## Formatting Lab Specifications

Lab specifications follow a simple, easy to use format.

#### Filenames
The filename (not including the file extension ".txt") signifies the lab name. This is the lab name that students will see when selecting a lab to submit for scoring.

#### Commands and Arguments
The contents of a lab specification follow a "COMMAND arg" format. Commands signify what is being checked and arguments are pieces of information that the command requires to perform its command. Commands must be in all CAPS and arguments must directly follow the command with a single space in between.

The following is a list of allowed commands followed by the arguments (if any) required. Arguments are signified by <...>. Information is provided about each command, signified by a hyphen (-) and information about the command. This is not part of the command:
```
TEST <test name> - Single test of the lab code. A submission can fail or pass a test.
DONE - End of a test. This does not restart the code, however.
IN <input> - Input to be sent to console. Must be within TEST and DONE commands.
OUT <output> - Output to be validated. Checks if <output> argument is contained in program's output to console. Must be within TEST and DONE commands.
```

#### Other
Comments are signified by "#" symbols. All lines beginning with "#" are ignored.

## Examples
The following is an example 1ab specification.

###### lab12.6.txt
```
# Test 1. See if their code can do the bare minimum.
TEST 1
IN 1
IN 2
IN 5
OUT 3478
DONE

# Test 2. See if it can handle illegitimate data.
TEST 2
IN 136
IN 11
OUT 43
IN 12
OUT 16
DONE

# ---:^)  ---:^)  ---:^)  ---:^)
```
