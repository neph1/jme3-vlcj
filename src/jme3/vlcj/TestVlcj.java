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

import com.jme3.app.SimpleApplication;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Quad;
import com.jme3.texture.Image;
import com.jme3.texture.Texture2D;
import com.jme3.util.BufferUtils;
import java.awt.image.BufferedImage;
import uk.co.caprica.vlcj.component.DirectMediaPlayerComponent;
import uk.co.caprica.vlcj.discovery.NativeDiscovery;
import uk.co.caprica.vlcj.player.direct.BufferFormat;
import uk.co.caprica.vlcj.player.direct.BufferFormatCallback;
import uk.co.caprica.vlcj.player.direct.RenderCallback;
import uk.co.caprica.vlcj.player.direct.format.RV32BufferFormat;

/**
 * vlcj test
 * @author reden
 */
public class TestVlcj extends SimpleApplication {

    
    public static void main(String[] args) {
        new NativeDiscovery().discover();
        TestVlcj app = new TestVlcj(args);
        app.start();
    }
    
    private DirectMediaPlayerComponent mediaPlayerComponent;
    private Texture2D texture;
    private Quad quad;
    
    private String mediaFile = "test_180.mp4";
    
    public TestVlcj(String[] args){
//        mediaFile = args[0];
    }

    @Override
    public void simpleInitApp() {
        flyCam.setEnabled(false);
        texture = new Texture2D();
        Image image = new Image();
        image.setWidth(settings.getWidth());
        image.setHeight(settings.getHeight());
        image.setFormat(Image.Format.BGRA8);
        image.setData(BufferUtils.createByteBuffer(image.getWidth()*image.getHeight()*4));
        texture.setImage(image);
        setupView();
        
        BufferFormatCallback bufferFormatCallback = new BufferFormatCallback() {
            @Override
            public BufferFormat getBufferFormat(int sourceWidth, int sourceHeight) {
                
                return new RV32BufferFormat(settings.getWidth(), settings.getHeight());
            }
        };
        
        mediaPlayerComponent = new DirectMediaPlayerComponent(bufferFormatCallback) {
            @Override
            protected RenderCallback onGetRenderCallback() {
                return new JmeRenderCallbackAdapter(texture);
            }
        };
        mediaPlayerComponent.getMediaPlayer().playMedia(mediaFile);
        
        stateManager.attach(new VlcControlsAppState(mediaPlayerComponent));
    }
    
    private void setupView(){
        float frustumSize = settings.getHeight();
        float aspect = (float) cam.getWidth() / cam.getHeight();
        cam.setFrustum(0, 20, -aspect * frustumSize, aspect * frustumSize, frustumSize, -frustumSize);
        cam.setParallelProjection(true);
        
        
        quad = new Quad(settings.getWidth(), settings.getHeight(), true);
        
        Geometry screen = new Geometry("Box", quad);
        screen.move(-settings.getWidth() * 0.5f, -settings.getHeight() * 0.5f, 0);
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        mat.setColor("Color", ColorRGBA.White);
        mat.setTexture("ColorMap", texture);
        screen.setMaterial(mat);

        rootNode.attachChild(screen);
        
    }
    
    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }

    @Override
    public void stop() {
        mediaPlayerComponent.release();
        System.exit(0);
        super.stop(); 
        
    }
    
}
