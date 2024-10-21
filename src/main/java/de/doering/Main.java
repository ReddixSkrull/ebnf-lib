package de.doering;

import de.doering.lib.Token;
import de.doering.lib.Tokenizer;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    public static void test(int i) {
        i+=1;
    }

    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();
        // wie man bei ausführung sehen kann gibt es den bug das das wahl symbol nach der shcliessenden runden klammern nciht mit geparsed wird
        System.out.println("ITERATION 1:");
        List<Token> tokens = tokenizer.tokenize("(\"a\",\"b\"|\"d\")|\"c\"");
        tokens.forEach(System.out::println);
        System.out.println("ITERATION 2:");
        LOGGER.info("was geht");
        tokens.forEach(token -> LOGGER.info(tokenizer.tokenize((token.getTokenValue()))));

    }
}