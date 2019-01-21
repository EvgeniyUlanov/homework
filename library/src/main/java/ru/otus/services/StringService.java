package ru.otus.services;

import java.util.List;

public interface StringService {

    List<String> allAuthorsToStringList();

    List<String> allGenresToString();

    List<String> allBooksToString();

    List<String> bookByGenreToString(String genre);

    List<String> bookByAuthorToString(String authorName);

    String bookByNameToString(String bookName);
}
