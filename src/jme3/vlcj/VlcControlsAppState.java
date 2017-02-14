/*

 * <Simple integration of VLCJ for jMonkeyEngine 3>

 *     Copyright (C) <2016>  <Rickard Ed�n>
 * 
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.

 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jme3.vlcj;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;

/**
 *
 * @author reden (neph1@github)
 */
public class VlcControlsAppState extends BaseAppState implements ActionListener{

    public static final String PAUSE = "PAUSE";
    
    private Application app;
    
    private boolean paused = false;
    private DirectMediaPlayerComponent mediaPlayer;
    
    public VlcControlsAppState(DirectMediaPlayerComponent mediaPlayer){
        this.mediaPlayer = mediaPlayer;
    }
    
    @Override
    protected void initialize(Application app) {
        this.app = app;
    }

    @Override
    protected void cleanup(Application app) {
    }

    @Override
    protected void onEnable() {
        app.getInputManager().addMapping(PAUSE, new KeyTrigger(KeyInput.KEY_SPACE));
        app.getInputManager().addListener(this, PAUSE);
    }

    @Override
    protected void onDisable() {
        app.getInputManager().deleteTrigger(PAUSE, new KeyTrigger(KeyInput.KEY_SPACE));
        app.getInputManager().removeListener(this);
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        switch(name){
            case PAUSE:
                if(isPressed){
                    paused = !paused;
                    if(paused){
                        mediaPlayer.getMediaPlayer().pause();
                    } else {
                        mediaPlayer.getMediaPlayer().play();
                    }
                    
                }
                break;
        }
    }
    
    
}
