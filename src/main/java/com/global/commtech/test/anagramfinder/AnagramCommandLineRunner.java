package com.global.commtech.test.anagramfinder;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class AnagramCommandLineRunner implements CommandLineRunner {

    private final AnagramFinder anagramFinder;

    @Override
    public void run(final String... args) throws Exception {
        Assert.isTrue(args.length == 1, "Please ensure that the input file is provided");
        anagramFinder.find(args[0]);
    }
}
