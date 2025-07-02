package com.gkk;

import java.util.Arrays;
import java.util.stream.Collectors;

public class CodeGenerator {
    private static SymbolTable symbolTable;
    CodeGenerator() {
        symbolTable = SymbolTable.getInstance();
    }
    public String getCode(String[] parts,boolean isAInstruction) {
        if(isAInstruction) {
            return getAInstructionCode(parts);
        } else {
            return getCInstructionCode(parts);
        }
    }

    public String getAInstructionCode(String[] parts) {
        String address = parts[1];
        if (address.matches("\\d+")) {
            return "0" + symbolTable.getBinaryCode(Integer.parseInt(address));
        } else {
            String binaryAddress = symbolTable.getRow(address);
            if (binaryAddress == null) {
                // If the address is not in the symbol table, we need to add it
                binaryAddress = symbolTable.getBinaryCode(symbolTable.getNextAvailableAddress());
                symbolTable.addRow(address, String.valueOf(symbolTable.getNextAvailableAddress()));
            }
            System.out.println("Code for" + parts[1] + " is " + binaryAddress);
            return "0" + binaryAddress;
        }
    }

    public String getCInstructionCode(String[] parts) {
        String dest = symbolTable.getDest(parts[0]);
        System.out.println("Code for dest: " + parts[0] + " is " + dest);
        String comp = symbolTable.getComp(parts[1]);
        System.out.println("Code for comp: " + parts[1] + " is " + comp);
        if(parts.length < 3) {
            return "111" + comp + dest + "000";
        }
        String jump = symbolTable.getJump(parts[2]);
        System.out.println("Code for jump: " + parts[2] + " is " + jump);
        return "111" + comp + dest + jump;
    }
}
