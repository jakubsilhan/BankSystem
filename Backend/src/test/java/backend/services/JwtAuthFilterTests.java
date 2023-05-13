
package backend.services;

import jakarta.servlet.ServletException;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockFilterChain;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

public class JwtAuthFilterTests {
    
    @Mock
    private JwtService jwtService;
    
    @InjectMocks
    private JwtAuthFilter authFilter;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    
    @Test
    public void testDoFilterInternal_ValidToken() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        String jwt = "validToken";
        request.addHeader("Authorization", "Bearer " + jwt);
        when(jwtService.isTokenValid(jwt)).thenReturn(true);

        List<SimpleGrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        Authentication authentication = new UsernamePasswordAuthenticationToken(null, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        authFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService).isTokenValid(jwt);
        verifyNoMoreInteractions(jwtService);
    }
    
    
    @Test
    public void testDoFilterInternal_InvalidToken() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        String jwt = "invalidToken";
        request.addHeader("Authorization", "Bearer " + jwt);
        when(jwtService.isTokenValid(jwt)).thenReturn(false);

        authFilter.doFilterInternal(request, response, filterChain);

        verify(jwtService).isTokenValid(jwt);
        verifyNoMoreInteractions(jwtService);
    }
    
    @Test
    public void testDoFilterInternal_NoToken() throws ServletException, IOException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        MockFilterChain filterChain = new MockFilterChain();

        authFilter.doFilterInternal(request, response, filterChain);

        verifyNoInteractions(jwtService);
    }
}
