package ee.tmtu.libludum.graphics;

import ee.tmtu.oogl.Bindable;
import org.lwjgl.BufferUtils;
import org.lwjgl.util.vector.*;

import java.nio.FloatBuffer;
import java.util.HashMap;
import java.util.Map;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

public class Shader implements Bindable, Disposable {

    private FloatBuffer matBuffer;
    private Map<String, Integer> uniformMap;
    private Map<String, Integer> attribMap;
    public int vertex, fragment, program;
    public int id;

    public Shader(String vertex, String fragment) {
        this.uniformMap = new HashMap<>();
        this.attribMap = new HashMap<>();
        this.matBuffer = BufferUtils.createFloatBuffer(16);

        this.vertex = this.compile(GL_VERTEX_SHADER, vertex);
        this.fragment = this.compile(GL_FRAGMENT_SHADER, fragment);
        this.program = glCreateProgram();
        glAttachShader(this.program, this.vertex);
        glAttachShader(this.program, this.fragment);
        glLinkProgram(this.program);

        int len = glGetProgrami(this.program, GL_ACTIVE_UNIFORMS);
        int strLen = glGetProgrami(this.program, GL_ACTIVE_UNIFORM_MAX_LENGTH);

        for (int i = 0; i < len; i++) {
            String name = glGetActiveUniform(program, i, strLen);
            int id = glGetUniformLocation(program, name);
            this.uniformMap.put(name, id);
        }
    }

    public int compile(int type, String source) {
        int shader = glCreateShader(type);
        glShaderSource(shader, source);
        glCompileShader(shader);
        return shader;
    }

    public int getUniform(String name) {
        int loc;
        if (this.uniformMap.containsKey(name)) {
            loc = this.uniformMap.get(name);
        } else {
            loc = glGetUniformLocation(this.program, name);
            this.uniformMap.put(name, loc);
        }
        return loc;
    }

    public int getAttribute(String name) {
        int loc;
        if (this.attribMap.containsKey(name)) {
            loc = this.attribMap.get(name);
        } else {
            loc = glGetAttribLocation(this.program, name);
            this.attribMap.put(name, loc);
        }
        return loc;
    }

    public void setUniformf(int loc, float x, float y) {
        glUniform2f(loc, x, y);
    }

    public void setUniformf(int loc, float x, float y, float z) {
        glUniform3f(loc, x, y, z);
    }

    public void setUniformf(int loc, float x, float y, float z, float w) {
        glUniform4f(loc, x, y, z, w);
    }

    public void setUniformi(int loc, int x, int y) {
        glUniform2i(loc, x, y);
    }

    public void setUniformi(int loc, int x, int y, int z) {
        glUniform3i(loc, x, y, z);
    }

    public void setUniformi(int loc, int x, int y, int z, int w) {
        glUniform4i(loc, x, y, z, w);
    }

    public void setUniformf(String name, float x, float y) {
        glUniform2f(this.getUniform(name), x, y);
    }

    public void setUniformf(String name, float x, float y, float z) {
        glUniform3f(this.getUniform(name), x, y, z);
    }

    public void setUniformf(String name, float x, float y, float z, float w) {
        glUniform4f(this.getUniform(name), x, y, z, w);
    }

    public void setUniformi(String name, int x, int y) {
        glUniform2i(this.getUniform(name), x, y);
    }

    public void setUniformi(String name, int x, int y, int z) {
        glUniform3i(this.getUniform(name), x, y, z);
    }

    public void setUniformi(String name, int x, int y, int z, int w) {
        glUniform4i(this.getUniform(name), x, y, z, w);
    }

    public void setUniformf(String name, Vector2f v) {
        setUniformf(getUniform(name), v);
    }

    public void setUniformf(String name, Vector3f v) {
        setUniformf(getUniform(name), v);
    }

    public void setUniformf(String name, Vector4f v) {
        setUniformf(getUniform(name), v);
    }

    public void setUniformf(int loc, Vector2f v) {
        setUniformf(loc, v.x, v.y);
    }

    public void setUniformf(int loc, Vector3f v) {
        setUniformf(loc, v.x, v.y, v.z);
    }

    public void setUniformf(int loc, Vector4f v) {
        setUniformf(loc, v.x, v.y, v.z, v.w);
    }

    public void setUniformMatrix(String name, boolean transpose, Matrix2f mat) {
        setUniformMatrix(getUniform(name), transpose, mat);
    }

    public void setUniformMatrix(String name, boolean transpose, Matrix3f mat) {
        setUniformMatrix(getUniform(name), transpose, mat);
    }

    public void setUniformMatrix(String name, boolean transpose, Matrix4f mat) {
        setUniformMatrix(getUniform(name), transpose, mat);
    }

    public void setUniformMatrix(int loc, boolean transpose, Matrix2f mat) {
        this.matBuffer.clear();
        mat.store(this.matBuffer);
        this.matBuffer.flip();
        glUniformMatrix4(loc, transpose, this.matBuffer);
    }

    public void setUniformMatrix(int loc, boolean transpose, Matrix3f mat) {
        this.matBuffer.clear();
        mat.store(this.matBuffer);
        this.matBuffer.flip();
        glUniformMatrix4(loc, transpose, this.matBuffer);
    }

    public void setUniformMatrix(int loc, boolean transpose, Matrix4f mat) {
        this.matBuffer.clear();
        mat.store(this.matBuffer);
        this.matBuffer.flip();
        glUniformMatrix4(loc, transpose, this.matBuffer);
    }

    @Override
    public void bind() {
        glUseProgram(this.id);
    }

    @Override
    public void unbind() {
        glUseProgram(0);
    }

    @Override
    public void dispose() {
        glDeleteProgram(this.id);
    }

}
