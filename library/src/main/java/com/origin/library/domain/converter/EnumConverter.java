package com.origin.library.domain.converter;

import jakarta.persistence.AttributeConverter;

public class EnumConverter<X extends EnumConverter.Valueable<Y>, Y>
    extends BaseConverter implements AttributeConverter<X, Y> {

  Class<X> clazz;

  public interface Valueable<Y> {
    Y value();
  }

  @Override
  public Y convertToDatabaseColumn(X attribute) {
    return attribute.value();
  }

  @Override
  public X convertToEntityAttribute(Y dbData) {
    if (clazz == null) {
      throw new RuntimeException("super.clazz must be set in the constructor");
    }
    if (dbData == null) {
      return null;
    }
    for (X v : clazz.getEnumConstants()) {
      if (v.value().equals(dbData)) {
        return v;
      }
    }
    throw new IllegalArgumentException("Enum " + clazz.getSimpleName()
        + " has an illegal input value: " + dbData);
  }

}