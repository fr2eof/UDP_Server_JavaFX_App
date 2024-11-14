package com.practice.command;

import com.practice.command.properties.Descriptiable;
import lombok.Getter;

public abstract class AbstractCommand implements Descriptiable {
    @Getter
    private final String name;
    private final String description;

    public AbstractCommand(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String toString() {
        return this.name;
    }

    public int hashCode() {
        return this.name.hashCode();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj == null) {
            return false;
        } else if (this.getClass() != obj.getClass()) {
            return false;
        } else {
            AbstractCommand other = (AbstractCommand) obj;
            return this.name.equals(other.name);
        }
    }

    public abstract boolean execute(Object[] args);

    public String getDescription() {
        return description;
    }

}
