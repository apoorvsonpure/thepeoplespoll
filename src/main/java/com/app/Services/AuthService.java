package com.app.Services;

import com.app.Entity.SignUpModel;
import com.app.Entity.User;
import com.app.Repositories.IUserRepository;
import com.app.Utils.CommonUtilities;
import com.app.Utils.JwtTokenUtil;
import jakarta.mail.MessagingException;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService
{
    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void signUpService(SignUpModel signUpModel) throws Exception
    {
        User userByEmail = userRepository.findByEmail(signUpModel.getEmail());
        if (userByEmail != null)
            throw new Exception(
                    "email already in use. Please use differet email to proceed.");

        // user is now created
        final String token = jwtTokenUtil.generateSignUpToken(signUpModel.getEmail());
        sendVerifyEmail(signUpModel.getFirstName(), signUpModel.getLastName(),
                signUpModel.getEmail(), token);
        final User user = createUser(signUpModel);

    }

    private void sendVerifyEmail(@NotBlank String firstName, @NotBlank String lastName, @NotBlank String email,
            String token)
    {
        try
        {
            emailService.sendEmail(firstName, lastName, email,token);
        }
        catch (MessagingException e)
        {
            throw new RuntimeException(e);
        }
    }

    private User createUser(SignUpModel signUpModel)
    {
        String jsonSignupModel = CommonUtilities.convertObjectToJson(signUpModel);
        User user = CommonUtilities.convertJsonToObject(jsonSignupModel, User.class);
        user.setPassword( passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public String verifyEmail(String signUpToken)
    {
        final String subjectFromToken = jwtTokenUtil.getSubjectFromToken(signUpToken);
        final User dbUser = userRepository.findByEmail(subjectFromToken);
        if(dbUser == null)
            return "invalid token... no user found for the given token";
        final int i = jwtTokenUtil.validateSignUpToken(signUpToken);
        if(i == -1)
            return "invalid scope for the provided token...";
        else if(i == 0)
            return "token expired...we will send the mail again.. please verify within 24 hrs.";
        else
        {
            dbUser.setAccountVerified(true);
            userRepository.save(dbUser);
            return "email verified successfully....You can proceed to login.";
        }
    }

    public User getUserByMail(String email)
    {
        return userRepository.findByEmail(email);
    }
}
