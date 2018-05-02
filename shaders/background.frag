#version 330 core

layout (location = 0) out vec4 color;

uniform sampler2D tex;

in DATA{
    vec2 textureCoordinates;    
} fs_in;

void main(){
    color = texture(tex, fs_in.textureCoordinates);
    if(color.x == 0.0 && color.y == 0.0 && color.z == 0.0){
        discard;
    }
}
