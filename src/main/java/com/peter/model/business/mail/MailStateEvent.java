package com.peter.model.business.mail;

/**
 * Created by andreajacobsson on 2016-09-27.
 */
public class MailStateEvent {

    private boolean running;

    MailStateEvent(boolean running) {

        this.running = running;
    }

    public boolean isRunning() {
        return running;
    }
}
