package com.gkk;

import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    HashMap<String,String> symbolTable;
    Map<String, String> compMap;
    Map<String, String> destMap;
    Map<String, String> jumpMap;
    Integer nextAvailableAddress = 0;
    public static SymbolTable instance;
    SymbolTable(){
        this.initializeSymbolTable();
    }
    public Integer getNextAvailableAddress() {
        return nextAvailableAddress;
    }
    public static SymbolTable getInstance(){
        if(instance == null){
            instance = new SymbolTable();
        }
        return instance;
    }
    public String getDest(String dest) {
        return destMap.getOrDefault(dest, "000");
    }
    public String getComp(String comp) {
        return compMap.getOrDefault(comp, "0000000");
    }
    public String getJump(String jump) {
        return jumpMap.getOrDefault(jump, "000");
    }
    void initializeSymbolTable() {
        symbolTable = new HashMap<>();
        for(int i = 0;i<16;i++){
            symbolTable.put("R"+i,getBinaryCode(i));
            nextAvailableAddress +=1;
        }
        symbolTable.put("SCREEN",getBinaryCode(16384));
        symbolTable.put("KBD",getBinaryCode(24576));
        nextAvailableAddress +=2;
        compMap = Map.ofEntries(
                Map.entry("0",   "0101010"),
                Map.entry("1",   "0111111"),
                Map.entry("-1",  "0111010"),
                Map.entry("D",   "0001100"),
                Map.entry("A",   "0110000"),
                Map.entry("!D",  "0001101"),
                Map.entry("!A",  "0110001"),
                Map.entry("-D",  "0001111"),
                Map.entry("-A",  "0110011"),
                Map.entry("D+1", "0011111"),
                Map.entry("A+1", "0110111"),
                Map.entry("D-1", "0001110"),
                Map.entry("A-1", "0110010"),
                Map.entry("D+A", "0000010"),
                Map.entry("D-A", "0010011"),
                Map.entry("A-D", "0000111"),
                Map.entry("D&A", "0000000"),
                Map.entry("D|A", "0010101"),
                Map.entry("M",   "1110000"),
                Map.entry("!M",  "1110001"),
                Map.entry("-M",  "1110011"),
                Map.entry("M+1", "1110111"),
                Map.entry("M-1", "1110010"),
                Map.entry("D+M", "1000010"),
                Map.entry("D-M", "1010011"),
                Map.entry("M-D", "1000111"),
                Map.entry("D&M", "1000000"),
                Map.entry("D|M", "1010101")
        );


         destMap = Map.of(
                "null", "000",
                "M", "001",
                "D", "010",
                "MD", "011",
                "A", "100",
                "AM", "101",
                "AD", "110",
                "AMD", "111"
        );

         jumpMap = Map.of(
                "null", "000",
                "JGT", "001",
                "JEQ", "010",
                "JGE", "011",
                "JLT", "100",
                "JNE", "101",
                "JLE", "110",
                "JMP", "111"
        );


    }

    String getBinaryCode(Integer decimal){
        return String.format("%16s", Integer.toBinaryString(decimal)).replace(' ', '0');
    }
    void addRow(String key, String value) {
        if (symbolTable.containsKey(key)) {
            return;
        }
        symbolTable.put(key, value);
        nextAvailableAddress += 1;
    }

    void removeRow(String key) {
        symbolTable.remove(key);

    }

    String getRow(String key) {
        if (symbolTable.containsKey(key)) {
            return symbolTable.get(key);
        }
        return null;
    }

}
