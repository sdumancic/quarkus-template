package auth.eventresource;

import auth.event.UserLoggedIn;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Lock;
import jakarta.ejb.LockType;
import jakarta.enterprise.event.ObservesAsync;
import jakarta.inject.Singleton;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.sse.OutboundSseEvent;
import jakarta.ws.rs.sse.Sse;
import jakarta.ws.rs.sse.SseBroadcaster;
import jakarta.ws.rs.sse.SseEventSink;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Singleton
@Path("logged-in-events")
@Slf4j
public class LoggedInEventResource {

    @Context
    Sse sse;
    private SseBroadcaster sseBroadcaster;
    private List<UserLoggedIn> allLogins = new ArrayList<>();

    @PostConstruct
    private void initSseBroadcaster(){
        sseBroadcaster = sse.newBroadcaster();
    }

    @GET
    @Produces(MediaType.SERVER_SENT_EVENTS)
    @Lock(LockType.READ)
    public void streamLoggedInUsers(@Context SseEventSink sseEventSink,
                                    @HeaderParam(HttpHeaders.LAST_EVENT_ID_HEADER) @DefaultValue("-1") int lastEventId){
        sseBroadcaster.register(sseEventSink);
        if (lastEventId >= 0){
            resendMissingLogins(sseEventSink, lastEventId);
        }
    }

    private void resendMissingLogins(SseEventSink sseEventSink, int lastEventId) {
        for (int i = lastEventId; i < allLogins.size(); i++){
            OutboundSseEvent event = createEvent(allLogins.get(i), i + 1);
            sseEventSink.send(event);
        }
    }

    /*
        All new logins are stored into array. When new client connects all logins are resend to it
     */
    @Lock
    public void onLoggedInUser(@ObservesAsync UserLoggedIn userLoggedIn){
        sseBroadcaster.broadcast(createEvent(userLoggedIn,allLogins.size() + 1));
        allLogins.add(userLoggedIn);
    }

    private OutboundSseEvent createEvent(UserLoggedIn userLoggedIn, int eventId ) {
        //return sse.newEvent(userLoggedIn.getUsername(), userLoggedIn.toString());
        return sse.newEventBuilder().id(String.valueOf(eventId)).data(userLoggedIn.toString()).build();
    }
}


