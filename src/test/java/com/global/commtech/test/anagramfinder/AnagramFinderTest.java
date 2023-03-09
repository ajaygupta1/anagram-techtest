package com.global.commtech.test.anagramfinder;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(OutputCaptureExtension.class)
public class AnagramFinderTest {

    private static AnagramFinder anagramFinder;

    @BeforeAll
    public static void setup(){
        anagramFinder = new AnagramFinder();
    }

    @Test
    void shouldThrowExceptionWhenInputFileDoesNotExist() {
        final var exception = assertThrows(Exception.class, () -> anagramFinder.find("notExists"));
        assertThat(exception.getMessage()).isEqualTo("notExists Does not exist");
    }
    @Test
    void emptyResultForEmptyInputFile(final CapturedOutput capturedOutput) throws Exception {
        anagramFinder.find("src/test/resources/example-empty.txt");
        assertThat(capturedOutput.getOut()).isEmpty();
    }

    @Test
    void shouldFindAnagrams(final CapturedOutput capturedOutput) throws Exception {
        anagramFinder.find("src/test/resources/example1.txt");
        assertThat(capturedOutput.getOut()).contains("abc,bac,cba");
        assertThat(capturedOutput.getOut()).contains("fun,unf");
        assertThat(capturedOutput.getOut()).contains("hello");
    }

    @Test
    void singleEntryForDuplicateItems(final CapturedOutput capturedOutput) throws Exception {
        anagramFinder.find("src/test/resources/example-duplicate-rows.txt");
        assertThat(capturedOutput.getOut()).doesNotContain("fun,fun,fun");
        assertThat(capturedOutput.getOut()).contains("fun");
    }

    @Test
    void oneWordPerLineWhenNoAnagramsFound(final CapturedOutput capturedOutput) throws Exception {
        anagramFinder.find("src/test/resources/example-no-anagrams.txt");
        assertThat(capturedOutput.getOut()).contains("abc");
        assertThat(capturedOutput.getOut()).contains("fun");
        assertThat(capturedOutput.getOut()).contains("hello");
    }
}