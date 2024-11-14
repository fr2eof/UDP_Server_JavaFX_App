package com.practice.command;

import lombok.Getter;

@Getter
public abstract class AbstractCommand {
    private final String name;


    public AbstractCommand(String name) {
        this.name = name;
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

}
