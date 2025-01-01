#version 460 core

in vec3 position; 
in vec3 color;
in vec2 textureCoord;

out vec3 fragColor;
out vec2 fragTextureCoord;

uniform mat4 model;
uniform mat4 view;
uniform mat4 projection;
uniform vec3 overrideColor;

void main(){
    gl_Position = projection * view * model * vec4(position, 1.0);
    fragColor = overrideColor;
    fragTextureCoord = textureCoord;
}
 
