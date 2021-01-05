package org.activiti.examples.model;

import java.time.LocalDate;

public class LocalDateContainer {

  LocalDate date;

  public LocalDateContainer() {}

  public LocalDateContainer(final LocalDate date) {
    this.date = date;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "LocalDateContainer{" +
        "date='" + date + '\'' +
        '}';
  }
}
