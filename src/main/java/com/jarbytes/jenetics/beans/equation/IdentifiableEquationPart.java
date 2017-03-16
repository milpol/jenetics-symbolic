package com.jarbytes.jenetics.beans.equation;

import com.jarbytes.jenetics.beans.EquationPart;

import java.util.Objects;
import java.util.UUID;

abstract class IdentifiableEquationPart implements EquationPart
{
    private final UUID id;

    public IdentifiableEquationPart()
    {
        this.id = UUID.randomUUID();
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdentifiableEquationPart that = (IdentifiableEquationPart) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id);
    }
}
