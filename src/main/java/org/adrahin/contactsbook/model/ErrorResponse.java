package org.adrahin.contactsbook.model;

// финальный класс, который включет в себя: @get, constructor, toString, hashCode
public record ErrorResponse(int statusCode, String errorMessage) {

}
