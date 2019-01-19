package ru.otus.services;

import java.util.List;

public interface StringService {

    List<String> AllAuthorsToStringList();

    List<String> AllGenresToString();

    List<String> AllBooksToString();

    List<String> BookByGenreToString(String genre);

    List<String> BookByAuthorToString(String authorName);

    String BookByNameToString(String bookName);
}
