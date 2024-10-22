package de.doering.lib;

import de.doering.Main;

import java.security.InvalidParameterException;
import java.util.*;

public class Tokenizer {

    String symbols = "{}()[]\"|,";
    Map<String, Token> rules = new HashMap<>();
    /*
     *   @TODO
     *              - nicht schön aber funktioniert
     *              - Ich muss sicherstellen das es nicht sich selbst in sich einsetzt und eine schleife erzeugt
     *              - der tokenizer erkennt noch kein ';' als eof
     *              - eventeull sollte der lexer erst alle regeln lexen und dann die ncihttermianl symbole untereinander auflösen
     *
     * */

    /* @TODO
     *              - schluss: ich denke der lexer sollte die nichtterminal gar nicht auflösen das sollte erst der
     *                  parser tun wenn er etwas gegen eine regel parst
    * */

    public Token tokenize(Token token) {
        LexingDTO lexingDTO = new LexingDTO(token.getTokenValue().split(""), 0);  // Initialisierung des LexingDTO
        List<Token> tokens = new ArrayList<>();
        while (lexingDTO.getIndex() < lexingDTO.getSplit().length) {
            Token currentToken;
            switch (lexingDTO.getSymbol()) {
                case "\"":
                    currentToken = lexTERMINAL(lexingDTO);
                    tokens.add(currentToken);
                    break;
                case "(":
                    currentToken = lexGRUPPE(lexingDTO);
                    tokens.add(currentToken);
                    break;
                case "{":
                    currentToken = lexWIEDERHOLUNG(lexingDTO);
                    tokens.add(currentToken);
                    break;
                case "[":
                    currentToken = lexOPTION(lexingDTO);
                    tokens.add(currentToken);
                    break;
                case ",":
                    currentToken = lexANEINANDERREIHUNG(lexingDTO);
                    tokens.add(currentToken);
                    break;
                case "|":
                    currentToken = lexWAHL(lexingDTO);
                    tokens.add(currentToken);
                    break;
                case " ", "=":
                    lexingDTO.increment();
                    break;
                default:
                    currentToken = lexNICHTTERMINAL(lexingDTO);
                    tokens.add(currentToken);
                    break;
            }
        }
        for (Token childToken : tokens) {
            switch (childToken.getTokenType()) {
                case TokenTypes.TERMINAL, TokenTypes.ANEINANDERREIHUNG, TokenTypes.WAHL, TokenTypes.NICHTTERMINAL:
                    break;
                default:
                    tokenize(childToken);
            }
        }
        token.setChildTokens(tokens);
        rules.put(token.getChildTokens().getFirst().getTokenValue(), token);
        return token;
    }

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
        while (lexingDTO.getIndex() < lexingDTO.getSplit().length && !Objects.equals(lexingDTO.getSymbol(), " ") && !symbols.contains(lexingDTO.getSymbol())) {
            currentToken.appendTokenValue(lexingDTO.getSymbolAndIncrement());
        }
        return currentToken;
    }

    private Token NICHTTERMINAL_lookup(Token token) {
        if (!token.tokenType.equals(TokenTypes.NICHTTERMINAL)) {
            throw new InvalidParameterException("Der Uebergene Token ist nicht vom Typ NICHTTERMINAL");
        }
        return rules.get(token.getTokenValue());
    }

    public Map<String, Token> parseFile(String filename) {
        FileReader fileReader = new FileReader();
        var lines = fileReader.readFileFromResources(filename);
        for (String line : lines) {
            tokenize(new Token(TokenTypes.WURZEL, line));
        }
        return rules;
    }

}

