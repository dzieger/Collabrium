package com.dzieger.services;

public class AuthServiceTest {


    /** Register User Tests */
    // TODO: Test registerUser -> should pass

    // TODO: Test registerUser_Success -> should pass

    // TODO: Test registerUser_Success_WithRole -> should pass

    // TODO: Test registerUser_NoValidData -> should fail

    // TODO: Test registerUser_UsernameExists -> should fail

    // TODO: Test registerUser_UsernameTooShort -> should fail

    // TODO: Test registerUser_UsernameTooLong -> should fail

    // TODO: Test registerUser_UsernameInvalidCharacters -> should fail

    // TODO: Test registerUser_UsernameNull -> should fail

    // TODO: Test registerUser_UsernameEmpty -> should fail

    // TODO: Test registerUser_EmailExists -> should fail

    // TODO: Test registerUser_InvalidEmailFormat -> should fail

    // TODO: Test registerUser_EmailNull -> should fail

    // TODO: Test registerUser_EmailEmpty -> should fail

    // TODO: Test registerUser_PasswordTooShort -> should fail

    // TODO: Test registerUser_PasswordTooLong -> should fail

    // TODO: Test registerUser_PasswordNull -> should fail

    // TODO: Test registerUser_PasswordEmpty -> should fail

    // TODO: Test registerUser_PasswordNoNumbers -> should fail

    // TODO: Test registerUser_PasswordNoLetters -> should fail

    // TODO: Test registerUser_PasswordNoSpecialCharacters -> should fail

    // TODO: Test registerUser_PasswordNoUppercaseLetters -> should pass

    // TODO: Test registerUser_PasswordNoLowercaseLetters -> should pass

    // TODO: Test registerUser_FirstNameTooShort -> should fail

    // TODO: Test registerUser_FirstNameTooLong -> should fail

    // TODO: Test registerUser_FirstNameNull -> should fail

    // TODO: Test registerUser_FirstNameEmpty -> should fail

    // TODO: Test registerUser_FirstNameInvalidCharacters -> should fail

    // TODO: Test registerUser_LastNameTooShort -> should fail

    // TODO: Test registerUser_LastNameTooLong -> should fail

    // TODO: Test registerUser_LastNameNull -> should fail

    // TODO: Test registerUser_LastNameEmpty -> should fail

    // TODO: Test registerUser_LastNameInvalidCharacters -> should fail



    /** User Login Tests */
    // TODO: Test login -> should pass

    // TODO: Test login_UserNotFound -> should fail

    // TODO: Test login_UsernameNull -> should fail

    // TODO: Test login_UsernameEmpty -> should fail

    // TODO: Test login_PasswordsDontMatch -> should fail

    // TODO: Test login_PasswordNull -> should fail

    // TODO: Test login_PasswordEmpty -> should fail

    /** Refresh Token Tests */

    // TODO: Test refreshToken -> should pass

    // TODO: Test refreshToken_InvalidToken -> should fail

    // TODO: Test refreshToken_ExpiredToken -> should fail

    // TODO: Test refreshToken_InvalidTokenVersion -> should fail

    // TODO: Test refreshToken_NoToken -> should fail

    /** Logout Tests
     *
     * The tests that use invalid tokens should fail meaning they do not affect the tokenVersion of any logged
     * in user. The frontend should handle a failed logout request by simply logging the user out of the frontend.
     * In that scenario, the token would be invalidated on the frontend because it was likely invalid on the backend.
     *
     * Also to note that in that scenario, assuming the JwtUtil could extract claims, the token could be added to a
     * blacklist or some other mechanism to prevent the token from being used again in case it was a valid token.
     *
     * The tests that use valid tokens should pass meaning they do affect the tokenVersion of the actively logged in user.
     *
     * */

    // TODO: Test logout -> should pass

    // TODO: Test logout_InvalidToken -> should fail

    // TODO: Test logout_ExpiredToken -> should fail

    // TODO: Test logout_InvalidTokenVersion -> should fail

    // TODO: Test logout_TokenNull -> should fail

    // TODO: Test logout_TokenEmpty -> should fail

    // TODO: Test logout_TokenVersionIncremented -> should pass

    // TODO: Test logout_TokenVersionNotIncremented -> should fail
}
