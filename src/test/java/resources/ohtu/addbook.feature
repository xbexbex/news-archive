Feature: A new book can be created
  Scenario: book creation is successful
    Given page is opened
    When  name "test" and tag "abc" are entered
    Then  a new book with name "test" is added
