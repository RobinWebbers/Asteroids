#version 330 core

layout (location = 0) out vec4 color;

uniform sampler2D tex;

in DATA{
    vec2 textureCoordinates;    
} fs_in;

void main(){
    color = texture(tex, fs_in.textureCoordinates);
    if(color.w < 1.0){
        discard;
    }
}