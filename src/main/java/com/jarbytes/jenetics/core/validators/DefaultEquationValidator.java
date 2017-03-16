package com.jarbytes.jenetics.core.validators;

import com.jarbytes.jenetics.beans.EquationPart;
import com.jarbytes.jenetics.beans.equation.BiEquationPart;
import com.jarbytes.jenetics.beans.equation.Variable;
import com.jarbytes.jenetics.core.EquationValidator;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

public class DefaultEquationValidator implements EquationValidator
{
    @Override
    public boolean isValid(final EquationPart equationPart)
    {
        requireNonNull(equationPart);
        Set<String> variables = getVariablesNames(equationPart);
        try {
            equationPart.eval(variables.stream().collect(Collectors.toMap(
                    Function.identity(),
                    e -> BigDecimal.ZERO
            )));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Set<String> getVariablesNames(EquationPart equationPart)
    {
        return getAllVariables(equationPart).stream()
                .map(Variable::toString)
                .collect(Collectors.toSet());
    }

    private List<Variable> getAllVariables(final EquationPart equationPart)
    {
        final ArrayList<Variable> variables = new ArrayList<>();
        if (equationPart.isTerminal()) {
            if (equationPart instanceof Variable) {
                variables.add((Variable) equationPart);
            }
        } else {
            variables.addAll(getAllVariables(((BiEquationPart) equationPart).getLeft()));
            variables.addAll(getAllVariables(((BiEquationPart) equationPart).getRight()));
        }
        return variables;
    }
}
