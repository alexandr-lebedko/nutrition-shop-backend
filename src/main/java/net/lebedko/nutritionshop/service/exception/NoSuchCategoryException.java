package net.lebedko.nutritionshop.service.exception;

import java.util.NoSuchElementException;

public class NoSuchCategoryException extends NoSuchElementException {
    private static final String MESSAGE = "Category with id %s not exists";

    public NoSuchCategoryException(Long id) {
        super(String.format(MESSAGE, id));
    }
}
