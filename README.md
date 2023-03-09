# Anagram Finder
A simple command line utility for finding anagrams in a specified file

## Software required to run this
* Java 17

## Building and Running the tests
```
./gradlew clean build
```

## Running the program
```
./gradlew bootRun --args="example2.txt" 
```
where example2.txt is the text file that we want to search for anagrams


## Big O Analysis

The program will process each row by by comparing it against the (n-1) rows in each pass.
The total no of comparison therefore will be: 

n + (n-1) + (n-2) +.... + 1  = [n x (n-1)] /2


## Data structure
I have used simple character comparison to find an anagram 

## What would you do given more time

I would tried to use a better way a reading the file, instead of reading it twice for each pass. I would have used a inmemory cache and load the entire file instead of reading in multiple times.
