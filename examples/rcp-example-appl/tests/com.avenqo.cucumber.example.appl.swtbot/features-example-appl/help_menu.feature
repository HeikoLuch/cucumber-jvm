Feature: Calling the About-Dialog
  This example shows the functionality of the SimpleApplication Help-Menu.

  Scenario: About Dialog is available
    Given the menu Help is enabled
    When I click then menu Help
    Then the About-dialog is open