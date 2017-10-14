package com.github.liyasharipova;

import java.util.ArrayList;
import java.util.List;

/**
 * Список геномов разделенных по группам размером k
 */
public class GenomsList {

    private int k;

    /**
     * Все строки геномов, каждая из которых разделена на группы размером k
     */
    private List<List<String>> allGenoms;

    public GenomsList() {
        this.allGenoms = new ArrayList<>();
    }

    public List<List<String>> getAllGenoms() {
        return allGenoms;
    }
}