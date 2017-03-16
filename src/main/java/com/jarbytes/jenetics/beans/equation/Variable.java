package com.jarbytes.jenetics.beans.equation;

import java.math.BigDecimal;
import java.util.Map;

import static java.util.Objects.requireNonNull;

public class Variable extends IdentifiableEquationPart
{
    private final String name;

    public Variable(final String name)
    {
        this.name = requireNonNull(name);
    }

    @Override
    public String toString()
    {
        return name;
    }

    @Override
    public BigDecimal eval(final Map<String, BigDecimal> values)
    {
        return values.getOrDefault(name, BigDecimal.ZERO);
    }

    @Override
    public boolean isTerminal()
    {
        return true;
    }
}