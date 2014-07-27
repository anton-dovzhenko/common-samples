Feature: VKontakte login

    @Login
        Scenario: Successful login
        Given I go to login page
        When I enter name "correct_name"
        When I enter password "correct_password"
        When I click on Enter button
        Then I successfully logged in
        When I logout

    @Login
        Scenario: Failed login. Incorrect credentials
        Given I go to login page
        When I enter name "noname"
        When I enter password "noname"
        When I click on Enter button
        Then I get login error message