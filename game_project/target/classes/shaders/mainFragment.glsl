#version 330 core

out vec4 outColor;

in vec3 passColor;

void main(){
    outColor = vec4(passColor, 1.0);
}