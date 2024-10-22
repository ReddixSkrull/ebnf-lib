package de.doering;

import de.doering.lib.Parser;
import de.doering.lib.Token;
import de.doering.lib.Tokenizer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final Logger LOGGER = LogManager.getLogger(Main.class);
    public static void main(String[] args) {
        Tokenizer tokenizerSimple = new Tokenizer();
        Parser parser = new Parser();
        // @TODO es scheint den bug zu geben das die regel mit den zahlen weggbricht
        //      er erkennt dass es 'Digit' in simple2.txt gibt härt aber auf das zu parsen
        // er erkennt digit im ersten durchlauf korrekt und parst es auch korrekt, im nöchsten schritt zerstlört er
        // dann den eintrag für digit wieder und überschreibt ihn mit irgendeinem müll
        // einen check einbauen ob der split string der am anfang in tokenize eingegeben wird ein breits bekanntes nicht
        // terminal symbol ist

        /* @TODO
        *       Das token muss verbessert werden damit es wenn man einen string parsed auch noch informationen darüber enthält zu welcher regel der welches token gehört!!!
        * */
        var regelsatzSimpel = tokenizerSimple.parseFile("simple2.txt");
        regelsatzSimpel.values().forEach(r -> LOGGER.info("\n" + r.toString(0)));
        var ttokens = parser.extractTerminalTokens(regelsatzSimpel);
        ttokens.forEach(t -> LOGGER.info(t.toString(0)));
        var output = parser.parseInputString(ttokens, "1 + 1");
        output.forEach(t -> LOGGER.info(t.toString(1)));
    }
}