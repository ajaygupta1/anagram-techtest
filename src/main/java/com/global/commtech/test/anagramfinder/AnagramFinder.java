package com.global.commtech.test.anagramfinder;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class AnagramFinder {

    public void find(String filePath) {
        final File file = new File(filePath);
        Assert.isTrue(file.exists(), filePath + " Does not exist");

        List<String> anagramGroupList = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream.forEach(wordOnFocus -> {
                boolean  isGrouped = isWordAlreadyGrouped(anagramGroupList, wordOnFocus);
                if(!isGrouped){
                    List<String> anagrams = findAnagrams(wordOnFocus, filePath);
                    String records = convertToDelimitedString(anagrams);
                    anagramGroupList.add(records);
                }
            });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        printOutput(anagramGroupList);
    }

    private boolean isWordAlreadyGrouped(List<String> anagramGroupList, String word) {
        return anagramGroupList.stream()
                .anyMatch(anagramGroup -> anagramGroup.contains(word));
    }

    private List<String> findAnagrams(String inputWord, String filePath) {
        final List<String> anagrams = new ArrayList<>();
        //Read the file again
        try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
            stream
                .filter(wordToCompare -> wordToCompare.length() == inputWord.length())
                .forEach(wordToCompare -> {
                    boolean allMatch = compareWordContentByEachCharacter(inputWord, wordToCompare);
                    if (allMatch) {
                        anagrams.add(wordToCompare);
                    }
                });
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return anagrams.stream().distinct().collect(Collectors.toList());
    }

    private boolean compareWordContentByEachCharacter(String first, String second) {
        List<Integer> firstWordChars = first.chars().boxed().toList();
        return second.chars()
                .allMatch(firstWordChars::contains);
    }

    private String convertToDelimitedString(List<String> list) {
        return list.stream().distinct().collect(Collectors.joining(","));
    }

    private void printOutput(List<String> list) {
        list.forEach(System.out::println);
    }

}
