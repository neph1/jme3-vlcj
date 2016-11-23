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
package vlcj;

import com.jme3.texture.Texture2D;
import com.jme3.util.BufferUtils;
import java.nio.ByteBuffer;
import uk.co.caprica.vlcj.player.direct.DirectMediaPlayer;
import uk.co.caprica.vlcj.player.direct.RenderCallbackAdapter;

/**
 *
 * @author reden (neph1@github)
 */
public class JmeRenderCallbackAdapter extends RenderCallbackAdapter {

    private ByteBuffer imageBuffer;
    private Texture2D texture;
    int width;
    int height;

    public JmeRenderCallbackAdapter(Texture2D texture) {
        super(new int[texture.getImage().getWidth() * texture.getImage().getHeight()]);
        width = texture.getImage().getWidth();
        height = texture.getImage().getHeight();
        imageBuffer = BufferUtils.clone(texture.getImage().getData(0));
        this.texture = texture;
    }

    @Override
    protected void onDisplay(DirectMediaPlayer mediaPlayer, int[] rgbBuffer) {
        // Simply copy buffer to the image and repaint
        if(imageBuffer != null){
            imageBuffer.rewind();
            imageBuffer.clear();
            for(int i : rgbBuffer){
                imageBuffer.putInt(i);
            }
            texture.getImage().setData(BufferUtils.clone(imageBuffer));
            texture.getImage().setUpdateNeeded();
        }
    }

}