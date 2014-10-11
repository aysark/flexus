/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ch.mbae.pusher.transport;

import ch.mbae.pusher.PusherChannel;
import ch.mbae.pusher.PusherCredentials;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author marcbaechinger
 */
public class GAEPusherTransportTest implements PusherCredentials {
    private PusherChannel channel;
    
    
    
    
    @Before
    public void setUp() {
        this.channel = new PusherChannel("gae", PusherCredentials.APPLICATION_ID,
                PusherCredentials.APPLICATION_KEY, PusherCredentials.APPLICATION_SECRET, 
                new GAEPusherTransport());
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of fetch method, of class GAEPusherTransport.
     */
    @Test
    @Ignore
    public void testFetch() throws Exception {
        channel.pushEvent("gae-event", "{'transport': 'GAE'}");
    }
}
