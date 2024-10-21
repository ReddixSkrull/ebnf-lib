package de.doering;

import de.doering.lib.Token;
import de.doering.lib.TokenTypes;
import de.doering.lib.Tokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        Tokenizer tokenizer = new Tokenizer();
        var rule1 = "ZifferAusserNull \"1\" | \"2\" | \"3\" | \"4\" | \"5\" | \"6\" | \"7\" | \"8\" | \"9\"";
        var rule2 = "Ziffer = \"0\" | ZifferAusserNull";
        var dummy = "(\"a\",\"b\"|\"d\")|\"c\"";
        var roottoken = new Token(TokenTypes.WURZEL, rule1);
        var tokenlist = tokenizer.tokenize(roottoken);
        LOGGER.info(tokenlist.toString(1));
        var roottoken2 = new Token(TokenTypes.WURZEL, rule2);
        var tokenlist2 = tokenizer.tokenize(roottoken2);
        LOGGER.info(tokenlist2.toString(1));
        var hexRule = "HexZahl (\"0\", \"x\"){\"a\" | \"b\" | \"c\" | \"d\" | \"e\" | \"f\" | \"0\" | \"1\" | \"2\" | \"3\" | \"4\" | \"5\" | \"6\" | \"7\" | \"8\" | \"9\"}+";
        var hexroot = new Token(TokenTypes.WURZEL, hexRule);
        var hextokens= tokenizer.tokenize(hexroot);
        LOGGER.info(hextokens.toString(1));

    }
}