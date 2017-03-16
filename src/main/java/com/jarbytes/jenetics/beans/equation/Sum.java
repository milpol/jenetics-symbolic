package com.jarbytes.jenetics.beans.equation;

import com.jarbytes.jenetics.beans.EquationPart;

import java.math.BigDecimal;
import java.util.Map;

public class Sum extends BiEquationPart
{
    public Sum(final EquationPart left,
               final EquationPart right)
    {
        super(left, right, "+");
    }

    @Override
    public BigDecimal eval(final Map<String, BigDecimal> values)
    {
        return left.eval(values).add(right.eval(values));
    }
}