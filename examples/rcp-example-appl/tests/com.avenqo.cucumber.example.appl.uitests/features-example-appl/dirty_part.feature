Feature: Dirty feature
  Changes in the view makes it dirty.

  Scenario: The view is not dirty, the textfield is empty 
    Given the textfield is empty    
    And the view is not dirty
    And the MenuItem "File" "Save" enabled state is "false"
    When I enter the text "some text"
    Then the view is dirty
    And the MenuItem "File" "Save" enabled state is "true"