package com.practice.command.stuff;

public interface Describable {
    public default String getDescription() {
        return "This command have no description yet.";
    };
}