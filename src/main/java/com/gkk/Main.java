package com.gkk;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static Parser parser = new Parser();
    private static CodeGenerator codeGenerator = new CodeGenerator();
    public static void main(String[] args) throws IOException {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!" + Arrays.toString(args));
        List<String> codeLines = new ArrayList<>(List.of());
        Path inputPath = Paths.get(args[0]);
        Path outputPath = Paths.get(args[1]);
        Files.readAllLines(inputPath)
                .forEach(line -> {
                    String cleanedLine = cleanLine(line);
                    String[] parts = parser.parse(cleanedLine);
                    System.out.println(Arrays.toString(parts));
                    String code = codeGenerator.getCode(parts, parser.isAinstruction(cleanedLine));
                    codeLines.add(code);
                    System.out.println("Processing line: " + line);
                    // Here you would typically parse the line and generate code
                    // For now, we just print it out
                });
        System.out.println(codeLines.toString());
    }

    public static String cleanLine(String line) {
        Pattern patt = Pattern.compile("\\$\\{(.*?)\\}");
        Matcher matcher = patt.matcher(line);
        return matcher.replaceAll("");
    }
}