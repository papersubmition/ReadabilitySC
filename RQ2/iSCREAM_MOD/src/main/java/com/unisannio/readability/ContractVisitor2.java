package com.unisannio.readability;

import com.unisannio.utility.LOCCompute;
import hu.sed.parser.antlr4.grammar.solidity.SolidityBaseVisitor;
import hu.sed.parser.antlr4.grammar.solidity.SolidityParser;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.antlr.v4.runtime.misc.NotNull;

/**
 *
 * @author Michele Fredella
 */
@SuppressWarnings("deprecation")
public class ContractVisitor2 extends SolidityBaseVisitor<Void> {

    private String sourceText;
    private ArrayList<String> totalCode;
    private SolidityParser.ContractDefinitionContext currentContract;
    private HashMap<String, Total> functionMetric = new HashMap<>();
    private HashMap<String, Total> totalContract = new HashMap<>();

    public ContractVisitor2(String sourceText, ArrayList<String> totalCode) {
        this.sourceText = sourceText;
        this.totalCode = totalCode;
    }
    private int slocTot, llocTot, clocTot, blankTot, cicliTot, numIfTot, numKeyordTot, numParTot, numOpaTot, numOpCTot, numAssTot, numVirTot, numPerTot, numSpaceTot, identifierTot, numNumberTot, maxLineIndentation, maxLenIdentifier, maxLenLenght, maxChar;
    private double mediaLenIdenTot, mediaLineLen, mediaLineIndentation;
    private String contractNameT, typeT, maxOccT;
    private ArrayList<String> occT;
    private ArrayList<Double> mediaLenIdenList;
    private ArrayList<Double> mediaLineIndentationList;
    private ArrayList<Double> mediaLineLenList;
    private ArrayList<Integer> maxLenIdentifierList;
    private ArrayList<Integer> maxLineIndentationList;
    private ArrayList<Integer> maxLenLenghtList;
    private ArrayList<Integer> maxCharList;
    private ArrayList<Integer> maxOccTList;

    @Override
    public Void visitContractDefinition(@NotNull SolidityParser.ContractDefinitionContext ctx) {
        mediaLenIdenList = new ArrayList<Double>();
        mediaLineIndentationList = new ArrayList<Double>();
        mediaLineLenList = new ArrayList<Double>();
        maxLenIdentifierList = new ArrayList<Integer>();
        maxLineIndentationList = new ArrayList<Integer>();
        maxLenLenghtList = new ArrayList<Integer>();
        maxCharList = new ArrayList<Integer>();
        maxOccTList = new ArrayList<Integer>();
        slocTot = 0;
        llocTot = 0;
        clocTot = 0;
        cicliTot = 0;
        numIfTot = 0;
        numKeyordTot = 0;
        numParTot = 0;
        numOpaTot = 0;
        numOpCTot = 0;
        numAssTot = 0;
        numVirTot = 0;
        numPerTot = 0;
        numSpaceTot = 0;
        identifierTot = 0;
        mediaLenIdenTot = 0;
        numNumberTot = 0;
        mediaLineIndentation = 0;
        mediaLineLen = 0;
        maxLineIndentation = 0;
        maxLenIdentifier = 0;
        maxLenLenght = 0;
        maxChar = 0;

        String contractName, type;
        currentContract = ctx;
        slocTot = (ctx.getStop().getLine() - ctx.getStart().getLine()) + 1;
        LOCCompute locCalc = new LOCCompute("\n", ctx.getStart().getLine() - 1, ctx.getStop().getLine() - 1);
        locCalc.calculateLOCMetrics(totalCode);
        llocTot = locCalc.getLLOC();
        clocTot = locCalc.getCLOC();
        blankTot = locCalc.Blank(totalCode);
        type = ctx.getChild(0).getText();

        contractName = ctx.getChild(1).getText();
        contractNameT = contractName;
        typeT = type;
        super.visitContractDefinition(ctx);
        return null;
    }

    @Override
    public Void visitModifierDefinition(@NotNull SolidityParser.ModifierDefinitionContext ctx) {
        int sloc, lloc, cloc, blank;
        String name, type, contract;
        sloc = (ctx.getStop().getLine() - ctx.getStart().getLine()) + 1;
        LOCCompute locCalc = new LOCCompute("\n", ctx.getStart().getLine() - 1, ctx.getStop().getLine() - 1);
        locCalc.calculateLOCMetrics(totalCode);
        lloc = locCalc.getLLOC();
        cloc = locCalc.getCLOC();
        blank = locCalc.Blank(totalCode);
        contract = currentContract.children.get(1).getText();

        type = ctx.getChild(0).getText();
        name = ctx.getChild(1).getText();

        locCalc.calculateLOCMetrics(totalCode);
        locCalc.calculateNumPar(totalCode);
        locCalc.calculateNumOP(totalCode);
        locCalc.calculateNumVir(totalCode);
        locCalc.calculateNumPeriod(totalCode);
        locCalc.calculateNumSpace(totalCode);
        locCalc.calculateMaxChar(totalCode);
        locCalc.calculateIndentation(totalCode);
        int cicli = new numCicliVisitor().visitModifierDefinition(ctx);
        cicliTot = cicliTot + cicli;
        int numif = new numIfVisitor().visitModifierDefinition(ctx);
        numIfTot = numIfTot + numif;
        int numKeyord = new keywordVisitor().visitModifierDefinition(ctx);
        numKeyordTot = numKeyordTot + numKeyord;
        int numPar = locCalc.getNumPar();
        numParTot = numParTot + numPar;
        int numOpA = locCalc.getNumOpA();
        numOpaTot = numOpaTot + numOpA;
        int numOpC = locCalc.getNumOpC();
        numOpCTot = numOpCTot + numOpC;
        int numAss = locCalc.getNumAss();
        numAssTot = numAssTot + numAss;
        int numVir = locCalc.getNumVir();
        numVirTot = numVirTot + numVir;
        int numPer = locCalc.getNumPer();
        numPerTot = numPerTot + numPer;
        int numSpace = locCalc.getNumSpace();
        numSpaceTot = numSpaceTot + numSpace;
        identifierVisitor ide = new identifierVisitor();
        int identifier = ide.visitModifierDefinition(ctx);
        identifierTot = identifierTot + identifier;
        ArrayList<Integer> identifierList = ide.getLenghtIdentifier();
        if (identifierList.size() > 0) {
            double sommaLenIden = 0;
            for (Integer a : identifierList) {
                sommaLenIden = sommaLenIden + a;
            }
            double mediaLenIden = sommaLenIden / identifierList.size();
            mediaLenIdenTot = mediaLenIden;
            mediaLenIdenList.add(mediaLenIdenTot);
            Collections.sort(identifierList);
            Collections.reverse(identifierList);
            maxLenIdentifier = identifierList.get(0);
            maxLenIdentifierList.add(maxLenIdentifier);
        } else {
            mediaLenIdenTot = 0;
            mediaLenIdenList.add(mediaLenIdenTot);
            maxLenIdentifier = 0;
            maxLenIdentifierList.add(maxLenIdentifier);
        }
        ArrayList<String> textIdent = ide.getIdentifierText();
        if (textIdent.size() > 0) {
            ArrayList<String> a = countFrequencies(textIdent);
            maxOccT = a.get(a.size() - 1).split(":")[1];
            maxOccTList.add(Integer.parseInt(maxOccT));
            occT = a;
        } else {
            maxOccT = "0";
            maxOccTList.add(Integer.parseInt(maxOccT));
            occT = new ArrayList<String>();
        }
        ArrayList<Integer> lineLen = locCalc.getLineLenght();
        double mediaLine;
        if (lineLen.size() > 0) {
            double sommaLineLen = 0;
            for (Integer a : lineLen) {
                sommaLineLen = sommaLineLen + a;
            }
            mediaLine = sommaLineLen / lineLen.size();
            mediaLineLen = mediaLine;

            mediaLineLenList.add(mediaLineLen);

            Collections.sort(lineLen);
            Collections.reverse(lineLen);
            maxLenLenght = lineLen.get(0);
            maxLenLenghtList.add(maxLenLenght);
        } else {
            mediaLineLen = 0;
            mediaLineLenList.add(mediaLineLen);
            maxLenLenght = 0;
            maxLenLenghtList.add(maxLenLenght);
        }
        ArrayList<Integer> indentation = locCalc.getIndentation();
        double mediaIndentationLine;
        if (indentation.size() > 0) {
            double sommaLineLen = 0;
            for (Integer a : indentation) {
                sommaLineLen = sommaLineLen + a;
            }
            mediaIndentationLine = sommaLineLen / indentation.size();
            mediaLineIndentation = mediaIndentationLine;
            mediaLineIndentationList.add(mediaLineIndentation);
            Collections.sort(indentation);
            Collections.reverse(indentation);
            maxLineIndentation = indentation.get(0);
            maxLineIndentationList.add(maxLineIndentation);
        } else {
            mediaLineIndentation = 0;
            mediaLineIndentationList.add(mediaLineIndentation);
            maxLineIndentation = 0;
            maxLineIndentationList.add(maxLineIndentation);
        }
        maxChar = locCalc.getMaxChar();
        maxCharList.add(maxChar);
        int numNumber = new numberVisitor().visitModifierDefinition(ctx);
        numNumberTot = numNumberTot + numNumber;
        Total t = new Total(contract, type, name, sloc, lloc, cloc, blank, cicli, numif, numKeyord, numPar, numOpA, numOpC, numAss, numVir, numPer, numSpace, identifier, mediaLenIdenTot, mediaLineLen, maxOccT, occT, numNumber, mediaLineIndentation, maxLineIndentation, maxLenIdentifier, maxLenLenght, maxChar);
        functionMetric.put(name, t);
        //                Total(contract, type, name,       sloc,    lloc,    cloc,    blank,     cicli,    numif,   numKeyord,    numPar,    numOpA,    numOpC,    numAss,     numVir,    numPer,    numSpace,    identifier,   mediaLenIdenTot, mediaLineLen, maxOccT, occT, numNumber,    mediaLineIndentation, maxLineIndentation, maxLenIdentifier, maxLenLenght, maxChar);
        Total t1 = new Total(contractNameT, typeT, contractNameT, slocTot, llocTot, clocTot, blankTot, cicliTot, numIfTot, numKeyordTot, numParTot, numOpaTot, numOpCTot, numAssTot, numVirTot, numPerTot, numSpaceTot, identifierTot, mediaLenIdenTot, mediaLineLen, maxOccT, occT, numNumberTot, mediaLineIndentation, maxLineIndentation, maxLenIdentifier, maxLenLenght, maxChar,
                mediaLenIdenList, mediaLineIndentationList, mediaLineLenList, maxLenIdentifierList, maxLineIndentationList, maxLenLenghtList, maxCharList, maxOccTList);
        totalContract.put(contractNameT, t1);
        super.visitModifierDefinition(ctx);
        return null;
    }

    @Override
    public Void visitFunctionDefinition(@NotNull SolidityParser.FunctionDefinitionContext ctx) {
        int sloc, lloc, cloc, blank;
        String name, type, contract;
        sloc = (ctx.getStop().getLine() - ctx.getStart().getLine()) + 1;
        LOCCompute locCalc = new LOCCompute("\n", ctx.getStart().getLine() - 1, ctx.getStop().getLine() - 1);
        locCalc.calculateLOCMetrics(totalCode);
        lloc = locCalc.getLLOC();
        cloc = locCalc.getCLOC();
        blank = locCalc.Blank(totalCode);

        contract = currentContract.children.get(1).getText();

        type = ctx.getChild(0).getText();
        name = ctx.getChild(1).getText();

        locCalc.calculateLOCMetrics(totalCode);
        locCalc.calculateNumPar(totalCode);
        locCalc.calculateNumOP(totalCode);
        locCalc.calculateNumVir(totalCode);
        locCalc.calculateNumPeriod(totalCode);
        locCalc.calculateNumSpace(totalCode);
        locCalc.calculateMaxChar(totalCode);
        locCalc.calculateIndentation(totalCode);
        int cicli = new numCicliVisitor().visitFunctionDefinition(ctx);
        cicliTot = cicliTot + cicli;
        int numif = new numIfVisitor().visitFunctionDefinition(ctx);
        numIfTot = numIfTot + numif;
        int numKeyord = new keywordVisitor().visitFunctionDefinition(ctx);
        numKeyordTot = numKeyordTot + numKeyord;
        int numPar = locCalc.getNumPar();
        numParTot = numParTot + numPar;
        int numOpA = locCalc.getNumOpA();
        numOpaTot = numOpaTot + numOpA;
        int numOpC = locCalc.getNumOpC();
        numOpCTot = numOpCTot + numOpC;
        int numAss = locCalc.getNumAss();
        numAssTot = numAssTot + numAss;
        int numVir = locCalc.getNumVir();
        numVirTot = numVirTot + numVir;
        int numPer = locCalc.getNumPer();
        numPerTot = numPerTot + numPer;
        int numSpace = locCalc.getNumSpace();
        numSpaceTot = numSpaceTot + numSpace;
        identifierVisitor ide = new identifierVisitor();
        int identifier = ide.visitFunctionDefinition(ctx);
        identifierTot = identifierTot + identifier;
        ArrayList<Integer> identifierList = ide.getLenghtIdentifier();
        if (identifierList.size() > 0) {
            double sommaLenIden = 0;
            for (Integer a : identifierList) {
                sommaLenIden = sommaLenIden + a;
            }
            double mediaLenIden = sommaLenIden / identifierList.size();
            mediaLenIdenTot = mediaLenIden;
            mediaLenIdenList.add(mediaLenIdenTot);
            Collections.sort(identifierList);
            Collections.reverse(identifierList);
            maxLenIdentifier = identifierList.get(0);
            maxLenIdentifierList.add(maxLenIdentifier);
        } else {
            mediaLenIdenTot = 0;
            mediaLenIdenList.add(mediaLenIdenTot);
            maxLenIdentifier = 0;
            maxLenIdentifierList.add(maxLenIdentifier);
        }
        ArrayList<String> textIdent = ide.getIdentifierText();
        if (textIdent.size() > 0) {

            ArrayList<String> a = countFrequencies(textIdent);
            maxOccT = a.get(a.size() - 1).split(":")[1];
            maxOccTList.add(Integer.parseInt(maxOccT));
            occT = a;
        } else {
            maxOccT = "0";
            occT = new ArrayList<String>();
            maxOccTList.add(Integer.parseInt(maxOccT));
        }
        ArrayList<Integer> lineLen = locCalc.getLineLenght();
        double mediaLine;
        if (lineLen.size() > 0) {
            double sommaLineLen = 0;
            for (Integer a : lineLen) {
                sommaLineLen = sommaLineLen + a;
            }
            mediaLine = sommaLineLen / lineLen.size();
            mediaLineLen = mediaLine;

            mediaLineLenList.add(mediaLineLen);

            Collections.sort(lineLen);
            Collections.reverse(lineLen);
            maxLenLenght = lineLen.get(0);
            maxLenLenghtList.add(maxLenLenght);
        } else {
            mediaLineLen = 0;
            mediaLineLenList.add(mediaLineLen);
            maxLenLenght = 0;
            maxLenLenghtList.add(maxLenLenght);
        }
        maxChar = locCalc.getMaxChar();
        maxCharList.add(maxChar);

        ArrayList<Integer> indentation = locCalc.getIndentation();
        double mediaIndentationLine;
        if (indentation.size() > 0) {
            double sommaLineLen = 0;
            for (Integer a : indentation) {
                sommaLineLen = sommaLineLen + a;
            }
            mediaIndentationLine = sommaLineLen / indentation.size();
            mediaLineIndentation = mediaIndentationLine;
            mediaLineIndentationList.add(mediaLineIndentation);
            Collections.sort(indentation);
            Collections.reverse(indentation);
            maxLineIndentation = indentation.get(0);
            maxLineIndentationList.add(maxLineIndentation);
        } else {
            mediaLineIndentation = 0;
            mediaLineIndentationList.add(mediaLineIndentation);

            maxLineIndentation = 0;
            maxLineIndentationList.add(maxLineIndentation);
        }
        int numNumber = new numberVisitor().visitFunctionDefinition(ctx);
        numNumberTot = numNumberTot + numNumber;
        Total t = new Total(contract, type, name, sloc, lloc, cloc, blank, cicli, numif, numKeyord, numPar, numOpA, numOpC, numAss, numVir, numPer, numSpace, identifier, mediaLenIdenTot, mediaLineLen, maxOccT, occT, numNumber, mediaLineIndentation, maxLineIndentation, maxLenIdentifier, maxLenLenght, maxChar);
        functionMetric.put(name, t);
        // Total t1 = new Total(contractNameT, typeT, contractNameT, slocTot, llocTot, clocTot, blankTot, cicliTot, numIfTot, numKeyordTot, numParTot, numOpaTot, numOpCTot, numAssTot, numVirTot, numPerTot, numSpaceTot, identifierTot, mediaLenIdenTot, mediaLineLen, maxOccT, occT, numNumberTot, mediaLineIndentation, maxLineIndentation, maxLenIdentifier, maxLenLenght, maxChar);
        Total t1 = new Total(contractNameT, typeT, contractNameT, slocTot, llocTot, clocTot, blankTot, cicliTot, numIfTot, numKeyordTot, numParTot, numOpaTot, numOpCTot, numAssTot, numVirTot, numPerTot, numSpaceTot, identifierTot, mediaLenIdenTot, mediaLineLen, maxOccT, occT, numNumberTot, mediaLineIndentation, maxLineIndentation, maxLenIdentifier, maxLenLenght, maxChar,
                mediaLenIdenList, mediaLineIndentationList, mediaLineLenList, maxLenIdentifierList, maxLineIndentationList, maxLenLenghtList, maxCharList, maxOccTList);
        totalContract.put(contractNameT, t1);
        super.visitFunctionDefinition(ctx);

        return null;
    }

    public ArrayList<Integer> getMaxOccTList() {
        return maxOccTList;
    }

    public void setMaxOccTList(ArrayList<Integer> maxOccTList) {
        this.maxOccTList = maxOccTList;
    }

    public ArrayList<Integer> getMaxCharList() {
        return maxCharList;
    }

    public void setMaxCharList(ArrayList<Integer> maxCharList) {
        this.maxCharList = maxCharList;
    }

    public ArrayList<Integer> getMaxLenLenghtList() {
        return maxLenLenghtList;
    }

    public void setMaxLenLenghtList(ArrayList<Integer> maxLenLenghtList) {
        this.maxLenLenghtList = maxLenLenghtList;
    }

    public ArrayList<Integer> getMaxLineIndentationList() {
        return maxLineIndentationList;
    }

    public void setMaxLineIndentationList(ArrayList<Integer> maxLineIndentationList) {
        this.maxLineIndentationList = maxLineIndentationList;
    }

    public ArrayList<Integer> getMaxLenIdentifierList() {
        return maxLenIdentifierList;
    }

    public void setMaxLenIdentifierList(ArrayList<Integer> maxLenIdentifierList) {
        this.maxLenIdentifierList = maxLenIdentifierList;
    }

    public ArrayList<Double> getMediaLineLenList() {
        return mediaLineLenList;
    }

    public void setMediaLineLenList(ArrayList<Double> mediaLineLenList) {
        this.mediaLineLenList = mediaLineLenList;
    }

    public ArrayList<Double> getMediaLineIndentationList() {
        return mediaLineIndentationList;
    }

    public void setMediaLineIndentationList(ArrayList<Double> mediaLineIndentationList) {
        this.mediaLineIndentationList = mediaLineIndentationList;
    }

    public ArrayList<Double> getMediaLenIdenList() {
        return mediaLenIdenList;
    }

    public void setMediaLenIdenList(ArrayList<Double> mediaLenIdenList) {
        this.mediaLenIdenList = mediaLenIdenList;
    }

    public HashMap<String, Total> getTotalContract() {
        return totalContract;
    }

    public HashMap<String, Total> getFunctionMetric() {
        return functionMetric;
    }

    public ArrayList<String> countFrequencies(ArrayList<String> list) {

        TreeMap<String, Integer> tmap = new TreeMap<String, Integer>();
        for (String t : list) {
            Integer c = tmap.get(t);
            tmap.put(t, (c == null) ? 1 : c + 1);
        }

        ArrayList<String> a = sortByValue(tmap);
        return a;

    }

    public <K, V extends Comparable<? super V>> ArrayList<String> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());

        ArrayList<String> app = new ArrayList<String>();
        for (Map.Entry<K, V> entry : list) {
            app.add(entry.getKey() + ":" + entry.getValue());
        }

        return app;
    }
}
