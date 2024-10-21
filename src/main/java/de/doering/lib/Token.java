package de.doering.lib;

public class Token {

    TokenTypes tokenType;
    String tokenValue = "";

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

    @Override
    public String toString() {
        return "Token{" +
                "tokenType=" + tokenType +
                ", tokenValue='" + tokenValue + '\'' +
                '}';
    }
}
