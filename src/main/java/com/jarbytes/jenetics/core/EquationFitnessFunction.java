package com.jarbytes.jenetics.core;

import com.jarbytes.jenetics.beans.EquationGene;
import org.jenetics.Genotype;

public interface EquationFitnessFunction
{
    Integer eval(final Genotype<EquationGene> gene);
}