package edu.eci.arsw.blueprints.persistence.impl;

import java.util.Objects;

public class Tuple<T1, T2> {

    public T1 first;
    public T2 second;

    public Tuple(T1 first, T2 second) {
        this.first = first;
        this.second = second;
    }

    public T1 getElem1() {
        return first;  // Retorna el primer elemento
    }

    public T2 getElem2() {
        return second; // Retorna el segundo elemento
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 17 * hash + Objects.hashCode(this.first);
        hash = 17 * hash + Objects.hashCode(this.second);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Tuple<?, ?> other = (Tuple<?, ?>) obj;
        if (!Objects.equals(this.first, other.first)) {
            return false;
        }
        if (!Objects.equals(this.second, other.second)) {
            return false;
        }
        return true;
    }
}
