package org.activiti.examples.model;

import java.time.LocalDate;

public class StringContainer {

  String date;

  public StringContainer() {}

  public StringContainer(final String date) {
    this.date = date;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "StringContainer{" +
        "date='" + date + '\'' +
        '}';
  }
}
