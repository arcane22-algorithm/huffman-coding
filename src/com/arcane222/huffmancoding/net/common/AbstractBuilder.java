package com.arcane222.huffmancoding.net.common;

public class AbstractBuilder {

    abstract static class Builder<T> {

        abstract AbstractBuilder build();

        protected abstract  T self();
    }
}
