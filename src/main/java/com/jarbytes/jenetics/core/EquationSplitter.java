package com.jarbytes.jenetics.core;

import com.jarbytes.jenetics.beans.EquationPart;

import java.util.List;

public interface EquationSplitter
{
    List<EquationPart> split(EquationPart equationPart);
}
