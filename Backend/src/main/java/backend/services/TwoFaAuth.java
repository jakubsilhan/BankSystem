
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;


@Component
public class TwoFaAuth {
    private static final String DATAPATH = System.getProperty("user.dir") + File.separator + "data"+ File.separator + "2fa.json";
    private static File dataFile;    
    
    @Autowired
    JavaMailSender emailSender;
    
    public TwoFaAuth(){
      this.dataFile = new File(DATAPATH);  
    };
    
    public TwoFaAuth(File file){
        this.dataFile = file;
    }
        
    public void sendMail(String mail, String code){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("kubix.development@gmail.com");
        message.setTo(mail);
        message.setSubject("Verification code");
        message.setText("The verification code for you bank is: " + code);
        emailSender.send(message);
    }
    
    public String generateCode(){
        Random rnd = new Random();
        String code = rnd.nextInt(1000,10000)+"";        
        return code;
    }
    
    public String saveCode(String email) throws IOException{
        EmailCode code = new EmailCode(email, generateCode());
        ObjectMapper objectMapper = new ObjectMapper();
        List<EmailCode> objects = objectMapper.readValue(this.dataFile, new TypeReference<List<EmailCode>>(){});
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
        //Files.write(this.dataFile.toPath(), mapper.writeValueAsBytes(objects));
        mapper.writeValue(dataFile, objects);
        return code.getCode();
    }
    
    public boolean validateCode(String email, String code) throws IOException{
        List<EmailCode> objects = new ObjectMapper().readValue(this.dataFile, new TypeReference<List<EmailCode>>(){});
        EmailCode toVerify = objects.stream()
            .filter(obj -> obj.getEmail().equals(email))
            .findFirst()
            .orElse(null);
        if (toVerify == null) {
            return false;
        }
        return (toVerify.getCode().equals(code));
    }
    
}
