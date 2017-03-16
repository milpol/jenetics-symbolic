package com.jarbytes.jenetics.beans;

import java.math.BigDecimal;
import java.util.Map;

public interface EquationPart
{
    BigDecimal eval(Map<String, BigDecimal> values);

    default BigDecimal eval()
    {
        return eval(null);
    }

    boolean isTerminal();
}
