
package backend.services;

import backend.classes.EmailCode;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import org.springframework.stereotype.Component;


@Component
public class TwoFaAuth {
    
    private static final String DATAPATH = System.getProperty("user.dir") + File.separator + "data"+ File.separator + "2fa.json";
    
    public void sendMail(String mail, String code){
        //complete this method
    }
    
    public String generateCode(){
        Random rnd = new Random();
        String code = rnd.nextInt(1000,10000)+"";        
        return code;
    }
    
    public void saveCode(String email) throws IOException{
        EmailCode code = new EmailCode(email, generateCode());
        List<EmailCode> objects = new ObjectMapper().readValue(Paths.get(DATAPATH).toFile(), new TypeReference<List<EmailCode>>(){});
        EmailCode toUpdate = objects.stream()
            .filter(obj -> obj.getEmail().equals(code.getEmail()))
            .findFirst()
            .orElse(null);
        
        if (toUpdate != null) {
            toUpdate.setCode(code.getCode());
        }else{
            objects.add(code);
        }            
        
        ObjectMapper mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        Files.write(Paths.get(DATAPATH), mapper.writeValueAsBytes(objects));
    }
    
    public boolean validateCode(String email, String code) throws IOException{
        List<EmailCode> objects = new ObjectMapper().readValue(Paths.get(DATAPATH).toFile(), new TypeReference<List<EmailCode>>(){});
        EmailCode toVerify = objects.stream()
            .filter(obj -> obj.getEmail().equals(email))
            .findFirst()
            .orElse(null);
        return (toVerify.getCode().equals(code));
    }
    
    public static void main(String[] args) throws IOException {
        TwoFaAuth auth = new TwoFaAuth();
        System.out.println(auth.validateCode("john.doe2@gmail.com","9314"));
    }
}
