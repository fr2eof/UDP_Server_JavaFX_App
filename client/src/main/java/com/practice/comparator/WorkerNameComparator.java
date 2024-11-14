package com.practice.comparator;

import com.practice.element.Worker;

import java.util.Comparator;

public class WorkerNameComparator implements Comparator<Worker> {
    public WorkerNameComparator() {
    }

    public int compare(Worker s1, Worker s2) {
        return s1.getName().compareTo(s2.getName());
    }
}
