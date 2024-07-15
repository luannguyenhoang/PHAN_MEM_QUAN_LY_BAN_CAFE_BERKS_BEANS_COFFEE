package view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import view.Login;

@SpringBootApplication
public class EmailApplication {

    @Autowired
    private sendEmail sendsevice;

    public static void main(String[] args) {
        SpringApplication.run(EmailApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void sendEmail() {
        sendsevice.send(Login.emailAdd, "Mã Xác Minh App Berk's Beans", "Mã xác minh : "+Integer.toString(Login.otp1));
   //     sendsevice.send(Login.emailAdd, "Mã Xác Minh App Berk's Beans", "Chim to dần ");
    }

}
