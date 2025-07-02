package com.gkk;

public class Parser {
    // This class is responsible for parsing the input and generating the AST (Abstract Syntax Tree)
    // It will handle the syntax of the language and create a structured representation of the code

    public String[] parse(String line) {
        String[] parts;
        if(isAinstruction(line)){
             parts = getAinstructionParts(line);
        }else{
             parts = getCinstructionParts(line);
        }
        System.out.println("Parsing input: " + line);
        return parts;
    }

    public Boolean isAinstruction(String line) {
        if(line.charAt(0) == '@') return true;
        return false;
    }

    public String[] getCinstructionParts(String line) {
        String dest = line.split("=")[0];
        String comp = line.split("=")[1];
        if(line.split(";").length < 2) {
            return new String[]{dest, comp, "null"};
        }
        String jump = line.split(";")[2];
        return new String[]{dest, comp, jump};
    }
    
    public String[] getAinstructionParts(String line) {
        return new String[]{line.split("@")[0], line.split("@")[1]};
    }
}
