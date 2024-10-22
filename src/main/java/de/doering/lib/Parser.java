package de.doering.lib;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class Parser {

    /* @TODO
     *       - die einzelnen nichtterminal/regeln einer ebnf sollten den token entsprechen zu denen man einen input parsen kann
     * */

    /*
     * Ich habe eine grammatik, diese grammatik wird in regeln geparst.
     * Jede dieser regeln sollte einem token entsprechen das der parser auf
     * einen eingabe text anwenden kann der die grammatik erfüllen soll.
     *
     * Erst stelle ich die grammatik her. dann tokenize ich die grammatik, dann tokenize ich mit den tokens die aus der
     * grammatik entstanden sind einen text
     * das ein input ein token ist sollte ich durch den rest der regel auflösen können
     * */

    public List<String> extractTokens(Map<String, Token> rules) {
        return rules.keySet().stream().toList();
    }

    // rekursiv
    public List<Token> extractTerminalTokens(Map<String, Token> rules) {
        List<Token> tokenRules = rules.values().stream().toList();
        List<Token> terminalTokens = new ArrayList<>();
        return extratTerminalTokens(tokenRules);
    }

    public List<Token> extratTerminalTokens(List<Token> rules) {
        List<Token> terminalTokens = new ArrayList<>();
        for (Token t : rules) {
            if (t.tokenType.equals(TokenTypes.TERMINAL)) {
                terminalTokens.add(t);
            }
            if (!t.getChildTokens().isEmpty()) {
                terminalTokens.addAll(extratTerminalTokens(t.getChildTokens()));
            }
        }
        return terminalTokens;
    }

    public List<Token> parseInputString(List<Token> terminalSymbole, String input) {
        return Arrays.stream(
                input.split(""))
                .filter(s -> !s.isBlank())
                .map(s -> terminalSymbole.stream()
                        .filter(token -> token.getTokenValue().equals(s))
                        .findFirst()
                        .orElse(new Token(TokenTypes.FEHLER, STR."Dem Wert \{s} konnte kein Token zugeordnet werden.")))
                .toList();
    }

    // eventuell sollte ich den input text erstmal in alle terminal symbole aus der grammatik parsen
    public List<String> parseTextAgainstRules(Map<String, Token> rules, List<String> input) {

        return null;
    }


}
