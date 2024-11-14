package com.practice;

import com.practice.database.DataBaseManager;
import com.practice.element.Worker;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.concurrent.CopyOnWriteArrayList;

public class CollectionManager {
    private static final LocalDateTime creationDate = LocalDateTime.now();
    private static CopyOnWriteArrayList<Worker> workerCollection = null;
    @Getter
    private static DataBaseManager dataBaseManager;

    public CollectionManager() {
    }

    public static CopyOnWriteArrayList<Worker> getCollection() {
        return workerCollection;
    }

    public static String infoAboutCollection() {
        return "Type - " + workerCollection.getClass() + "\nCreation date - "  + "\nAmount of elements - " ;
    }

    public static void setWorkerCollection(CopyOnWriteArrayList<Worker> workerCollection) {
        CollectionManager.workerCollection = workerCollection;
    }

    public static void setDataBaseManager(DataBaseManager dataBaseManager) {
        CollectionManager.dataBaseManager = dataBaseManager;
    }

    public static void delete(Worker worker) {
    }
}
