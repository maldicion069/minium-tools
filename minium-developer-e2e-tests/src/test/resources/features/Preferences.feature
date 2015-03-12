@runit
Feature: Editor Preferences

  Background: 
    Given I'm at Minium Developer
    And I click on toolbar "Preferences > Preferences"

  Scenario Outline: Test theme
    When I fill:
      | Theme   |
      | <theme> |
    And I click on button "Save changes"
    Then I should see a notification with text "Preferences updated with success" and with type "success"
    And the editor should have theme "<theme>"

    Examples: 
      | theme  |
      | Chrome |
