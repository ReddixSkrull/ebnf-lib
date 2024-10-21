package de.doering.lib;

import com.sun.source.tree.BreakTree;

import java.util.ArrayList;
import java.util.List;

public class Token {

    TokenTypes tokenType;
    String tokenValue = "";
    List<Token> childTokens = new ArrayList<>();

    public Token() {
    }

    public Token(TokenTypes tokenType, String tokenValue) {
        this.tokenType = tokenType;
        this.tokenValue = tokenValue;
    }

    public TokenTypes getTokenType() {
        return tokenType;
    }

    public void setTokenType(TokenTypes tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public void appendTokenValue(String tokenValue) {
        this.tokenValue = this.tokenValue.concat(tokenValue);
    }


    public List<Token> getChildTokens() {
        return childTokens;
    }

    public void setChildTokens(List<Token> childTokens) {
        this.childTokens = childTokens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Token token = (Token) o;

        if (getTokenType() != token.getTokenType()) return false;
        return getTokenValue().equals(token.getTokenValue());
    }

    @Override
    public int hashCode() {
        int result = getTokenType().hashCode();
        result = 31 * result + getTokenValue().hashCode();
        return result;
    }

    public String toString(int depths) {
        depths++;
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < depths; i++) {
            stringBuilder.append("\t");
        }
        stringBuilder.append(STR."tokenType=\{tokenType}, ");
        stringBuilder.append(STR."tokenValue='\{tokenValue}' ");
        if (childTokens.isEmpty()) {
            return stringBuilder.toString();
        }
        for (Token child : childTokens) {
            if (child == null) {
                continue;
            }
            stringBuilder.append(STR."\n\t\{child.toString(depths)}");
        }
        return stringBuilder.toString();
    }
}
