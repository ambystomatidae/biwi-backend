package org.biwi.acme.scheduler;

import org.biwi.rest.AuctionsActive;

import java.util.TimerTask;

public class ScheduleBean extends TimerTask {

    private AuctionsActive aa;

    public ScheduleBean(AuctionsActive aa){
        this.aa = aa;
    }

    public void run(){
        aa.setOpen(false);
        System.out.println("NO RUN");
    }

}
