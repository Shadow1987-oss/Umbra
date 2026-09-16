package gg.umbra.utils.render;

import gg.umbra.Umbra;
import gg.umbra.utils.render.BufferedGuiRenderPrimitives;
import gg.umbra.utils.render.FloatBufferObject;
import gg.umbra.utils.render.GlImageTexture;
import gg.umbra.utils.render.GlTextureUnitState;
import gg.umbra.utils.render.IntBufferObject;
import gg.umbra.utils.render.OpenGlBackendHolder;
import gg.umbra.utils.render.PrimitiveTopology;
import gg.umbra.utils.render.RenderBatch;
import gg.umbra.utils.render.RenderBatchBuilder;
import gg.umbra.utils.render.RenderBatchManager;
import gg.umbra.utils.render.RenderBatchShaderProgram;
import gg.umbra.utils.render.RenderMatrix4f;
import gg.umbra.utils.render.VertexArrayObject;
import gg.umbra.utils.render.VertexAttributeType;
import gg.umbra.wrapper.impl.GlStateManager;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;

public class RenderBatchBuffer {
    private RenderMatrix4f modelMatrix;
    private final FloatBufferObject vertexBufferObject;
    private IntBuffer indexBuffer;
    private GlImageTexture texture;
    private RenderBatchShaderProgram shaderProgram;
    private float lineWidth;
    private FloatBuffer vertexBuffer;
    private int vertexStride = 0;
    private PrimitiveTopology topology;
    private final VertexArrayObject vertexArrayObject;
    private final IntBufferObject indexBufferObject;

    public void stageBatch(RenderBatch renderBatch) {
        this.texture = renderBatch.getTexture();
        for (RenderBatchBuilder renderBatchBuilder : renderBatch.getBuilders()) {
            this.vertexBuffer.put(renderBatchBuilder.getVertexData());
            this.indexBuffer.put(renderBatchBuilder.getIndices());
        }
        this.topology = renderBatch.getTopology();
        this.modelMatrix = renderBatch.getModelMatrix();
        this.lineWidth = renderBatch.getLineWidth();
    }

    public void deleteResources() {
        this.vertexArrayObject.delete();
        this.vertexBufferObject.delete();
        this.indexBufferObject.delete();
    }

    public int getIndexCapacity() {
        return this.indexBuffer.capacity();
    }

    public void draw() {
        int vertexFloatCount = this.vertexBuffer.position();
        int indexCount = this.indexBuffer.position();
        if (vertexFloatCount == 0 || indexCount == 0) {
            throw new IllegalStateException("Number of vertices can't be 0");
        }
        gg.umbra.wrapper.impl.GL20.w(this.shaderProgram.modelUniformLocation, false, this.modelMatrix.toFloatBuffer());
        if (this.topology == PrimitiveTopology.LINES || this.topology == PrimitiveTopology.LINES_LOOP) {
            OpenGlBackendHolder.backend.enableCapability(2848);
        }
        if (this.topology == null) {
            Umbra.debugLog("Drawmode null: something fucked up");
            return;
        }
        int previousTextureId = GL11.glGetInteger((int)32873);
        if (this.texture != null) {
            GlTextureUnitState.saveAndClearTextureUnitZero();
            this.texture.bind();
        }
        this.vertexBuffer.flip();
        this.indexBuffer.flip();
        this.vertexBufferObject.upload(this.vertexBuffer);
        this.indexBufferObject.upload(this.indexBuffer);
        GL11.glDrawElements((int)this.topology.getOpenGlMode(), (int)indexCount, (int)5125, (long)0L);
        this.clearStagingBuffers();
        if (this.texture != null) {
            GlStateManager.bindTexture(previousTextureId);
            GlTextureUnitState.restoreTextureUnitZero();
        }
        if (this.topology == PrimitiveTopology.LINES || this.topology == PrimitiveTopology.LINES_LOOP) {
            OpenGlBackendHolder.backend.disableCapability(2848);
        }
    }

    public RenderBatchBuffer(RenderBatchShaderProgram shaderProgram, int maxVertices, VertexAttributeType ... vertexAttributes) {
        VertexAttributeType[] attributes = vertexAttributes;
        int attributeCount = attributes.length;
        for (int attributeIndex = 0; attributeIndex < attributeCount; ++attributeIndex) {
            VertexAttributeType vertexAttribute = attributes[attributeIndex];
            this.vertexStride += vertexAttribute.count;
        }
        this.shaderProgram = shaderProgram;
        this.topology = null;
        this.vertexBuffer = BufferUtils.createFloatBuffer((int)(maxVertices * this.vertexStride * 4));
        this.indexBuffer = BufferUtils.createIntBuffer((int)(maxVertices * 6));
        this.vertexArrayObject = new VertexArrayObject();
        this.vertexArrayObject.bindAndRememberPrevious();
        this.vertexBufferObject = new FloatBufferObject();
        this.vertexBufferObject.bind();
        GL15.glBufferData((int)34962, (long)((long)this.vertexBuffer.capacity() * 4L), (int)35048);
        int attributeOffset = 0;
        for (int attributeIndex = 0; attributeIndex < vertexAttributes.length; ++attributeIndex) {
            VertexAttributeType vertexAttribute = vertexAttributes[attributeIndex];
            GL20.glVertexAttribPointer((int)attributeIndex, (int)vertexAttribute.count, (int)vertexAttribute.type, (boolean)vertexAttribute.normalized, (int)(this.vertexStride * 4), (long)((long)attributeOffset * 4L));
            GL20.glEnableVertexAttribArray((int)attributeIndex);
            attributeOffset += vertexAttribute.count;
        }
        this.indexBufferObject = new IntBufferObject();
        this.indexBufferObject.bind();
        GL15.glBufferData((int)34963, (long)((long)this.indexBuffer.capacity() * 4L), (int)35048);
        this.configureTextureSampler();
        this.vertexArrayObject.restorePreviousBinding();
    }

    public int getVertexCapacity() {
        return this.vertexBuffer.capacity();
    }

    public void bindResources() {
        this.vertexArrayObject.bindAndRememberPrevious();
        this.shaderProgram.bind();
        this.vertexBufferObject.bind();
        this.indexBufferObject.bind();
        gg.umbra.wrapper.impl.GL20.w(this.shaderProgram.projectionUniformLocation, false, BufferedGuiRenderPrimitives.projectionMatrix.toFloatBuffer());
        gg.umbra.wrapper.impl.GL20.w(this.shaderProgram.viewUniformLocation, false, BufferedGuiRenderPrimitives.viewMatrix.toFloatBuffer());
    }

    public int getVertexStride() {
        return this.vertexStride;
    }

    private void configureTextureSampler() {
        int previousProgramId = GL11.glGetInteger((int)35725);
        try {
            if (this.shaderProgram == null || this.shaderProgram.programId <= 0) {
                throw new IllegalStateException("Universal shader program was not created");
            }
            GL20.glUseProgram((int)this.shaderProgram.programId);
            int samplerUniformLocation = GL20.glGetUniformLocation((int)this.shaderProgram.programId, (CharSequence)"imgTexture");
            if (samplerUniformLocation < 0) {
                throw new IllegalStateException("Failed to resolve shader uniform 'imgTexture' (location=" + samplerUniformLocation + ")");
            }
            GL20.glUniform1i((int)samplerUniformLocation, (int)0);
            int openGlError = GL11.glGetError();
            if (openGlError != 0) {
                throw new IllegalStateException("OpenGL error " + openGlError + " after glUniform1i(imgTexture, 0)");
            }
        }
        catch (Throwable throwable) {
            throw RenderBatchManager.initializationFailure("mesh uniform setup", throwable);
        }
        finally {
            GL20.glUseProgram((int)previousProgramId);
        }
    }

    private static Throwable propagateThrowable(Throwable throwable) {
        return throwable;
    }

    public void clearStagingBuffers() {
        this.indexBuffer.clear();
        this.vertexBuffer.clear();
    }
}
