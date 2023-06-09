
package backend.services;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtService {
    
    @Value("${token.secret.key}")
    private String SECRET;

    public JwtService(){};
    
    public void setSecret(String key){
        this.SECRET = key;
    }
    
    public String generateToken(String userName, long accountNumber){
        Map<String,Object> claims=new HashMap<>();
        return createToken(claims,userName, accountNumber);
    }

    private String createToken(Map<String, Object> claims, String userName, long accountNumber) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*30))
                .claim("accountNumber", accountNumber)
                .signWith(getSignKey())
                .signWith(getSignKey(), SignatureAlgorithm.HS256).compact();
    }
    

    
    public boolean isTokenValid(String token) {
     try {
            Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token);
            //OK, we can trust this JWT
        return true;
        } catch (JwtException e) {
            //don't trust the JWT!
            return false; 
        }
    }
    
    public long getAccountNumber(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody().get("accountNumber", Long.class);
    }
    
    public String getSubject(String token){
        return Jwts.parserBuilder().setSigningKey(getSignKey()).build().parseClaimsJws(token).getBody().getSubject();
    }

    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }   
}
