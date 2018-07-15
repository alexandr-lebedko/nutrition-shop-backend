package net.lebedko.nutritionshop.service.transformer;

public interface Transformer<F, T> {
    T toDto(F from);

    F fromDto(T from);
}
