package com.arcane222.huffmancoding.net.deprecated.common;

public class AbstractBuilder {

    abstract static class Builder<T> {

        abstract AbstractBuilder build();

        protected abstract  T self();
    }
}
