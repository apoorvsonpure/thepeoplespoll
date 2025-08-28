package com.app.Controllers;

import com.app.Entity.LoginModel;
import com.app.Entity.LoginResponse;
import com.app.Entity.SignUpModel;
import com.app.Entity.User;
import com.app.Services.AuthService;
import com.app.Utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController
{
    @Autowired
    private AuthService authService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;


    @PostMapping(value ="/api/internal/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<String> signUp(@RequestBody  SignUpModel signUpModel) {

        try
        {
            authService.signUpService(signUpModel);
            return  new ResponseEntity<>("Sign Up successful!!! Please check your email to verify your user account.", HttpStatus.OK);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>("Some error occured. = "+ e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/api/internal/verify_email", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> verifyEmail(@RequestParam(value = "token", required = true) String signUpToken)
    {
        final String message = authService.verifyEmail(signUpToken);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    @PostMapping(value = "/api/login" , consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LoginResponse> userLogin(@RequestBody LoginModel loginModel)
    {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getPassword()));

        if(authentication.isAuthenticated())
        {
//            final User dbUser = (User) authService.getUserByMail(
//                    authentication.getName());
            final User dbUser = (User) authentication.getPrincipal();
            final String token = jwtTokenUtil.generateLoginToken(dbUser);
            LoginResponse loginResponse = new LoginResponse();
            loginResponse.setToken(token);
            loginResponse.setUserId(dbUser.getId());
            loginResponse.setUsername(dbUser.getUsername());
            return new ResponseEntity<>(loginResponse, HttpStatus.OK);
        }
        throw new AuthenticationServiceException("Invalid email or password provided.. please enter correct credentials");
    }

}
