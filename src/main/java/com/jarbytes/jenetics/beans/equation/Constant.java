package com.jarbytes.jenetics.beans.equation;

import java.math.BigDecimal;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class Constant extends IdentifiableEquationPart
{
    private final BigDecimal value;

    public Constant(final BigDecimal value)
    {
        this.value = requireNonNull(value);
    }

    @Override
    public BigDecimal eval(final Map<String, BigDecimal> values)
    {
        return value;
    }

    @Override
    public boolean isTerminal()
    {
        return true;
    }

    @Override
    public String toString()
    {
        return value.toString();
    }
}