package me.jellysquid.mods.sodium.client.gl.buffer;

import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL31;

import java.nio.ByteBuffer;

public class GlBufferTexture {
    private final int glTexHandle;

    private final int glBufferHandle;

    private int bufferSize = 0;

    public GlBufferTexture() {
        this.glTexHandle = GlStateManager._genTexture();
        this.glBufferHandle = GlStateManager._glGenBuffers();
    }

    public void uploadData(ByteBuffer data, int size) {
        GL31.glBindBuffer(GL31.GL_TEXTURE_BUFFER, this.glBufferHandle);
        if (size > this.bufferSize) {
            RenderSystem.glBufferData(GL31.GL_TEXTURE_BUFFER, data, GlConst.GL_DYNAMIC_DRAW);
            this.bufferSize = size;
        } else {
            GL15.glBufferSubData(GL31.GL_TEXTURE_BUFFER, 0, data);
        }
    }

    public void bind() {
        GL11.glBindTexture(GL31.GL_TEXTURE_BUFFER, this.glTexHandle);
        GL31.glTexBuffer(GL31.GL_TEXTURE_BUFFER, GL31.GL_R32UI, this.glBufferHandle);
        RenderSystem.setShaderTexture(3, this.glTexHandle);
    }
}
