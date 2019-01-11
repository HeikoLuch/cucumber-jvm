@foo
Feature: Basic Arithmetic

  Background: A Calculator
    Given a calculator I just turned on

  Scenario: Addition
  # Try to change one of the values below to provoke a failure
    When I add 4 and 5
    Then the result is 9

  Scenario: Another Addition
  # Try to change one of the values below to provoke a failure
    When I add 4 and 7
    Then the result is 11

  Scenario Outline: Many additions using memory
    Given the memory is cleared
    #And the previous entries:
    And some calculations were performed and added to the memory
      | first | second | operation |
      | 1     | 1      | +         |
      | 2     | 1      | +         |
    When I recall memory
    And I press +
    And I add <a> and <b>
    Then the result is <c>

  Examples: Single digits
    | a | b | c  |
    | 1 | 2 | 8  |
    | 2 | 3 | 10 |

  Examples: Double digits
    | a  | b  | c  |
    | 10 | 20 | 35 |
    | 20 | 30 | 55 |
