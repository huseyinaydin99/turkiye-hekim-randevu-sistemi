package tr.com.huseyinaydin.security.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import tr.com.huseyinaydin.exceptions.InvalidLoginException;

import java.io.IOException;

public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public CustomAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
        setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/clogin", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        UsernamePasswordAuthenticationToken authRequest =
                UsernamePasswordAuthenticationToken.unauthenticated(email, password);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        //super.successfulAuthentication(request, response, chain, authResult);
        System.out.println("successfulAuthentication metodu!");
        // Güvenlik bağlamına kullanıcıyı yerleştir (gerekirse)
        SecurityContextHolder.getContext().setAuthentication(authResult);

        // Örnek: JWT token oluşturduysan header'a koy
        // response.setHeader("Authorization", "Bearer " + jwtToken);

        // JSON response dönebilirsin
        // response.setContentType("application/json");
        // response.getWriter().write("{\"message\": \"Login başarılı\"}");

        // Veya doğrudan yönlendirme
        response.sendRedirect("/appointments/search");

        // ❌ super.successfulAuthentication(...) YÖNLENDİRME YAPACAKSAK YOK YOKSA HATA(İSTİSNA)
        chain.doFilter(request, response); // bu önemli!
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        //super.unsuccessfulAuthentication(request, response, failed);
        System.out.println("unsuccessfulAuthentication metodu!");
        response.sendRedirect("/errorPage");
        //request.getRequestDispatcher("/errorPage").forward(request, response); //Bu, client’a redirect göndermez. Sunucu içi yönlendirmedir.
    }
}