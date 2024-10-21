package de.doering.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Tokenizer {

    String symbols = "{}()[]\"|,";

    public List<Token> tokenize(String rule) {
        List<Token> tokens = new ArrayList<>();
        int index = 0;
        String[] split = rule.split("");
        Token currentToken;
        while (index < split.length) {
            switch (split[index]) {
                case "\"":
                    currentToken = new Token();
                    currentToken.setTokenType(TokenTypes.TERMINAL);
                    index++;
                    while (!Objects.equals(split[index], "\"")) {
                        currentToken.appendTokenValue(split[index]);
                        index++;
                    }
                    index++;
                    tokens.add(currentToken);
                    break;
                case "(":
                    currentToken = new Token();
                    currentToken.setTokenType(TokenTypes.GRUPPE);
                    index++;
                    while (!Objects.equals(split[index], ")")) {
                        currentToken.appendTokenValue(split[index]);
                        index++;
                    }
                    index++;
                    tokens.add(currentToken);
                    break;
                case "{":
                    currentToken = new Token();
                    currentToken.setTokenType(TokenTypes.WIEDERHOLUNG_NULL_ZU_UNENDLICH);
                    index++;
                    while (!Objects.equals(split[index], "}")) {
                        currentToken.appendTokenValue(split[index]);
                        index++;
                    }
                    if (index + 1 < split.length && split[index + 1] == "+") {
                        index++;
                        currentToken.setTokenType(TokenTypes.WIEDERHOLUNG_EINS_ZU_UNENDLICH);
                    }
                    index++;
                    tokens.add(currentToken);
                    break;
                case "[":
                    currentToken = new Token();
                    currentToken.setTokenType(TokenTypes.OPTION);
                    index++;
                    while (!Objects.equals(split[index], "]")) {
                        currentToken.appendTokenValue(split[index]);
                        index++;
                    }
                    index++;
                    tokens.add(currentToken);
                    break;
                case ",":
                    currentToken = new Token();
                    currentToken.setTokenType(TokenTypes.ANEINANDERREIHUNG);
                    currentToken.setTokenValue(",");
                    index++;
                    tokens.add(currentToken);
                    break;
                case "|":
                    currentToken = new Token();
                    currentToken.setTokenType(TokenTypes.WAHL);
                    currentToken.setTokenValue("|");
                    index++;
                    tokens.add(currentToken);
                    break;
                default:
                    /* @TODO bei einem rekursiven aufruf würden alle bereits geparsten Terminal symbole die als Token
                            reingegebn werden zu nichtterminal symbolen werden da sie im Value nicht mehr über die
                            gänsefüßchen verfügen. Das bedeutet ich brauche eine zweite implementierung die rein auf
                            tokenlisten basiert*/
                    currentToken = new Token();
                    currentToken.setTokenType(TokenTypes.NICHTTERMINAL);
                    index++;
                    while (index < split.length && split[index].equals(" ") && !symbols.contains(split[index])) {
                        currentToken.appendTokenValue(split[index]);
                        index++;
                    }
                    tokens.add(currentToken);
                    break;
            }
        }
        return tokens;
    }


//    private Token lexWIEDERHOLUNG_NULL_ZU_UNENDLICH(Token token,  int index) {
//
//    }
//
//    private Token lexWIEDERHOLUNG_EINS_ZU_UNENDLICH(Token token, int index) {
//
//    }
//
//    private Token lexOPTION(Token token, int index) {
//
//    }
//
//    private Token lexWAHL(Token token, int index) {
//
//    }

//    private Token lexTERMINAL(String[] split, int index) {
//    }

//    private Token lexNICHTTERMINAL(Token token, int index) {
//
//    }
//
//    private Token lexANEINANDERREIHUNG(Token token, int index) {
//
//    }
//
//    private Token lexGRUPPE(Token token, int index) {
//
//    }

    private Token lexTERMINAL(LexingDTO lexingDTO) {
        Token currentToken = new Token();
        currentToken.setTokenType(TokenTypes.TERMINAL);
        lexingDTO.increment();
        while (!Objects.equals(lexingDTO.getSymbol(), "\"")) {
            currentToken.appendTokenValue(lexingDTO.getSymbolAndIncrement());
        }
        lexingDTO.increment();
        return currentToken;
    }

    private Token lexGRUPPE(LexingDTO lexingDTO) {
        Token currentToken = new Token();
        currentToken.setTokenType(TokenTypes.GRUPPE);
        lexingDTO.increment(); // "(" überspringen
        while (!Objects.equals(lexingDTO.getSymbol(), ")")) {
            currentToken.appendTokenValue(lexingDTO.getSymbolAndIncrement());
        }
        lexingDTO.increment(); // ")" überspringen
        return currentToken;
    }

    private Token lexWIEDERHOLUNG(LexingDTO lexingDTO) {
        Token currentToken = new Token();
        currentToken.setTokenType(TokenTypes.WIEDERHOLUNG_NULL_ZU_UNENDLICH);
        lexingDTO.increment(); // "{" überspringen
        while (!Objects.equals(lexingDTO.getSymbol(), "}")) { // Hier sollte es "}" sein, nicht ")"
            currentToken.appendTokenValue(lexingDTO.getSymbolAndIncrement());
        }
        // Überprüfen, ob das nächste Symbol ein "+" ist
        if (lexingDTO.getIndex() + 1 < lexingDTO.getSplit().length && Objects.equals(lexingDTO.getSymbolAtIndex(lexingDTO.getIndex() + 1), "+")) {
            lexingDTO.increment();
            currentToken.setTokenType(TokenTypes.WIEDERHOLUNG_EINS_ZU_UNENDLICH);
        }
        lexingDTO.increment(); // "}" überspringen
        return currentToken;
    }

    private Token lexOPTION(LexingDTO lexingDTO) {
        Token currentToken = new Token();
        currentToken.setTokenType(TokenTypes.OPTION);
        lexingDTO.increment(); // "[" überspringen
        while (!Objects.equals(lexingDTO.getSymbol(), "]")) { // Hier sollte es "]" sein, nicht ")"
            currentToken.appendTokenValue(lexingDTO.getSymbolAndIncrement());
        }
        lexingDTO.increment(); // "]" überspringen
        return currentToken;
    }

    private Token lexANEINANDERREIHUNG(LexingDTO lexingDTO) {
        Token currentToken = new Token();
        currentToken.setTokenType(TokenTypes.ANEINANDERREIHUNG);
        currentToken.setTokenValue(",");
        lexingDTO.increment(); // "," überspringen
        return currentToken;
    }

    private Token lexWAHL(LexingDTO lexingDTO) {
        Token currentToken = new Token();
        currentToken.setTokenType(TokenTypes.WAHL);
        currentToken.setTokenValue("|");
        lexingDTO.increment(); // "|" überspringen
        return currentToken;
    }

    private Token lexNICHTTERMINAL(LexingDTO lexingDTO) {
        Token currentToken = new Token();
        currentToken.setTokenType(TokenTypes.NICHTTERMINAL);
        while (lexingDTO.getIndex() < lexingDTO.getSplit().length &&
                !Objects.equals(lexingDTO.getSymbol(), " ") &&
                !symbols.contains(lexingDTO.getSymbol())) {
            currentToken.appendTokenValue(lexingDTO.getSymbolAndIncrement());
        }
        return currentToken;
    }

}
