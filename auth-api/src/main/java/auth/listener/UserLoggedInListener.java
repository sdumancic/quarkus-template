package auth.listener;

import auth.event.UserLoggedIn;
import jakarta.enterprise.event.Observes;
import jakarta.enterprise.event.ObservesAsync;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserLoggedInListener {

    public void onUserLoggedIn(@ObservesAsync UserLoggedIn userLoggedIn){
        log.info("new user logged in {}", userLoggedIn.toString());
    }
}
