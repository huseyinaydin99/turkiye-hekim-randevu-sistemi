package tr.com.huseyinaydin.configs.handlers;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import tr.com.huseyinaydin.security.AppUserDetails;
import tr.com.huseyinaydin.session.UserSession;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final UserSession userSession;
    private final ModelMapper modelMapper;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        var userDetails = (AppUserDetails) authentication.getPrincipal();
        userSession.setEmail(userDetails.getUsername());
        userSession.setFullName(userDetails.getPassword());
        userSession.setRole(userSession.getRole());

        response.sendRedirect("/assignation/search");
    }
}