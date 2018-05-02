#version 330 core

layout (location = 0) in vec4 position;
layout (location = 1) in vec2 textureCoordinates;

uniform mat4 projection_matrix;
uniform mat4 model_matrix = mat4(1.0);

out DATA {
     vec2 textureCoordinates;
} vs_out;

void main(){
    gl_Position = projection_matrix * model_matrix * position;
    vs_out.textureCoordinates = textureCoordinates;
}
